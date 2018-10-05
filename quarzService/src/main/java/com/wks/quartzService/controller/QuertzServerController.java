package com.wks.quartzService.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wks.quartzService.common.CommonReturnMsg;
import com.wks.quartzService.service.QuartzContext;
import com.wks.quartzService.common.ResponseEnum;
import com.wks.quartzService.vo.JobDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 定时任务管理controller
 * 包含 ：
 * 增加任务；
 * 增加触发规则；
 * 暂停任务；
 * 暂停触发规则；
 * 修改任务的调用规则；
 * 获得任务的 列表和 触发器列表；
 * 删除任务；
 * 删除触发规则；
 * 开启触发器；
 * 触发任务；
 * <p>
 * <p>
 * 基础查询
 */


@RestController
@Slf4j
@RequestMapping("quartz")
@Validated
public class QuertzServerController {

    @Autowired
    QuartzContext context;


    @PostMapping("addjobs")
    public CommonReturnMsg addjobs(@NotNull String id, String desc, @NotNull String className) {
        try {
            context.addjobs(
                    id,
                    desc,
                    className
            );
        } catch (SchedulerException e) {
            log.error("增加 JOB 错误", e);
            return new CommonReturnMsg(ResponseEnum.FAILURE);

        } catch (ClassNotFoundException e) {
            log.error("增加 JOB 错误:无此JOB CLass", e);
            return new CommonReturnMsg(ResponseEnum.NONE_JOB);

        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);

    }

    @PostMapping("addTrigger")
    public CommonReturnMsg addTrigger(@NotNull String jobId, @NotNull String triggerID, String desc, @NotNull String cron) {
        try {
            context.addTrigger(jobId, triggerID, desc, cron);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }

        return new CommonReturnMsg(ResponseEnum.SUCCESS);
    }

    @PostMapping("pauseJob")
    public CommonReturnMsg pauseJob(@NotNull String jobId) {
        try {
            context.pauseJob(jobId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }

        return new CommonReturnMsg(ResponseEnum.SUCCESS);

    }

    @PostMapping("pauseTirgger")
    public CommonReturnMsg pauseTrigger(@NotNull String triggerId) {
        try {
            context.pauseTrigger(triggerId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);

    }

    @PostMapping("updateTriggrtOfJob")
    public CommonReturnMsg updateTriggrtOfJob(@NotNull String triggerID, String desc, @NotNull String cron) {
        try {
            context.updateTriggrtOfJob(triggerID, desc, cron);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);
    }

    @GetMapping("getAllJobsAndTrigger")
    public CommonReturnMsg<List<JobDetailVO>> getAllJobsAndTrigger() {
        List<JobDetailVO> jobs;
        try {
            jobs = context.getAllJobsAndTrigger();
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS, jobs);
    }


    @PostMapping("deleteJob")
    public CommonReturnMsg deleteJob(@NotNull String jobId) {
        try {
            context.deleteJob(jobId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);

    }

    @PostMapping("deleteTrigger")
    public CommonReturnMsg deleteTrigger(@NotNull String triggerId) {
        try {
            context.deleteTrigger(triggerId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);

    }

    @PostMapping("triggerJob")
    public CommonReturnMsg triggerJob(@NotNull String jobId) {
        try {
            context.triggerJob(jobId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);
    }


    @PostMapping("startTrigger")
    public CommonReturnMsg startTrigger(@NotNull String triggerId) {
        try {
            context.startTrigger(triggerId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);
    }

    @PostMapping("startJob")
    public CommonReturnMsg startJob(@NotNull String jobId) {
        try {
            context.startJob(jobId);
        } catch (SchedulerException e) {
            return new CommonReturnMsg(ResponseEnum.FAILURE);
        }
        return new CommonReturnMsg(ResponseEnum.SUCCESS);
    }

}
