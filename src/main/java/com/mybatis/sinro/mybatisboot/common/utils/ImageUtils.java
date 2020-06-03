package com.mybatis.sinro.mybatisboot.common.utils;

import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.common.constant.StringConstants;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Objects;

/**
 * @ClassName: ImageUtils
 * @Package com.tce.operator.jsiot.phone.util
 * @Description:
 * @Author wuxinjian
 * @Date 2019/3/27 18:39
 * @Version V1.0
 */
public class ImageUtils {

    /**
     * 对图片进行旋转
     *
     * @param base64   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static String rotate(String base64, int angel) throws IOException {
        byte[] buf = Base64Utils.decodeFromString(base64);
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(buf));
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rectangle = calcRotatedSize(new Rectangle(new Dimension(
                srcWidth, srcHeight)), angel);
        BufferedImage res = new BufferedImage(rectangle.width, rectangle.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rectangle.width - srcWidth) / 2,
                (rectangle.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);

        g2.drawImage(src, null, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(res, "jpg", out);
        return Base64Utils.encodeToString(out.toByteArray());
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        final int ninety = 90;
        final int two = 2;
        if (angel >= ninety) {
            if (angel / ninety % two == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % ninety;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDaltaWidth = Math.atan((double) src.height / src.width);
        double angelDaltaHeight = Math.atan((double) src.width / src.height);

        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha
                - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha
                - angelDaltaHeight));
        int desWidth = src.width + lenDaltaWidth * 2;
        int desHeight = src.height + lenDaltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }

    public static String cutImage(String base64, int left,
                              int top, int width, int height) throws IOException {
        byte[] buf = Base64Utils.decodeFromString(base64);
        InputStream input = new ByteArrayInputStream(buf);
        ImageInputStream imageStream = null;
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(input);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();

            Rectangle rect = new Rectangle(left, top, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);
            return Base64Utils.encodeToString(out.toByteArray());
        } finally {
            if (Objects.nonNull(imageStream)) {
                try {
                    imageStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param base64
     * @return
     */
    public static String compressImage (String base64) {
        try {
            File distFile = new File("temp" + File.separator + "thumbnail" + File.separator + UUIDUtils.create() + ".jpg");
            if (!distFile.exists()) {
                if (!distFile.getParentFile().exists()) {
                    distFile.getParentFile().mkdirs();
                }
                distFile.createNewFile();
            }
            byte[] src = Base64Utils.decodeFromString(base64);
            ByteArrayInputStream in = new ByteArrayInputStream(src);
            Thumbnails.of(in).scale(0.7).outputQuality(0.8).toFile(distFile);
            //Thumbnails.of(in).size(compressProperties.getWidth(), compressProperties.getHeight()).keepAspectRatio(false).toFile(distFile);
            InputStream inputStream = null;
            byte[] bytes;
            try {
                inputStream = new FileInputStream(distFile);
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, bytes.length);
            } finally {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
                if (distFile.exists()) {
                    distFile.delete();
                }
            }
            return Base64Utils.encodeToString(bytes);
        } catch (Exception ignored) {
        }
        return base64;
    }

    /**
     * 计算图片大小
     * @param imageBase64 图片Base64编码
     * @return
     */
    public static Integer imageSize(String imageBase64) {
        Integer equalIndex= imageBase64.indexOf(StringConstants.EQUAL_SIGN);
        if(imageBase64.indexOf(StringConstants.EQUAL_SIGN)>0) {
            imageBase64=imageBase64.substring(0, equalIndex);
        }
        Integer strLength=imageBase64.length();
        Integer size=strLength-(strLength/8)*2;
        return size;
    }

    /**
     * 计算图片大小,返回不超过maxSize的图片
     * @param imageBase64 图片Base64编码
     * @param maxSize 图片最大尺寸，如果图片超过此尺寸会返回压缩后图片,默认单位kb
     * @return
     */
    public static String imageSize(String imageBase64,int maxSize) {
        int size = imageSize(imageBase64);
        if (size > maxSize * NumberConstants.ONE_THOUSAND_TWENTY_FOUR) {
            return compressImage(imageBase64);
        }
        return imageBase64;
    }
}
