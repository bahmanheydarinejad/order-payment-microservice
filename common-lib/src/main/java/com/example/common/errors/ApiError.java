package com.example.common.errors;

import java.time.Instant;
import java.util.Map;

public record ApiError(String type, String title, int status, String detail, String instance, Instant timestamp,
                       Map<String, Object> extra) {
}
