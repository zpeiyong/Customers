package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.dao.IComment;
import com.dataint.monitor.dao.entity.Comment;
import com.dataint.monitor.model.form.CommentForm;
import com.dataint.monitor.service.ICommentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
@Slf4j
public class CommentController {
    @Autowired
    ICommentService commentService;
    @RequestMapping(value = "/queryCommentList",method = RequestMethod.GET)
    @ApiOperation(value = "评论查询",notes = "评论查询")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "current",required = true, value = "当前页",dataType ="Long",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",required = true, value = "每页多少条",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "articleId",required = false, value = "文章Id",dataType ="String",paramType = "query")
    })
    @ResponseBody
    public ResultVO getCommentLists(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken,Long articleId,int current,int pageSize) {
        Long userId = JWTUtil.getUserId(accessToken);

        Page<IComment> commentPage = commentService.queryComment(articleId, userId, current, pageSize);
        Pagination pagination  = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrent(current);
        pagination.setTotal(commentPage.getTotalElements());
        return ResultVO.success(commentPage,pagination);

    }

    @RequestMapping(value = "/delCommentById")
    @ApiOperation(value = "删除评论", notes = "根据id指定评论")
    @ApiImplicitParam(paramType = "", name = "id", value = "评论ID", required = true, dataType = "long")
    public ResultVO delete(@RequestParam Long id) {

        return ResultVO.success(commentService.delCommentById(id));
    }
    @RequestMapping(value = "/saveComment",method = RequestMethod.POST)
    @ApiOperation(value = "评论数据保存", notes = "评论数据保存")
    @ApiImplicitParam(name = "commentForm", value = "保存评论数据", required = true, dataType = "commentForm")
    public ResultVO saveComment(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken, CommentForm commentForm){
        Long userId = JWTUtil.getUserId(accessToken);
        commentForm.setUserId(userId);

        return ResultVO.success(commentService.saveComment(commentForm));
    }

}
