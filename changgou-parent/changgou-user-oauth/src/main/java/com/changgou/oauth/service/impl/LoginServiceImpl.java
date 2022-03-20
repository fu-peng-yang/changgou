package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.LoginService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.oauth.service.impl *
 * @since 1.0
 */
@Service
public class  LoginServiceImpl implements LoginService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grandType) throws Exception {

        //String url = "http://localhost:9001/oauth/token";
        //1.定义url (申请令牌的url)
        //参数 : 微服务的名称spring.appplication指定的名称
        ServiceInstance choose = loadBalancerClient.choose("user-auth");
        if (choose == null) {
            throw new RuntimeException("找不到对应的服务");
        }
        String url =choose.getUri().toString()+"/oauth/token";

        //2.定义头信息 (有client id 和client secr)
        String authorization = "Basic "+new String(Base64.getEncoder().encode((clientId+":"+clientSecret).getBytes()),"UTF-8");
        MultiValueMap<String,String> headers =new LinkedMultiValueMap<String, String>();
        headers.add("Authorization",authorization);
        /*MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization","Basic "+new String(Base64.getEncoder().encode((clientId+":"+clientSecret).getBytes()),"UTF-8"));*/

        //3. 定义请求体  有授权模式 用户的名称 和密码
        MultiValueMap<String,String> parameterMap = new LinkedMultiValueMap<String, String>();
        parameterMap.add("grant_type",grandType);
        parameterMap.add("username",username);
        parameterMap.add("password",password);
        System.out.println("参数参数参数:"+parameterMap);
        //4.模拟浏览器 发送POST 请求 携带 头 和请求体 到认证服务器

        /**
         * 参数1  指定要发送的请求的url
         * 参数2  指定要发送的请求的方法 PSOT
         * 参数3 指定请求实体(包含头和请求体数据)
         */
        HttpEntity requestentity = new HttpEntity(parameterMap,headers);
        System.out.println("请求数据:"+requestentity);
        //5.接收到返回的响应(就是:令牌的信息)
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestentity, Map.class);

        //用户登录后的令牌信息
        Map<String,String> body = responseEntity.getBody();

        //将令牌信息转换成AuthToken对象
        AuthToken authToken = new AuthToken();
        //访问令牌(jwt)
        String accessToken = (String) body.get("access_token");
        //刷新令牌(jwt)
        String refreshToken = (String) body.get("refresh_token");
        //jti，作为用户的身份标识
        String jwtToken= (String) body.get("jti");


        authToken.setJti(jwtToken);
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);


        //6.返回
        return authToken;
    }


    public static void main(String[] args) {
        byte[] decode = Base64.getDecoder().decode(new String("Y2hhbmdnb3UxOmNoYW5nZ291Mg==").getBytes());
        System.out.println(new String(decode));
    }
}
