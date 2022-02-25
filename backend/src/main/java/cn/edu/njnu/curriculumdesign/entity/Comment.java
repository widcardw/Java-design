package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_comment")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String content;
    private Integer answerScore;
    private Integer postId;
    private Date createDate;
}
