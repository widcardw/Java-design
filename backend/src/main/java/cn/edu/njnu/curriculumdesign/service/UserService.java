package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    Page<User> getStudentByPage(Page<User> page);
    Page<User> getTeacherByPage(Page<User> page);
    User login(User user);
}
