package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.dto.PostDto;
import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.mapper.PostMapper;
import cn.edu.njnu.curriculumdesign.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PostService")
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    PostMapper postMapper;

    @Override
    public Page<PostDto> pageByCourseId(Page<PostDto> page, Integer courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("pageNum", (page.getCurrent() - 1) * page.getSize());
        params.put("pageSize", page.getSize());
        List<PostDto> postDtos = postMapper.pageByCourseId(params);
        Long total = postMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getCourseId, courseId));
        page.setRecords(postDtos);
        page.setTotal(total);
        return page;
    }

    @Override
    public Page<Post> pageByUserId(Page<Post> page, Integer userId) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getAuthorId, userId).orderByDesc(Post::getCreateDate);
        postMapper.selectPage(page, wrapper);
        return page;
    }

    @Deprecated
    @Override
    public Page<Post> pageByCourseIdAndTag(Page<Post> page, Integer courseId, List<Integer> tagIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("tagIds", tagIds);
        params.put("pageNum", (page.getCurrent() - 1) * page.getSize());
        params.put("pageSize", page.getSize());
        List<Post> posts = postMapper.getPostPageByCourseIdAndTag(params);
        Long total = postMapper.countPostPageByCourseIdAndTag(params);
        page.setRecords(posts);
        page.setTotal(total);
        return page;
    }

    @Override
    public Page<PostDto> pageByCourseIdAndTagList(Page<PostDto> page, Integer courseId, List<Integer> tagIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("tagIds", tagIds);
        params.put("pageNum", (page.getCurrent() - 1) * page.getSize());
        params.put("pageSize", page.getSize());

        List<PostDto> postDtos = postMapper.getPostDtoPageByCourseIdAndTagList(params);
        Long total = postMapper.countPostPageByCourseIdAndTag(params);

        page.setTotal(total);
        page.setRecords(postDtos);

        return page;
    }

    @Override
    public Page<PostDto> pageAll(Page<PostDto> page) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (page.getCurrent() - 1) * page.getSize());
        params.put("pageSize", page.getSize());

        List<PostDto> postDtos = postMapper.pageAll(params);
        Long total = postMapper.selectCount(null);

        page.setRecords(postDtos);
        page.setTotal(total);
        return page;
    }

}
