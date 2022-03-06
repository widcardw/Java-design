<template>
  <div style="display: flex; padding: 5px 20px 10px 20px; border-bottom: 1px solid lightgrey">
    <el-button type="text" style="width: 120px;" class="text" @click="backMain">Consulting</el-button>
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
          <el-dropdown-item style="text-align: center;"
                            v-if="$store.getters.userInfo && $store.getters.userInfo.isTeacher"
                            @click="addCourse">
            <el-icon>
              <plus/>
            </el-icon>
            新建分区
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <div style="flex: 1; margin: auto 20px; display: flex;">
      <el-select v-model="dynamicTags" multiple placeholder="按标签搜索" size="small" style="flex: 1;margin-right: 10px;"
                 @change="selectTagChange">
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
      <el-button size="small" @click="clearTag">
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
            <el-dropdown-item
                v-if="$store.getters.userInfo && $store.getters.userInfo.isTeacher"
                @click="goToStudentAdministration">学生管理
            </el-dropdown-item>
            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-button type="text" v-else class="text" @click="$router.push('/login')">登录 / 注册</el-button>
    </div>
    <el-dialog v-model="inputCourseVisible" title="新建分区" width="40%">
      <el-form label-width="80px">
        <el-form-item label="分区名">
          <el-input v-model="newCourse" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitNewCourse">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {ArrowDown, Search, Close, Plus} from '@element-plus/icons-vue'
import request from "../utils/request";
import fetchCourses from "../utils/fetchCourses";

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
    Close, Plus
  },
  data() {
    return {
      courses: [],
      selectedCourse: {},
      user: {},
      dynamicTags: [],
      allTags: [],
      newCourse: "",
      inputCourseVisible: false,
    }
  },
  created() {
    this.user = this.currentUser;
    this.selectedCourse = this.$store.getters.currentCourse;
    fetchCourses().then(res => {
      this.courses = res;
    })
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
    goToStudentAdministration() {
      if (this.$store.getters.userInfo && this.$store.getters.userInfo.isTeacher) {
        this.$router.push('/studentAdministration')
      }
    },
    selectTagChange(val) {
      this.$store.dispatch('SetCurrentTags', val);
    },
    clearTag() {
      this.$store.dispatch('SetCurrentTags', []);
      this.dynamicTags = []
    },
    backMain() {
      this.$router.push("/")
      this.$store.dispatch("SetCurrentCourse", null)
      this.selectedCourse = null;
      this.$emitter.emit('headerFetchAll')
      console.log("header emit backMain")
    },
    fetchPostsByCourseId() {
      let courseId = this.selectedCourse.id;
      this.$emitter.emit('headerFetchPostsByCourseId', courseId);
      console.log("header emit fetchPostsByCourseId")
    },
    fetchPostsByCourseIdAndTags() {
      let courseId = this.selectedCourse.id;
      let tagIds = Object.values(this.dynamicTags);
      this.$emitter.emit('headerFetchPostsByCourseIdAndTags', courseId, tagIds);
      console.log("header emit fetchPostsByCourseIdAndTags")
    },
    searchByTag() {
      console.log(this.dynamicTags);
      if (this.dynamicTags.length > 0) {
        this.fetchPostsByCourseIdAndTags();
      } else {
        this.fetchPostsByCourseId();
      }
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
    selectCourse(course) {
      this.selectedCourse = JSON.parse(JSON.stringify(course));
      console.log(this.selectedCourse);
      this.$store.dispatch('SetCurrentCourse', course);
      this.fetchPostsByCourseId();
      this.fetchAllTags();
      this.$router.push("/")
    },
    submitNewCourse() {
      if (this.newCourse.trim() === '') {
        this.$message.error('分区名不能为空');
        return;
      }
      request({
        method: "post",
        url: "/course/addone",
        data: {
          courseName: this.newCourse
        }
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.$message.success('创建成功');
          fetchCourses().then(res => {
            this.courses = res
          });
          this.inputCourseVisible = false;
        } else {
          this.$message.error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
        console.log(err)
      })
    },
    addCourse() {
      this.inputCourseVisible = true;
      this.newCourse = '';
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