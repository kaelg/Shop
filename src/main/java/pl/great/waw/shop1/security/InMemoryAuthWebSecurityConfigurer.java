package pl.great.waw.shop1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class InMemoryAuthWebSecurityConfigurer {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("USER")
                .build();
        UserDetails user1 = User.withUsername("Pawel")
                .password(encoder.encode("Pawel"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("Maciek")
                .password(encoder.encode("Maciek"))
                .roles("USER")
                .build();

        UserDetails user4 = User.withUsername("Bartek")
                .password(encoder.encode("Bartek"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, user1, user2, user4);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/create")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,"/cart").permitAll()
                .antMatchers("/product/byCategory/**")
                .permitAll()
                .antMatchers("/product/allProducts")
                .permitAll()
                .antMatchers("/product/**")
                .authenticated()
                .antMatchers("/order")
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}