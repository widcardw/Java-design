package cn.edu.njnu.curriculumdesign.service;

import cn.edu.njnu.curriculumdesign.dto.ScoreDto;
import cn.edu.njnu.curriculumdesign.entity.Score;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ScoreService {
    ScoreDto getScore(Integer userId, Integer courseId);
    int updateScore(Score score);
    int deleteScore(Integer userId, Integer courseId);
    int insertScore(Score score);

    Page<ScoreDto> getScorePageByStuId(Integer userId, long pageNum, long pageSize);
    Page<ScoreDto> getScorePageByCourseId(Integer courseId, long pageNum, long pageSize);

    long countByStuId(Integer userId);
    long countByCourseId(Integer courseId);

    void calculateScoreByCourseId(Integer courseId);
}
