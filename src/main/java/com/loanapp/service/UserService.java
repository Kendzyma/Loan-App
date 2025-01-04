package com.loanapp.service;

import com.loanapp.dto.DefaultResponse;
import com.loanapp.dto.UserDto;
import com.loanapp.dto.UserRequest;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
public interface UserService {
    UserDto create(UserRequest userRequest);

    UserDto update(UserRequest userRequest, Long id);

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    DefaultResponse deleteUser(Long id);
}
