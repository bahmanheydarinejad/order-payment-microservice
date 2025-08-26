package com.example.common.exceptions;

public class BusinessException extends BaseAppException {

    public BusinessException(MessageCodeContainer... exceptionMessages) {
        super(exceptionMessages);
    }

    public BusinessException(String code) {
        super(new ExceptionMessage(code, (Object[]) null));
    }

    public BusinessException(String code, Object... args) {
        super(new ExceptionMessage(code, args));
    }

}
