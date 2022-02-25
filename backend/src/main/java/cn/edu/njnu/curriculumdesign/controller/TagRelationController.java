package cn.edu.njnu.curriculumdesign.controller;

import cn.edu.njnu.curriculumdesign.common.Result;
import cn.edu.njnu.curriculumdesign.entity.TagRelation;
import cn.edu.njnu.curriculumdesign.service.TagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tagrelation")
public class TagRelationController {

    @Resource
    TagRelationService tagRelationService;

//    @GetMapping("/course")
//    public Result<?> getPostPageByCourseId(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
//                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
//                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
//        return Result.success(tagRelationService.getPostPageByCourseId(courseId, page, size));
//    }

//    @GetMapping("/postbytag")
//    public Result<?> getPostPageByTag(@RequestParam(value = "courseId", defaultValue = "1") Integer courseId,
//                                      @RequestParam(value = "tagId", defaultValue = "1") Integer tagId,
//                                      @RequestParam(value = "page", defaultValue = "1") int page,
//                                      @RequestParam(value = "size", defaultValue = "10") int size) {
//        return Result.success(tagRelationService.getPostsByTag(courseId, tagId, page, size));
//    }

    @GetMapping("/tagbypost")
    public Result<?> getTagByPost(@RequestParam("postId") Integer postId) {
        return Result.success(tagRelationService.getTagsByPost(postId));
    }

    @PostMapping("/addone")
    public Result<?> addTagRelation(@RequestParam("tagRelation") TagRelation tagRelation) {
        tagRelationService.addTagRelation(tagRelation);
        return Result.success();
    }

    @PostMapping("/addmany")
    public Result<?> addTagRelations(@RequestBody List<TagRelation> tagRelations) {
        tagRelationService.addTagRelationBatch(tagRelations);
        return Result.success();
    }

    @DeleteMapping("/deleteone")
    public Result<?> deleteTagRelation(@RequestBody TagRelation tagRelation) {
        tagRelationService.deleteTagRelation(tagRelation);
        return Result.success();
    }
}
