package org.baichuan.borrow.domin;


import lombok.Data;


@Data
public class LoginVo {
	

	private String realname;
	private String password;
	private String uuid;
	private String invitecode;
	private String action;
	private String keyword;
    private String tableName;
	private boolean state;
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
