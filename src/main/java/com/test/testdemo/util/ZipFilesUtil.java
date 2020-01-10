package com.test.testdemo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 描述：文件打包下载
 */
public class ZipFilesUtil {

    /**
     * 创建zip文件
     *
     * @param files 文件
     * @param path  暂存目录
     */
    public static void createZipFiles(List<File> files, String path, HttpServletResponse response) {
        try {
            //List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
            //创建一个临时压缩文件
            //临时文件可以放在CDEF盘中，但不建议这么做，因为需要先设置磁盘的访问权限，最好是放在服务器上，方法最后有删除临时文件的步骤

            File file = new File(path);
            file.deleteOnExit();
            file.createNewFile();
            response.reset();

            //创建文件输出流
            FileOutputStream fileOutput = new FileOutputStream(file);
            ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);
            zipFile(files, zipOutput);
            zipOutput.close();
            fileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 把接受的全部文件打成压缩包
     */
    private static void zipFile(List<File> files, ZipOutputStream outputStream) {
        for (Object file1 : files) {
            File file = (File) file1;
            zipFile(file, outputStream);
        }
    }


    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    private static void zipFile(File inputFile, ZipOutputStream outputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputStream.putNextEntry(entry);

                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, nNumber);
                    }

                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                zipFile(file, outputStream);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void downloadFile(File file, String originFileName, HttpServletRequest request, HttpServletResponse response) {
        if (file.exists()) {
            try {
                // 以流的形式下载文件。
                InputStream input = new BufferedInputStream(new FileInputStream(file.getPath()));
                byte[] buffer = new byte[input.available()];
                input.read(buffer);
                input.close();
                // 清空response
                response.reset();
                generate(originFileName, request, response);

                OutputStream output = new BufferedOutputStream(response.getOutputStream());
                output.write(buffer);
                output.flush();
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 解决文件乱码问题
     *
     * @param fileName
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    private static void generate(String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("success", "true");// 与前端约定

        String userAgent = request.getHeader("User-Agent");
        String formFileName;
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            // 针对IE或者以IE为内核的浏览器：
            formFileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            formFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
        response.setHeader("Content-Disposition", "attachment;filename=" + formFileName);
    }
}
