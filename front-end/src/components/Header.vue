<template>
  <div style="display: flex; padding: 5px 20px 10px 20px; border-bottom: 1px solid lightgrey">
    <el-button type="text" style="width: 120px;" class="text" @click="$router.push('/')">Consulting</el-button>
    <el-dropdown style="width: 100px; display: flex; justify-content: end;">
      <el-button type="text" class="el-dropdown-link text">
        {{ selectedCourse && selectedCourse.courseName ? selectedCourse.courseName : '选择分区' }}
        <el-icon class="el-icon--right">
          <arrow-down/>
        </el-icon>
      </el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item v-for="(item, index) in courses" :key="item"
                            @click="selectCourse(item)">{{ item.courseName }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <div style="flex: 1; margin: auto 20px; display: flex;">
      <el-select v-model="dynamicTags" multiple placeholder="Select" size="small" style="flex: 1;margin-right: 10px;" @change="selectTagChange">
        <el-option
            v-for="item in allTags"
            :key="item.id"
            :label="item.tagName"
            :value="item.id"
        >
        </el-option>
      </el-select>
      <el-button size="small" @click="searchByTag">
        <el-tooltip content="搜索">
          <el-icon>
            <search/>
          </el-icon>
        </el-tooltip>
      </el-button>
      <el-button size="small" @click="dynamicTags=[]">
        <el-tooltip content="清空标签">
          <el-icon>
            <close/>
          </el-icon>
        </el-tooltip>
      </el-button>
    </div>
    <div>
      <el-dropdown v-if="user && user.nickname">
        <el-button type="text" class="el-dropdown-link text">
          {{ user.nickname }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/info')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-button type="text" v-else class="text" @click="$router.push('/login')">登录 / 注册</el-button>
    </div>
  </div>
</template>

<script>
import {ArrowDown, Search, Close} from '@element-plus/icons-vue'
import request from "../utils/request";

export default {
  name: "Header",
  props: {
    currentUser: {
      type: Object,
      default: {}
    }
  },
  components: {
    Search,
    ArrowDown,
    Close
  },
  data() {
    return {
      courses: [],
      selectedCourse: {},
      user: {},
      dynamicTags: [],
      allTags: [],
    }
  },
  created() {
    this.user = this.currentUser;
    this.fetchCourses();
  },
  watch: {
    currentUser: {
      handler(val) {
        this.user = val
      },
      deep: true
    }
  },
  methods: {
    selectTagChange(val){
      this.$store.dispatch('SetCurrentTags', val);
    },
    fetchPostsByCourseId() {
      let courseId = this.selectedCourse.id;
      this.$emitter.emit('headerFetchPostsByCourseId', courseId);
    },
    fetchPostsByCourseIdAndTags() {
      let courseId = this.selectedCourse.id;
      let tagIds = Object.values(this.dynamicTags);
      this.$emitter.emit('headerFetchPostsByCourseIdAndTags', courseId, tagIds);
    },
    searchByTag() {
      console.log(this.dynamicTags);
      this.fetchPostsByCourseIdAndTags();
    },
    logout() {
      this.$store.dispatch('Logout')
      this.user = null
    },
    fetchAllTags() {
      request({
        method: 'get',
        url: '/tag/courseId',
        params: {
          courseId: this.selectedCourse.id
        }
      }).then(res => {
        this.allTags = res.data;
      });
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
    selectCourse(course) {
      this.selectedCourse = JSON.parse(JSON.stringify(course));
      console.log(this.selectedCourse);
      this.$store.dispatch('SetCurrentCourse', course);
      this.fetchPostsByCourseId();
      this.fetchAllTags();
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    handleInputConfirm() {
      if (this.inputValue) {
        this.dynamicTags.push(this.inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
    showInput() {
      if (this.dynamicTags.length >= 5) {
        this.$message.error('最多只能添加 5 个标签');
        return;
      }
      this.inputVisible = true;
      this.$nextTick(() => {
        this.$refs.InputRef.focus();
      });
    }
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
  color: #333;
  font-weight: normal;
}
</style>