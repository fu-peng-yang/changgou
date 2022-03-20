package com.changgou.test;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@DefaultProperties(defaultFallback = "rollbacks")
public class Demo {
    //@HystrixCommand(fallbackMethod = "rollbacks")
    //@HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")})
    @HystrixCommand
    //@HystrixCommand(commandProperties = {@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2000")})
    public String query(String id) {

        String user = new User().toString();
        return user;
    }

    public String rollbacks(){
        return "熔断信息";
    }
}
