package com.wks.quartzService.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 本来是想 每个COntroller都有一个自己的ResponseEnum , 来定义自己的接口的 返回代码 ， 但是问题在于 在CommonReturnMsg 中 无法做到针对不同的ResponseEnum ,都可以调用相同的get方法，
 * 原因 是Enum 无法继承 ，ResponseReturnMsg 不确定Enum类型，
 * <p>
 * 还有一个解决办法是 一个系统 就只有一个ResponseEnum ,这样 也方便 返回代码的管理， 除了接口项目 返回值可能会很多 导致比较臃肿之外 就没啥缺点了
 */
public enum ResponseEnum {

    SUCCESS("1000", "操作正常"),

    FAILURE("1001", "操作失败"),

    NONE_JOB("1002", "无此CLASS文件");


    public String state;

    public String info;

    private ResponseEnum(String state, String info) {
        this.state = state;
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }
}
