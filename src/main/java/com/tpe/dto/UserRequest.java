package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Please Provide FirstName")
    private String firstName;

    @NotBlank(message = "Please Provide lastName")
    private String lastName;

    @NotBlank(message = "Please Provide UserName")
    @Size(min = 5,max = 10,message = "please provide a user name min=5,max10 chars long")
    private String userName;

    @NotBlank(message = "Please Provide Password")
    private String password;


}
