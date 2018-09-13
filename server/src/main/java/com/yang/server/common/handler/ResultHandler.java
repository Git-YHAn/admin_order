package com.yang.server.common.handler;

import com.yang.server.common.enums.ResponseCode;

public class ResultHandler<T> {
    private String code;
    private String message;
    private T data;

    public ResultHandler() {
    }

    public ResultHandler(T data) {
        this(ResponseCode.SUCCESS.getCode(), data);
    }

    public ResultHandler(String code, T data) {
        this(code, "", data);
    }

    public ResultHandler(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    /**
     * 重载成功方法,传入成功msg信息
     */
    public static <T> ResultHandler<T> success(T data) {
        return new ResultHandler<>(data);
    }

    public static <T> ResultHandler<T> success(String msg, T data) {
        return new ResultHandler<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResultHandler<T> error(T data) {
        return error("", data);
    }

    public static <T> ResultHandler<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> ResultHandler<T> error(String msg, T data) {
        return error(ResponseCode.FAILED, msg, data);
    }

    public static <T> ResultHandler<T> error(ResponseCode code, String msg, T data) {
        return new ResultHandler<>(code.getCode(), msg, data);
    }
    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode().equals(code);
    }
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return message;
    }
    public void setMsg(String msg) {
        this.message = msg;
    }
    public T getData() {
        return data;
    }
}
