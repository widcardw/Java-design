package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.dto.CommentDto;
import cn.edu.njnu.curriculumdesign.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> pageCommentDtoByPostId(Map<String, Object> params);
}
