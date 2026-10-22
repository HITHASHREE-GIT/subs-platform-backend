package com.subsplatform.subs_platform.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
}