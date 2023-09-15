package com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.config;


import com.emperor.Emperor.Fleet.Vehicle.Management.System.Mono.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity

public class SecurityConfig {

    private  final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private static final String[] UNSECURED_ORGANIZER_END_POINTS ={
//            "/api/image/**",
//            "/api/identity/organizer/confirmtoken/**",
//            "/api/identity/organizer/signin",
//            "/api/identity/organizer/signup",
//            "/api/identity/organizer/password/reset"
//    };
//    private static final String[] UNSECURED_ADMIN_END_POINTS ={
//            "/api/identity/admin/signin",
//            "/api/identity/admin/signup"
//    };
//    private static final String[] UNSECURED_ATTENDEE_END_POINTS ={
//            "/api/identity/attendee/signin",
//            "/api/identity/attendee/signup",
//            "/api/identity/attendee/password/reset",
//            "/api/identity/attendee/single/attendee/**"
//    };
//
//    private static final String[] SECURED_ADMIN_END_POINTS ={
//            "/api/identity/admin/createProfile",
//    };
//
//    private static final String[] SECURED_ORGANIZER_END_POINTS ={
//            "/api/identity/organizer/update/**",
//            "api/identity/role/**",
//            "api/identity/role/create",
//            "api/identity/role/role/{roleId}",
//            "api/identity/role/get/**",
//            "api/identity/organizer/getAll/**",
//            "api/identity/organizer/getAll/organizer",
//            "api/identity/organizer/getAll/getSingleOrganizer/**",
//            "api/identity/organizer/getAll/organizer",
//            "api/identity/organizer/getAll/getSingleOrganizer/{organizerId}",
//            "api/identity/attendee/single/attendee/{userId}",
//            "api/identity/attendee/all/attendee",
//            "/**"
//    };
//    private static final String[] SECURED_ATTENDEE_END_POINTS ={
//            "/api/identity/attendee/update/**",
//            "/api/identity/attendee/single/attendee"
////            "/api/identity/attendee/single/**"
//    };

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                //http://localhost:8082/api/v1/auth/signup
                                .requestMatchers("api/**").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/admin/login").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/drivers/**").hasAnyRole("DRIVER", "ADMIN")
                                .requestMatchers("/api/admin").hasRole("ADMIN").anyRequest().authenticated()


                )
                .httpBasic(Customizer.withDefaults());
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
