package com.loanapp.service.impl;

import com.loanapp.dto.UserDto;
import com.loanapp.dto.UserRequest;
import com.loanapp.enums.RoleName;
import com.loanapp.exception.BadRequestException;
import com.loanapp.model.Role;
import com.loanapp.model.User;
import com.loanapp.repository.RoleRepository;
import com.loanapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/4/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserRequest userRequest;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role(1L, RoleName.USER);

        user = User.builder()
                .email("john.doe@example.com")
                .password("password")
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .roles(Set.of(role))
                .build();
        userRequest = new UserRequest("John", "Doe", "john.doe@example.com", "password", "1234567890", Set.of(RoleName.USER));

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);
    }

    @Test
    void createUser_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByName((RoleName.USER))).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.create(userRequest);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_UserAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.create(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roleRepository.findByName((RoleName.USER))).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.update(userRequest, 1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUser_UserNotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> userService.getUser(1L));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }
}