package com.ycw.sdk.websocket;

public class WebSocketTemplate {

    private String token;

    private String data;

    public WebSocketTemplate(String token, String data) {
        this.token = token;
        this.data = data;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
