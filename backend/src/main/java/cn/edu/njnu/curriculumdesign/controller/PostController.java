package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.dto.PostDto;
import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.service.PostService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    PostService postService;

    @GetMapping("/all")
    public Result<?> getAllPost(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageAll(page));
    }

    @GetMapping("/bycourseid")
    public Result<?> getPostByCourseId(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
                                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseId(page, courseId));
    }

    @GetMapping("/byid")
    public Result<?> getPostById(@RequestParam(value = "id", defaultValue = "1") Integer id) {
        Post post = postService.getById(id);
        System.out.println(post);
        return Result.success(post);
    }

    @GetMapping("/byuserid")
    public Result<?> getPostByUserId(@RequestParam(value = "userId", defaultValue = "1") Integer userId,
                                     @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByUserId(page, userId));
    }

    @Deprecated
    @GetMapping("/bycourseidandtag")
    public Result<?> getPostByCourseIdAndTag(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
                                             @RequestParam(value = "tagIds", defaultValue = "1") List<Integer> tagIds,
                                             @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseIdAndTag(page, courseId, tagIds));
    }

    @GetMapping("/ciatl")
    public Result<?> getPostByCourseIdAndTagList(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
                                                 @RequestParam(value = "tagIds", defaultValue = "1") List<Integer> tagIds,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseIdAndTagList(page, courseId, tagIds));
    }

    @PostMapping("/add")
    public Result<?> createPost(@RequestBody Post post) {
        post.setCreateDate(new Date());
        post.setQuestionScore(0);
        postService.save(post);
        return Result.success(post);
    }

    @PutMapping("/update")
    public Result<?> updatePost(@RequestBody Post post) {
        return Result.success(postService.updateById(post));
    }

    @DeleteMapping("/{id}")
    public Result<?> deletePost(@PathVariable Integer id) {
        return Result.success(postService.removeById(id));
    }
}
