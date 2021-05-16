package com.imooc.common.result;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    /* 常用静态方法 */
    public static <T> Result<T> success(T data) {
        Result<T> result = Result.create();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(null);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> result = Result.create();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> create() {
        return new Result<T>();
    }

    protected Result() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

}
