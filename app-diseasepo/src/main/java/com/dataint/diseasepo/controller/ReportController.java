package com.dataint.diseasepo.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.param.ReportQueryParam;
import com.dataint.diseasepo.service.IReportService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/report")
@Slf4j
public class ReportController {

    @Autowired
    private IReportService reportService;

    @ApiOperation(value = "获取简报列表", notes = "根据条件查询简报")
    @ApiImplicitParam(name = "reportQueryParam", value = "简报查询参数", required = true, dataType = "ReportQueryParam")
    @PostMapping("/queryReportList")
    public ResultVO queryReportList(@RequestBody ReportQueryParam reportQueryParam) {

        return ResultVO.success(reportService.queryReportList(reportQueryParam));
    }

    @ApiOperation(value = "生成指定期间内的简报(日/周/月)", notes = "生成指定期间内的简报(日/周/月)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", value = "期间起始时间", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "overTime", value = "期间终止时间", required = true, dataType = "string")
    })
    @GetMapping("/genPeriodReport")
    public ResultVO genPeriodReport(@RequestParam String beginTime, @RequestParam String overTime) {
        /*  暂只生成期间内的日报 */
        reportService.genPeriodReport(beginTime, overTime);

        return ResultVO.success();
    }

    @ApiOperation(value = "预览/下载简报", notes = "根据简报Id预览/下载简报")
    @ApiImplicitParam(paramType = "path", name = "reportId", value = "简报ID", required = true, dataType = "long")
    @GetMapping("/download/{reportId}")
    public ResponseEntity<Resource> downloadReport(@PathVariable("reportId") Integer reportId, HttpServletRequest request) {
        Resource resource = reportService.loadFileAsResource(reportId);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        String encodeName = "default.docx";
        try {
            if (resource.getFilename() != null)
                encodeName = URLEncoder.encode(resource.getFilename(),"UTF-8");
            else
                encodeName = URLEncoder.encode(encodeName,"UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodeName + "\"")
                .body(resource);
    }
}
