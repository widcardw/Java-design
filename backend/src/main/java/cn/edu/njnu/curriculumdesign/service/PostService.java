package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.dto.PostDto;
import cn.edu.njnu.curriculumdesign.entity.Post;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PostService extends IService<Post> {
    Page<PostDto> pageByCourseId(Page<PostDto> page, Integer courseId);
    Page<Post> pageByUserId(Page<Post> page, Integer userId);
    @Deprecated
    Page<Post> pageByCourseIdAndTag(Page<Post> page, Integer courseId, List<Integer> tagIds);

    Page<PostDto> pageByCourseIdAndTagList(Page<PostDto> page, Integer courseId, List<Integer> tagIds);

    Page<PostDto> pageAll(Page<PostDto> page);

    PostDto getPostDtoById(Integer id);
}
