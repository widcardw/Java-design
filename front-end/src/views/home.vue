<template>
  <div>
    <el-card shadow="hover" style="margin-bottom: 10px;"
             v-if="$store.getters.currentCourse && $store.getters.currentCourse.id">
      <el-button type="text" style="font-size: small" plain
                 @click="goToWritePost">分享你的难题
      </el-button>
    </el-card>
    <el-skeleton :loading="loading" animated :rows="10" :throttle="500">
      <el-empty v-if="posts && posts.length === 0"></el-empty>
      <el-card v-for="item in posts" :key="item" shadow="hover" style="margin-bottom: 10px;">
        <template #header>
          <div class="card-header">
            <div class="title">
              <h3>
                <el-link :underline="false" style="font: inherit" @click="goToPostDetail(item)">{{
                    item.title
                  }}
                </el-link>
              </h3>
              <h4 style="margin: 10px 0;">
                <el-badge :value="Math.floor(Math.log2(item.author.level))" :max="99" type="primary">
                  <div style="margin-right: 10px;">{{ item.author.nickname }}</div>
                </el-badge>
              </h4>
              <span class="ctime">{{ new Date(item.createDate).toLocaleString() }}</span>
            </div>
            <div>
              <div style="display: flex; justify-content: flex-end;">
                <el-tooltip
                    :content="$store.getters.userInfo && $store.getters.userInfo.isTeacher ? '进入详情页面评分' : (item.questionScore === 0 ? '暂无评分' : item.questionScore + ' 分')">
                  <el-rate v-model="item.questionScore" disabled :colors="['#f56c6c','#f56c6c','#f56c6c']"></el-rate>
                </el-tooltip>
              </div>
              <h4 style="text-align: right">{{ item.course.courseName }}</h4>
              <div style="display: flex; justify-content: flex-end;">
                <el-tag size="small" v-for="it2 in item.tags" :key="it2" style="margin-left: 10px;"
                >{{ it2.tagName }}
                </el-tag>
              </div>
            </div>
          </div>
        </template>
        <MarkShow :source="item.content + ''"/>
      </el-card>
    </el-skeleton>
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[5,10,20]"
        layout="total, sizes, prev, pager, next"
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
import MarkShow from "../components/MarkShow.vue";


export default {
  name: "home.vue",
  components: {
    Edit, MarkShow
  },
  data() {
    return {
      posts: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 50,
    }
  },
  methods: {
    goToWritePost() {
      if (this.$store.getters.hasLogin) {
        this.$router.push({name: 'writepost', query: {courseId: this.$store.getters.currentCourse.id}})
      } else {
        this.$router.push({name: 'login'})
      }
    },
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
      if (!this.$store.getters.currentCourse || this.$store.getters.currentCourse.id === 0) {
        this.fetchAll();
      } else if (!this.$store.getters.currentTags || this.$store.getters.currentTags.length === 0) {
        this.fetchPostsByCourseId(this.$store.getters.currentCourse.id)
      } else {
        this.fetchPostsByCourseIdAndTags(this.$store.getters.currentCourse.id, this.$store.getters.currentTags)
      }
    },
    handleSizeChange(val) {
      console.log("Size changed")
      this.pageSize = val
      this.handleChange();
    },
    handleCurrentChange(val) {
      console.log("Current changed")
      this.pageNum = val;
      this.handleChange();
    },
    fetchAll() {
      this.loading = true;
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
      }).finally(() => {
        this.loading = false;
      })
    },
    goToPostDetail(post) {
      console.log("redirect")
      this.$router.push({name: "postdetail", query: {post: JSON.stringify(post.id)}})
    }
  },
  created: function () {
    this.handleChange();
    this.$emitter.on('headerFetchAll', () => {
      console.log("headerFetchAll")
      this.fetchAll()
    })
    this.$emitter.on('headerFetchPostsByCourseId', (courseId) => {
      console.log("FetchPostsByCourseId")
      this.fetchPostsByCourseId(courseId)
    })
    this.$emitter.on('headerFetchPostsByCourseIdAndTags', (courseId, tags) => {
      console.log("FetchPostsByCourseIdAndTags")
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