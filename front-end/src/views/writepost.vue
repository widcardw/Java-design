<template>
  <div>
    <el-form ref="myForm" :model="form" :rules="rules" label-width="60px">
      <el-form-item style="display: none;" prop="authorId">
        <el-input v-model="form.authorId" placeholder="作者"></el-input>
      </el-form-item>
      <el-form-item label="分区" prop="courseId">
        <el-select v-model="form.courseId" placeholder="请选择分区" style="width: 100%;" @change="fetchAllTags">
          <el-option v-for="item in courses" :key="item.id" :label="item.courseName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
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
          <el-button v-else slot="append" icon="el-icon-plus" @click="showInput"
                     style="width: 100%; font-weight: normal">+ 添加标签
          </el-button>
        </div>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!--        <el-input type="textarea" v-model="form.content" placeholder="请输入内容"></el-input>-->
        <Codemirror
            v-model:value="form.content"
            :option="cmOptions"
            border
            style="font-family: 'JetBrains Mono','Courier New', monospace;font-size: 14px;"
            aria-placeholder="提交后会按照 markdown 语法渲染"
        >
        </Codemirror>
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
import Codemirror from "codemirror-editor-vue3"
import "codemirror/mode/javascript/javascript.js"
import "codemirror/theme/dracula.css";
import {ref} from 'vue'
import fetchCourses from "../utils/fetchCourses";

export default {
  name: "writepost",
  components: {
    Plus, Codemirror
  },
  data() {
    return {
      form: {content: ''},
      courses: [],
      tags: [],
      inputVisible: false,
      inputValue: "",
      rules: {
        authorId: [{required: true, message: "请先登录", trigger: "blur"}],
        courseId: [{required: true, message: "请选择分区", trigger: "blur"}],
        title: [{required: true, message: "请输入标题", trigger: "blur"}, {
          min: 2,
          max: 99,
          message: "标题长度在2到99个字符之间",
          trigger: "blur"
        }],
        content: [{required: true, message: "请输入内容", trigger: "blur"}, {min: 10, message: "内容至少10个字符", trigger: "blur"}],
      },
      cmOptions: {
        mode: "text/javascript", // Language mode
        theme: "default", // Theme
        lineNumbers: true, // Show line number
        smartIndent: true, // Smart indent
        indentUnit: 2, // The smart indent unit is 2 spaces in length
        foldGutter: true, // Code folding
        styleActiveLine: true, // Display the style of the selected row
      },
    }
  },
  created() {
    this.form.authorId = this.$store.getters.userInfo.id;
    this.form.courseId = JSON.parse(this.$route.query.courseId); //this.$store.getters.currentCourse.id;
    fetchCourses().then(res => {this.courses = res})
    this.fetchAllTags();
  },
  methods: {
    submit() {
      console.log(this.form);
      this.$refs.myForm.validate(valid => {
        if (!valid) {
          this.$message.error("请先输入内容")
          return false;
        }
        request({
          url: "/post/add",
          method: "post",
          data: this.form
        }).then(res => {
          console.log(res);
          if (res.code === '200') {
            return res;
          } else {
            // this.$message.error(res.message);
            throw new Error(res.message);
          }
        }).then(res => {
          if (res.code !== '200') {
            throw new Error(res.message);
          }
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
        }).catch(err => {
          console.log(err)
          this.$message.error(err.message)
        })
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
          this.$message.success("标签添加成功");
        }).catch(err => {
          this.$message.error(err.message);
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