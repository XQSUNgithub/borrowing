package org.baichuan.borrow.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;


@Slf4j
public class JwtUtil {

    // 过期时间： 一天
    //public static final long EXPIRE = 1000 * 60 * 60*24;
    public static final long EXPIRE =1000*60*60*5*24;
    // 加密密钥
    public static final String APP_SECRET = "abcdefg";

    /**
     * 生成 token 字符串
     * @param id
     * @return
     */
    public static String getJwtToken(String id) {
        String jwtToken = Jwts.builder()
                // 设置 token 头部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置 token 的主题 subject，自定义
                .setSubject("token")
                // 设置 token 的创建时间
                .setIssuedAt(new Date())
                // 设置过期时间，于何时过期
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 设置 token 的有效载荷
                .claim("id", id)
              //  .claim("nickname", nickname)
                // 设置签名，使用的加密算法以及密钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return jwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken)throws Exception {
        if (StringUtils.isEmpty(jwtToken)) {
            log.info("空");
            return true;
        }/*
        try { //未检验过期时间
            Claims claims =
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e) {
            log.info("密码错误");
            e.printStackTrace();
            return false;
        }*/
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(APP_SECRET) // 设置标识名
                    .parseClaimsJws(jwtToken)  //解析token
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        Date expiration = claims.getExpiration();
        log.info(String.valueOf(new Date(System.currentTimeMillis()).after(expiration)));
        return new Date(System.currentTimeMillis()).after(expiration); //false时不过期
    }

    /**
     * 根据token获取会员id，根据用户 id 查询数据库获取用户基本信息
     * @return
     */
    public static String getMemberIdByJwtToken(String jwtToken)throws Exception {
        log.info(jwtToken);
        if (StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        log.info("getMemberIdByJwtToken");
        try {
            Jws<Claims> claimsJws =
                    Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
            log.info("claimsJws");
            Claims claims = claimsJws.getBody();
            log.info("getBody");
            log.info((String) claims.get("id"));
            return (String) claims.get("id");
        }catch(Exception e){
            return null;
        }

    }

}

