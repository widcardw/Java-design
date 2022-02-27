package cn.edu.njnu.curriculumdesign.dto;

import lombok.Data;

@Data
public class ScoreDto {
    private Integer stuId;
    private String stuName;
    private String stuNickname;
    private Integer level;
    private Integer isTeacher;

    private Integer courseId;
    private String courseName;

    private Integer score;
}
