package com.ycw.sdk.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ycw.sdk.websocket.WebSocketSession;
import com.ycw.sdk.websocket.WebSocketTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushController {
    @PostMapping("/push")
    public JSONObject push(@RequestParam("data") String data) {
        JSONObject response = new JSONObject();
        try {
            WebSocketTemplate webSocketTemplate = JSONObject.parseObject(data, WebSocketTemplate.class);
            WebSocketSession.send(webSocketTemplate);
            response.put("code", 1);
            response.put("msg", "推送成功");
        } catch (Exception e) {
            response.put("code", 0);
            response.put("msg", "推送失败");
        }
        return response;
    }
}
