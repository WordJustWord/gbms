package com.pigmo.gbms.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pigmo.gbms.enums.ResponseState;
import com.pigmo.gbms.enums.ResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class ResponseResult<T> {

    private T data;

    @JsonSerialize(using = ResponseState.FastJsonEnumSerializer.class)
    private ResponseState state = ResponseState.SUCCESS;
    private String code = ResultStatusEnum.SUCCESS.getCode();
    private String msg = ResultStatusEnum.SUCCESS.getMessage();

    private static <T> ResponseResult<T> failed(T data, String msg, ResultStatusEnum resultStatus) {
        return new ResponseResult<>(data, ResponseState.FAILED, resultStatus.getCode(), msg);
    }

    private ResponseResult() {
    }

    public static <T> ResponseEntity<ResponseResult<T>> ok(T body) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(body);
        return ResponseEntity.ok(result);
    }

    public static <T> ResponseEntity<ResponseResult<T>> fail(T body) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(body);
        result.setState(ResponseState.FAILED);
        result.setCode(ResultStatusEnum.FAILED.getCode());
        result.setMsg(ResultStatusEnum.FAILED.getMessage());

        return ResponseEntity.ok(result);
    }

    public static <T> ResponseEntity<ResponseResult<T>> of(T body, HttpStatus status) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(body);
        result.setState(ResponseState.FAILED);
        result.setCode(ResultStatusEnum.FAILED.getCode());
        result.setMsg(ResultStatusEnum.FAILED.getMessage());

        return ResponseEntity.status(status).body(result);
    }

    public static ResponseBuilder message(String message) {
        return new ResponseBuilder(message);
    }

    public static class ResponseBuilder {

        private String message;

        public ResponseBuilder(String message) {
            this.message = message;
        }

        public <T> ResponseEntity<ResponseResult<T>> ok(T body) {
            ResponseResult<T> result = new ResponseResult<>();
            result.setMsg(message);
            result.setData(body);
            return ResponseEntity.ok(result);
        }

        public <T> ResponseEntity<ResponseResult<T>> fail(T body) {
            ResponseResult<T> result = new ResponseResult<>();
            result.setData(body);
            result.setState(ResponseState.FAILED);
            result.setMsg(this.message);
            result.setCode(ResultStatusEnum.FAILED.getCode());

            return ResponseEntity.ok(result);
        }

        public <T> ResponseEntity<ResponseResult<T>> of(T data, ResultStatusEnum status) {
            ResponseResult<T> result = new ResponseResult<>();
            result.setData(data);
            result.setState(ResponseState.FAILED);
            result.setMsg(this.message);
            result.setCode(status.getCode());
            return ResponseEntity.ok(result);
        }

        public <T> ResponseEntity<ResponseResult<T>> notFound(T data) {
            return this.of(data, ResultStatusEnum.NOT_FOUND);
        }

        public <T> ResponseEntity<ResponseResult<T>> unknown(T data) {
            return this.of(data, ResultStatusEnum.UNKNOWN);
        }
    }
}
