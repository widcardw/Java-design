package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.dto.CommentDto;
import cn.edu.njnu.curriculumdesign.entity.Comment;
import cn.edu.njnu.curriculumdesign.service.CommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @GetMapping("/page")
    public Result<?> pageCommentByPostId(@RequestParam Integer postId,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<CommentDto> page = new Page<>(pageNum, pageSize);
        commentService.pageCommentDtoByPostId(page, postId);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> addComment(@RequestBody Comment comment) {
        comment.setCreateDate(new Date());
        comment.setAnswerScore(0);
        commentService.save(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteComment(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateComment(@RequestBody Comment comment) {
        commentService.updateById(comment);
        return Result.success();
    }
}
