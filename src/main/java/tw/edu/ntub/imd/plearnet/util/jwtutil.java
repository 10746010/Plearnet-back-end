package tw.edu.ntub.imd.plearnet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;

import java.time.ZonedDateTime;
import java.util.Date;

public class jwtutil {
        String secret = "vsvbdfejoijmlkpbkopsfkvl;kgbpfskvl[sfkvbd";

        public String parseToken(String token) {
               try {

                        // Claims就是JWT的payload部分
                        // setSigningKey(String base64EncodedKeyBytes)只吃base64編碼的字串, 傳入無法base64解碼的字串會發生錯誤
                        Claims body = Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(token)
                                .getBody();

                        String name = (String) body.get("name");
                        Integer id = (Integer) body.get("id");


                        // 以下設定會影響Spring Security是否讓此帳號通過驗證
                        boolean enabled = true;               // 此帳號是否啟用
                        boolean accountNonExpired = true;     // 此帳號是否未過期
                        boolean credentialsNonExpired = true; // 此憑證是否過期
                        boolean accountNonLocked = true;      // 此帳號是否鎖住

                        String user = name + " " + id;

                        return user;

                } catch (JwtException | ClassCastException e) {
                        return "1";
                } catch (Exception e) {
                        return "11112";
                }
        }


        public String generateToken(String name, Integer id,Integer sex, String email) {
                Claims claims = Jwts.claims().setSubject("");
                claims.put("name", name);
                claims.put("id", id);

                String token = Jwts.builder()
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS512, secret)
                        .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(60).toInstant()))
                        .compact();

                return token;
        }



}
