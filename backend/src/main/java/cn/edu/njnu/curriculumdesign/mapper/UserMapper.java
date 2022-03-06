package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Long> sumQuestionScoreByUserId(Integer userId);
    List<Long> sumAnswerScoreByUserId(Integer userId);
}
