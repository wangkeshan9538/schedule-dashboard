package com.wks.quartzService.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class CommonReturnMsg<T> {

    private String code = "";

    private String msg = "";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    /**
     * 字段名： datas<br>
     * 作用：  返回 数据内容<br>
     * 作者： wks<br>
     * 创建日期： 2018年4月23日<br>
     * 版本： V1.0<br>
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T datas;


    public CommonReturnMsg(ResponseEnum responseEnum) {
        this.code = responseEnum.getState();
        this.msg = responseEnum.getInfo();
        this.timestamp = new Date();
    }

    public CommonReturnMsg(ResponseEnum responseEnum, T datas) {
        this.code = responseEnum.getState();
        this.msg = responseEnum.getInfo();
        this.timestamp = new Date();
        this.datas = datas;
    }


}
