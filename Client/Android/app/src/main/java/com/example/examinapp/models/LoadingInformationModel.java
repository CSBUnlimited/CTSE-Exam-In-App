package com.example.examinapp.models;

public class LoadingInformationModel {

    private boolean isSuccess;
    private boolean isPending;
    private boolean isError;
    private String message;


    public LoadingInformationModel(boolean isPending, boolean isError, boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.isPending = isPending;
        this.isError = isError;
        this.message = message;
    }

    public LoadingInformationModel() {
        this.message = "";
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(boolean isPending) {
        this.isPending = isPending;
    }

    public boolean getIsSucess() {
        return isSuccess;
    }

    public void setIsSucess(boolean isSucess) {
        this.isSuccess = isSucess;
    }
}
