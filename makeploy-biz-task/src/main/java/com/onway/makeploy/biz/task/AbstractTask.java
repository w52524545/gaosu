package com.onway.makeploy.biz.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.helper.TimeKey;
import com.onway.platform.common.utils.LogUtil;

/**
 * ����task������
 * 
 * @author guangdong.li
 * @version $Id: AbstractTask.java, v 0.1 7 Jan 2016 13:53:15 guangdong.li Exp $
 */
public abstract class AbstractTask {

    /** logger  */
    protected static final Logger                       logger   = Logger
                                                                     .getLogger(AbstractTask.class);

    /** �̷߳��ظ�ִ�й���    */
    protected static final Map<Class<?>, AtomicBoolean> lockMaps = new ConcurrentHashMap<Class<?>, AtomicBoolean>();

    /**
     * ����ִ��,����TimeKey,�������taskִ��ҵ��״��
     */
    public void execute() {
        try {
            if (!preProcess()) {
                LogUtil.warn(logger, this.getClass().getSimpleName() + "ִ���նˣ�ԭ������taskִ����...");
                return;
            }

            if (canProcess()) {
                LogUtil.info(logger, "��ʼִ��task,taskName=>" + this.getClass().getSimpleName());
                process();
            }
        } catch (Throwable e) {
            LogUtil.error(logger, e, "ִ��task�쳣,taskName=>" + this.getClass().getSimpleName());
        } finally {
            afterProcess();
        }
    }

    /**
     * ҵ����ǰ���ж�
     */
    protected abstract boolean canProcess();

    /**
     * ҵ����
     */
    protected abstract void process();

    /**
     * ��ӡinfo��־
     * 
     * @param message
     */
    protected void printInfoLog(String message) {
        if (StringUtils.isNotBlank(message)) {
            LogUtil.info(logger, message);
        }
    }

    /**
     * taskִ��ǰ�� 
     * <pre>
     *      �����ظ�ִ�д���,�״�ִ�������ִ��class,�ٴ�ִ�оܾ�
     * </pre>
     * 
     * @return
     */
    protected boolean preProcess() {
        // ����ʱ���
        TimeKey.start();
        printInfoLog("ִ�� " + this.getClass().getSimpleName() + " ��ʼ...");
        //�����ǰ��ֵΪtrue��ʾ����ִ�д�task,����״̬��Ϊfalse����ִ��״̬
        if (!lockMaps.containsKey(this.getClass())) {
            lockMaps.put(this.getClass(), new AtomicBoolean(true));
            return true;
        }
        return false;
    }

    /**
     * ɾ��ִ������
     */
    protected void afterProcess() {
        printInfoLog("ִ�� " + this.getClass().getSimpleName() + " ����...");

        //�����ǰ���ڲ���ִ��״̬,������Ϊ��ִ��״̬,��ʾ��ǰ�߳���ִ����,�������������߳�����ִ��
        lockMaps.remove(this.getClass());
        // ���ʱ���
        TimeKey.clear();
    }

}
