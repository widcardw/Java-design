package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.entity.User;
import cn.edu.njnu.curriculumdesign.mapper.UserMapper;
import cn.edu.njnu.curriculumdesign.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public Page<User> getStudentByPage(Page<User> page) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIsTeacher, false);
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<User> getTeacherByPage(Page<User> page) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIsTeacher, true);
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .eq(User::getIsTeacher, user.getIsTeacher());
        return userMapper.selectOne(wrapper);
    }

}
