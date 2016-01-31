package com.adp.sockets.models;

import com.adp.sockets.models.enums.MessageType;
import com.adp.utils.exceptions.ResponseErrorMsg;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by adarsh.sharma on 31/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocketMessage {
    private MessageType messageType;
    private String data;
    private ResponseErrorMsg responseErrorMsg;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResponseErrorMsg getResponseErrorMsg() {
        return responseErrorMsg;
    }

    public void setResponseErrorMsg(ResponseErrorMsg responseErrorMsg) {
        this.responseErrorMsg = responseErrorMsg;
    }
}
