package com.subsplatform.subs_platform.dto;

import lombok.Data;

@Data
public class UsageRequest {
    private Long customerId;
    private Long featureId;
    private Integer quantity;
}