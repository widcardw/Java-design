<template>
  <div>
    <el-form>
      <el-form-item style="display: none;">
        <el-input v-model="form.authorId" placeholder="作者"></el-input>
      </el-form-item>
      <el-form-item label="分区">
        <el-select v-model="form.courseId" placeholder="请选择分区" style="width: 100%;" @change="fetchAllTags">
          <el-option v-for="item in courses" :key="item.id" :label="item.courseName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <el-form-item label="标签">
        <el-select v-model="form.tagIds" placeholder="添加标签" style="width: 85%" multiple>
          <el-option v-for="item in tags" :key="item.id" :label="item.tagName" :value="item.id"></el-option>
        </el-select>
        <div style="width: 14%;margin-left: 1%;">
          <el-input
              v-if="inputVisible"
              v-model="inputValue"
              placeholder="请输入标签" style="width: 100%;"
              @keyup.enter="addTag"
              @blur="inputVisible = false"
              ref="InputRef"></el-input>
          <el-button v-else slot="append" icon="el-icon-plus" @click="showInput" style="width: 100%; font-weight: normal">+ 添加标签</el-button>
        </div>
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
import {Plus} from "@element-plus/icons-vue"

export default {
  name: "writepost",
  components: {
    Plus
  },
  data() {
    return {
      form: {},
      courses: [],
      tags: [],
      inputVisible: false,
      inputValue: "",
    }
  },
  created() {
    this.form.authorId = this.$store.getters.userInfo.id;
    this.form.courseId = this.$store.getters.currentCourse.id;
    this.fetchCourses();
    this.fetchAllTags();
  },
  methods: {
    submit() {
      console.log(this.form);
      request({
        url: "/post/add",
        method: "post",
        data: this.form
      }).then(res => {
        console.log(res);
        if (res.code === '200') {
          let tagRelations = [];
          this.form.tagIds.forEach(item => {
            tagRelations.push({
              postId: res.data.id,
              tagId: item
            })
          });
          console.log("标签映射", tagRelations);
          request({
            url: '/tagrelation/addmany',
            method: 'post',
            data: tagRelations
          }).then(res => {
            console.log(res);
          }).catch(err => {
            console.log(err);
          })
          this.$message.success("发布成功");
          this.$router.push("/");
        } else {
          this.$message.error(res.message);
        }
      })
    },
    fetchCourses() {
      request({
        method: "get",
        url: "/course/all"
      }).then(res => {
        console.log(res);
        this.courses = res.data
      }).catch(err => {
        this.$message.error(err.message)
        console.log(err)
      })
    },
    fetchAllTags() {
      console.log(this.form.courseId, "选课改变");
      this.form.tagIds = [];
      request({
        method: 'get',
        url: '/tag/courseId',
        params: {
          courseId: this.form.courseId
        }
      }).then(res => {
        this.tags = res.data;
      });
    },
    addTag() {
      if (this.inputValue) {
        request({
          method: 'post',
          url: '/tag',
          data: {
            tagName: this.inputValue,
            courseId: this.form.courseId
          }
        }).then(res => {
          console.log(res.data)
          this.tags.push(res.data);
          this.form.tagIds.push(res.data.id);
          this.inputValue = "";
          this.inputVisible = false;
        });
      }
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick(() => {
        this.$refs.InputRef.focus();
      });
    },
  }
}
</script>

<style scoped>

</style>