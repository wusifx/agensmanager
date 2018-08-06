package cn.wusifx.agensmanager.bean;

import lombok.Getter;
import lombok.Setter;

public class Developer {
    @Getter
    private Integer id;
    @Getter
    @Setter
    private String alias;
    @Getter
    @Setter
    private String developerId;
    @Getter
    @Setter
    private String rule;
    @Getter
    @Setter
    private String securityCode;
}
