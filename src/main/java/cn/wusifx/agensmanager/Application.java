package cn.wusifx.agensmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
    public static void main(String[] args){
        new SpringApplicationBuilder(Application.class)
                .build()
                .run(args);
    }
}
