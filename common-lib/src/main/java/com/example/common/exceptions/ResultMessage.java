package com.example.common.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ResultMessage(String code, String message, @JsonIgnore Object... args) implements
        MessageCodeContainer {

    public ResultMessage(String code, Object... args) {
        this(code, null, args);
    }

    public ResultMessage(String code, String message) {
        this(code, message, (Object[]) null);
    }

    @Override
    public String getCode() {
        return code();
    }
}
