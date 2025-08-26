package com.example.common.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record GeneralResponse<RESPONSE>(RESPONSE response, List<? extends MessageCodeContainer> messages) {

    public GeneralResponse(RESPONSE response) {
        this(response, null);
    }

    public GeneralResponse(List<? extends MessageCodeContainer> messages) {
        this(null, messages);
    }

    public GeneralResponse(MessageCodeContainer... messages) {
        this(null, Optional.ofNullable(messages).map(Arrays::asList).orElse(List.of()));
    }

}
