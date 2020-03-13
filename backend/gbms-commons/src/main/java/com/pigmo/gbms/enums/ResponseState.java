package com.pigmo.gbms.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public enum ResponseState {
    FAILED(0, "执行失败"), SUCCESS(1, "执行成功"),
    ;

    private long state;
    private String message;

    ResponseState(long state, String message) {
        this.state = state;
        this.message = message;
    }

    public long getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public static class FastJsonEnumSerializer extends JsonSerializer<ResponseState> {

        @Override
        public void serialize(ResponseState responseState, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(responseState.getState());
        }
    }
}
