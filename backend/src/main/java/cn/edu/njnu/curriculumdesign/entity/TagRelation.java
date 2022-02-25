package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tbl_tag_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRelation {
    @TableId(value = "post_id")
    private Integer postId;
    @TableId(value = "tag_id")
    private Integer tagId;
}
