package com.test.testdemo.controller;

import com.test.testdemo.base.controller.BaseController;
import com.test.testdemo.base.util.PageBean;
import com.test.testdemo.criteria.DocumentCriteria;
import com.test.testdemo.response.ResEntity;
import com.test.testdemo.servcie.DocumentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 描述：文件管理
 */
//@Api(value = "文件管理")
@RestController
@RequestMapping("api/document")
public class DocumentController extends BaseController {
    @Autowired
    DocumentServiceImpl documentService;

    /**
     * 文件上传 (支持多文件上传  swagger上只支持单个测试)
     *
     * @param request 文件request
     * @return
     */
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
//    @ApiOperation(value = "文件上传", notes = "多文件文件上传", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fileUpload(MultipartHttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file) {
        return ResEntity.success(documentService.fileUpload(request));
    }

    /**
     * 单文件下载
     *
     * @param id 文件id
     * @return
     */
    @GetMapping("/download/{id}")
//    @ApiOperation(value = "单文件下载", notes = "单文件下载", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public void fileDownload(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        documentService.downloadAlone(id, request, response);
    }

    /**
     * 获取文件管理列表
     *
     * @param documentCriteria 查询条件
     * @return 文件管理列表
     */
    @GetMapping("")
//    @ApiOperation(value = "获取文件管理列表", notes = "获取文件管理列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findByPage(DocumentCriteria documentCriteria) {
        PageBean pageBean = super.getPageBean();
        documentService.findByPage(pageBean, documentCriteria);
        return ResEntity.success(pageBean);
    }

    /**
     * 删除文件
     *
     * @param ids
     * @return
     */
    @DeleteMapping("")
//    @ApiOperation(value = "删除文件", notes = "删除文件", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(@RequestBody List<Integer> ids) {
        return ResEntity.success(documentService.delete(ids));
    }
}
