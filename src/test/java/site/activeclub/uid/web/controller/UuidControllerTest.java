package site.activeclub.uid.web.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import site.activeclub.uid.ApplicationTests;
import site.activeclub.uid.common.utils.SnowflakeUuidUtil;
import site.activeclub.uid.pojo.vo.UUidResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UuidControllerTest {

    @BeforeAll
    public static void init(){
        SnowflakeUuidUtil.initStartId();
    }


    @Test
    public void testGetUuid() {
        ResponseEntity<UUidResponse> response = sendGetRequest("/web/uuid/get");

        assertEquals("0", response.getBody().getCode());
        assertEquals(String.valueOf(SnowflakeUuidUtil.getWorkerId()), response.getBody().getMsg());
        assertEquals(SnowflakeUuidUtil.nextId() - 1L, response.getBody().getData());
    }

    private ResponseEntity<UUidResponse> sendGetRequest(String path) {
        switch (path){
            case "/web/uuid/get":
                return new UuidController().getUuid();
            default:
                return null;
        }
        // code to send a GET request and return the response
    }
}