package com.tdtu.webproject.utils;

import java.math.BigDecimal;
import java.util.Optional;

public class StringUtil {
    public static String convertBigDecimalToString(BigDecimal number){
        return Optional.ofNullable(number).isPresent() ?
                String.valueOf(number) : null;
    }
}
