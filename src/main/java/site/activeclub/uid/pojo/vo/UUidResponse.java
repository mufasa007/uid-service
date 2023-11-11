package site.activeclub.uid.pojo.vo;

import java.io.Serializable;

/**
 * @author wanyu
 */
public class UUidResponse implements Serializable {
    private String code;
    private String msg;
    private Long data;

    public UUidResponse() {
    }

    public String getCode() {
        return code;
    }

    public UUidResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public UUidResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Long getData() {
        return data;
    }

    public UUidResponse setData(Long data) {
        this.data = data;
        return this;
    }
}
