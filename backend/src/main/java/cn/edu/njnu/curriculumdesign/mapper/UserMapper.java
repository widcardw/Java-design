package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
