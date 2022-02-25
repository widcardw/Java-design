package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.isis.applib.value.TimeStamp;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_post")
public class Post {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;    // _id
    private String title; // 标题
    private String content;  // 内容，前端按照 markdown 渲染
    private Integer authorId;  // 提问者 id
    private Date createDate;       // 日期
    private Integer questionScore;  // 问题得分
    private Integer courseId;  // 课程 id
}
