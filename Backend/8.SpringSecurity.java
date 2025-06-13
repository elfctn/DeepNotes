🛡️ 8. Spring Security
Uygulama güvenliğini (kimlik doğrulama ve yetkilendirme) sağlar.

Temel Konfigürasyon (JWT ile):

1-Bağımlılıkları ekle (pom.xml): spring-boot-starter-security ve JWT için jjwt-api, jjwt-impl, jjwt-jackson.
2-SecurityFilterChain Beani oluştur:

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API'ler için genellikle devre dışı bırakılır.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // STATELESS session yönetimi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Bu yola kimlik doğrulaması olmadan erişilebilir.
                        .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulaması gerektirir.
                );
        // .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class); // Kendi JWT filtreni ekle

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
