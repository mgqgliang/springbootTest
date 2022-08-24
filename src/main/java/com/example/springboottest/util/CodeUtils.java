package com.example.springboottest.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class CodeUtils {

    private static final Map<String, List<Long>> codeMap = new HashMap<String, List<Long>>();

    public static synchronized String getBusinessCode(String prefix) {
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        // 获取出来当前毫秒的六位数
        String count = (ca.get(Calendar.YEAR) + "").substring(2) + ca.get(Calendar.DAY_OF_YEAR);
        List<Long> l = codeMap.get(count);
        if (l == null) {
            l = new ArrayList<Long>();
            for (long i = 100000; i++ < 1000000;) {
                l.add(i);
            }
            codeMap.put(count, l);
        }

        Long _temp = l.get(0);
        l.remove(0);
        if(StringUtils.isNotEmpty(prefix)){
            return prefix + "-" + codeUtils.nextId();
        }else{
            return codeUtils.nextId() + "";
        }

    }

    public static synchronized String getBusinessCode(String prefix, Boolean longType) {

        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        // 获取出来当前毫秒的六位数
        String count = (ca.get(Calendar.YEAR) + "").substring(2) + ca.get(Calendar.DAY_OF_YEAR)
                + ca.get(Calendar.HOUR_OF_DAY);
        if (longType) {
            count += ca.get(Calendar.MINUTE);
        }
        List<Long> l = codeMap.get(count);
        if (l == null) {
            l = new ArrayList<Long>();
            for (long i = 100000; i++ < 1000000;) {
                l.add(i);
            }
            codeMap.put(count, l);
        }

        Long _temp = l.get(0);
        l.remove(0);

        return prefix + "-" + codeUtils.nextId();

    }

    public static synchronized String getBusinessCode(int type, int randomLength) {

        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        // 获取出来当前毫秒的六位数
        String count = (ca.get(Calendar.YEAR) + "").substring(2) + ca.get(Calendar.MONTH + 1) + ca.get(Calendar.DATE);

        List<Long> l = codeMap.get(count);
        if (l == null) {
            l = new ArrayList<Long>();
            for (long i = 1; i++ < Math.pow(10, randomLength);) {
                l.add(i);
            }
            codeMap.put(count, l);
        }

        Long _temp = l.get(0);
        l.remove(0);
        String prefix = "";
        switch (type) {
            case 1:
                prefix = "CK";
                break;

            default:
                prefix = "";
                break;
        }


        return prefix + "-" +codeUtils.nextId();

    }


    public CodeUtils() {

    }

    private static final CodeUtils codeUtils = new CodeUtils(1, 1);
    // private final long twepoch = 1597393660213L;
    // 初始时间戳
    private long twepoch = 1288834974657L;
    // 长度为5位
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    // 最大值
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 序列号id长度
    private long sequenceBits = 12L;
    // 序列号最大值
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 工作id需要左移的位数，12位
    private long workerIdShift = sequenceBits;
    // 数据id需要左移位数 12+5=17位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间戳需要左移位数 12+5+5=22位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public CodeUtils(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public static String getCode() {
        return String.valueOf(codeUtils.nextId());
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            // 服务器时钟被调整了,ID生成器停止服务.
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }


    public static void testProductId(int dataCenterId, int workerId, int n) {
        CodeUtils idWorker = new CodeUtils(workerId, dataCenterId);
        CodeUtils idWorker2 = new CodeUtils(workerId + 1, dataCenterId);
        Set<Long> setOne = new HashSet<>();
        Set<Long> setTow = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            setOne.add(idWorker.nextId());// 加入set
        }
        long end1 = System.currentTimeMillis() - start;
        System.out.printf("第一批ID预计生成{%d}个,实际生成{%d}个<<<<*>>>>共耗时:{%d}", n, setOne.size(), end1);
        System.out.println();
        for (int i = 0; i < n; i++) {
            setTow.add(idWorker2.nextId());// 加入set
        }
        long end2 = System.currentTimeMillis() - start;
        System.out.printf("第二批ID预计生成{%d}个,实际生成{%d}个<<<<*>>>>共耗时:{%d}", n, setTow.size(), end2);
        System.out.println();
        setOne.addAll(setTow);
        System.out.printf("合并总计生成ID个数:{%d}", setOne.size());
        System.out.println();
    }

    public static void testPerSecondProductIdNums() {
        CodeUtils idWorker = new CodeUtils(1, 2);
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; System.currentTimeMillis() - start < 1000; i++, count = i) {
            idWorker.nextId();
        }
        long end = System.currentTimeMillis() - start;
        System.out.println(end);
        System.out.println(count);
    }
}
