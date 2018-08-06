package cn.wusifx.agensmanager.service;

import cn.wusifx.agensmanager.bean.Link;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by huangchao-015 on 2018/8/6.
 */
@Slf4j
@Service
public class LinkRuleService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Link addLinkRule(Link link) {
        jdbcTemplate.update("insert into link(source_vlabel,source_property,target_vlabel,target_property,link_name,link_type,link_weight) values(?,?,?,?,?,?,?)"
                , new Object[]{link.getSourceVLabel(), link.getSourceProperty(), link.getTargetVLabel(), link.getTargetProperty()
                        , link.getLinkName(), link.getLinkType(), link.getLinkWeight()});
        return link;
    }

    public Link removeLinkRule(int id) {
        String sql = "delete from link where id = ?";
        Link link = jdbcTemplate.queryForObject("select * from link where id = ?", (r, i) -> this.linkMap(r), id);

        jdbcTemplate.update(sql, new Object[]{id});
        return link;
    }

    public Collection<Link> getLinkRules(Link link) {
        String sql = "select * from link where 1=1 ";
        if (link.getId() != null) {
            sql = sql + String.format("and id = %d ", link.getId());
        } else {
            if (link.getSourceVLabel() != null) {
                sql = sql + String.format("and source_vlabel = %s ", link.getSourceVLabel());
            } else if (link.getSourceProperty() != null) {
                sql = sql + String.format("and source_property = %s ", link.getSourceProperty());
            } else if (link.getTargetVLabel() != null) {
                sql = sql + String.format("and target_vlabel = %s ", link.getTargetVLabel());
            } else if (link.getTargetProperty() != null) {
                sql = sql + String.format("and target_property = %s ", link.getTargetProperty());
            } else if (link.getLinkName() != null) {
                sql = sql + String.format("and link_name = %s ", link.getLinkName());
            } else if (link.getLinkType() != null) {
                sql = sql + String.format("and link_type = %s ", link.getLinkType());
            } else if (link.getLinkWeight() != null) {
                sql = sql + String.format("and link_weight = %s ", link.getLinkWeight());
            }
        }
        sql = sql + ";";
        Collection<Link> linkCollection = jdbcTemplate.query(sql, (r, i) -> this.linkMap(r));
        return linkCollection;
    }

    private Link linkMap(ResultSet resultSet) {
        Link item = new Link();
        try {
            item.setId(resultSet.getInt("id"));
            item.setSourceVLabel(resultSet.getString("source_vlabel"));
            item.setSourceVLabel(resultSet.getString("source_property"));
            item.setSourceVLabel(resultSet.getString("target_vlabel"));
            item.setSourceVLabel(resultSet.getString("target_property"));
            item.setLinkName(resultSet.getString("link_name"));
            item.setLinkType(resultSet.getString("link_type"));
            item.setLinkWeight(resultSet.getString("link_weight"));
        } catch (SQLException e) {
            log.error(e.getMessage(), e.fillInStackTrace());
        }
        return item;
    }
}
