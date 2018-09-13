package com.yang.server.common.enums;

public enum ResponseCode {
    /** 资源不存在 */
    NOT_FOUND("404", "资源不存在"),
    /** 失败 */
    FAILED("0", "失败"),
    /** 成功 */
    SUCCESS("1", "成功"),
    /** 未登录 */
    TO_LOGIN("401", "未登录"),
    /** 无权限 */
    NOT_PERMISSION("-2", "无权限");

    private String code;
    private String info;

    ResponseCode(String code, String info) {
        this.code = code;
        this.info = info;
    }
    public String getCode() {
        return code;
    }
    public String getInfo() {
        return info;
    }
}
