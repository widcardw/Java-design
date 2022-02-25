<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px" :rules="rules" style="width: 500px; margin: 10px auto;">
      <el-form-item label="角色" prop="isTeacher">
        <el-radio-group v-model="form.isTeacher">
          <el-radio :label="0" disabled>学生</el-radio>
          <el-radio :label="1" disabled>老师</el-radio>
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
      <el-form-item label="等级" prop="level">
        <el-input v-model="form.level" type="number" disabled></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确认修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "info",
  data() {
    return {
      form: {isTeacher: 0, level: 1},
      rules: {
        isTeacher: [{required: true, message: "请选择角色", trigger: "blur"}],
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
        nickname: [{required: true, message: "请输入昵称", trigger: "blur"}],
        level: [{required: true, message: "请输入等级", trigger: "blur"}],
      }
    }
  },
  created() {
    this.form = JSON.parse(JSON.stringify(this.$store.getters.userInfo))
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        console.log("ok")
      })
      console.log(this.form);
    }
  }
}
</script>

<style scoped>

</style>