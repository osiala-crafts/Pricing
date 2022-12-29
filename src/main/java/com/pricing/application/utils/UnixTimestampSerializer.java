package com.pricing.application.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

public class UnixTimestampSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (instant == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(String.valueOf(instant.getEpochSecond()));
        }
    }
}
