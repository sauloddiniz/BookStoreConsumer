package br.com.bookstoreconsumer.adapters.configuration;

public class FeignError {

    private String date;
    private String path;
    private String method;
    private String error;
    private int httpStatus;

    public String getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getError() {
        return error;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
