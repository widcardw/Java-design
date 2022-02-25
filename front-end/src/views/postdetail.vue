<template>
  <div>
    <el-page-header content="问题详情" @back="$router.back" style="margin-bottom: 10px;"></el-page-header>
    <!--    问题区    -->
    <el-card shadow="hover" style="margin-bottom: 10px;">
      <template #header>
        <div class="card-header">
          <div class="title">
            <h3>{{ post.title }}</h3>
            <span class="ctime">{{ new Date(post.createDate).toLocaleString() }}</span>
          </div>
          <div>
            <h4 style="text-align: right">{{ post.author.nickname }} | {{ post.course.courseName }}</h4>
            <el-tag size="small" v-for="it2 in post.tags" :key="it2" style="margin-left: 10px;"
            >{{ it2.tagName }}
            </el-tag>
          </div>
        </div>
      </template>
      <div class="content">
        <p>{{ post.content }}</p>
      </div>
    </el-card>
    <!--    评论区    -->
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <h3>评论</h3>
        </div>
      </template>
        <el-form label-width="80px" :model="form" ref="form" :rules="rules">
          <el-form-item label="问题编号" style="display: none;" prop="postId">
            <el-input v-model="form.postId" disabled></el-input>
          </el-form-item>
          <el-form-item label="用户 id" style="display: none;" prop="userId">
            <el-input v-model="form.userId" disabled></el-input>
          </el-form-item>
          <el-form-item label="评论内容" prop="content">
            <el-input type="textarea" v-model="form.content" placeholder="请输入评论内容"
                      :autosize="{ minRows: 2, maxRows: 5 }"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addComment">提交</el-button>
          </el-form-item>
        </el-form>
      <el-divider/>
      <div v-for="item in comments" :key="item">
          <div class="card-header">
            <div class="comment-title">
              <h4>{{ item.nickname }}</h4>
              <span class="ctime">{{ new Date(item.createDate).toLocaleString() }}</span>
            </div>
            <h5>{{ item.answerScore !== 0 ? item.answerScore : '' }}</h5>
          </div>
        {{ item.content }}
        <el-divider/>
      </div>
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"></el-pagination>
    <el-backtop/>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "postdetail",
  data() {
    return {
      post: {},
      comments: [{
        user_id: "某人",
        content: "啊这",
        createDate: "2022/02/22",
        answerScore: 0,
        postId: 0
      }],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      form: {},
      rules: {
        content: [
          { required: true, message: "请输入评论内容", trigger: "blur" }
        ],
        postId: [
          { required: true, message: "无问题编号", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "无用户 id", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    console.log("postdetail created");
    if (!this.$route.params.post) {
      this.$router.push("/");
      return;
    }
    this.post = JSON.parse(this.$route.params.post);
    this.form.postId = this.post.id;
    this.form.userId = this.$store.getters.userInfo.id;
    this.fetchComments();
  },
  methods: {
    fetchComments() {
      request({
        method: "get",
        url: "/comment/page",
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          postId: this.post.id
        }
      }).then(res => {
        console.log(res);
        if (res.code === '200') {
          this.comments = res.data.records;
          this.total = res.data.total;
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      });
    },
    addComment() {
      console.log(this.form);
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return;
        }
        request({
          method: "post",
          url: "/comment",
          data: this.form
        }).then(res=> {
          console.log(res)
          if (res.code === '200') {
            this.$message.success("发送成功");
            this.fetchComments();
            this.form.content = '';
          } else {
            this.$message.error(res.message);
          }
        }).catch(err => {
          console.log(err);
          this.$message.error(err.message);
        });
      })
    },
    handleSizeChange() {
      this.fetchComments()
    },
    handleCurrentChange() {
      this.fetchComments()
    }
  }
}
</script>

<style scoped>
.ctime {
  font-size: 10px;
  color: #8492a6;
}

.card-header {
  display: flex;
  justify-content: space-between;
}
.comment-title {
  margin-bottom: 10px;
}
</style>