package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.entity.TagRelation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface TagRelationService {
//    Page<Post> getPostPageByCourseId(Integer courseId, long pageNum, long pageSize);
//    Page<Post> getPostsByTag(Integer courseId, Integer tagId, long pageNum, long pageSize);
    List<Tag> getTagsByPost(int postId);

    void addTagRelation(TagRelation tagRelation);
    void addTagRelationBatch(List<TagRelation> tagRelations);
    void deleteTagRelation(TagRelation tagRelation);
}
