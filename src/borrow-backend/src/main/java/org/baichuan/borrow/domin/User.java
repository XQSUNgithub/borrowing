package org.baichuan.borrow.domin;

import lombok.Data;

@Data
public class User {
    private String uuid;
    private String realname;
    private String password;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    private boolean state;
}
