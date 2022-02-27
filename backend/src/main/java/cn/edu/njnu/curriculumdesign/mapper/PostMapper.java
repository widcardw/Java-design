package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.dto.PostDto;
import cn.edu.njnu.curriculumdesign.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    List<Post> getPostPageByCourseIdAndTag(Map<String, Object> params);

    Long countPostPageByCourseIdAndTag(Map<String, Object> params);

    List<PostDto> getPostDtoPageByCourseIdAndTagList(Map<String, Object> params);

    List<PostDto> pageByCourseId(Map<String, Object> params);

    List<PostDto> pageAll(Map<String, Object> params);

    PostDto getPostById(Integer id);
}
