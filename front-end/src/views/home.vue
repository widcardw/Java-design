<template>
  <div>
    <el-card shadow="hover" style="margin-bottom: 10px;"
             v-if="$store.getters.currentCourse && $store.getters.currentCourse.id">
      <el-button type="text" style="font-size: small" plain @click="$router.push('/writepost')">分享你的难题</el-button>
    </el-card>
    <el-card v-for="item in posts" :key="item" shadow="hover" style="margin-bottom: 10px;">
      <template #header>
        <div class="card-header">
          <div class="title">
            <h3><el-link :underline="false" style="font: inherit" @click="goToPostDetail(item)">{{ item.title }}</el-link></h3>
            <span class="ctime">{{ new Date(item.createDate).toLocaleString() }}</span>
          </div>
          <div>
            <h4 style="text-align: right">{{item.author.nickname}} | {{item.course.courseName}}</h4>
            <el-tag size="small" v-for="it2 in item.tags" :key="it2" style="margin-left: 10px;"
            >{{it2.tagName}}</el-tag>
          </div>
        </div>
      </template>
      <div class="content">
        <p>{{ item.content }}</p>
      </div>
    </el-card>
    <el-pagination
        v-model:currentPage="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
    <el-backtop/>
  </div>
</template>

<script>
import request from "../utils/request";
import {Edit} from "@element-plus/icons-vue";

export default {
  name: "home.vue",
  components: {
    Edit
  },
  data() {
    return {
      posts: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 50
    }
  },
  created() {
    // this.$emitter.on('fetchPostsByCourseId', (courseId) => {
    //   this.fetchPostsByCourseId(courseId)
    // })
    // this.$emitter.on('fetchPostsByCourseIdAndTags', (courseId, tags) => {
    //   this.fetchPostsByCourseIdAndTags(courseId, tags)
    // })
  },
  methods: {
    fetchPostsByCourseId(courseId) {
      this.loading = true;
      request({
        method: 'get',
        url: "/post/bycourseid",
        params: {
          courseId: courseId,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.posts = res.data.records;
          this.total = res.data.total;
          console.log(this.posts)
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
        console.log(err)
      }).finally(() => {
        this.loading = false;
      })
    },
    fetchPostsByCourseIdAndTags(courseId, tagIds) {
      this.loading = true;
      console.log(tagIds)
      let params = new URLSearchParams();
      params.append('courseId', courseId);
      params.append('pageNum', this.pageNum);
      params.append('pageSize', this.pageSize);
      params.append('tagIds', tagIds);
      request({
        method: 'get',
        url: "/post/ciatl",
        params: params
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.posts = res.data.records;
          this.total = res.data.total;
          console.log(this.posts)
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
        console.log(err)
      }).finally(() => {
        this.loading = false;
      })
    },
    handleChange() {
      if (!this.$store.getters.currentTags || this.$store.getters.currentTags.length === 0) {
        this.fetchPostsByCourseId(this.$store.getters.currentCourse.id)
      } else {
        this.fetchPostsByCourseIdAndTags(this.$store.getters.currentCourse.id, this.$store.getters.currentTags)
      }
    },
    handleSizeChange() {
      this.handleChange();
    },
    handleCurrentChange() {
      this.handleChange();
    },
    fetchAll() {
      request({
        method: 'get',
        url: "/post/all",
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.posts = res.data.records;
          this.total = res.data.total;
          console.log(this.posts)
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
        console.log(err)
      })
    },
    goToPostDetail(post) {
      this.$router.push({name: "postdetail", params: {post: JSON.stringify(post)}})
    }
  },
  mounted() {
    if (this.$store.getters.currentCourse && this.$store.getters.currentCourse.id) {
      this.handleChange();
    } else {
      this.fetchAll();
    }
    this.$emitter.on('headerFetchPostsByCourseId', (courseId) => {
      this.fetchPostsByCourseId(courseId)
    })
    this.$emitter.on('headerFetchPostsByCourseIdAndTags', (courseId, tags) => {
      this.fetchPostsByCourseIdAndTags(courseId, tags)
    })
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
</style>