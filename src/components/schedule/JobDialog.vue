<template>

    <el-dialog :title="title" :visible="visible" width="30%" :before-close="handlerClose">
        <el-form>

            <el-form-item label="JobId">
                <el-input v-model="jobId" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="描述">
                <el-input v-model="desc" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="任务类">
                <el-input v-model="targetClass" autocomplete="off"></el-input>
            </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="$emit('close')">取 消</el-button>
            <el-button type="primary" @click="submit">提交</el-button>
        </div>
    </el-dialog>

</template>

<script>
    import {formDataPost, addjobsUrl} from './ScheduleService';


    export default {
        name: "job-dialog",
        props: ['visible'],
        data() {
            return {
                jobId: "",
                desc: "",
                targetClass: ""
            }
        },
        created() {

        },
        methods: {
            handlerClose(done) {
                this.$emit('close')
            },

            submit() {

                let vm = this;
                formDataPost(addjobsUrl, {
                    id: this.jobId,
                    desc: this.desc,
                    className: this.targetClass
                }).then(function (response) {
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
                return "增加Job";
            }
        }


    }
</script>

<style scoped>

</style>
