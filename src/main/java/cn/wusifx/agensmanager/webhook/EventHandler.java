package cn.wusifx.agensmanager.webhook;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class EventHandler {
    @Autowired @Qualifier("webHookRestTemplate")
    RestTemplate restTemplate;
    @Getter
    private String url;
    public EventHandler(String url){
        this.url = url;
    }

    public String send(Map<String,Object> data){
        ResponseEntity<String> response = restTemplate.postForEntity(this.url,data,String.class);
        return response.getBody();
    }
}
