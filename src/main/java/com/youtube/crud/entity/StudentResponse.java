package com.youtube.crud.entity;


public record StudentResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String photoUrl // /api/students/{id}/photo
) {}
