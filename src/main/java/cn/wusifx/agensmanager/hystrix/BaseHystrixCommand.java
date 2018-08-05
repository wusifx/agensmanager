package cn.wusifx.agensmanager.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public abstract class BaseHystrixCommand extends HystrixCommand<ResponseEntity<Map<String, Object>>> {

    protected BaseHystrixCommand(Setter setter) {
        super(setter);
    }

    @Override
    protected ResponseEntity<Map<String, Object>> getFallback() {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
    }
}
