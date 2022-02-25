package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.entity.TagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagRelationMapper {
//    List<Post> getPostPageByTag(Map<String, Object> params);
//    List<Post> getPostPageByCourseId(Map<String, Object> params);
    List<Tag> getTagByPostId(int postId);

    void addTagRelation(TagRelation tagRelation);
    void addTagRelationBatch(@Param("tagRelations") List<TagRelation> tagRelations);
    void deleteTagRelation(TagRelation tagRelation);

//    long getPostCountByTag(int tagId);
//    long getPostCountByCourseId(int courseId);
}
