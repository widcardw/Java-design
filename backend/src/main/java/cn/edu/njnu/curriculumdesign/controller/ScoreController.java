package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.entity.Score;
import cn.edu.njnu.curriculumdesign.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    ScoreService scoreService;

    @GetMapping("/courseId")
    public Result<?> getScorePageByCourseId(@RequestParam(defaultValue = "1") Integer courseId,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(scoreService.getScorePageByCourseId(courseId, pageNum, pageSize));
    }

    @GetMapping("/studentId")
    public Result<?> getScorePageByStuId(@RequestParam(defaultValue = "1") Integer studentId,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(scoreService.getScorePageByStuId(studentId, pageNum, pageSize));
    }

    @PostMapping("/update")
    public Result<?> updateScoreByCourseId(@RequestBody Score score) {
        long l = scoreService.calculateScoreByCourseId(score.getCourseId());
        return Result.success(l);
    }
}