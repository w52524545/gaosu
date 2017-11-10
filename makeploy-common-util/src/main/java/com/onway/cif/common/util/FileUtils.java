package com.onway.cif.common.util;

import com.onway.common.lang.DateUtils;
import com.onway.platform.common.configration.ConfigrationFactory;



public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final String SUFFIX     = ".jpg";

    private static final String SUFFIX_PDF = ".pdf";

    /**
     * �����ļ�·�� ��ʽ: /201401/1234234123.jpg
     * 
     * @param cfgPath  ���õ��ļ�����Ŀ¼
     * @param userId
     * @return
     */
    public static String buildFilePath(String cfgPath, String userId) {
        return buildFilePathByName(cfgPath) + "/" + DateUtils.getTodayString().substring(0, 6)
               + "/" + userId + SUFFIX;
    }

    /**
     * �����ļ�·�� ��ʽ: 888888888_1234234123_cert.jpg
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :��cert
     * @return
     */
    public static String buildFilePath(String cfgPath, String userId, String authType) {
        return buildFilePathByName(cfgPath) + authType + "/" + DateUtils.getTodayString() + "/"
               + userId + "_" + System.currentTimeMillis() + "_" + authType + SUFFIX;
    }

    /**
     * �����ļ�·�� ��ʽ: 888888888_1234234123.pdf
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :��cert
     * @return
     */
    public static String buildFilePathForPDF(String cfgPath, String prefix) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + prefix + "_"
               + System.currentTimeMillis() + SUFFIX_PDF;
    }

    public static String buildFilePathForPDF(String cfgPath, String fundCode, String prefix) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + fundCode + "/"
               + prefix + SUFFIX_PDF;
    }

    /**
     * �����ļ�·�� ��ʽ: 888888888_1234234123_temp.pdf
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :��cert
     * @return
     */
    public static String buildFilePathForPDFTemp(String cfgPath, String userId) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + userId + "_"
               + System.currentTimeMillis() + "_temp" + SUFFIX_PDF;
    }

    /**
     * ���ڴ��л�ȡ�ļ�·��
     * 
     * @param cfgPath  ���õ��ļ�����Ŀ¼
     * @return
     */
    public static String buildFilePathByName(String cfgPath) {
        return ConfigrationFactory.getConfigration().getPropertyValue(cfgPath);
    }

}
