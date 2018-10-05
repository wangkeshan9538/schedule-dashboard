package com.wks.quartzService.jobs;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;


public class Task1 extends BaseJob {

    @Override
    public void executeJob(JobExecutionContext context) {

        System.out.println("Task执行");

    }

}
