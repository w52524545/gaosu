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
 * ͼƬ�ϴ�ѹ��������
 * @author wenqiang.Wang
 * @version $Id: ImageZipUtil.java, v 0.1 2016��11��21�� ����11:45:46 wenqiang.Wang Exp $
 */
public class ImageZipUtil {  
    /** 
     * �ȱ���ѹ��ͼƬ�ļ�<br> �ȱ���ԭ�ļ�����ѹ�����ϴ� 
     * @param oldFile  Ҫ����ѹ�����ļ� 
     * @param newFile  ���ļ� 
     * @param width  ��� //���ÿ��ʱ���߶ȴ���0���ȱ������ţ� 
     * @param height �߶� //���ø߶�ʱ����ȴ���0���ȱ������ţ� 
     * @param quality ���� 
     * @return ����ѹ������ļ���ȫ·�� 
     */  
    public static String zipImageFile(MultipartFile oldFile,String newFile, int width, int height,  
            float quality) {  
        if (oldFile == null) {  
            return null;  
        }  
        try {  
            /** �Է������ϵ���ʱ�ļ����д��� */  
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
            /** ��,���趨 */  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.'));  
            /** ѹ������ļ��� */  
            //newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length());  
  
            /** ѹ��֮����ʱ���λ�� */  
            FileOutputStream out = new FileOutputStream(newFile);  
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** ѹ������ */  
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
     * ����ȸ߶�ѹ��ͼƬ�ļ�<br> �ȱ���ԭ�ļ�����ѹ�����ϴ� 
     * @param oldFile  Ҫ����ѹ�����ļ�ȫ·�� 
     * @param newFile  ���ļ� 
     * @param width  ��� 
     * @param height �߶� 
     * @param quality ���� 
     * @return ����ѹ������ļ���ȫ·�� 
     */  
    public static String zipWidthHeightImageFile(MultipartFile oldFile,String newFile, int width, int height,  
            float quality) {  
        if (oldFile == null) {  
            return null;  
        }  
        String newImage = null;  
        try {  
            /** �Է������ϵ���ʱ�ļ����д��� */  
            Image srcFile = ImageIO.read(oldFile.getInputStream());  
            int w = srcFile.getWidth(null);  
            System.out.println(w);  
            int h = srcFile.getHeight(null);  
            System.out.println(h);  
  
            /** ��,���趨 */  
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));  
            /** ѹ������ļ��� */  
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());  
  
            /** ѹ��֮����ʱ���λ�� */  
            FileOutputStream out = new FileOutputStream(newFile);  
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** ѹ������ */  
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
    
    /*����ѹ��������ѹ��ͼƬ
     * 
     */
    public static boolean compressPic(MultipartFile srcFilePath, String descFilePath) throws IOException {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
 
        // ָ��дͼƬ�ķ�ʽΪ jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // Ҫʹ��ѹ��������ָ��ѹ����ʽΪMODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // ����ָ��ѹ���ĳ̶ȣ�����qality��ȡֵ0~1��Χ�ڣ�
        imgWriteParams.setCompressionQuality((float) 0.3);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel =ImageIO.read(srcFilePath.getInputStream()).getColorModel();// ColorModel.getRGBdefault();
        // ָ��ѹ��ʱʹ�õ�ɫ��ģʽ
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
                // ������ָ�� outֵ�����ܵ���write����, ImageOutputStream����ͨ���κ�
                // OutputStream����
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // ����write�������Ϳ�����������дͼƬ
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