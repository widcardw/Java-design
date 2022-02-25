package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.entity.Course;
import cn.edu.njnu.curriculumdesign.mapper.CourseMapper;
import cn.edu.njnu.curriculumdesign.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("CourseService")
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
