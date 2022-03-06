<template>
  <div>
    <div style="display: flex; justify-content: space-between;">
      <el-page-header content="问题详情" @back="$router.back" style="margin-bottom: 10px;"/>
      <div v-if="post.author && $store.getters.userInfo && (post.author.id===$store.getters.userInfo.id)">
        <el-button type="text" @click="handleEdit">
          <el-icon>
            <edit/>
          </el-icon>
        </el-button>
        <el-popconfirm title="确定删除这一问题吗？" @confirm="handleDelete">
          <template #reference>
            <el-button type="text" style="color: #f56c6c;">
              <el-icon>
                <delete/>
              </el-icon>
            </el-button>
          </template>
        </el-popconfirm>
      </div>
    </div>
    <!--    问题区    -->
    <el-card shadow="hover" style="margin-bottom: 10px;">
      <template #header>
        <div class="card-header">
          <div class="title">
            <h3>{{ post.title }}</h3>
            <h4 style="margin: 10px 0;">
              <el-badge :value="post.author && Math.floor(Math.log2(post.author.level))" :max="99"
                        type="primary">
                <div style="margin-right: 10px;">{{ post.author && post.author.nickname }}</div>
              </el-badge>
            </h4>
            <span class="ctime">{{ new Date(post.createDate).toLocaleString() }}</span>
          </div>
          <div>
            <div style="display: flex; justify-content: flex-end;">
              <el-rate
                  v-model="post.questionScore"
                  :disabled="$store.getters.userInfo && $store.getters.userInfo.isTeacher !== 1"
                  :colors="['#f56c6c','#f56c6c','#f56c6c']"
                  @change="submitQuestionScore"></el-rate>
            </div>
            <h4 style="text-align: right">{{ post.course && post.course.courseName }}</h4>
            <div style="display: flex; justify-content: flex-end;">
              <el-tag size="small" v-for="it2 in post.tags" :key="it2" style="margin-left: 10px;"
              >{{ it2.tagName }}
              </el-tag>
            </div>
          </div>
        </div>
      </template>
      <MarkShow :source="post.content + ''"/>
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
          <el-input type="textarea" v-model="form.content" placeholder="请输入评论内容，提交后支持 markdown 渲染"
                    :autosize="{ minRows: 2, maxRows: 5 }" ref="commentRef"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addComment">提交</el-button>
          <el-button @click="cancelComment">取消</el-button>
        </el-form-item>
      </el-form>
      <el-divider/>
      <div v-for="item in comments" :key="item" :id="'comment-'+item.id">
        <div class="card-header">
          <div class="comment-title">
            <h4>
              <el-badge :value="Math.floor(Math.log2(item.level))" :max="99" class="mark" type="primary">
                <div style="margin-right: 10px;">{{ item.nickname }}</div>
              </el-badge>
            </h4>
            <span class="ctime">{{ new Date(item.createDate).toLocaleString() }}</span>
          </div>
          <div>
            <el-rate
                v-model="item.answerScore"
                :disabled="!(post.author && $store.getters.userInfo && (post.author.id===$store.getters.userInfo.id))"
                @change="submitCommentScore(item.id, item.answerScore)"
            ></el-rate>
            <div style="display: flex; justify-content: flex-end;">
              <el-button type="text" @click="handleCommentEdit(item)">
                <el-icon>
                  <edit/>
                </el-icon>
              </el-button>
              <el-popconfirm title="确定删除这一评论吗？" @confirm="handleCommentDelete(item.id)">
                <template #reference>
                  <el-button type="text" style="color: #f56c6c;">
                    <el-icon>
                      <delete/>
                    </el-icon>
                  </el-button>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </div>
        <MarkShow :source="item.content + ''"/>
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
        :total="total"
        style="margin-top: 10px;"></el-pagination>
    <el-backtop/>
  </div>
</template>

<script>
import request from "../utils/request";
import {Edit, Delete} from "@element-plus/icons-vue"
import MarkShow from "../components/MarkShow.vue";

export default {
  name: "postdetail",
  components: {
    Edit, Delete, MarkShow
  },
  data() {
    return {
      post: {},
      comments: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      form: {},
      rules: {
        content: [
          {required: true, message: "请输入评论内容", trigger: "blur"},
          {min: 3,  message: "请输入至少 3 个字符", trigger: "blur"}
        ],
        postId: [
          {required: true, message: "无问题编号", trigger: "blur"}
        ],
        userId: [
          {required: true, message: "请先登录", trigger: "blur"}
        ]
      },
    }
  },
  created() {
    console.log("postdetail created");
    if (!this.$route.query.post) {
      this.$router.push("/");
      return;
    }
    this.form.postId = JSON.parse(this.$route.query.post)
    this.form.userId = this.$store.getters.userInfo.id;
    console.log(this.form)
    this.fetchPost()
    // this.fetchComments();
  },
  methods: {
    handleCommentEdit(item) {
      this.form.id = item.id;
      this.form.content = item.content;
      document.querySelector("#comment-" + item.id).style.display = "none";
      this.$refs.commentRef.focus();
    },
    handleCommentDelete(id) {
      request({
        url: "/comment/" + id,
        method: "delete"
      }).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.fetchComments();
        } else {
          throw new Error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
      });
    },
    cancelComment() {
      if (this.form.id) {
        document.querySelector("#comment-" + this.form.id).style.display = "block";
      }
      this.form.id = null;
      this.form.content = '';
    },
    fetchPost() {
      if (!this.form.postId) {
        return;
      }
      request({
        url: '/post/byid',
        method: 'get',
        params: {
          id: this.form.postId
        }
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.post = res.data;
          return this.post.id;
        } else {
          throw new Error(res.message);
        }
      }).then(id => {
        if (!id) {
          throw new Error("无问题编号");
        }
        this.fetchComments();
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      })
    },
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
          throw new Error(res.message)
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      });
    },
    addComment() {
      // console.log(this.form);
      let method = 'post', successMsg = '发送成功';
      if (this.form.id) {
        method = 'put';
        successMsg = '修改成功';
      }
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return;
        }
        request({
          method: method,
          url: "/comment",
          data: this.form
        }).then(res => {
          console.log(res)
          if (res.code === '200') {
            this.$message.success(successMsg);
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.fetchComments()
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.fetchComments()
    },
    handleEdit() {
      this.$router.push({
        path: "/editpost",
        query: {
          post: JSON.stringify(this.post)
        }
      })
    },
    submitCommentScore(commentId, score) {
      request({
        method: "put",
        url: "/comment",
        data: {
          id: commentId,
          answerScore: score
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("评分成功");
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      })
    },
    submitQuestionScore() {
      request({
        method: "put",
        url: "/post/update",
        data: {
          id: this.post.id,
          questionScore: this.post.questionScore
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("评分成功");
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      })
    },
    handleDelete() {
      console.log(this.post.id);
      request({
        method: "delete",
        url: "/post/" + this.post.id
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
          this.$router.push("/");
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        console.log(err);
        this.$message.error(err.message);
      })
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

.comment-on-edit {
  user-select: none;

}
</style>