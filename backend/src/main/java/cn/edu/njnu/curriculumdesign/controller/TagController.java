package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.entity.Tag;
import cn.edu.njnu.curriculumdesign.service.TagService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    TagService tagService;

    @GetMapping("/courseId")
    public Result<?> getAllTags(@RequestParam(defaultValue = "1") Integer courseId) {
        return Result.success(tagService.getByCourseId(courseId));
    }

    @PostMapping
    public Result<?> addTag(@RequestBody Tag tag) {
        int length = StrUtil.length(tag.getTagName());
        if (length > 20) {
            return Result.fail("-2", "标签名称不能超过20个字符");
        }
        tagService.save(tag);
        return Result.success(tag);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteTag(@PathVariable Integer id) {
        tagService.removeById(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateTag(@RequestBody Tag tag) {
        int length = StrUtil.length(tag.getTagName());
        if (length > 20) {
            return Result.fail("-2", "标签名称不能超过20个字符");
        }
        tagService.updateById(tag);
        return Result.success();
    }
}
