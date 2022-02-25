package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.entity.User;
import cn.edu.njnu.curriculumdesign.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/one")
    public Result<?> getUser(@RequestParam Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/stu")
    public Result<?> getStudentByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<User>(pageNum, pageSize);
        userService.getStudentByPage(page);
        return Result.success(page);
    }

    @GetMapping("/teacher")
    public Result<?> getTeacherByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<User>(pageNum, pageSize);
        userService.getTeacherByPage(page);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User user) {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (one != null) {
            return Result.fail("-1", "用户名已存在");
        }
        userService.save(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User login = userService.login(user);
        if (login != null) {
            return Result.success(login);
        } else {
            return Result.fail("-1", "登录失败");
        }
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }
}
