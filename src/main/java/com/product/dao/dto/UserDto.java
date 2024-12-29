package com.product.dao.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {

    private String username;
    private String firstname;
    private String email;
    private String password;
}
