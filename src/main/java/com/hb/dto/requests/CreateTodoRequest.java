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
public class CreateTodoRequest {
    @NotNull
    private Long id;
    @NotNull
    @Min(3)
    @Max(15)
    private String listName;
    @NotNull
    private Long accountId;
}
