package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private Integer level;
    private Integer isTeacher;
}
