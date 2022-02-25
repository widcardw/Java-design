package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> getByCourseId(Integer courseId);
    Tag addTag(Tag tag);
}
