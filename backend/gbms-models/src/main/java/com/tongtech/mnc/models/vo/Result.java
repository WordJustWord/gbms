package com.tongtech.mnc.models.vo;

import com.tongtech.mnc.enums.ResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    // 响应业务状态
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;

    public static Result success(Object data, String msg) {
        return new Result(ResultStatusEnum.SUCCESS.getCode(), msg, data);
    }

    public static Result failed(Object data, String msg) {
        return new Result(ResultStatusEnum.FAILED.getCode(), msg, data);
    }

    public static Result notFound(Object data, String msg) {
        return new Result(ResultStatusEnum.NOT_FOUND.getCode(), msg, data);
    }

    public static Result unknown(Object data, String msg) {
        return new Result(ResultStatusEnum.UNKNOW.getCode(), msg, data);
    }

    public static Result ok(Integer status,Object data,String msg) {
        return new Result(status,msg,data);
    }

    private Result() {

    }

}
