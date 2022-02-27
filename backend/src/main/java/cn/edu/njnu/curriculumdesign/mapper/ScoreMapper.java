package cn.edu.njnu.curriculumdesign.mapper;

import cn.edu.njnu.curriculumdesign.dto.ScoreDto;
import cn.edu.njnu.curriculumdesign.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScoreMapper {
    ScoreDto getScore(Map<String, Object> params);
    int updateScore(Score score);
    int deleteScore(Map<String, Object> params);
    int insertScore(Score score);

    List<ScoreDto> getScorePageByStuId(Map<String, Object> params);
    List<ScoreDto> getScorePageByCourseId(Map<String, Object> params);

    Long countByStuId(Integer stuId);
    Long countByCourseId(Integer courseId);

    List<Score> getSumAnswerScoreByCourse(Integer courseId);
    List<Score> getSumQuestionScoreByCourse(Integer courseId);

    void insertScoreBatch(List<Score> scoreList);
}
