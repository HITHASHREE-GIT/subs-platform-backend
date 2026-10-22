package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.dto.UsageRequest;
import com.subsplatform.subs_platform.service.UsageValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    @Autowired
    private UsageValidationService usageValidationService;

    @PostMapping("/consume")
    public String consumeUsage(@RequestBody UsageRequest request) {
        return usageValidationService.validateAndConsume(request);
    }
}