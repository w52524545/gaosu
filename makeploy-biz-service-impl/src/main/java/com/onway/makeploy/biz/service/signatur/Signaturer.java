package com.onway.makeploy.biz.service.signatur;

import java.io.InputStream;

/**
 * ǩ��������
 * 
 * @author guangdong.li
 * @version $Id: Signaturer.java, v 0.1 17 Feb 2016 15:13:05 guangdong.li Exp $
 */
public interface Signaturer {

    /**
     * ��ָ���ַ�������ǩ����
     * 
     * @param key 
     *            ָ��ǩ��key
     * @param content
     *            Ҫǩ�����ַ���
     * @param keyPairName
     *            key pair
     * 
     * @return base64�����ǩ��
     */
    String sign(byte[] key, String content);

    /**
     * ��ָ���ַ�������ǩ����
     * 
     * @param content
     *            Ҫǩ�����ַ���
     * @param keyPairName
     *            key pair
     * 
     * @return base64�����ǩ��
     */
    String sign(String content);

    /**
     * ��ָ���ַ�������ǩ����
     * 
     * @param content
     *            Ҫǩ�����ַ���
     * @param keyPairName
     *            key pair
     * @param charset
     *            �ַ����ı����ַ���
     * 
     * @return base64�����ǩ��
     */
    String sign(String content, String charset);

    /**
     * ��ָ���ֽ�������ǩ����
     * 
     * @param content
     *            Ҫǩ�����ֽ���
     * @param keyPairName
     *            key pair
     * 
     * @return base64�����ǩ��
     */
    String sign(byte[] content);

    /**
     * ��ָ������������ǩ����
     * 
     * @param content
     *            Ҫǩ����������
     * @param keyPairName
     *            key pair
     * 
     * @return base64�����ǩ��
     */
    String sign(InputStream content);

    /**
     * ����content��ǩ����
     * 
     * @param content
     *            Ҫ���������
     * @param signature
     *            ǩ��
     * @param keyPairName
     *            key pair
     * 
     * @return ���ǩ����ȷ���򷵻�<code>true</code>
     */
    boolean check(String content, String signature, String charset);

    /**
     * ���ǩ��
     * 
     * @param content
     * @param signature
     * @return
     */
    boolean check(String content, String signature);

    /**
     * ����content��ǩ����
     * 
     * @param content
     *            Ҫ���������
     * @param signature
     *            ǩ��
     * @param keyPairName
     *            key pair
     * 
     * @return ���ǩ����ȷ���򷵻�<code>true</code>
     */
    boolean check(byte[] content, String signature);

    /**
     * ����content��ǩ����
     * 
     * @param content
     *            Ҫ���������
     * @param signature
     *            ǩ��
     * @param keyPairName
     *            key pair
     * 
     * @return ���ǩ����ȷ���򷵻�<code>true</code>
     */
    boolean check(InputStream content, String signature);
}
