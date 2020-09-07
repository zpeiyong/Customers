package com.dataint.diseasepo.auth.handler;

import com.dataint.cloud.common.model.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ResultVO resultVO = ResultVO.success("注销成功!");
        System.out.println("注销成功!");

        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(resultVO));
        out.flush();
        out.close();
    }
}