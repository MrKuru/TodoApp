package com.hb.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountViewResponse {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdTime;
}
