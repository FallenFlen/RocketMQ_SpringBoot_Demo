package com.flz.rm.sb.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String message;
    private T data;

    public static <T> Result<T> of(T data) {
        return new Result<>("success", data);
    }

    public static <T> Result<T> of(String message, T data) {
        return new Result<>(message, data);
    }

    public static <T> Result<T> of(String message) {
        return new Result<>(message, null);
    }
}
