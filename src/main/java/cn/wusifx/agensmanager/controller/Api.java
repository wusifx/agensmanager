package cn.wusifx.agensmanager.controller;

import cn.wusifx.agensmanager.bean.Link;
import cn.wusifx.agensmanager.service.GraphService;
import cn.wusifx.agensmanager.service.LinkRuleService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class Api {
    @Autowired
    GraphService graphService;
    @Autowired
    LinkRuleService linkRuleService;

    @RequestMapping(path = "add/{vlabel}", method = RequestMethod.POST)
    public HttpEntity<Map<String, Object>> addVLabel(@PathVariable String vLabel) {
        Pair<Integer, String> status = graphService.addVlabel(vLabel);
        return ResponseEntity.status(status.getLeft())
                .header("message", status.getRight())
                .build();
    }

    @RequestMapping(path = "insert/{vlabel}", method = RequestMethod.POST)
    public HttpEntity<Map<String, Object>> insert2VLabel(@PathVariable String vLabel, @RequestBody Map<String, Object> data) {
        Pair<Integer, String> status = graphService.insert2Vlabel(vLabel, data);
        return ResponseEntity.status(status.getLeft())
                .header("message", status.getRight())
                .body(data);
    }

    @RequestMapping(path = "remove/{vlabel}", method = RequestMethod.POST)
    public HttpEntity<Map<String, Object>> removeVLabel(@PathVariable String vLabel) {
        Pair<Integer, String> status = graphService.removeVlabel(vLabel);
        return ResponseEntity.status(status.getLeft())
                .header("message", status.getRight())
                .build();
    }

    @RequestMapping(path = "delete/{vlabel}", method = RequestMethod.POST)
    public HttpEntity<Map<String, Object>> delete2VLabel(@PathVariable String vLabel, @RequestBody Map<String, Object> data) {
        Pair<Integer, String> status = graphService.delete2VLabel(vLabel, data);
        return ResponseEntity.status(status.getLeft())
                .header("message", status.getRight())
                .body(data);
    }

    @RequestMapping(path = "add/elinkRule", method = RequestMethod.POST)
    public HttpEntity<Map<String, Object>> addELinkRule(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("sourceVLabel")
                && !data.containsKey("targetVLabel")
                && !data.containsKey("sourceProperty")
                && !data.containsKey("targetProperty")) {
            return ResponseEntity.badRequest().body(data);
        }
        String sourceVLabel = (String) data.get("sourceVLabel");
        String targetVLabel = (String) data.get("targetVLabel");
        String sourceProperty = (String) data.get("sourceProperty");
        String targetProperty = (String) data.get("targetProperty");
        String linkName = "elink";
        String linkType = (String) data.getOrDefault("linkType", "link");
        String linkWeight = (String) data.getOrDefault("linkWeight", "1");
        Link link = new Link();
        link.setSourceVLabel(sourceVLabel);
        link.setSourceProperty(sourceProperty);
        link.setTargetVLabel(targetVLabel);
        link.setTargetProperty(targetProperty);
        link.setLinkName(linkName);
        link.setLinkType(linkType);
        link.setLinkWeight(linkWeight);
        linkRuleService.addLinkRule(link);
        return ResponseEntity.ok().body(data);
    }
}
