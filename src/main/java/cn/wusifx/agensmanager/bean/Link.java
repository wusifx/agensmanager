package cn.wusifx.agensmanager.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by huangchao-015 on 2018/8/6.
 */
public class Link {
    @Getter @Setter
    private Integer id;
    @Getter
    @Setter
    private String sourceVLabel;
    @Getter
    @Setter
    private String sourceProperty;
    @Getter
    @Setter
    private String targetVLabel;
    @Getter
    @Setter
    private String targetProperty;
    @Getter
    @Setter
    private String linkName;
    @Getter
    @Setter
    private String linkType;
    @Getter
    @Setter
    private String linkWeight;



}
