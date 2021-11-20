package usyd.elec5619.group42.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import usyd.elec5619.group42.backend.filters.MyAuthenticationFilter;
import usyd.elec5619.group42.backend.filters.MyAuthorisationFilter;
import usyd.elec5619.group42.backend.utils.Constants;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        myAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        http.csrf().disable();
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/auth/register", "/auth/login", "/favicon.ico", "/websocket/**", "/petPost/index").permitAll()
                    .antMatchers("/websocket/**", "/petPost/index", "/weather/Sydney", "/weather/Melbourne", "/weather/Canberra").anonymous()
                    .and()
                .authorizeRequests()
                    .antMatchers("/v3/**", "/swagger-ui/**", "/swagger-resources/**", "/v2/**", "/webjars/**").permitAll()
                    .and()
                .authorizeRequests()
                    .anyRequest().hasAuthority(Constants.AUTHENTICATED)
                    .and()
                .addFilter(myAuthenticationFilter)
                .addFilterBefore(new MyAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin(Constants.Configuration.ORIGIN);
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
