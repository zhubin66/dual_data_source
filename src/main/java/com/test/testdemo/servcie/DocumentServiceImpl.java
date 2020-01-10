package com.test.testdemo.servcie;

import com.test.testdemo.base.repository.CommonRepository;
import com.test.testdemo.base.util.PageBean;
import com.test.testdemo.criteria.DocumentCriteria;
import com.test.testdemo.entity.primary.Document;
import com.test.testdemo.repository.primary.DocumentRepository;
import com.test.testdemo.response.ResException;
import com.test.testdemo.util.DateUtil;
import com.test.testdemo.util.Helpers;
import com.test.testdemo.util.ZipFilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述：文件管理
 */
@Service
public class DocumentServiceImpl {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CommonRepository commonRepository;
    @Value("${fileProps.filePath}")
    private String filePath;

    /**
     * 文件上传类型
     */
    private final static List<String> fileTypes = new ArrayList<String>() {{
        add("doc");
        add("docx");
        add("xls");
        add("xlsx");
        add("ppt");
        add("pptx");
        add("txt");
        add("md");
        add("pdf");
        add("jpg");
        add("jpeg");
        add("png");
        add("gif");
        add("bmp");
    }};

    /**
     * 文件上传(支持多文件上传  swagger上只支持单个测试)
     *
     * @param request 请求
     * @return
     */
    public List<Integer> fileUpload(MultipartHttpServletRequest request) {
        // 1.按月来保存一个目录
        String uploadPath = DateUtil.dateToString(new Date(), DateUtil.YEAR_NO_DAY);

        // 2.文件路径不存在,则新建
        String checkPath = filePath + "/" + uploadPath;
        File fileStream = new File(checkPath);
        if (!fileStream.exists()) {
            fileStream.mkdirs();
        }

        // 3.开始上传
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        List<MultipartFile> files = new ArrayList<>();
        for (Map.Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()) {
            files.addAll(temp.getValue());
        }

        List<Document> documents = new ArrayList<>();
        Document document;
        BufferedOutputStream stream;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new ResException("存在文件为空,请确认!");
            }
            try {
                document = new Document();
                //文件属性
                String fileName = file.getOriginalFilename();
                BigDecimal fileSize = BigDecimal.valueOf(file.getSize());
                int len = fileName.lastIndexOf(".");
                if (len <= 0) {
                    throw new ResException("文件类型有误,请确认!");
                }
                String fileType1 = fileName.substring(len + 1).toLowerCase();
                if (!fileTypes.contains(fileType1)) {
                    throw new ResException("只允许上传word、excel、ppt、pdf、txt和图片!");
                }
                String name = fileName.substring(0, len);
                String fileType = fileName.substring(len + 1);
                String physicalName = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date()) + "_" + fileName;
                String path = uploadPath + "/" + physicalName;

                document.setName(name);
                document.setFileSize(fileSize);
                document.setFileType(fileType);
                document.setPath(path);
                document.setDownloadTimes(0);

                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(new File(checkPath + "/" + physicalName)));
                stream.write(bytes);
                stream.close();

                documents.add(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        documentRepository.save(documents);

        // java8获取文件id集合
        List<Integer> documentIds = documents.stream().map(Document::getId).collect(Collectors.toList());

        return documentIds;
    }

    /**
     * 暂时只写了单个文件下载
     * 我的csdn: https://blog.csdn.net/qq_39150374  上有完整的多个文件下载
     *
     * @param id
     * @param request
     * @param response
     */
    @Transactional
    public void downloadAlone(Integer id, HttpServletRequest request, HttpServletResponse response) {
        Document document = documentRepository.findOne(id);
        Helpers.requireNonNull("当前文件不存在,请联系管理员", document);

        File file = new File(filePath + "/" + document.getPath());
        if (!file.exists()) {
            throw new ResException("文件 :" + document.getName() + "不存在,请联系管理员!");
        }

        document.setDownloadTimes(document.getDownloadTimes() == null ? 1 : document.getDownloadTimes() + 1);
        String originFileName = document.getName() + "." + document.getFileType();
        ZipFilesUtil.downloadFile(file, originFileName, request, response);
    }

    /**
     * 分页
     *
     * @param pageBean
     * @param documentCriteria
     */
    public void findByPage(PageBean pageBean, DocumentCriteria documentCriteria) {
        pageBean.setEntityName("Document d");
        pageBean.setSelect("select d");
        commonRepository.findByPage(pageBean, documentCriteria);
    }

    /**
     * 删除
     */
    @Transactional
    public Integer delete(List<Integer> ids) {
        if (ids.isEmpty())
            return 0;
        return documentRepository.deleteAllByIdIn(ids);
    }
}
