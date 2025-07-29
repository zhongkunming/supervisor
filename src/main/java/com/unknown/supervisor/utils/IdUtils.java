package com.unknown.supervisor.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

/**
 * 雪花算法ID生成工具类
 *
 * @author zhongkunming
 */
@Slf4j
public final class IdUtils {

    /**
     * 自动寻找网卡时,默认启动最大时间间隔,超过这个初始化时间打印warn日志
     */
    private static final long MAX_START_INTERVAL_TIME = TimeUnit.SECONDS.toNanos(5);

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long TWEPOCH = 1288834974657L;

    /**
     * 机器标识位数
     */
    private static final long WORKER_ID_BITS = 1L;
    private static final long DATACENTER_ID_BITS = 1L;
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    /**
     * 毫秒内自增位
     */
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间戳左移动位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 单例实例
     */
    private static volatile IdUtils instance;

    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private InetAddress inetAddress;

    /**
     * 私有构造函数
     */
    private IdUtils() {
        long start = System.nanoTime();
        this.datacenterId = getDatacenterId(MAX_DATACENTER_ID);
        this.workerId = getMaxWorkerId(datacenterId, MAX_WORKER_ID);
        long end = System.nanoTime();
        if (end - start > MAX_START_INTERVAL_TIME) {
            log.warn("Initialization Sequence Very Slow! Get datacenterId:{} workerId:{}", this.datacenterId, this.workerId);
        } else {
            initLog();
        }
    }

    /**
     * 获取单例实例
     */
    private static IdUtils getInstance() {
        if (instance == null) {
            synchronized (IdUtils.class) {
                if (instance == null) {
                    instance = new IdUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 生成下一个ID（静态方法）
     *
     * @return 雪花算法生成的ID
     */
    public static long nextId() {
        return getInstance().generateNextId();
    }

    public static String nextIdStr() {
        return String.valueOf(getInstance().generateNextId());
    }

    private void initLog() {
        if (log.isDebugEnabled()) {
            log.debug("Initialization Sequence datacenterId:{} workerId:{}", this.datacenterId, this.workerId);
        }
    }

    /**
     * 获取 maxWorkerId
     */
    private long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotBlank(name)) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split(StringPool.AT)[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识id部分
     */
    private long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            if (null == this.inetAddress) {
                if (log.isDebugEnabled()) {
                    log.debug("Use localhost address ");
                }
                this.inetAddress = InetAddress.getLocalHost();
            }
            if (log.isDebugEnabled()) {
                log.debug("Get " + inetAddress + " network interface ");
            }
            NetworkInterface network = NetworkInterface.getByInetAddress(this.inetAddress);
            if (log.isDebugEnabled()) {
                log.debug("Get network interface info: " + network);
            }
            if (null == network) {
                log.warn("Unable to get network interface for " + inetAddress);
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
                    id = id % (maxDatacenterId + 1);
                }
            }
        } catch (Exception e) {
            log.warn(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    /**
     * 获取下一个 ID（内部方法）
     *
     * @return 下一个 ID
     */
    private synchronized long generateNextId() {
        long timestamp = timeGen();
        //闰秒
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }
        }

        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为 1 - 2 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastTimestamp = timestamp;

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return SystemClock.now();
    }
}
