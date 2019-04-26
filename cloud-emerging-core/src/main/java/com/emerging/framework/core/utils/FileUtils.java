package com.emerging.framework.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emerging.framework.core.constants.Constants;
import com.emerging.framework.core.encrypt.Base64;
import com.emerging.framework.core.exception.InOutException;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;




public class FileUtils extends Utils {

    private static final String ENCODING = Constants.CHARSET_UTF_8;
    private static final int KILOBYTE = 1024;
    private static final String FILE_EXT_SEPARATOR = Constants.STRING_DOT;

    public static final String getExt(final String path) {
        return StringUtils.substringAfterLast(path, FILE_EXT_SEPARATOR).toLowerCase();
    }

    public static String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf(Constants.STRING_DOT);
            if ((i > -1) && (i < (filename.length()))) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }

    public static final String getName(final String filePath, final boolean isIncludeExt) {

        String fileName = null;

        if (filePath.indexOf(File.separator) < 0) {
            fileName = filePath;
        } else {
            fileName = StringUtils.substringAfterLast(filePath, File.separator);
        }
        if (!isIncludeExt) {
            fileName = StringUtils.substringBefore(fileName, FILE_EXT_SEPARATOR);
        }
        return fileName;
    }

    public static final String getPath(final String localPath, final boolean isIncludeName) {
        return isIncludeName ? StringUtils.substringBeforeLast(localPath, FILE_EXT_SEPARATOR) : StringUtils
                .substringBeforeLast(localPath, File.separator);
    }

    /**
     * get file size(kb)
     * 
     * @param file
     * @return long
     */
    public static final long getSize(final File file) {
        return file.length() / KILOBYTE;
    }

    public static final long getSize(final String filePath) {
        final File file = new File(filePath);
        return getSize(file);
    }

    public static final String getTempDirPath() {
        return SystemUtils.getJavaIoTmpDir().getPath() + File.separator;
    }

    public static final void remove(final String path) {
        final File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
    }

    /**
     * Remove the directory tree of the given path. The sub-dirs and files of
     * the directory will be removed together
     * 
     * @param path
     */
    public static final void removeDir(final String path) {
        final File dir = new File(path);
        removeDir(dir);
    }

    public static final void removeDir(final File dir) {
        if (!dir.exists()) {
            return;
        }

        if (!dir.isDirectory()) {
            dir.delete();
            return;
        }

        File[] filelist = dir.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
                removeDir(filelist[i]);
            } else {
                filelist[i].delete();
            }
        }
        dir.delete();
    }

    public static Boolean write2Template(final String output, final String template, final Configuration config,
            final Map<?, ?> context) throws InOutException {

        File toFile = new File(output);

        if (toFile.exists()) {
            remove(output);
        }
        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            create(toFile.toString());
            fos = new FileOutputStream(toFile);
            writer = new BufferedWriter(new OutputStreamWriter(fos, ENCODING));
            freemarker.template.Template vtemplate = config.getTemplate(template, ENCODING);
            vtemplate.process(context, writer);
            writer.flush();
            writer.close();
        } catch (final IOException e) {
            throw new InOutException(e);
        } catch (TemplateException e) {
            throw new InOutException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }

        return true;
    }



    public static Boolean write2Json(final String output, final String json) throws InOutException {

        File toFile = new File(output);

        if (toFile.exists()) {
            return true;
        }

        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            create(output);
            fos = new FileOutputStream(toFile);
            writer = new BufferedWriter(new OutputStreamWriter(fos, Constants.CHARSET_UTF_8));
            writer.write(json);

            writer.flush();
            writer.close();
        } catch (final IOException e) {
            throw new InOutException(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }

        return true;
    }

    public static boolean copy(final String input, final String output) throws InOutException {
        File fromFile;
        File toFile;

        fromFile = new File(input);

        if (!fromFile.exists()) {
            return false;
        }

        toFile = new File(output);

        if (toFile.exists())
            return true;

        FileInputStream fis = null;

        FileOutputStream fos = null;

        try {
            create(output);

            fis = new FileInputStream(fromFile);

            fos = new FileOutputStream(toFile);

            int bytesRead;

            final byte[] buf = new byte[4 * 1024]; // 4K buffer

            while ((bytesRead = fis.read(buf)) != -1)
                fos.write(buf, 0, bytesRead);

            fos.flush();
        } catch (final IOException e) {
            throw new InOutException(e);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }

        return true;
    }

    public static boolean create(final String name) throws InOutException {
        final File f = new File(name);

        if (f.exists()) {
            return true;
        }
        // final String path =
        // StringUtils.substringBeforeLast(name,File.separator);
        final String path = StringUtils.substringBeforeLast(name, File.separator);
        createDir(path);

        final File file = new File(name);

        try {
            file.createNewFile();
        } catch (final IOException e) {
            throw new InOutException("Create file [" + name + "] failure", e);
        }

        return true;
    }

    public static String createFile(final MultipartHttpServletRequest request, final String path, final String key) {
        String filename = "";
        try {
            MultipartFile file = request.getFile(key);
            filename = RandomStringUtils.randomNumeric(10) + file.getOriginalFilename();

            FileUtils.createDir(path);
            String filePath = path + File.separator + filename;
            File targetFile = new File(filePath);
            file.transferTo(targetFile);
            return filename;
        } catch (IOException e) {
            throw new InOutException("Create file [" + filename + "] failure", e);
        }
    }

    public static boolean createDir(final String path) {
        final File filepath = new File(path);
        Boolean isCreate = false;
        if (!filepath.exists()) {
            isCreate =  filepath.mkdirs();
            filepath.setExecutable(true, false);
            filepath.setReadable(true, false);
            return isCreate;
        }
        return true;
    }

    public static List<String> getFilesPath(final String path) {
        final List<String> files = new ArrayList<String>();
        final File file = new File(path);

        if (!file.exists() || !file.isDirectory()) {
            return files;
        }
        final File[] fileArray = file.listFiles();

        for (final File f : fileArray) {
            files.add(f.getPath());
        }
        return files;
    }

    public static final boolean renameFileTo(final String filePath, final String newFilePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        final File newFile = new File(newFilePath);
        if (newFile.exists()) {
            return false;
        }
        return file.renameTo(newFile);
    }

    public static String filePathRepair(final String name) {
        return name.replaceAll(File.separator.equals(Constants.STRING_SLASH) ? Constants.STRING_BACKSLASH
                : Constants.STRING_SLASH, File.separator);
    }

  
    public static Boolean download(HttpServletRequest request, HttpServletResponse response, String fileFullPath) {

        InputStream inputStream = null;
        DataOutputStream dos = null;
        try {
            File f = new File(fileFullPath);
            if (!f.exists()) {
                return Boolean.FALSE;
            }
            String encodedfileName;
            String fileName = f.getName();
            // 中文文件名支持
            if (request.getHeader(Constants.USER_AGENT).toUpperCase().indexOf(Constants.BROWSER_MISE) > 0) {
                encodedfileName = URLEncoder.encode(fileName, Constants.CHARSET_UTF_8);
            } else if (request.getHeader(Constants.USER_AGENT).toLowerCase().indexOf(Constants.BROWSER_FIREFOX) > 0
                    || request.getHeader(Constants.USER_AGENT).toLowerCase().indexOf(Constants.BROWSER_OPERA) > 0) {
                encodedfileName = new String(fileName.getBytes(), Constants.CHARSET_ISO_8859_1);
            } else {
                encodedfileName = URLEncoder.encode(fileName, Constants.CHARSET_UTF_8);
            }
            // 设置弹出下载框
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
            inputStream = new FileInputStream(f);
            dos = new DataOutputStream(response.getOutputStream());
            byte[] bytes = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(bytes)) != -1) {
                dos.write(bytes, 0, i);
            }
            dos.flush();
            inputStream.close();
        } catch (FileNotFoundException e) {
            throw new InOutException("Create file [" + fileFullPath + "] failure", e);
        } catch (IOException e) {
            throw new InOutException("Create file [" + fileFullPath + "] failure", e);
        } catch (Exception e) {
            throw new InOutException("Create file [" + fileFullPath + "] failure", e);
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(inputStream);
        }
        return Boolean.TRUE;
    }

    /**
     * 判断指定目录文件是否存在
     * 
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File f = new File(filePath);
        return f.exists();
    }

    public static String readFile(String filePath) {
		try {
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] bytes = new byte[(int) file.length()];
			while (true) {
				int len = bis.read(bytes);
				if (len == -1) {
					break;
				}
			}
			is.close();
			bis.close();
			return Base64.getBase64(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
