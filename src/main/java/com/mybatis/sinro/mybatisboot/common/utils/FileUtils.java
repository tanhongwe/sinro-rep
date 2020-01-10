package com.mybatis.sinro.mybatisboot.common.utils;

import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author wxjason
 */
@Component
@Slf4j
public class FileUtils {


    /**
     * 图形交换格式
     */
    public static final String IMAGE_TYPE_GIF = "gif";

    /**
     * 联合照片专家组
     */
    public static final String IMAGE_TYPE_JPG = "jpg";

    /**
     * 联合照片专家组
     */
    public static final String IMAGE_TYPE_JPEG = "jpeg";
    /**
     * 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
     */
    public static final String IMAGE_TYPE_BMP = "bmp";
    /**
     * 可移植网络图形
     */
    public static final String IMAGE_TYPE_PNG = "png";
    /**
     * Photoshop的专用格式Photoshop
     */
    public static final String IMAGE_TYPE_PSD = "psd";

    /**
     * 删除目录
     *
     * @author fengshuonan
     * @Date 2017/10/30 下午4:15
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (CollectionUtils.isEmpty(children)) {
                return false;
            }
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 保存文件到临时目录下
     *
     * @param in
     * @param newFileName
     */
    public static String save(InputStream in, String newFileName, String filePath) throws FileNotFoundException {
        String tempFile = ResourceUtils.getFile("")
                .getAbsolutePath()
                .concat(File.separator)
                .concat("upload")
                .concat(File.separator)
                .concat("wrapper")
                .concat(File.separator)
                .concat(filePath)
                .concat(File.separator)
                .concat(newFileName);
        save(in, tempFile);
        return tempFile;
    }
    public static void save(InputStream in, String newFileName) {
        OutputStream os = null;
        try {
            String path = newFileName.substring(0, newFileName.lastIndexOf(File.separator));
            File pathFile = new File(path);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            File newFile = new File(newFileName);
            os = new FileOutputStream(newFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer, 0, NumberConstants.ONE_THOUSAND_TWENTY_FOUR)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void close(Writer output) {
        close((Closeable) output);
    }

    public static void close(InputStream input) {
        close((Closeable) input);
    }

    public static void close(OutputStream output) {
        close((Closeable) output);
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void download(HttpServletResponse response, String classPathFile) {
        download(response, classPathFile, ResourceUtils.CLASSPATH_URL_PREFIX);
    }

    public static void download(HttpServletResponse response, String filePath, String pathType) {
        InputStream is = null;
        OutputStream os = null;
        try {
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            if (pathType.equals(ResourceUtils.CLASSPATH_URL_PREFIX)) {
                ClassPathResource classPathResource = new ClassPathResource(filePath);
                is = classPathResource.getInputStream();
                fileName = classPathResource.getFilename();
            } else {
                File file = ResourceUtils.getFile(filePath);
                is = new FileInputStream(file);
            }

            response.addHeader("content-type", contentType(filePath).type);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            /// 注释原因: 加上下载模版被损坏
            response.addHeader("Content-Length", String.valueOf(is.available()));
            byte[] buff = new byte[1024];

            os = response.getOutputStream();
            int i = is.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = is.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
            close(is);
        }
    }

    public static ContentType contentType(String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            String postFix = fileName.substring((fileName.lastIndexOf(".") + 1));
            for (ContentType contentType : ContentType.values()) {
                if (contentType.postFix.equalsIgnoreCase(postFix)) {
                    return contentType;
                }
            }
        }
        return ContentType.UNKNOWN_TYPE;
    }

    enum ContentType {
        /**
         * application/octet-stream
         */
        UNKNOWN_TYPE("", "application/octet-stream"),
        /**
         * application/x-bmp
         */
        BMP_TYPE("bmp", "application/x-bmp"),
        /**
         * image/gif
         */
        GIF_TYPE("gif", "image/gif"),
        /**
         * application/x-img
         */
        IMG_TYPE("img", "application/x-img"),
        /**
         * application/x-jpg
         */
        JPG_TYPE("jpg", "application/x-jpg"),
        /**
         * application/vnd.ms-excel
         */
        XLS_EXCEL_TYPE("xls", "application/vnd.ms-excel"),
        /**
         * application/vnd.ms-excel
         */
        XLSX_EXCEL_TYPE("xlsx", "application/vnd.ms-excel");
        String postFix;
        String type;

        ContentType(String postFix, String type) {
            this.postFix = postFix;
            this.type = type;
        }

        public String getPostFix() {
            return postFix;
        }

        public void setPostFix(String postFix) {
            this.postFix = postFix;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static String imgType(byte[] imageData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
             MemoryCacheImageInputStream is = new MemoryCacheImageInputStream(bais);) {
            Iterator<ImageReader> it = ImageIO.getImageReaders(is);

            if (!it.hasNext()) {
                throw new IOException("非图片文件");
            }
            ImageReader reader = it.next();
            return reader.getFormatName();
        }
    }
    /**
     * 检测图片类型
     *
     * @param photo
     * @return
     */
    public static boolean checkType(String photo) {
        String imgtype = photo.substring(photo.lastIndexOf(".") + 1, photo.length()).toLowerCase();
        if (!IMAGE_TYPE_PNG.equalsIgnoreCase(imgtype)
                && !"jpeg".equalsIgnoreCase(imgtype)
                && !"jpg".equalsIgnoreCase(imgtype)) {
            return false;
        }
        return true;
    }

    public static String imageBase64(byte[] data) throws IOException {
        if (Objects.nonNull(data)) {
            String type = imgType(data);
            if (StringUtils.isEmpty(type)) {
                return "";
            }
            String prefix;
            switch (type.toLowerCase()) {
                case IMAGE_TYPE_PNG:
                    prefix = "data:image/png;base64,";
                    break;
                case IMAGE_TYPE_JPEG:
                    prefix = "data:image/jpeg;base64,";
                    break;
                case IMAGE_TYPE_GIF:
                    prefix = "data:image/gif;base64,";
                    break;
                case IMAGE_TYPE_JPG:
                    prefix = "data:image/jpg;base64,";
                    break;
                default:
                    return "";
            }
            return prefix + Base64Utils.encodeToString(data);
        }
        return "";
    }

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
        }
        return toByteArray(f);
    }
    /**
     * NIO way
     */
    public static byte[] toByteArray(File file) {

        if (!file.exists()) {
            log.error("文件未找到！" + file);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                /// do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            log.error("读取文件失败", e);
            return new byte[0];
        } finally {
            try {
                if (Objects.nonNull(channel)) {
                    channel.close();
                }
            } catch (IOException e) {
                log.error("读取文件失败", e);
            }
            try {
                if (Objects.nonNull(fs)) {
                    fs.close();
                }
            } catch (IOException e) {
                log.error("读取文件失败", e);
            }
        }
    }

    public static void copyInputStreamToFile(InputStream in, File targetFile) throws IOException {
        byte[] buffer = new byte[in.available()];
        OutputStream out = null;
        try {
            in.read(buffer);
            out = new FileOutputStream(targetFile);
            out.write(buffer, 0, buffer.length);
        } finally {
            in.close();
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
    }

    public static void copyFile(File file, FileOutputStream destTempfos) throws IOException {

        InputStream input = null;

        try {
            input = new FileInputStream(file);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            destTempfos.write(buffer, 0, buffer.length);
        } finally {
            if (Objects.nonNull(input)) {
                input.close();
            }
        }

    }

}