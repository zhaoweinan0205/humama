package com.humama.common.httpclient;

/**
 * @描述: .
 * @作者: ZHaoWeiNan .
 * @创建时间: 2016/9/10 .
 * @版本: 1.0 .
 */
public class HttpResult {

    private Integer code;
    private String data;

    public HttpResult() {
    }

    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
