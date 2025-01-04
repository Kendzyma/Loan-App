package com.loanapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/2/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserInfoService userDetailsService;
    private static final int MAX_REQUESTS_PER_5_MINUTES = 100;
    private final Cache<String, Integer> requestCountsPerIpAddress;


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String clientIpAddress = request.getRemoteAddr();
        Integer requestCount = requestCountsPerIpAddress.getIfPresent(clientIpAddress);
        if (requestCount == null) {
            requestCount = 0;
        }

        if (requestCount >= MAX_REQUESTS_PER_5_MINUTES) {

            ResponseUtil.writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                    "Too many requests, please try again later.");
            return;
        }
        requestCountsPerIpAddress.put(clientIpAddress, requestCount + 1);

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }


        filterChain.doFilter(request, response);
    }

    private static class ResponseUtil {
        public static void writeErrorResponse(
                HttpServletResponse response, int status, String message) {
            try {
                response.setStatus(status);
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                ErrorResponse errorResponse =
                        ErrorResponse.builder().code(response.getStatus()).message(message).build();
                String jsonResponse = mapper.writeValueAsString(errorResponse);
                response.getWriter().write(jsonResponse);
            } catch (Exception e) {
                log.error("Error writing response", e);
            }
        }

        @Builder
        @Setter
        @Getter
        private static class ErrorResponse {
            private int code;
            private final boolean success = false;
            private final String message;
        }
}
}
