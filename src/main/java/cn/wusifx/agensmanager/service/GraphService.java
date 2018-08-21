package cn.wusifx.agensmanager.service;

import cn.wusifx.agensmanager.utils.AgensJsonWriter;
import cn.wusifx.agensmanager.webhook.WebHookEventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by huangchao-015 on 2018/8/6.
 */
@Slf4j
@Service
public class GraphService {
    @Autowired
    WebHookEventBus webHookEventBus;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Pair<Integer, String> insert2Vlabel(String vLabel, Map<String, Object> data) {
        AgensJsonWriter agensJsonWriter = new AgensJsonWriter(new StringWriter());
        String jsonData = agensJsonWriter.toJson(data, data.getClass());
        String sql = String.format("create(:%s %s);", vLabel, jsonData);
        if (jdbcTemplate.update(sql) == 0) {//insert so update row == 0
            webHookEventBus.sendVertex(WebHookEventBus.DbType.GRAPH, WebHookEventBus.OperationType.INSERT, vLabel, data);
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        } else {
            return ImmutablePair.of(HttpStatus.NOT_ACCEPTABLE.value(), "vlabel add data fail");
        }
    }

    public Pair<Integer, String> addVlabel(String vLabel) {
        if (!this.checkParam(vLabel)) {
            return ImmutablePair.of(HttpStatus.BAD_REQUEST.value(), vLabel + " not correct");
        } else {
            String sql = String.format("create vlabel if not exists %s;", vLabel);
            log.debug(sql);
            jdbcTemplate.execute(sql);
            webHookEventBus.sendVertex(WebHookEventBus.DbType.GRAPH, WebHookEventBus.OperationType.CREATE, vLabel, null);
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        }
    }

    public Pair<Integer, String> removeVlabel(String vLabel) {
        if (!this.checkParam(vLabel)) {
            return ImmutablePair.of(HttpStatus.BAD_REQUEST.value(), vLabel + " not correct");
        } else {
            String sql = String.format("drop vlabel if exists %s;", vLabel);
            log.debug(sql);
            jdbcTemplate.execute(sql);
            webHookEventBus.sendVertex(WebHookEventBus.DbType.GRAPH, WebHookEventBus.OperationType.DROP, vLabel, null);
            return ImmutablePair.of(HttpStatus.OK.value(), "SUCCESS");
        }
    }

    public Pair<Integer, String> delete2VLabel(String vLabel, Map<String, Object> data) {
        AgensJsonWriter agensJsonWriter = new AgensJsonWriter(new StringWriter());
        String jsonData = agensJsonWriter.toJson(data, data.getClass());
        String sql = String.format("MATCH (n:%s %s) DETACH DELETE (n);", vLabel, jsonData);
        if (jdbcTemplate.update(sql) == 0) {
            webHookEventBus.sendVertex(WebHookEventBus.DbType.GRAPH, WebHookEventBus.OperationType.DELETE, vLabel, data);
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
