package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.dto.CommentDto;
import cn.edu.njnu.curriculumdesign.entity.Comment;
import cn.edu.njnu.curriculumdesign.mapper.CommentMapper;
import cn.edu.njnu.curriculumdesign.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CommentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Deprecated
    @Override
    public Page<Comment> pageCommentByPostId(Page<Comment> page, Integer postId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId);
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<CommentDto> pageCommentDtoByPostId(Page<CommentDto> page, Integer postId) {
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("pageNum", (page.getCurrent() - 1) * page.getSize());
        params.put("pageSize", page.getSize());
        List<CommentDto> commentDtos = commentMapper.pageCommentDtoByPostId(params);
        Long total = commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId));
        page.setRecords(commentDtos);
        page.setTotal(total.intValue());
        return page;
    }
}
