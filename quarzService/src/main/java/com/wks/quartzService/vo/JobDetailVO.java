package com.wks.quartzService.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class JobDetailVO {

    public String id;

    public String desc;

    public String targetClass;


    public List<TriggerInfoVO> triggers;

}
