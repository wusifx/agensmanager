package cn.wusifx.agensmanager.webhook;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class Subscriber {
    private Collection<EventHandler> eventHandlers = new ConcurrentLinkedQueue<>();

    @Subscribe
    public void hook(Map<String, Object> data) {
        this.eventHandlers.forEach(e -> {
            String response = e.send(data);
            if (!response.contains("success")) {
                log.warn("{}><{}", e.getUrl(), response);
            }
        });
    }

    public void addHandler(EventHandler eventHandler) {
        this.eventHandlers.add(eventHandler);
    }
}
