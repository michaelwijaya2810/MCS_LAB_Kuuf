package com.mcs_project.mcs_lab_kuuf_project;

public class Transactions {
    int transId;
    int userId;
    int productId;
    String transcationDate;

    public Transactions(int transId, int userId, int productId, String transcationDate) {
        this.transId = transId;
        this.userId = userId;
        this.productId = productId;
        this.transcationDate = transcationDate;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTranscationDate() {
        return transcationDate;
    }

    public void setTranscationDate(String transcationDate) {
        this.transcationDate = transcationDate;
    }
}
