<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px" :rules="rules" style="width: 500px; margin: 10px auto;">
      <el-form-item label="角色" prop="isTeacher">
        <el-radio-group v-model="form.isTeacher">
          <el-radio :label="0" >学生</el-radio>
          <el-radio :label="1" >老师</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirm">
        <el-input v-model="form.confirm" type="password" show-password></el-input>
      </el-form-item>
      <el-form-item label="等级" prop="level" style="display: none">
        <el-input v-model="form.level" type="number" disabled></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" :disabled="loading">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import request from "../utils/request";

export default {
  name: "signup",
  data() {
    return {
      form: {isTeacher: 0, level: 0},
      rules: {
        isTeacher: [{required: true, message: "请选择角色", trigger: "blur"}],
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}, {min: 6, max: 16, message: "密码长度在 6 到 16 个字符", trigger: "blur"}],
        confirm: [{required: true, message: "请输入密码", trigger: "blur"}, {validator: this.validateConfirm, trigger: "blur"}],
        nickname: [{required: true, message: "请输入昵称", trigger: "blur"}],
      },
      loading: false,
    }
  },
  mounted() {},
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          request({
            method: "post",
            url: "/user/add",
            data: this.form,
          }).then(res => {
            console.log(res);
            if (res.code === '200') {
              this.$message({
                message: '注册成功',
                type: 'success'
              });
              this.$router.push('/login')
            } else {
              this.$message({
                message: res.message,
                type: 'error'
              });
            }
          }).catch(err => {
            this.$message({
              message: '注册失败',
              type: 'error'
            });
            console.log(err);
          }).finally(() => {
            this.loading = false;
          })
        } else {
          this.$message.error('输入的信息有误！');
          return false;
        }
      });
    },
    validateConfirm(rule, value, callback) {
      if (value === this.form.password) {
        callback();
      } else {
        callback(new Error('两次输入密码不一致!'));
      }
    },
  },
}
</script>

<style scoped>

</style>