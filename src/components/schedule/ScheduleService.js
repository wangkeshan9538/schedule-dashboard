import axios from 'axios'
import qs from 'qs';

const host = '';

export const addjobsUrl = 'addjobs';
export const addTriggerUrl = 'addTrigger';
export const getAllJobsAndTriggerUrl = "getAllJobsAndTrigger";
export const pauseJobUrl = "pauseJob";
export const pauseTirggerUrl = "pauseTirgger";
export const updateTriggrtOfJobUrl = "updateTriggrtOfJob";
export const deleteJobUrl = "deleteJob";
export const deleteTriggerUrl = "deleteTrigger";
export const triggerJobUrl = "triggerJob";
export const startTriggerUrl = "startTrigger";
export const startJobUrl = "startJob";

export function get(url, params) {
    return axios.get(host + '/quartz/' + url, {params})
}

export function formDataPost(url, params) {
    var str = qs.stringify(params);
    const wholeUrl = host + '/quartz/' + url;
    return axios({
        method: 'post',
        url: wholeUrl,
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        data: str
    });
}

export function formDataDelete(url, params) {
    var str = qs.stringify(params);
    const wholeUrl = host + '/quartz/' + url;
    return axios({
        method: 'delete',
        url: wholeUrl,
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        data: str
    });
}

//将后端给出的数据格式 转化成 表格能处理的格式
//并提供 每个Job 的所占行号的 开始行号 和 count 方便后面合并单元格的处理
export function convertJobData(d) {
    let mergeInfo = new Map(); //提供 合并单元格的信息，
    let data = [];
    let sumCount = 0;
    for (let job in d) {
        let item = d[job];
        let jobInfo = {};
        jobInfo = Object.assign(jobInfo, {jobId: item.id, desc: item.desc, targetClass: item.targetClass});

        let triggerCount = 0;
        mergeInfo.set(item.id, [sumCount]);
        for (let trigger in  item.triggers) {
            let item2 = item.triggers[trigger];
            let triggerInfo = {
                cron: item2.cron,
                triggerId: item2.id,
                triggerStatus: item2.state,
                triggerDesc: item2.desc
            };
            let d = {};
            data.push(Object.assign(d, jobInfo, triggerInfo));
            ++triggerCount;
            ++sumCount;
        }

        if (triggerCount == 0) {
            ++sumCount;
            mergeInfo.get(item.id)[1] = 1;
            data.push(jobInfo);
        } else {
            mergeInfo.get(item.id)[1] = triggerCount;
        }

    }
    return {data, mergeInfo};
}
