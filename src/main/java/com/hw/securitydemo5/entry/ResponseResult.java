package com.hw.securitydemo5.entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}