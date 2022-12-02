package org.baichuan.borrow.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.LoginVo;
import org.baichuan.borrow.domin.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
public class LoginService {
	
	
	public static final String COOKI_NAME_TOKEN = "token";
	@Autowired
	UserDao userDao;


	public String login(LoginVo loginVo) throws Exception{
		log.info("login");
		log.info((userDao.getByField("uuid",loginVo.getUuid(),"user").toJSONString()));
		User myUser= JSON.toJavaObject(userDao.getByField("uuid",loginVo.getUuid(),"user"),User.class);
		if(myUser==null)
			return null;
		//生成cookie
		String token = JwtUtil.getJwtToken(myUser.getUuid());
		log.info(token);
		return token;
	}
	public String register(LoginVo loginVo) throws Exception {
         log.info("register");
		//base64解码成json
/*
		String decode = new String(Base64.decodeBase64(loginVo), "UTF-8");
		String f = JSON.toJSONString(decode);
		JSONObject message = JSON.parseObject(f);
		String realname=message.getString("realname");
		String password=message.getString("password");*/
		if(!loginVo.getInvitecode().equals("123")){
			return null;
		}
		String realname=loginVo.getRealname();
		String password=loginVo.getPassword();
		Date time1 = new Date();
		String random= String.valueOf(time1.getTime()).substring(7,13)+ ((Double) Math.random()).toString().substring(4, 8);
		User newUser=new User();
		newUser.setUuid(random);
		newUser.setPassword(password);
		newUser.setRealname(realname);
		newUser.setState(false);
		userDao.insertUser(newUser);
		//生成cookie
		//String token	 = createToken(random);
		return random;
	}
	
	private void addCookie(HttpServletResponse response, String token, String user) {
		log.info(token);
		Cookie cookie =  new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
	public static final String SECRET = "JKKLJOoasdlfj";
	/** token 过期时间: 10天 */
	public static final int calendarField = Calendar.DATE;
	public static final int calendarInterval = 10;

	public static String createToken(String user_id) throws Exception {
		Date iatDate = new Date();
		// expire time
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(calendarField, calendarInterval);
		Date expiresDate = nowTime.getTime();

		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");

		// build token
		// param backups {iss:Service, aud:APP}
		String token = JWT.create().withHeader(map) // header
				.withClaim("iss", "Service") // payload
				.withClaim("aud", "APP").withClaim("user_id", user_id)
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(SECRET)); // signature

		return token;
	}

	public List<User> list(){
		//userDao.list()
		return null;
	}

}
