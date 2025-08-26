package com.example.common.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public abstract class BaseAppException extends RuntimeException {

    protected final List<? extends MessageCodeContainer> messages;

    public BaseAppException(Throwable t, MessageCodeContainer... exceptionMessages) {
        super(String.format("Exception::[%s], %s",
                Optional.ofNullable(t).map(Throwable::getMessage).orElse(""),
                Optional.ofNullable(exceptionMessages).map(List::of)
                        .map(m -> m.stream().filter(Objects::nonNull)
                                .map(MessageCodeContainer::toString)
                                .collect(Collectors.joining(", ", "[", "]"))
                        )
                        .orElse("")));
        messages = Optional.ofNullable(exceptionMessages).map(List::of).orElse(List.of());
    }

    public BaseAppException(MessageCodeContainer... exceptionMessages) {
        this(null, exceptionMessages);
    }

    public BaseAppException(Throwable t) {
        this(t, (MessageCodeContainer) null);
    }

}
