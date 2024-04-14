package mx.edu.utez.server.modules.security;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.modules.security.jwt.JwtAuthenticationFilter;
import mx.edu.utez.server.modules.security.jwt.JwtEntryPoint;
import mx.edu.utez.server.modules.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class MainSecurity {
    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/api/hashing/**",
            "/api/open/**",
            "/api/captcha/**",
    };

    private final UserDetailsServiceImpl service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtEntryPoint entryPoint() {
        return new JwtEntryPoint();
    }

    @Bean
    public JwtAuthenticationFilter filter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/management/area/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/management/area/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/area/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/management/area/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/management/area/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/speciality/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/management/speciality/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/speciality/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/management/speciality/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/management/speciality/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/management/user/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/doctor/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/management/doctor/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/doctor/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/management/doctor/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/doctor/available/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/doctor/aux/available/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/management/doctor/available-hours/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/patient/profile/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.GET, "/api/doctor/profile/").hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(HttpMethod.GET, "/api/person/{id}").hasAnyAuthority(Roles.DOCTOR.name(), Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.PUT, "/api/person/").hasAnyAuthority(Roles.DOCTOR.name(), Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/management/pending/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/management/active/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/management/history/paged/").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/management/assign-doctor/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/management/reassign-doctor/{id}").hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/patient/pending/paged/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/patient/active/paged/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/patient/history/paged/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/patient/to-review/paged/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/check-availability/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/doctor/assigned/paged/").hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/doctor/history/paged/").hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(HttpMethod.GET, "/api/appointment/doctor/non-availability/{id}").hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(HttpMethod.POST, "/api/appointment/cancel/{id}").hasAnyAuthority(Roles.DOCTOR.name(), Roles.PATIENT.name(), Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/appointment/{id}").hasAnyAuthority(Roles.DOCTOR.name(), Roles.PATIENT.name(), Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/review/").hasAuthority(Roles.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/review/paged/").hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(HttpMethod.POST, "/api/record/").hasAuthority(Roles.DOCTOR.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.clearAuthentication(true).logoutUrl("/api/auth/sign-out"));
        return http.build();
    }
}
