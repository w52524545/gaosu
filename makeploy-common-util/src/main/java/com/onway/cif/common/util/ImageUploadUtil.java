package com.onway.cif.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * ͼƬ�ϴ�������
 * 
 * @author Administrator
 * @version $Id: ImageUploadUtil.java, v 0.1 2016��4��13�� ����3:58:08 Administrator Exp $
 */
public class ImageUploadUtil {

    private static final String OS_WINDOWS = "Windows";
    /**
     * �ļ��ϴ��Ļ���·��
     */
    private static String       BASE_PATH  = "/usr/local/upload";

    //����һ�����飬���ڱ�����ϴ����ļ����� 
    private static List<String> fileTypes  = null;
    static {
        /**
         * ���ݲ�ͬϵͳ����ʼ����ͬ��ͼƬ����·��
         */
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            BASE_PATH = "D:/upload/file/";
        }

        //����һ�����飬���ڱ�����ϴ����ļ����� 
        fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("txt");
        fileTypes.add("png");
    }

    /**
     * �ж����������Ƿ��������
     * 
     * @param request
     * @param keyName
     * @return
     */
    public static boolean exitImage(HttpServletRequest request, String keyName) {
        //ת��ΪMultipartHttpRequest(�ص������)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  ��õ�1��ͼƬ������ǰ̨��name���Ƶõ��ϴ����ļ���   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //�����һ��ͼƬ  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            return true;
        }
        return false;
    }

    /**
     * �ϴ�ͼƬ�ļ�,�����浽ָ����·������ 
     * 
     * @param request
     * @param path1
     * @param path2
     * @param keyName ��form�е�name
     * @return
     */
    public static String uploadImage(HttpServletRequest request, String path1, String path2,
                                     String keyName) {
        //ת��ΪMultipartHttpRequest(�ص������)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  ��õ�1��ͼƬ������ǰ̨��name���Ƶõ��ϴ����ļ���   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //�����һ��ͼƬ  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            /*������õķ�������Ҫ����������ϴ����ļ��Ƿ����������ϴ������ͷ�Χ�ڣ������ݴ����·���� 
            *�Զ������ļ��к��ļ��������ص�File�ļ����ǿ���������������ʹ�ã���õ��������ļ���·���� 
            *�����Ҿ��Ȳ�����Ľ��ܡ� 
            */
            File file1 = getFile(imgFile1, path1, path2);
            return file1.getPath();
        }
        return null;
    }

    /*<p>  
        </p><p>��ʵ����Ĵ��뻹�ǱȽϼ򵥵ģ��ص����ڽ����ǳ�����request����ת��Ϊ<span style="white-space: pre;">MultipartHttpRequest������������������ǾͿ��Եõ��û��ϴ����ļ��ˡ��õ��û��ϴ����ļ�֮��</span></p>  
        <p><span style="white-space: pre;">���ǾͿ�����һЩ���������������ˡ����������ǻ�����һЩ�£��Ǿ����ж��û��ϴ����ļ������Ƿ�����������������Ǹ�</span></p>  
        <p>�����ڵ����ͣ���������ж��Ƿ����������ϴ������ͣ��һ�������ķ������и�������ʵҲ���Խ�����Ĵ���д��һ�������������Ϊ���ã��Ҿͷֿ�д�ˡ�Ҳ���ҵ�����������õġ��൱�ڸ����һ������ɣ�</p>  
        <p> </p>  
        <p>�������������������������������������Ҫ�������¡�һ���ж��û��ϴ����ļ��Ƿ��������Ƕ�������ͷ�Χ֮�ڣ��ڶ������ļ����浽ָ����·�������·���������Լ������ġ�</p>  
        <p>  
        </p>
        <pre class="java" name="code">/** 
         * ͨ������ҳ���ȡ�����ļ�������󱣴浽���ش��̣�������һ���Ѿ������õ�File 
         * @param imgFile ��ҳ���ж�ȡ�����ļ� 
         * @param typeName  ��Ʒ�ķ������� 
         * @param brandName ��Ʒ��Ʒ������ 
         * @param fileTypes ������ļ���չ������ 
         * @return 
         */
    public static File getFile(MultipartFile imgFile, String typeName, String brandName) {
        String fileName = imgFile.getOriginalFilename();
        //��ȡ�ϴ��ļ����͵���չ��,�ȵõ�.��λ�ã��ٽ�ȡ��.����һ��λ�õ��ļ���������õ���չ��  
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //����չ������Сдת��  
        //        ext = ext.toLowerCase();

        File file = null;
        if (fileTypes.contains(ext.toLowerCase())) { //�����չ�����������ϴ������ͣ��򴴽��ļ�  
            try {
                file = creatFolder(typeName, fileName);
                imgFile.transferTo(file); //�����ϴ����ļ�  
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /** 
     * ����봴��һ���������ļ��С��ļ��� 
                 ������ͨ������������ַ�������һ���ļ��кͶ����ļ������� 
                ͨ�����ְ취���ǿ������������û���ѡ�񱣴浽��Ӧ���ļ����� 
     */
    private static File creatFolder(String typeName, String brandName, String fileName) {
        File file = null;
        typeName = typeName.replaceAll("/", ""); //ȥ��"/"  
        typeName = typeName.replaceAll(" ", ""); //�滻��ǿո�  
        typeName = typeName.replaceAll(" ", ""); //�滻ȫ�ǿո�  

        brandName = brandName.replaceAll("/", ""); //ȥ��"/"  
        brandName = brandName.replaceAll(" ", ""); //�滻��ǿո�  
        brandName = brandName.replaceAll(" ", ""); //�滻ȫ�ǿո�  

        File firstFolder = new File(typeName); //һ���ļ���  
        if (firstFolder.exists()) { //���һ���ļ��д��ڣ���������ļ���  
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //��������ļ���Ҳ���ڣ��򴴽��ļ�  
                file = new File(secondFolder, fileName);
            } else { //��������ļ��в����ڣ��򴴽������ļ���  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName); //����������ļ��к��ٺϽ��ļ�  
            }
        } else { //���һ�������ڣ��򴴽�һ���ļ���  
            firstFolder.mkdir();
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //��������ļ���Ҳ���ڣ��򴴽��ļ�  
                file = new File(secondFolder, fileName);
            } else { //��������ļ��в����ڣ��򴴽������ļ���  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);
            }
        }
        return file;
    }

    /**
     * �����ļ�
     * 
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File creatFolder(String url, String fileName) throws IOException {
        File file = creatFolder(url, "", fileName);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        if (!file.createNewFile()) {
            return null;
        }
        return file;
    }
}
