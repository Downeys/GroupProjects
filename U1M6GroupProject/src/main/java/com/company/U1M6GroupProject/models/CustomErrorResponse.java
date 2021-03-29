package com.company.U1M6GroupProject.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomErrorResponse {
    private String errorMessage;
    private int status;
    private String errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime localDateTime;

    public CustomErrorResponse(String errorMessage, String errorCode){
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        localDateTime = LocalDateTime.now();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomErrorResponse that = (CustomErrorResponse) o;
        return status == that.status && Objects.equals(errorMessage, that.errorMessage) && Objects.equals(errorCode, that.errorCode) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, status, errorCode, localDateTime);
    }

    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "errorMessage='" + errorMessage + '\'' +
                ", status=" + status +
                ", errorCode='" + errorCode + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
