package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tbl_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String tagName;
    private Integer courseId;
}
