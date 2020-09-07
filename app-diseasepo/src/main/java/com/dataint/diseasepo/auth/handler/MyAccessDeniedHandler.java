package com.dataint.diseasepo.auth.handler;
import com.dataint.cloud.common.model.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义403响应的内容,返回我们的错误逻辑提示
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        ResultVO resultVO = ResultVO.fail("权限不足，请联系管理员!");

        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(resultVO));
        out.flush();
        out.close();
    }
}
