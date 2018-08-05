package cn.wusifx.agensmanager.controller;

import cn.wusifx.agensmanager.bean.Developer;
import cn.wusifx.agensmanager.service.DeveloperService;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superman/")
public class Superman {
    @Autowired
    DeveloperService developerService;

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public ResponseEntity<Developer> add(@RequestBody Developer developer) {
        if (StringUtils.isEmpty(developer.getAlias())|| developerService.aliasExist(developer.getAlias())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("message", String.format("alias:%s exist or empty", developer.getAlias()))
                    .body(developer);
        }
        return ResponseEntity.
                ok()
                .body(developerService.add(developer));
    }
    @RequestMapping(path = "refresh/{alias}/securityCode", method = RequestMethod.GET)
    public ResponseEntity<Developer> refreshSecurityCode(@PathVariable String alias) {
        if (!StringUtils.isEmpty(alias) || developerService.aliasExist(alias)) {
            Developer developer = developerService.refreshSecurityCode(alias);
            return ResponseEntity.ok()
                    .body(developer);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("message", String.format("alias:%s not exist or empty", alias))
                    .build();
        }
    }
    @RequestMapping(path = "get/{alias}/developerId", method = RequestMethod.GET)
    public ResponseEntity<Developer> getDeveloperId(@PathVariable String alias) {
        if (!StringUtils.isEmpty(alias) || developerService.aliasExist(alias)) {
            Developer developer = developerService.getDeveloperId(alias);
            return ResponseEntity.ok()
                    .body(developer);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("message", String.format("alias:%s not exist or empty", alias))
                    .build();
        }
    }
}
