package com.nhade.api.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final SimpleDateFormat ISO_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US);
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

    @Override
    public LocalDateTime convert(@SuppressWarnings("null") String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.ofInstant(ISO_formatter.parse(source).toInstant(), ZoneOffset.ofHours(8));
        } catch (ParseException e1) {
            try {
                return LocalDateTime.ofInstant(formatter.parse(source).toInstant(), ZoneOffset.ofHours(8));
            } catch (ParseException e2) {
                throw new IllegalArgumentException("Unable to parse deadline: " + source);
            }
        }
    }
}
