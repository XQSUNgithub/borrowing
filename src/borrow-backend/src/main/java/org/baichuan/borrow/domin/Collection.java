package org.baichuan.borrow.domin;


import lombok.Data;
import org.apache.ibatis.annotations.Delete;

@Data
public class Collection
{
    private String cid;
    private String source;
    private String purpose;
    private String location;
    private String provider;
    private String providetime;
    private String gatherer;
    private String donor;
    private String destroyed;
    private String num;
    private String recorder;
    private String access;
    private String recorderid;
    private String name;
}
