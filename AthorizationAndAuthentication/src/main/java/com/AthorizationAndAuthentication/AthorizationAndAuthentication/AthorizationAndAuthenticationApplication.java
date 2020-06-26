
package com.AthorizationAndAuthentication.AthorizationAndAuthentication;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.KeyPairClassService;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class AthorizationAndAuthenticationApplication {

   

	public static void main(String[] args) {
		SpringApplication.run(AthorizationAndAuthenticationApplication.class, args);


     

}


@Bean
    @LoadBalanced
    public RestTemplate restTemplate() { return new RestTemplate(); }

}