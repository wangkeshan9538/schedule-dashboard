<template>

    <el-dialog :title="title" :visible="visible" width="30%" :before-close="handlerClose">
        <el-form :model="editData">

            <el-form-item label="JobId">
                <el-input v-model="jobId" autocomplete="off" :readonly="true"></el-input>
            </el-form-item>

            <el-form-item label="triggerID">
                <el-input v-model="editData.triggerID" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="描述">
                <el-input v-model="editData.desc" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="触发规则">
                <el-input v-model="editData.cron" autocomplete="off"></el-input>
            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button type="primary" @click="submit">提交</el-button>
        </div>
    </el-dialog>

</template>

<script>
    import {addTriggerUrl, updateTriggrtOfJobUrl, formDataPost} from './ScheduleService';

    export default {
        name: "trigger-dialog",
        props: ['visible', 'operationType', 'jobId', 'editData'],
        data() {
            return {}
        },
        created() {

        },
        methods: {
            handlerClose(done) {
                this.$emit('close')
            },
            submit() {
                let url;
                let params;
                if (this.operationType == 'edit') {
                    url = updateTriggrtOfJobUrl
                    params = {
                        triggerID: this.editData.triggerID,
                        desc: this.editData.desc,
                        cron: this.editData.cron
                    }
                } else if (this.operationType == 'add') {
                    url = addTriggerUrl;
                    params = {
                        jobId: this.jobId,
                        triggerID: this.editData.triggerID,
                        desc: this.editData.desc,
                        cron: this.editData.cron
                    }
                }
                formDataPost(url, params).then((response) => {
                    if (response.data.code === '1000') {
                        confirm("添加完成");
                    } else {
                        confirm("操作失败")
                    }
                    vm.$emit('close');
                });
            }
        },
        computed: {
            title: function () {
                if (this.operationType == 'edit') {
                    return "编辑触发器";
                } else if (this.operationType == 'add') {
                    return "增加触发器";
                }
            }
        }
    }
</script>

<style scoped>

</style>
