package cn.wusifx.agensmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck/")
public class HealthCheck {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping(value = "/postgres", method = RequestMethod.GET)
    public Integer checkPostgreSQL(){
        return jdbcTemplate.queryForObject("select 1+1;",Integer.class);
    }
}
