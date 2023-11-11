package site.activeclub.uid.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wanyu
 */
public class SnowflakeUuidUtil {

    /**
     * business meaning: machine ID (0 ~ 1023)【 The corresponding id under each machine code is segmented monotonically increasing 】 actual layout in
     * memory: highest 1 bit: 0 next 10 bit: workerId [machine code] middle 41 bit: timestamp [timestamp]
     * lowest 12 bit: sequence [The self-increasing id in this timestamp, strictly monotonically increasing]
     */
    private static final AtomicLong idSequence = new AtomicLong(0L);
    private static Logger log = LoggerFactory.getLogger(SnowflakeUuidUtil.class);
    private static long workerId = 1L;

    /**
     * Service start-up registration
     */
    public static void initStartId() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String hostName = ia.getHostName();//获取计算机主机名
            log.info("The name of the host running the service is {}", hostName);
            String[] split = hostName.split("-");
            try {
                workerId = Long.parseLong(split[split.length - 1]);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.info("Rectify the error and restart. Currently using single node mode!");
                workerId = 1L;
            }
            log.info("Register the workerId is : {}", workerId);

            // 机器码左移至高位
            /**
             * Machine code left shift 53
             */
            long MACHINE_BIT = 53;
            long workerIndex = workerId << MACHINE_BIT;
            // Performs an or operation with the previously saved high 11 bits

            /**
             * Timestamp shifted left by 12
             */
            long TIMESTAMP_BIT = 12;
            long startId = workerIndex | (System.currentTimeMillis() << TIMESTAMP_BIT);
            idSequence.set(startId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize workId");
        }
    }


    /**
     * Get a self-increment unique id (unique within a service cluster)
     */
    public static long nextId() {
        long id = idSequence.incrementAndGet();
        log.debug("work node : {}, generate id : {}", workerId, id);
        return id;
    }

    public static long getWorkerId() {
        return workerId;
    }
}
