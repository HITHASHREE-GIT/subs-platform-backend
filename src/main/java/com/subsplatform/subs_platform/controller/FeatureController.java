package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.entity.Feature;
import com.subsplatform.subs_platform.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    @Autowired
    private FeatureRepository featureRepository;

    // Create a new feature
    @PostMapping
    public Feature createFeature(@RequestBody Feature feature) {
        return featureRepository.save(feature);
    }

    // Get all features
    @GetMapping
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }
}