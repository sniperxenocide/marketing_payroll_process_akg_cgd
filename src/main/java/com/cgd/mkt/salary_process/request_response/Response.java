package com.cgd.mkt.salary_process.request_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Response implements Serializable {
    private boolean status;
    private String msg;
    private Object data;
    public Response(boolean status, String msg){
        this.status = status;
        this.msg = msg;
    }
}
