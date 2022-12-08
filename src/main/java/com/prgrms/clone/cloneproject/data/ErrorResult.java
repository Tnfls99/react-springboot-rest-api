package com.prgrms.clone.cloneproject.data;

public class ErrorResult {
    private final String error;
    private final String detail;


    public ErrorResult(String error, String detail) {
        this.error = error;
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public String getDetail() {
        return detail;
    }
}
