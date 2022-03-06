package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.dto.ScoreDto;
import cn.edu.njnu.curriculumdesign.entity.Score;
import cn.edu.njnu.curriculumdesign.mapper.ScoreMapper;
import cn.edu.njnu.curriculumdesign.service.ScoreService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    @Resource
    ScoreMapper scoreMapper;

    @Override
    public ScoreDto getScore(Integer userId, Integer courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("courseId", courseId);
        return scoreMapper.getScore(params);
    }

    @Override
    public int updateScore(Score score) {
        return scoreMapper.updateScore(score);
    }

    @Override
    public int deleteScore(Integer userId, Integer courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("courseId", courseId);
        return scoreMapper.deleteScore(params);
    }

    @Override
    public int insertScore(Score score) {
        return scoreMapper.insertScore(score);
    }

    @Override
    public Page<ScoreDto> getScorePageByStuId(Integer userId, long pageNum, long pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        Page<ScoreDto> page = new Page<>(pageNum, pageSize);
        page.setRecords(scoreMapper.getScorePageByStuId(params));
        page.setTotal(scoreMapper.countByStuId(userId));
        return page;
    }

    @Override
    public Page<ScoreDto> getScorePageByCourseId(Integer courseId, long pageNum, long pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        Page<ScoreDto> page = new Page<>(pageNum, pageSize);
        page.setRecords(scoreMapper.getScorePageByCourseId(params));
        page.setTotal(scoreMapper.countByCourseId(courseId));
        return page;
    }

    @Override
    public long countByStuId(Integer userId) {
        return scoreMapper.countByStuId(userId);
    }

    @Override
    public long countByCourseId(Integer courseId) {
        return scoreMapper.countByCourseId(courseId);
    }

    @Override
    public long calculateScoreByCourseId(Integer courseId) {
        List<Score> sumAnswerScore = scoreMapper.getSumAnswerScoreByCourse(courseId);
        List<Score> sumQuestionScore = scoreMapper.getSumQuestionScoreByCourse(courseId);
        Map<Integer, Score> hashMap = new HashMap<>();

        Stream.concat(sumAnswerScore.stream(), sumQuestionScore.stream()).forEach(score -> {
            if (!hashMap.containsKey(score.getUserId())) {
                hashMap.put(score.getUserId(), score);
            } else {
                hashMap.get(score.getUserId()).setScore(hashMap.get(score.getUserId()).getScore() + score.getScore());
            }
        });

        if (hashMap.size() == 0) {
            return 0;
        }

        List<Score> scoreList = new ArrayList<>(hashMap.values());
        scoreMapper.insertScoreBatch(scoreList);
        return scoreList.size();
    }
}
