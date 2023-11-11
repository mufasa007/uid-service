package site.activeclub.uid.common.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnowflakeUuidUtilTest {

    @BeforeAll
    static void setUp() {
        SnowflakeUuidUtil.initStartId();
    }

    @Test
    void nextId() {
        long id1 = SnowflakeUuidUtil.nextId();
        long id2 = SnowflakeUuidUtil.nextId();

        assertEquals(id1 + 1L, id2);
    }

    @Test
    void getWorkerId() {
        assertEquals(1L, SnowflakeUuidUtil.getWorkerId());
    }
}