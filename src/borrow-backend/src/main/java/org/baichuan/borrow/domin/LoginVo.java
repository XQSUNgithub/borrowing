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
	private String keytype;
    private String tableName;
	private byte state=-1;

}
