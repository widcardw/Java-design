package cn.edu.njnu.curriculumdesign.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tbl_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private int userId;
    private int courseId;
    private int score;
}
