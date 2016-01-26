package com.adp.utils.exceptions;

import javax.ws.rs.core.Response;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class ApiException extends Exception {

    private Response.Status status;
    private ResponseErrorMsg reason;
    private String message;

    public ApiException(){
    }

    public ApiException(Response.Status status, String message) {
        this.status = status;
        this.reason = ResponseErrorMsg.RUNTIME_ERROR;
        this.message = message;
    }

    public ApiException(Response.Status status, ResponseErrorMsg reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public ResponseErrorMsg getReason() {
        return reason;
    }

    public void setReason(ResponseErrorMsg reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
