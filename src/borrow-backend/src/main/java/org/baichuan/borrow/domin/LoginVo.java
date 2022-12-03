package org.baichuan.borrow.domin;


import com.alibaba.fastjson.JSONObject;
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
	private String key;
	private JSONObject value;
	private boolean state;
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getKeyname(String name) {
		switch (name){
			case "user": return "uuid";
		}
		return "error";
	}
}
