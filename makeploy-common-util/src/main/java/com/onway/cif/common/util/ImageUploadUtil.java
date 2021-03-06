package com.onway.cif.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 图片上传工具类
 * 
 * @author Administrator
 * @version $Id: ImageUploadUtil.java, v 0.1 2016年4月13日 下午3:58:08 Administrator Exp $
 */
public class ImageUploadUtil {

    private static final String OS_WINDOWS = "Windows";
    /**
     * 文件上传的基本路径
     */
    private static String       BASE_PATH  = "/usr/local/upload";

    //定义一个数组，用于保存可上传的文件类型 
    private static List<String> fileTypes  = null;
    static {
        /**
         * 根据不同系统，初始化不同的图片保存路径
         */
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            BASE_PATH = "D:/upload/file/";
        }

        //定义一个数组，用于保存可上传的文件类型 
        fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("txt");
        fileTypes.add("png");
    }

    /**
     * 判断请求里面是否存在数据
     * 
     * @param request
     * @param keyName
     * @return
     */
    public static boolean exitImage(HttpServletRequest request, String keyName) {
        //转型为MultipartHttpRequest(重点的所在)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  获得第1张图片（根据前台的name名称得到上传的文件）   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //保存第一张图片  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            return true;
        }
        return false;
    }

    /**
     * 上传图片文件,并保存到指定的路径当中 
     * 
     * @param request
     * @param path1
     * @param path2
     * @param keyName 早form中的name
     * @return
     */
    public static String uploadImage(HttpServletRequest request, String path1, String path2,
                                     String keyName) {
        //转型为MultipartHttpRequest(重点的所在)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  获得第1张图片（根据前台的name名称得到上传的文件）   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //保存第一张图片  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            /*下面调用的方法，主要是用来检测上传的文件是否属于允许上传的类型范围内，及根据传入的路径名 
            *自动创建文件夹和文件名，返回的File文件我们可以用来做其它的使用，如得到保存后的文件名路径等 
            *这里我就先不做多的介绍。 
            */
            File file1 = getFile(imgFile1, path1, path2);
            return file1.getPath();
        }
        return null;
    }

    /*<p>  
        </p><p>其实上面的代码还是比较简单的，重点再于将我们常见的request对象转换为<span style="white-space: pre;">MultipartHttpRequest对象，有了这个对象，我们就可以得到用户上传的文件了。得到用户上传的文件之后，</span></p>  
        <p><span style="white-space: pre;">我们就可以做一些我们想做的事情了。在上面我们还做了一些事，那就是判断用户上传的文件类型是否属于我们所定义的那个</span></p>  
        <p>数组内的类型，至于如何判断是否属于允许上传的类型，我会在下面的方法当中给出。其实也可以将下面的代码写在一个方法里，但是了为重用，我就分开写了。也许我的做法不是最好的。相当于给大家一个方向吧！</p>  
        <p> </p>  
        <p>我们来看下面的两个方法，这两个方法最主要做两件事。一、判断用户上传的文件是否属于我们定义的类型范围之内，第二、将文件保存到指定的路径，这个路径是我们自己创建的。</p>  
        <p>  
        </p>
        <pre class="java" name="code">/** 
         * 通过传入页面读取到的文件，处理后保存到本地磁盘，并返回一个已经创建好的File 
         * @param imgFile 从页面中读取到的文件 
         * @param typeName  商品的分类名称 
         * @param brandName 商品的品牌名称 
         * @param fileTypes 允许的文件扩展名集合 
         * @return 
         */
    public static File getFile(MultipartFile imgFile, String typeName, String brandName) {
        String fileName = imgFile.getOriginalFilename();
        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //对扩展名进行小写转换  
        //        ext = ext.toLowerCase();

        File file = null;
        if (fileTypes.contains(ext.toLowerCase())) { //如果扩展名属于允许上传的类型，则创建文件  
            try {
                file = creatFolder(typeName, fileName);
                imgFile.transferTo(file); //保存上传的文件  
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /** 
     * 检测与创建一级、二级文件夹、文件名 
                 这里我通过传入的两个字符串来做一级文件夹和二级文件夹名称 
                通过此种办法我们可以做到根据用户的选择保存到相应的文件夹下 
     */
    private static File creatFolder(String typeName, String brandName, String fileName) {
        File file = null;
        typeName = typeName.replaceAll("/", ""); //去掉"/"  
        typeName = typeName.replaceAll(" ", ""); //替换半角空格  
        typeName = typeName.replaceAll(" ", ""); //替换全角空格  

        brandName = brandName.replaceAll("/", ""); //去掉"/"  
        brandName = brandName.replaceAll(" ", ""); //替换半角空格  
        brandName = brandName.replaceAll(" ", ""); //替换全角空格  

        File firstFolder = new File(typeName); //一级文件夹  
        if (firstFolder.exists()) { //如果一级文件夹存在，则检测二级文件夹  
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder, fileName);
            } else { //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName); //创建完二级文件夹后，再合建文件  
            }
        } else { //如果一级不存在，则创建一级文件夹  
            firstFolder.mkdir();
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder, fileName);
            } else { //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);
            }
        }
        return file;
    }

    /**
     * 创建文件
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
