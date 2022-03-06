package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.entity.User;
import cn.edu.njnu.curriculumdesign.mapper.UserMapper;
import cn.edu.njnu.curriculumdesign.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public Page<User> getStudentByPage(Page<User> page) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIsTeacher, 0);
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<User> getTeacherByPage(Page<User> page) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIsTeacher, 1);
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

    @Override
    public void calculateLevel(Integer userId) {
        List<Long> q = userMapper.sumQuestionScoreByUserId(userId);
        List<Long> a = userMapper.sumAnswerScoreByUserId(userId);
        System.out.println("q.size=" + q.size() + ",a.size=" + a.size());
        long qs = 0, as = 0;
        if (q.size() > 0) {
            qs = q.get(0);
        }
        if (a.size() > 0) {
            as = a.get(0);
        }
        long level = qs * 2 + as;
        if (level == 0) {
            level = 1;
        }
        User user = new User();
        user.setId(userId);
        user.setLevel(Math.toIntExact(level));
        System.out.println(user);
        userMapper.updateById(user);
    }
}
