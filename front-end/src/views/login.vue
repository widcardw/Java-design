<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px" class="my-form" :rules="rules">
      <el-form-item label="角色" prop="isTeacher">
        <el-radio-group v-model="form.isTeacher">
          <el-radio :label="0">学生</el-radio>
          <el-radio :label="1">老师</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" class="input-text"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" class="input-text" type="password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" :disabled="loading">登录</el-button>
        <el-button @click="$router.push('/signup')">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "../utils/request";
import {mapState} from "vuex";

export default {
  name: "login",
  data() {
    return {
      form: {},
      rules: {
        isTeacher: [{required: true, message: "请选择角色", trigger: "blur"}],
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}]
      },
      loading: false
    }
  },
  methods: {
    onSubmit() {
      this.$refs['form'].validate((valid)=>{
        if (valid) {
          this.loading = true;
          request({
            method: "post",
            url: "/user/login",
            data: this.form
          }).then((res) => {
            console.log(res);
            if (res.code === '200') {
              this.$message({
                type: 'success',
                message: '登录成功!'
              });
              this.$store.dispatch('Login', res.data);
              this.$emit('loggedin', res.data);
              this.$router.push('/');
            } else {
              this.$message({
                type: 'error',
                message: res.message
              });
            }
          }).catch((err) => {
            this.$message.error(err.message);
            console.log(err);
          }).finally(() => {
            this.loading = false;
          });
        }
      })
    }
  }
}
</script>

<style scoped>
.my-form {
  padding: 20px;
  width: 500px;
  margin: 50px auto;
}

.input-text {
  width: 80%;
}
</style>