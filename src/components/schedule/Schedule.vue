<template>
    <div class="schedul_table_wrap">

        <trigger-dialog ref="TriggerDialog"
                        :title="triggerDialogData.title"
                        :jobId="triggerDialogData.jobId"
                        :visible="triggerDialogData.visible"
                        :operationType="triggerDialogData.operationType"
                        :editData="triggerDialogData.editData"
                        @close="closeTriggerDialog"
        ></trigger-dialog>

        <job-dialog ref="JobDialog"
                    :visible="jobDialogVisible"
                    @close="closejobDialog"
        ></job-dialog>


        <el-button id="addJobtn" type="primary" @click="()=>{this.jobDialogVisible=true}">增加Job</el-button>
        <el-table
                :span-method="objectSpanMethod"
                :data="tableData"
                style="width: 100%">


            <el-table-column
                    prop="jobId"
                    label="ID"
                    width="70">
            </el-table-column>

            <el-table-column
                    prop="desc"
                    label="DESC"
                    width="150">
            </el-table-column>

            <el-table-column
                    prop="targetClass"
                    label="任务类"
                    width="150">
            </el-table-column>

            <el-table-column label="触发器">

                <el-table-column
                        prop="triggerId"
                        label="触发ID"
                        width="70">
                </el-table-column>

                <el-table-column
                        prop="cron"
                        label="触发规则"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="triggerDesc"
                        label="描述"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="triggerStatus"
                        label="状态"
                        width="50"
                        :formatter="statusformatter">
                </el-table-column>

                <el-table-column
                        prop="triggerOperation"
                        label="操作"
                        width="300">
                    <template slot-scope="scope" v-if="scope.row.triggerId != null">
                        <el-button
                                size="mini"
                                @click="stopTrigger(scope.$index, scope.row)">暂停
                        </el-button>
                        <el-button
                                size="mini"
                                @click="startTrigger(scope.$index, scope.row)">开启
                        </el-button>
                        <el-button
                                size="mini"
                                @click="editTrigger(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="deleteTrigger(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>

            </el-table-column>

            <el-table-column
                    prop="jobOperation"
                    label="操作"
                    width="400">

                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="addTrigger(scope.$index, scope.row)">增加触发
                    </el-button>
                    <el-button
                            size="mini"
                            @click="stopJob(scope.$index, scope.row)">关闭
                    </el-button>
                    <el-button
                            size="mini"
                            @click="startJob(scope.$index, scope.row)">开启
                    </el-button>
                    <el-button
                            size="mini"
                            @click="triggerJob(scope.$index, scope.row)">触发
                    </el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="deleteJob(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>

        </el-table>
    </div>
</template>

<script>
    import TriggerDialog from './TriggerDialog';
    import JobDialog from './JobDialog';
    import {
        convertJobData,
        get,
        formDataPost,
        formDataDelete,
        pauseJobUrl,
        getAllJobsAndTriggerUrl,
        startTriggerUrl,
        pauseTirggerUrl,
        deleteTriggerUrl,
        triggerJobUrl,
        deleteJobUrl,
        startJobUrl
    } from './ScheduleService'

    export default {
        name: "schedule",
        components: {
            TriggerDialog,
            JobDialog
        },
        data() {
            return {
                tableData: [],
                mergeInfo: {},

                triggerDialogData: {
                    visible: false,
                    title: "",
                    jobId: "",
                    operationType: "",
                    editData: {
                        triggerID: "",
                        desc: "",
                        cron: ""
                    }
                },
                jobDialogVisible: false,

            }
        }/* data*/,

        methods: {

            freshTable() {
                get(getAllJobsAndTriggerUrl).then((response) => {
                    let res = convertJobData(response.data.datas);
                    this.tableData = res.data;
                    this.mergeInfo = res.mergeInfo;
                })
            },

            //关闭 触发器 对话框
            closeTriggerDialog() {
                this.triggerDialogData.visible = false;
                this.freshTable();
            },

            closejobDialog() {
                this.jobDialogVisible = false;
                this.freshTable();
            },

            /*合并单元格*/
            objectSpanMethod({row, column, rowIndex, columnIndex}) {
                if (columnIndex === 0 || columnIndex === 1 || columnIndex === 2 || columnIndex === 8) {
                    let beginEnd = this.mergeInfo.get(row.jobId);
                    if (rowIndex === beginEnd[0]) {
                        return [beginEnd[1], 1];
                    } else {
                        return [0, 0];
                    }
                }
            },

            startTrigger(index, row) {
                formDataPost(startTriggerUrl, {triggerId: row.triggerId}).then((res) => this.resolveResponse(res))
            },

            stopTrigger(index, row) {
                formDataPost(pauseTirggerUrl, {triggerId: row.triggerId}).then((res) => this.resolveResponse(res))
            },

            editTrigger(index, row) {
                this.triggerDialogData = {
                    visible: true,
                    jobId: row.jobId,
                    operationType: "edit",
                    editData: {
                        triggerID: row.triggerId,
                        triggerDesc: row.triggerDesc,
                        cron: row.cron
                    }
                }
            },

            deleteTrigger(index, row) {
                formDataPost(deleteTriggerUrl, {triggerId: row.triggerId}).then((res) => this.resolveResponse(res))
            },


            addTrigger(index, row) {
                this.triggerDialogData = {
                    visible: true,
                    jobId: row.jobId,
                    operationType: "add",
                    editData: {
                        triggerID: "",
                        triggerDesc: "",
                        cron: ""
                    }
                }
            },

            deleteJob(index, row) {
                let vm = this;
                formDataPost(deleteJobUrl, {jobId: row.jobId}).then((res) => this.resolveResponse(res));
            },
            stopJob(index, row) {
                formDataPost(pauseJobUrl, {jobId: row.jobId}).then((res) => this.resolveResponse(res))
            },
            startJob(index, row) {
                formDataPost(startJobUrl, {jobId: row.jobId}).then((res) => this.resolveResponse(res))
            },
            triggerJob(index, row) {
                formDataPost(triggerJobUrl, {jobId: row.jobId}).then((res) => this.resolveResponse(res))
            },

            resolveResponse(response) {
                if (response.data.code === '1000') {
                    confirm("操作完成");
                } else {
                    confirm("操作失败")
                }
                this.freshTable()
            },
            statusformatter(row, column) {
                switch (row.triggerStatus) {
                    case 0:
                        return "正常";
                    case 2:
                        return "错误";
                    case 1:
                        return "停止";
                    case 4:
                        return "阻塞";
                    case -1:
                        return "NONE";
                    case 3:
                        return "完成";
                }
                return row.address;
            }

        }, /*motheds*/
        created() {
            this.freshTable();
        },
    }


</script>

<style>
    #addJobtn {
        margin-bottom: 10px;
        margin-left: 10px;
    }

    .el-table th {
        text-align: center;
    }

    .el-table td {
        text-align: center;
    }

</style>
