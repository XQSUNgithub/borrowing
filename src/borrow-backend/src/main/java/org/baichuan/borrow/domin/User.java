package org.baichuan.borrow.domin;

import lombok.Data;

@Data
public class User {
    private String uuid;
    private String realname;
    private String password;
    private byte state;
}
