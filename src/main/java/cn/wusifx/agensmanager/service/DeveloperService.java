package cn.wusifx.agensmanager.service;

import cn.wusifx.agensmanager.bean.Developer;
import liquibase.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
@Slf4j
@Service
public class DeveloperService {
    public DeveloperService(){
        log.info("TBD");
    }
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean aliasExist(String alias) {
        if (StringUtils.isEmpty(alias)) {
            return true;
        } else {
            Integer count = jdbcTemplate.queryForObject("select count(1) from developer where alias = ?"
                    , new Object[]{alias}
                    , Integer.class);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Developer add(Developer developer) {//alias not empty
        developer.setDeveloperId(UUID.randomUUID().toString());
        developer.setSecurityCode(UUID.randomUUID().toString());
        String shaSecurityCode = DigestUtils.md5DigestAsHex(developer.getSecurityCode().getBytes());
        jdbcTemplate.update("insert into developer(alias,developer_id,rule,security_code) values(?,?,?,?)"
                , new Object[]{developer.getAlias(), developer.getDeveloperId(), developer.getRule(), shaSecurityCode});
        return developer;
    }

    public Developer refreshSecurityCode(String alias) {
        Developer developer = new Developer();
        developer.setAlias(alias);
        developer.setSecurityCode(UUID.randomUUID().toString());
        String shaSecurityCode = DigestUtils.md5DigestAsHex(developer.getSecurityCode().getBytes());
        jdbcTemplate.update("update developer set security_code = ? where alias = ?"
                , new Object[]{shaSecurityCode, developer.getAlias()});
        return developer;
    }

    public Developer getDeveloperId(String alias) {
        Developer developer = new Developer();
        developer.setAlias(alias);
        String developerId = jdbcTemplate.queryForObject("select developer_id from developer where alias = ?"
                , new Object[]{alias}, String.class);
        developer.setDeveloperId(developerId);
        return developer;
    }

    //TODO
    public HttpStatus auth(String accessToken) {
        return HttpStatus.OK;
    }

    //TODO
    public HttpStatus auth(String developerId, String securityCode) {
        return HttpStatus.OK;
    }
}
