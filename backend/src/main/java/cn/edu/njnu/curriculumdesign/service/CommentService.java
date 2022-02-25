package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.dto.CommentDto;
import cn.edu.njnu.curriculumdesign.entity.Comment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<Comment> {
    Page<Comment> pageCommentByPostId(Page<Comment> page, Integer postId);

    Page<CommentDto> pageCommentDtoByPostId(Page<CommentDto> page, Integer postId);
}
