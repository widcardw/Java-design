<template>
  <div>
    <div style="display: flex; padding: 5px 20px 10px 20px; border-bottom: 1px solid lightgrey; margin-bottom: 5px;">
      <el-button type="text" class="text" @click="backMain">Consulting</el-button>
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
      <el-button type="text" class="text" @click="updateScoreByCourseId" v-if="selectedCourse && selectedCourse.id">更新本科目成绩
      </el-button>
      <div style="flex: 1;"></div>
      <el-button type="text" class="text">{{ $store.getters.userInfo && $store.getters.userInfo.nickname }}</el-button>
    </div>
    <div style="max-width: 1200px; margin: 0 auto">
      <el-table :data="tableData" style="width: 100%;" border :loading="loading">
        <el-table-column label="用户名" prop="stuName"
                         :filters="[{text: '学生', value: 0},{text: '教师',value:1}]"
                         :filter-method="filterRole"/>
        <el-table-column label="昵称" prop="stuNickname"/>
        <el-table-column label="等级" prop="level"/>
        <el-table-column label="课程名" prop="courseName"/>
        <el-table-column label="成绩" prop="score"/>
      </el-table>
    </div>
    <el-pagination
        v-model:page-size="pageSize"
        v-model:current-page="pageNum" :page-sizes="[10,20,50,100, 200]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
import fetchCourses from "../utils/fetchCourses";
import {ArrowDown} from "@element-plus/icons-vue";
import request from "../utils/request";

export default {
  name: "studentadministration",
  components: {
    ArrowDown
  },
  data() {
    return {
      tableData: [],
      selectedCourse: {},
      courses: [],
      pageSize: 20,
      pageNum: 1,
      total: 0,
      loading: false
    }
  },
  created() {
    fetchCourses().then(res => {
      this.courses = res;
    });
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
      this.getData();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getData();
    },
    filterRole(value, row) {
      return row.isTeacher === value;
    },
    backMain() {
      this.$router.push({path: "/"});
    },
    updateScoreByCourseId() {
      request({
        method: "post",
        url: "/score/update",
        data: {
          courseId: this.selectedCourse.id,
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("更新 " + res.data + " 条成绩");
          return true;
        } else {
          throw new Error(res.message);
        }
      }).then(res => {
        if (res) {
          this.getData();
        }
      }).catch(err => {
        this.$message.error(err.message);
      });
    },
    getData() {
      this.loading = true;
      console.log("getData", this.selectedCourse);
      request({
        url: "/score/courseId",
        method: "get",
        params: {
          courseId: this.selectedCourse.id,
          pageSize: this.pageSize,
          pageNum: this.pageNum
        }
      }).then(res => {
        console.log(res)
        if (res.code === '200') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        } else {
          throw new Error(res.message);
        }
      }).catch(err => {
        this.$message.error(err.message);
        console.log(err);
      }).finally(() => {
        this.loading = false;
      })
    },
    selectCourse(course) {
      console.log(course);
      this.selectedCourse = course;
      this.getData();
    },
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
  color: #333;
  font-weight: normal;
  margin-left: 20px;
}
</style>