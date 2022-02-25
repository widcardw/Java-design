package cn.edu.njnu.curriculumdesign.dto;

import cn.edu.njnu.curriculumdesign.entity.Course;
import cn.edu.njnu.curriculumdesign.entity.Post;
import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private Integer id;
    private String title;
    private String content;
    private Integer questionScore;
    private Date createDate;

    private User author;
    private Course course;
    private List<Tag> tags;


//    private Integer authorId;
//    private String username;
//    private String nickname;
//    private Integer level;
//    private Integer isTeacher;
//
//    private Integer courseId;
//    private String courseName;
//
//    private List<Tag> tagList;
}
