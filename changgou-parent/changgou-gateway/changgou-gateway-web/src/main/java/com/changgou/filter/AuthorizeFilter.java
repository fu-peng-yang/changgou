package com.changgou.filter;

import com.changgou.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
*
*@ClassName: AuthorizeFilter
*@Description
*@Author yang
*@Date 2020/10/5
*@Time 10:43
 * 全局过滤器
 * 实现用户权限鉴别(校验)
*/
@Component
public class AuthorizeFilter implements GlobalFilter,Ordered {
    //令牌的名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    private static final String USER_LOGIN_URL="http://localhost:9001/oauth/login";
    /**
     * 全局拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //用户如果登录或者不需要做权限认证的请求,直接放行
        String uri =request.getURI().toString();
        if (!URLFilter.hasAuthorizer(uri)){
            return chain.filter(exchange);
        }
        //获取用户令牌信息
        //1)头文件中
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        //boolean true: 令牌在头文件中  false:令牌不在头文件中->将令牌封装到头文件中,再传递给其他微服务
        boolean hasToken = true;
        //2)参数获取令牌
        if (StringUtils.isEmpty(token)){
            //从所有请求参数中获取第一个参数
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken = false;
        }

        //3)令牌为空,则允许访问, 直接拦截
        if (StringUtils.isEmpty(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (httpCookie!=null){
                token = httpCookie.getValue();
            }
        }
        //如果没有令牌,则拦截
        if (StringUtils.isEmpty(token)){
            //设置没有权限的状态码  401
            //response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            //return  response.setComplete();
            return needAuthorization(USER_LOGIN_URL+"?FROM"+request.getURI(),exchange);


        }
        //如果有令牌,则校验令牌是否有效
        /*try {
            JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //无效则拦截
            //设置没有权限的状态码  401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return  response.setComplete();
        }*/

        //令牌为空,则允许访问,直接拦截  bearer
        if (StringUtils.isEmpty(token)){
            //设置没有权限的状态码  401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return  response.setComplete();
        }else {
            if (!hasToken){
                //判断当前令牌是否有bearer前缀,如果没有,则添加前缀 bearer
                if (!token.startsWith("bearer ") && !token.startsWith("Bearer ")){
                    token = "bearer "+token;
                }
                //将令牌封装到头文件中
                request.mutate().header(AUTHORIZE_TOKEN,token);
            }
        }


        //有效放行
        return chain.filter(exchange);
    }

    /**
     * 排序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 响应设置
     * @param url
     * @param exchange
     * @return
     */
    public Mono<Void> needAuthorization(String url,ServerWebExchange exchange){

        ServerHttpResponse response =exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set("Location",url);
        return exchange.getResponse().setComplete();
    }
}
