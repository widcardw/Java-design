package cn.edu.njnu.curriculumdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLevelDto {
    Integer id;
    Integer level;
}
