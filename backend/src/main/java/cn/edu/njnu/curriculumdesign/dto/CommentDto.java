package cn.edu.njnu.curriculumdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer userId;
    private String username;
    private String nickname;
    private String content;
    private Integer answerScore;
    private Integer postId;
    private Date createDate;
}
