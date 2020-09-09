package com.dataint.monitor.controller;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Api("user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string")
    })
    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody Map<String, Object> params) {
        if (!params.containsKey("username") || !params.containsKey("password"))
            throw new DataintBaseException(BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR);

        Map<String, Object> rstMap = userService.checkUserByUsername((String)params.get("username"), (String)params.get("password"));

        // Return the token
        return ResultVO.success(rstMap);
    }

    @ApiOperation(value = "获取当前用户", notes = "获取当前用户")
    @GetMapping(value = "/currentUser")
    public ResultVO getCurrentUser(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        String username = JWTUtil.getUserName(accessToken);

        return ResultVO.success(userService.getCurrentUser(username));
    }

    @ApiOperation(value = "用户登出", notes = "用户登出")
    @GetMapping(value = "/logout")
    public ResultVO logout(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
//        Integer userId = JWTUtil.getUserId(accessToken);
//        return ResultVO.success(userService.logout(Long.valueOf(userId)));
        return null;
    }

    @GetMapping("/test")
    public ResultVO test(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        System.out.println("test");

        Map<String, Object> userMap = JWTUtil.getUserIdAndName(accessToken);

        System.out.println("username: " + (String)userMap.get("username"));
        System.out.println("userId: " + (Integer)userMap.get("userId"));

        throw new DataintBaseException(BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR);
    }

}

