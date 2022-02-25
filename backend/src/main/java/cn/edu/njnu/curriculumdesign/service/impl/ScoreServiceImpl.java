package cn.edu.njnu.curriculumdesign.service.impl;

import cn.edu.njnu.curriculumdesign.dto.ScoreDto;
import cn.edu.njnu.curriculumdesign.entity.Score;
import cn.edu.njnu.curriculumdesign.mapper.ScoreMapper;
import cn.edu.njnu.curriculumdesign.service.ScoreService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
}
