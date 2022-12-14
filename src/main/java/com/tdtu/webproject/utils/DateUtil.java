package com.tdtu.webproject.utils;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateUtil {
    public static final String YYYMMDD_FORMAT_SLASH = "uuuu/MM/dd";
    public static final String DATETIME_FORMAT_SLASH = "uuuu/MM/dd HH:mm:ss";

    public static String getValueFromLocalDateTime(@NonNull LocalDateTime target,
                                                   @NonNull String formatter) {
        return target.format(DateTimeFormatter.ofPattern(formatter));
    }

    public static LocalDateTime parseLocalDateTime(@NonNull String target,
                                                   @NonNull String pattern) {
        return LocalDateTime.parse(target,
                DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT));
    }

    public static LocalDate parseLocalDate(@NonNull LocalDateTime target) {
        return target.toLocalDate();
    }

    public static LocalDateTime parseLocalDateTime(@NonNull LocalDate target) {
        return target.atStartOfDay();
    }
}
