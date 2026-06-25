package com.master.utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormat {

    DateTimeFormat(){
    }

    private static final DateTimeFormatter[] TS_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    };

    public static Timestamp parseTimestamp(String val) {

        if (val == null || val.isBlank() || "NULL".equalsIgnoreCase(val)) {
            return null;
        }

        val = val.trim().replaceAll("\\s+", " ");

        // This method is for if time stamp will have value : 2025-12-08 14:06:40.70 -->
        // this will parse it and insert the data
        int dotIndex = val.indexOf('.');
        if (dotIndex != -1) {
            int fracLen = val.length() - dotIndex - 1;
            if (fracLen == 1) {
                val = val + "00";
            } else if (fracLen == 2) {
                val = val + "0";
            }
        }

        for (DateTimeFormatter f : TS_FORMATS) {
            try {
                LocalDateTime ldt = LocalDateTime.parse(val, f);
                return Timestamp.valueOf(ldt);
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Unsupported TIMESTAMP format: " + val);
    }

    public static void setTimestamp(PreparedStatement ps, int pos, String val)
            throws Exception {

        Timestamp ts = parseTimestamp(val);
        if (ts == null) {
            ps.setNull(pos, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(pos, ts);
        }
    }

    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            // DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH)
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("dd-MMM-yyyy")
                    .toFormatter(Locale.ENGLISH)
    };

    public static Date parseDate(String val) {

        if (val == null || val.isBlank() || "NULL".equalsIgnoreCase(val)) {
            return null;
        }

        val = val.trim();

        for (DateTimeFormatter f : DATE_FORMATS) {
            try {
                LocalDate ld = LocalDate.parse(val, f);
                return Date.valueOf(ld);
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Unsupported DATE format: " + val);
    }

    public static void setDate(PreparedStatement ps, int pos, String val)
            throws Exception {
        Date d = parseDate(val);

        if (d == null) {
            ps.setNull(pos, Types.DATE);
        } else {
            ps.setDate(pos, d);
        }
    }

    public static void setDecimal(PreparedStatement cs, int pos, String val)
            throws Exception {

        if (val == null || val.isEmpty() || "NULL".equalsIgnoreCase(val)) {
            cs.setNull(pos, Types.DECIMAL);
            return;
        }

        try {
            cs.setBigDecimal(pos, new BigDecimal(val.trim()));
        } catch (Exception e) {
            cs.setNull(pos, Types.DECIMAL);
        }
    }

    public static void setString(PreparedStatement cs, int pos, String val)
            throws Exception {

        if (val == null || val.isEmpty() || "NULL".equalsIgnoreCase(val)) {
            cs.setNull(pos, Types.VARCHAR);
        } else {
            cs.setString(pos, val.trim());
        }
    }
}
