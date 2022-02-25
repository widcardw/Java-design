package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("tbl_course")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private String courseName;
}
