<template>
  <div>
    <el-form :model="form">
      <el-form-item style="display: none;">
        <el-input v-model="form.authorId" placeholder="作者"></el-input>
      </el-form-item>
      <el-form-item label="分区">
        <el-select v-model="form.courseId" placeholder="请选择分区" style="width: 100%;">
          <el-option v-for="item in courses" :key="item.id" :label="item.courseName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea" v-model="form.content" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "../utils/request";
import fetchCourses from "../utils/fetchCourses";

export default {
  name: "editpost",
  data() {
    return {
      form: {},
      courses: []
    };
  },
  created() {
    this.form = JSON.parse(this.$route.query.post);
    fetchCourses().then(res=>{
      this.courses = res;
    })
    this.form.authorId = this.$store.getters.userInfo.id;
    this.form.courseId = this.form.course.id;
    this.form.author && delete this.form.author;
    this.form.tags && delete this.form.tags;
    this.form.course && delete this.form.course;
  },
  methods: {
    submit() {
      console.log(this.form);
      request({
        method: "put",
        url: "/post/update",
        data: this.form
      }).then(res => {
        console.log(res);
        if (res.code === '200') {
          this.$message.success("更新成功");
          this.$router.push("/");
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      });
    }
  }
}
</script>

<style scoped>

</style>