package com.fzy.admin.fp.file.rich_text.controller;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.auth.repository.WxOpenConfigRepository;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ZhangWenJian
 * @data 2019/1/4--17:12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/fms/domain")
@Api(value = "CheckDomainController", tags = {"域名校验"})
public class CheckDomainController {

    @Resource
    private WxOpenConfigRepository wxOpenConfigRepository;

    @GetMapping("/save_rewrite")
    @ApiOperation(value = "上传域名校验文件", notes = "上传域名校验文件")
    public Resp test(@RequestParam(value = "fileName") @ApiParam(value = "文件名 不包括.txt") String fileName,
                     @RequestParam(value = "content") @ApiParam(value = "文件内容") String content ) {
        WxOpenConfig old = wxOpenConfigRepository.findByFilename(fileName);
        if (old != null) {
            wxOpenConfigRepository.delete(old);
        }
        WxOpenConfig wxOpenConfig = new WxOpenConfig();
        wxOpenConfig.setFilename(fileName.replace(".txt",""));
        wxOpenConfig.setFilecontent(content);
        wxOpenConfigRepository.save(wxOpenConfig);
        return Resp.success("");
    }


}
