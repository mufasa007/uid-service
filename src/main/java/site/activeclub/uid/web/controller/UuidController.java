package site.activeclub.uid.web.controller;

import site.activeclub.uid.common.utils.SnowflakeUuidUtil;
import site.activeclub.uid.pojo.vo.UUidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanyu
 */
@RestController
public class UuidController {

    @GetMapping("/web/uuid/get")
    public ResponseEntity<UUidResponse> getUuid() {
        return ResponseEntity.ok(new UUidResponse().setCode("0").setMsg(String.valueOf(SnowflakeUuidUtil.getWorkerId())).setData(SnowflakeUuidUtil.nextId()));
    }

}
