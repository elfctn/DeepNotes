// 🛡️ 8. Spring Security Notları
// Spring Security, Java uygulamalarında kimlik doğrulama (Authentication) ve yetkilendirme (Authorization)
// gibi güvenlik ihtiyaçlarını karşılayan güçlü ve özelleştirilebilir bir framework'tür.
// Özellikle web uygulamalarında kimin neye erişebileceğini yönetmek için vazgeçilmezdir.

// Kimlik Doğrulama (Authentication): Bir kullanıcının kimliğini doğrulama sürecidir (örneğin kullanıcı adı/şifre ile).
// Yetkilendirme (Authorization): Kimliği doğrulanmış bir kullanıcının belirli bir kaynağa veya işleme erişim izni olup olmadığını belirleme sürecidir.

// Temel Konfigürasyon (JWT ile):
// Modern Spring Boot uygulamalarında, özellikle REST API'lerde, oturum (session) tabanlı güvenlik yerine
// daha hafif ve ölçeklenebilir olan Token tabanlı güvenlik (JWT) tercih edilir.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Kullanıcı detay servis sağlayıcısı
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService; // Kullanıcı detaylarını yüklemek için
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Şifreleri güvenli bir şekilde saklamak için
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // JWT filtresi eklenirken kullanılabilir

// --- 1. Bağımlılıkları Ekle (pom.xml) ---
/*
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version> </dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version> <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version> <scope>runtime</scope>
</dependency>
*/


// --- 2. SecurityFilterChain Beani Oluştur ---
// Bu sınıf, Spring Security'nin nasıl davranacağını yapılandırır.
@Configuration // Bu sınıfın Spring tarafından bir konfigürasyon sınıfı olarak algılanmasını sağlar.
@EnableWebSecurity // Spring Security'nin web güvenliği desteğini etkinleştirir.
public class SecurityConfig {

    private final UserDetailsService userDetailsService; // Kullanıcı bilgilerini yüklemek için
    // Gerçek uygulamada bu, bir servis veya repository implementasyonu olacaktır.

    // Constructor Injection ile UserDetailsService'i enjekte ediyoruz.
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // SecurityFilterChain Beani: HTTP isteklerinin nasıl işleneceğini tanımlar.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF (Cross-Site Request Forgery) korumasını devre dışı bırakır.
                // REST API'lerde (özellikle JWT kullanıldığında) genellikle devre dışı bırakılır,
                // çünkü stateless yapıda CSRF token'larına ihtiyaç duyulmaz.
                .csrf(csrf -> csrf.disable())

                // Oturum (Session) yönetimi politikası.
                // STATELESS: Sunucuda hiçbir oturum durumu tutulmaz. Her istek, kimlik doğrulama bilgisini
                //            (örn. JWT) bağımsız olarak içermelidir. REST API'ler için idealdir.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // İstek yetkilendirme kuralları.
                .authorizeHttpRequests(auth -> auth
                        // Belirli yollara kimlik doğrulaması olmadan erişim izni verir.
                        // Genellikle kimlik doğrulama (login) ve kayıt (register) endpoint'leri için kullanılır.
                        .requestMatchers("/api/auth/**", "/public/**").permitAll() // "/public/**" gibi başka açık yollar da olabilir

                        // SWAGGER (API dökümantasyonu) yollarını herkese açmak için (genellikle dev ortamında yapılır):
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Diğer tüm isteklerin kimlik doğrulaması gerektirmesini sağlar.
                        .anyRequest().authenticated()
                );
        // .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class); // Kendi JWT filtreni ekle
        // JWT tabanlı kimlik doğrulama için özel bir JWT filtresini
        // UsernamePasswordAuthenticationFilter'dan önce zincire ekleriz.
        // Bu filtre, gelen isteklerde JWT'yi ayrıştırır ve kullanıcıyı doğrular.

        return http.build(); // Oluşturulan SecurityFilterChain'i döndürür.
    }

    // PasswordEncoder Beani: Şifreleri hash'lemek için kullanılır.
    // Şifreler asla düz metin olarak veritabanında saklanmamalıdır.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Güçlü bir hashing algoritmasıdır.
    }

    // AuthenticationManager Beani: Kimlik doğrulama işlemini yönetir.
    // Spring Security'nin kimlik doğrulama sağlayıcılarını (Authentication Providers) kullanır.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Spring'in varsayılan AuthenticationManager'ını döndürür.
    }

    // UserDetailsService ve DaoAuthenticationProvider Beani:
    // Bu metodlar, AuthenticationManager'a kullanıcı bilgilerini nereden ve nasıl alacağını söyler.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Kullanıcı detaylarını yükleyecek servis
        authProvider.setPasswordEncoder(passwordEncoder());     // Şifreleri karşılaştırmak için encoder
        return authProvider;
    }

// --- Örnek Bir UserDetailsService Implementasyonu (gerçek uygulamada ayrı bir sınıf olur) ---
    /*
    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        private final KullaniciRepository kullaniciRepository; // Kullanıcı veritabanından çekmek için

        public UserDetailsServiceImpl(KullaniciRepository kullaniciRepository) {
            this.kullaniciRepository = kullaniciRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Kullanici kullanici = kullaniciRepository.findByKullaniciAdi(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

            // Spring Security'nin UserDetails arayüzünü implemente eden bir User objesi döndürülür.
            // Bu kısım, kullanıcının rolleri/yetkileri ile birlikte yapılandırılır.
            return org.springframework.security.core.userdetails.User
                    .withUsername(kullanici.getKullaniciAdi())
                    .password(kullanici.getPasswordHash()) // Gerçek uygulamada şifre hash'i
                    .roles("USER") // Örnek rol
                    .build();
        }
    }
    */

// --- JWT Filtreleme Mantığı (örnek olarak, ayrı bir sınıf olarak tanımlanır) ---
    /*
    // @Component // Bu bir Spring bileşeni olacak
    public class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtService jwtService; // JWT token işlemleri için servis
        private final UserDetailsService userDetailsService;

        public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userName;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            userName = jwtService.extractUsername(jwt); // JWT'den kullanıcı adını çıkar

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Şifre burada null çünkü zaten token doğrulandı
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken); // Kullanıcıyı doğrulanmış olarak ayarla
                }
            }
            filterChain.doFilter