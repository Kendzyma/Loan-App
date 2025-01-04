package com.loanapp.service.impl;

import com.loanapp.dto.DefaultResponse;
import com.loanapp.dto.UserDto;
import com.loanapp.dto.UserRequest;
import com.loanapp.enums.RoleName;
import com.loanapp.exception.BadRequestException;
import com.loanapp.model.Role;
import com.loanapp.model.User;
import com.loanapp.repository.RoleRepository;
import com.loanapp.repository.UserRepository;
import com.loanapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserDto create(UserRequest userRequest) {

        userRepository.findByEmail(userRequest.email())
                .ifPresent(email -> {
                    throw new BadRequestException("User already exists");
                });

        Set<Role> roles = userRequest.roles().stream().map(role -> roleRepository.findByName(role)
                        .orElseThrow(() ->
                                new BadRequestException(String.format("Role %s does not exist", role.name()))))
                .collect(Collectors.toSet());

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .phoneNumber(userRequest.phoneNumber())
                .roles(roles)
                .build();
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto update(UserRequest userRequest, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Set<Role> roles = userRequest.roles().stream().map(role -> roleRepository.findByName(role)
                        .orElseThrow(() ->
                                new BadRequestException(String.format("Role %s does not exist", role.name()))))
                .collect(Collectors.toSet());

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setRoles(roles);

        User updateUser = userRepository.save(user);

        return modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        userRepository.delete(user);
    }
}
