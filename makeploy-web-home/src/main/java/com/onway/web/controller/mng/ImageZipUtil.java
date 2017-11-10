package com.onway.web.controller.mng;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
  
/**
 * 图片上传压缩工具类
 * @author wenqiang.Wang
 * @version $Id: ImageZipUtil.java, v 0.1 2016年11月21日 下午11:45:46 wenqiang.Wang Exp $
 */
public class ImageZipUtil {  
    /** 
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传 
     * @param oldFile  要进行压缩的文件 
     * @param newFile  新文件 
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放） 
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放） 
     * @param quality 质量 
     * @return 返回压缩后的文件的全路径 
     */  
    public static String zipImageFile(MultipartFile oldFile,String newFile, int width, int height,  
            float quality) {  
        if (oldFile == null) {  
            return null;  
        }  
        try {  
            /** 对服务器上的临时文件进行处理 */  
            Image srcFile = ImageIO.read(oldFile.getInputStream());  
            int w = srcFile.getWidth(null);  
        //  System.out.println(w);  
            int h = srcFile.getHeight(null);  
        //  System.out.println(h);  
            double bili;  
            if(width>0){  
                bili=width/(double)w;  
                height = (int) (h*bili);  
            }else{  
                if(height>0){  
                    bili=height/(double)h;  
                    width = (int) (w*bili);  
                }  
            }  
            /** 宽,高设定 */  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.'));  
            /** 压缩后的文件名 */  
            //newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length());  
  
            /** 压缩之后临时存放位置 */  
            FileOutputStream out = new FileOutputStream(newFile);  
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** 压缩质量 */  
            jep.setQuality(quality, true);  
            encoder.encode(tag, jep);  
            out.close();  
  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return newFile;  
    }  
  
    /** 
     * 按宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传 
     * @param oldFile  要进行压缩的文件全路径 
     * @param newFile  新文件 
     * @param width  宽度 
     * @param height 高度 
     * @param quality 质量 
     * @return 返回压缩后的文件的全路径 
     */  
    public static String zipWidthHeightImageFile(MultipartFile oldFile,String newFile, int width, int height,  
            float quality) {  
        if (oldFile == null) {  
            return null;  
        }  
        String newImage = null;  
        try {  
            /** 对服务器上的临时文件进行处理 */  
            Image srcFile = ImageIO.read(oldFile.getInputStream());  
            int w = srcFile.getWidth(null);  
            System.out.println(w);  
            int h = srcFile.getHeight(null);  
            System.out.println(h);  
  
            /** 宽,高设定 */  
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));  
            /** 压缩后的文件名 */  
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());  
  
            /** 压缩之后临时存放位置 */  
            FileOutputStream out = new FileOutputStream(newFile);  
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** 压缩质量 */  
            jep.setQuality(quality, true);  
            encoder.encode(tag, jep);  
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return newImage;  
    }  
    
    /*根据压缩质量来压缩图片
     * 
     */
    public static boolean compressPic(MultipartFile srcFilePath, String descFilePath) throws IOException {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
 
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality((float) 0.3);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel =ImageIO.read(srcFilePath.getInputStream()).getColorModel();// ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
//        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
//                colorModel, colorModel.createCompatibleSampleModel(16, 16)));
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(16, 16)));
 
        try {
            if (srcFilePath.isEmpty()) {
                return false;
            } else {
//                file = new File(srcFil);
//                System.out.println(file.length());
                src = ImageIO.read(srcFilePath.getInputStream());
                out = new FileOutputStream(descFilePath);
 
                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
public static boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.trim().equals("")) {
            return true;
        }
        return false;
    }

}