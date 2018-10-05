package com.wks.quartzService.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TriggerInfoVO {

    public String cron;

    public String id;

    public int state;

    public String desc;

}
