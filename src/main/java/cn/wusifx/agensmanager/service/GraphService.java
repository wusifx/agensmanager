package cn.wusifx.agensmanager.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by huangchao-015 on 2018/8/6.
 */
@Slf4j
@Service
public class GraphService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Pair<Integer, String> insert2Vlabel(String vLabel, Map<String, Object> data) {
        String sql = "create(? ?);";
        if (jdbcTemplate.update(sql, new Object[]{vLabel, new Gson().toJson(data)}) == 1) {
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        } else {
            return ImmutablePair.of(HttpStatus.NOT_ACCEPTABLE.value(), "vlabel add data fail");
        }
    }

    public Pair<Integer, String> addVlabel(String vLabel) {
        if (this.checkParam(vLabel)) {
            return ImmutablePair.of(HttpStatus.BAD_REQUEST.value(), vLabel + " not correct");
        } else {
            String sql = String.format("create if not exist vlabel %s;", vLabel);
            log.debug(sql);
            jdbcTemplate.execute(sql);
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        }
    }

    public Pair<Integer, String> removeVlabel(String vLabel) {
        if (this.checkParam(vLabel)) {
            return ImmutablePair.of(HttpStatus.BAD_REQUEST.value(), vLabel + " not correct");
        } else {
            String sql = String.format("drop vlabel %s;", vLabel);
            log.debug(sql);
            jdbcTemplate.execute(sql);
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        }
    }

    public Pair<Integer, String> delete2VLabel(String vLabel, Map<String, Object> data) {
        String sql = "MATCH (n:? ?) DETACH DELETE (n);(? ?);";
        if (jdbcTemplate.update(sql, new Object[]{vLabel, new Gson().toJson(data)}) == 1) {
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        } else {
            return ImmutablePair.of(HttpStatus.NOT_ACCEPTABLE.value(), "vlabel delete data fail");
        }
    }

    private final boolean checkParam(String param) {
        if (param.contains(" ") || param.contains(";")) {
            return false;
        } else {
            return true;
        }
    }

}
