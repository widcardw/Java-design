package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.mapper.TagMapper;
import cn.edu.njnu.curriculumdesign.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("TagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    TagMapper tagMapper;

    @Override
    public List<Tag> getByCourseId(Integer courseId) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getCourseId, courseId);
        return tagMapper.selectList(wrapper);
    }

    @Override
    public Tag addTag(Tag tag) {
        tagMapper.insert(tag);
        return tag;
    }
}
