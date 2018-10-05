package com.wks.quartzService.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/***
 * 为了防止任务出现运行异常 ，和统一日志
 *
 */

@Slf4j(topic = "JobsLog")
public abstract class BaseJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long beginTime = System.currentTimeMillis();

        log.info(context.getJobDetail().getDescription() + "开始执行，" + "触发器为：" + context.getTrigger().getDescription());
        try {
            executeJob(context);
        } catch (Exception e) {
            log.error(context.getJobDetail().getDescription(), "出现错误", e);
        }
        Long endTime = System.currentTimeMillis();
        log.info(context.getJobDetail().getDescription() + "执行完毕，" + "触发器为：" + context.getTrigger().getDescription() + "执行时间为：" + (endTime - beginTime) + "Millis");

    }

    public abstract void executeJob(JobExecutionContext context);

}
