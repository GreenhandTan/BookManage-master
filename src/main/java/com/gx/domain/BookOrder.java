package com.gx.domain;

import lombok.Data;
//这里我们使用了lombook插件，一个@Data注解就可以搞定
@Data
public class BookOrder {
    /**
     * 借书订单表
     */
    private int orderid;
    private int userid;
    private int bid;
    private int orderstatus;//借还状态 0表示已归还，1表示未归还

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(int orderstatus) {
        this.orderstatus = orderstatus;
    }
}
