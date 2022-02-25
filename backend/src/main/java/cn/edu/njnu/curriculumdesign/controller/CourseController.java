package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.entity.Course;
import cn.edu.njnu.curriculumdesign.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    CourseService courseService;

    @GetMapping("/all")
    public Result<?> getAllCourses() {
        List<Course> courses = courseService.list();
        return Result.success(courses);
    }

    @GetMapping("/page")
    public Result<?> getCoursesByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        courseService.page(page);
        return Result.success(page);
    }

    @PostMapping("/addone")
    public Result<?> addCourse(@RequestBody Course course) {
        courseService.save(course);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCourse(@PathVariable Integer id) {
        courseService.removeById(id);
        return Result.success();
    }

    @PutMapping("/updateone")
    public Result<?> updateCourse(@RequestBody Course course) {
        courseService.updateById(course);
        return Result.success();
    }
}
