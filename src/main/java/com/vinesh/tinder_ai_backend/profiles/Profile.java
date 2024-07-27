package com.vinesh.tinder_ai_backend.profiles;

import groovy.transform.builder.Builder;

@Builder
public record Profile(
        String id,
        String firstName,
        String lastName,
        int age,
        String ethnicity,
        Gender gender,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
) {
}
