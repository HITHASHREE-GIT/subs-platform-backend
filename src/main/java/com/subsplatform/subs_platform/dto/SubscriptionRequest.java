package com.subsplatform.subs_platform.dto;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private Long customerId;
    private Long planId;
}