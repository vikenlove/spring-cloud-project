package com.emerging.framework.core.utils;

import com.emerging.framework.core.fastdfs.FastDFSClient;
import com.emerging.framework.core.fastdfs.FastDFSFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FastdfsUtils {

    // 日志
    private static Logger LOG = LoggerFactory.getLogger(FastdfsUtils.class);

    /**
     * fastdfs保存文件至服务器
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String[] saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            LOG.error("upload file Exception!", e);
            e.printStackTrace();
        }
        if (fileAbsolutePath == null) {
            LOG.error("upload file failed,please upload again!");
        }
        return fileAbsolutePath;
    }

    /**
     * 获取保存至fastdfs服务器的http访问地址
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String getHttpPathOfSavedFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = FastdfsUtils.saveFile(multipartFile);
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }

    /**
     * 获取保存文件的fileid(fastdfs规则)
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String getSavedFileId(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = FastdfsUtils.saveFile(multipartFile);
        return fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
    }

    public static InputStream downloadFileByFileId(String fileId) throws Exception {
        InputStream inputStream = null;
        String[] filePath = FastDFSClient.getFilePathArray(fileId);
        //获取文件流
        inputStream = FastDFSClient.downFile(filePath[0], filePath[1]);
        return inputStream;
    }
}
