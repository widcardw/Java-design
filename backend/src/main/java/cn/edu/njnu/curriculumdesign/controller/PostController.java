package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.dto.PostDto;
import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.service.PostService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


// TODO  SQL 语句还有问题！复合查询的时候数据会无缘无故少掉！
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    PostService postService;

    @GetMapping("/all")
    public Result<?> getAllPost(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageAll(page));
    }

    @GetMapping("/bycourseid")
    public Result<?> getPostByCourseId(@RequestParam(defaultValue = "1") Integer courseId,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseId(page, courseId));
    }

    @GetMapping("/byid")
    public Result<?> getPostById(@RequestParam(defaultValue = "1") Integer id) {
        PostDto post = postService.getPostDtoById(id);
//        System.out.println(post);
        return Result.success(post);
    }

    @GetMapping("/byuserid")
    public Result<?> getPostByUserId(@RequestParam(defaultValue = "1") Integer userId,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByUserId(page, userId));
    }

    @Deprecated
    @GetMapping("/bycourseidandtag")
    public Result<?> getPostByCourseIdAndTag(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
                                             @RequestParam(value = "tagIds", defaultValue = "1") List<Integer> tagIds,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Post> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseIdAndTag(page, courseId, tagIds));
    }

    @GetMapping("/ciatl")
    public Result<?> getPostByCourseIdAndTagList(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
                                                 @RequestParam(value = "tagIds", defaultValue = "1") List<Integer> tagIds,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostDto> page = new Page<>(pageNum, pageSize);
        return Result.success(postService.pageByCourseIdAndTagList(page, courseId, tagIds));
    }

    @PostMapping("/add")
    public Result<?> createPost(@RequestBody Post post) {
        int titleLength = StrUtil.length(post.getTitle());
        if (titleLength > 100) {
            return Result.fail("-2", "标题过长！");
        }
        int contentLength = StrUtil.length(post.getContent());
        if (contentLength > 65535) {
            return Result.fail("-2", "内容过长！");
        }
        post.setCreateDate(new Date());
        post.setQuestionScore(0);
        postService.save(post);
        return Result.success(post);
    }

    @PutMapping("/update")
    public Result<?> updatePost(@RequestBody Post post) {
        int titleLength = StrUtil.length(post.getTitle());
        if (titleLength > 100) {
            return Result.fail("-2", "标题过长！");
        }
        int contentLength = StrUtil.length(post.getContent());
        if (contentLength > 65535) {
            return Result.fail("-2", "内容过长！");
        }
        return Result.success(postService.updateById(post));
    }

    @DeleteMapping("/{id}")
    public Result<?> deletePost(@PathVariable Integer id) {
        return Result.success(postService.removeById(id));
    }
}
