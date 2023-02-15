package com.example.spring03.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.spring03.auth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable();

        //form, oauth2
          http.authorizeRequests()
                  .antMatchers("/user/**").authenticated()    
                  .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")  // /manager요청에 대해서는 역할을 가지고 있어야함
                  .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                  .anyRequest().permitAll()                       
              .and()
                  .formLogin()
                  .loginPage("/view/login")
                  .loginProcessingUrl("/view/login")   
                  .defaultSuccessUrl("/")
              .and()
              	.logout()
              	.logoutSuccessUrl("/")
              	.invalidateHttpSession(true)
              .and()
              	.oauth2Login()
                .loginPage("/view/login")
                .defaultSuccessUrl("/view/main")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);   // 카카오 로그인 완료 후 후처리 필요. 액세스토큰과 사용자 프로필 정보를 받아올 수 있음.
                
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
    
    // auth2 코드
//	private final CustomOAuth2UserService customOAuth2UserService;
//	
//	 public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
//	        this.customOAuth2UserService = customOAuth2UserService;
//	    }
//
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.formLogin().disable()
//			.httpBasic().disable()
//			.authorizeRequests()
//			.antMatchers("/api/user").permitAll()
//			.and()
//			.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
//		
//		return http.build();
//			
//	}
		
}
	
