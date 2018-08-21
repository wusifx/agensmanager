package cn.wusifx.agensmanager.webhook;

import cn.wusifx.agensmanager.bean.Link;
import com.google.common.eventbus.EventBus;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebHookEventBus extends EventBus{
    public enum OperationType {
        INSERT,
        DELETE,
        UPDATE,
        CREATE,
        DROP
    }
    public enum DbType {
        RDB,
        GRAPH
    }
    @Autowired
    @Getter
    private Subscriber subscriber;
    public WebHookEventBus(){
        this.register(subscriber);
    }

    public void send(Map<String,Object> data){
        this.post(data);
    }

    public void sendLink(DbType dbType,OperationType operationType, Link link){
        Map<String,Object> linkMap = new HashMap<>();
        linkMap.put("dbType",dbType.name());
        linkMap.put("operationType",operationType.name());
        linkMap.put("type","link");
        linkMap.put("link",link);
        this.send(linkMap);
    }

    public void sendVertex(DbType dbType,OperationType operationType,String vLabel, Map<String, Object> data){
        Map<String,Object> vertexMap = new HashMap<>();
        vertexMap.put("dbType",dbType.name());
        vertexMap.put("operationType",operationType.name());
        vertexMap.put("type","vlabel");
        vertexMap.put("name",vLabel);
        vertexMap.put("data",data);
        this.send(vertexMap);
    }

    public void sendEdge(DbType dbType,OperationType operationType,String eLabel, Map<String, Object> data){
        Map<String,Object> edgeMap = new HashMap<>();
        edgeMap.put("dbType",dbType.name());
        edgeMap.put("operationType",operationType.name());
        edgeMap.put("type","elabel");
        edgeMap.put("name",eLabel);
        edgeMap.put("data",data);
        this.send(edgeMap);
    }
}
