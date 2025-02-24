package com.example.thy.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
