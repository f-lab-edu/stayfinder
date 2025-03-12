package com.vacation.platform.api.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    // yyyyMMdd → LocalDateTime 변환 (00:00:00 설정)
    public static LocalDateTime parseStartDate(String dateString) {
        if (dateString == null || dateString.isBlank()) return null;
        LocalDate localDate = LocalDate.parse(dateString, DATE_FORMAT);
        return localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toLocalDateTime(); // 00:00:00
    }

    // yyyyMMdd → LocalDateTime 변환 (23:59:59 설정)
    public static LocalDateTime parseEndDate(String dateString) {
        if (dateString == null || dateString.isBlank()) return null;
        LocalDate localDate = LocalDate.parse(dateString, DATE_FORMAT);
        return localDate.atTime(23, 59, 59); // 23:59:59
    }

}
