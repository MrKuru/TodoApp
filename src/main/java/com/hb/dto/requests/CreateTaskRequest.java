package com.hb.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    @NotNull
    private Long id;
    @NotNull
    @Min(3)
    @Max(15)
    private String title;
    @NotNull
    @Min(3)
    @Max(255)
    private String description;
    @NotNull
    private Long todoId;
}
