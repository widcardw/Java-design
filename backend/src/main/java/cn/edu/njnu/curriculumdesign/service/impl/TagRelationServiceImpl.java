package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.entity.TagRelation;
import cn.edu.njnu.curriculumdesign.mapper.TagRelationMapper;
import cn.edu.njnu.curriculumdesign.service.TagRelationService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TagRelationService")
public class TagRelationServiceImpl implements TagRelationService {

    @Resource
    TagRelationMapper tagRelationMapper;

//    @Override
//    public Page<Post> getPostPageByCourseId(Integer courseId, long pageNum, long pageSize) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("courseId", courseId);
//        params.put("pageNum", pageNum);
//        params.put("pageSize", pageSize);
//        List<Post> posts = tagRelationMapper.getPostPageByCourseId(params);
//        long total = tagRelationMapper.getPostCountByCourseId(courseId);
//        Page<Post> page = new Page<>(pageNum, pageSize);
//        page.setRecords(posts);
//        page.setTotal(total);
//        return page;
//    }
//
//    @Override
//    public Page<Post> getPostsByTag(Integer courseId, Integer tagId, long pageNum, long pageSize) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("courseId", courseId);
//        params.put("tagId", tagId);
//        params.put("pageNum", pageNum);
//        params.put("pageSize", pageSize);
//        List<Post> posts = tagRelationMapper.getPostPageByTag(params);
//        long total = tagRelationMapper.getPostCountByTag(tagId);
//        Page<Post> page = new Page<>(pageNum, pageSize);
//        page.setRecords(posts);
//        page.setTotal(total);
//        return page;
//    }

    @Override
    public List<Tag> getTagsByPost(int postId) {
        return tagRelationMapper.getTagByPostId(postId);
    }

    @Override
    public void addTagRelation(TagRelation tagRelation) {
        tagRelationMapper.addTagRelation(tagRelation);
    }

    @Override
    public void addTagRelationBatch(List<TagRelation> tagRelations) {
        tagRelationMapper.addTagRelationBatch(tagRelations);
    }

    @Override
    public void deleteTagRelation(TagRelation tagRelation) {
        tagRelationMapper.deleteTagRelation(tagRelation);
    }
}
