package org.baichuan.borrow.domin;


import lombok.Data;

@Data
public class Record {
    private String id;
    private String cid;
    private String uuid;
    private String type;
    private String time0;
    private String time1;//带审批人
    private String time2;
    private String time3;//审批人

}
