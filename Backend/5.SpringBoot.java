// 🌱 5. Spring Boot ve Temelleri Notları
// Spring Boot, minimum konfigürasyonla, üretime hazır Spring tabanlı uygulamalar oluşturmayı kolaylaştırır.
// Hız, verimlilik ve "konfigürasyondan çok uzlaşım" (convention over configuration) ilkeleriyle öne çıkar.

// Gerekli Spring Boot ve Lombok kütüphanelerini içe aktarıyoruz.
// Lombok annotation'ları için import'lar burada varsayılır.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

// Lombok kütüphanesi için import'lar (gerçek bir dosyada gereklidir)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


// --- Ana Dosya ve Annotationlar ---
// Uygulamanın başlangıç noktasıdır. main metodu, Spring uygulamasını bootstrap eder.
@SpringBootApplication // @Configuration, @EnableAutoConfiguration ve @ComponentScan'i birleştirir.
public class DemoApplication {
    public static void main(String[] args) {
        // Spring uygulamasını başlatır.
        SpringApplication.run(DemoApplication.class, args);
    }
}

/*
 * @SpringBootApplication: Bu meta-annotation, üç temel Spring Boot annotation'ını bir araya getirir:
 *
 * 1. @Configuration: Bu sınıfın Spring konteyneri tarafından yönetilen bean tanımlamaları için bir kaynak olduğunu belirtir.
 * 2. @EnableAutoConfiguration: Spring Boot'un classpath'indeki JAR bağımlılıklarına, diğer bean'lere ve uygulama ayarlarına
 * dayanarak otomatik olarak Spring konfigürasyonunu ayarlamasını sağlar. Örneğin, 'spring-webmvc' varsa DispatcherServlet'i
 * otomatik yapılandırır.
 * 3. @ComponentScan: Spring'e, bu paketten ve alt paketlerinden @Component, @Service, @Repository, @Controller gibi
 * Spring bileşenlerini taramasını ve bunları Spring konteynerine kaydetmesini söyler.
 */


// --- REST Controller ---
// Web isteklerini (HTTP) karşılayan ve yanıtlayan sınıflardır. Genellikle JSON/XML gibi veri formatlarını döndürürler.
@RestController
@RequestMapping("/api/v1/products") // Bu controller'daki tüm endpoint'ler bu yolla başlar.
public class ProductController {

    // ProductService bağımlılığı. Bu, Spring tarafından enjekte edilecektir.
    private final ProductService productService;

    // Constructor Injection: Bağımlılıkları enjekte etmenin en iyi yolu.
    // @Autowired annotation'ı, Spring'in bu constructor'ı kullanarak bağımlılığı sağlamasını söyler.
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // HTTP GET isteği -> /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id); // ProductService'den ürün bulma
        return ResponseEntity.ok(product); // 200 OK statüsü ile ürünü döndürür.
    }

    // HTTP POST isteği -> /api/v1/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product); // ProductService ile ürünü kaydetme
        // 201 CREATED statüsü ile kaydedilen ürünü döndürür.
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}

/*
 * @RestController: Bu, @Controller ve @ResponseBody annotation'larının birleşimidir. Bu sınıfın RESTful web
 * servisleri için bir controller olduğunu ve metodların doğrudan yanıt gövdesini (HTTP response body) döndüreceğini
 * belirtir (genellikle JSON veya XML formatında).
 * @RequestMapping: Sınıf veya metot seviyesinde bir veya daha fazla URL yolunu (URI) bir handler metoduna eşlemek için kullanılır.
 * @Autowired: Spring'in bağımlılık enjeksiyonu (Dependency Injection) mekanizmasını kullanarak bir bean'in
 * bağımlılıklarını otomatik olarak çözümlemesini ve sağlamasını sağlar. Constructor injection, enjeksiyon için en çok
 * tercih edilen yöntemdir, çünkü immutability'yi teşvik eder ve test edilebilirliği artırır.
 * @GetMapping, @PostMapping: @RequestMapping'in özel versiyonlarıdır ve sırasıyla HTTP GET ve POST isteklerini
 * belirli metotlara eşler. Benzer şekilde @PutMapping, @DeleteMapping vb. de mevcuttur.
 * @PathVariable: URL yolundaki bir değişkeni metot parametresine bağlamak için kullanılır (örneğin /products/{id}).
 * @RequestBody: Gelen HTTP isteğinin gövdesini (genellikle JSON veya XML) bir Java nesnesine dönüştürmek için kullanılır.
 * ResponseEntity: HTTP yanıtının tamamını (statü kodu, başlıklar ve gövde) kontrol etmeni sağlar.
 * HttpStatus: Standart HTTP statü kodlarını (örn. 200 OK, 201 CREATED, 404 NOT FOUND) temsil eden bir enum'dur.
 */


// --- Lombok ---
// Lombok, Java'da boilerplate (tekrar eden, kalıp) kodları azaltmak için kullanılan bir kütüphanedir.
// Derleme zamanında çalışır ve getter, setter, constructor, equals, hashCode, toString gibi metodları
// otomatik olarak oluşturur. Bu, kodunu daha kısa ve okunabilir hale getirir.

// Örnek Kullanım:
// Bir Product sınıfı için:

// Lombok annotation'ları ile Product sınıfı tanımı:
@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode ve @RequiredArgsConstructor'ı birleştirir.
@NoArgsConstructor // Parametresiz bir constructor oluşturur.
@AllArgsConstructor // Tüm alanları içeren bir constructor oluşturur.
class Product { // Bu sınıf, gerçek bir Spring Boot uygulamasında @Entity olarak da işaretlenebilir.
    private Long id;
    private String name;
    // Derleme sonrası, Lombok otomatik olarak tüm metodları ekleyecektir.
}

// ProductController içinde kullanılan Service arayüzü ve implementasyonu (örnek amaçlı)
interface ProductService {
    Product findById(Long id);
    Product save(Product product);
}

class ProductServiceImpl implements ProductService {
    // Burada gerçek bir Repository bağımlılığı olabilir.
    @Override
    public Product findById(Long id) {
        // Mock data or actual database call
        System.out.println("Finding product by ID: " + id);
        return new Product(id, "Sample Product " + id);
    }

    @Override
    public Product save(Product product) {
        // Mock data or actual database save operation
        System.out.println("Saving product: " + product.getName());
        if (product.getId() == null) {
            product.setId(System.currentTimeMillis()); // Basit bir ID atama
        }
        return product;
    }
}

/*
 * Lombok Maven Bağımlılığı (pom.xml'e eklenmeli):
 * <dependency>
 * <groupId>org.projectlombok</groupId>
 * <artifactId>lombok</artifactId>
 * <optional>true</optional> // Sadece derleme zamanı gerektiği için isteğe bağlı işaretlenir
 * </dependency>
 */


// --- Spring Boot Actuator ---
// Spring Boot uygulamalarının izlenmesi ve yönetilmesi için üretim ortamına hazır özellikler sunar.
// Uygulamanın çalışma zamanı durumunu (sağlık durumu, metrikler, ortam bilgisi, loglar vb.)
// kontrol etmeyi ve gözlemlemeyi kolaylaştırır.

/*
 * Sağladığı Endpoint'lerden Bazıları:
 * /actuator/health: Uygulamanın sağlık durumunu gösterir (DB bağlantısı, disk alanı vb.).
 * /actuator/info: Uygulama hakkında özel bilgiler (sürüm, oluşturma zamanı vb.).
 * /actuator/metrics: Uygulama metriklerini (CPU kullanımı, bellek, HTTP istekleri vb.) sağlar.
 * /actuator/env: Spring Environment özelliklerini gösterir.
 * /actuator/beans: Uygulamadaki tüm Spring bean'lerini listeler.
 */

/*
 * Actuator Maven Bağımlılığı (pom.xml'e eklenmeli):
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-actuator</artifactId>
 * </dependency>
 */

/*
 * Konfigürasyon (application.properties/yml'ye eklenmeli):
 * Varsayılan olarak sadece health ve info endpoint'leri açıktır. Diğerlerini etkinleştirmek için:
 * management.endpoints.web.exposure.include=*
 * # veya sadece belirli endpoint'leri açmak için:
 * # management.endpoints.web.exposure.include=health,info,metrics
 */


// --- Bağımlılık Yönetimi (Dependencies) ---
// Spring Boot, bağımlılık yönetimini büyük ölçüde basitleştirir.

/*
 * Starter Pom'lar: Spring Boot, belirli bir teknoloji yığını için gerekli tüm bağımlılıkları ve bunların
 * uyumlu versiyonlarını tek bir "starter" bağımlılığı altında toplar. Bu, pom.xml dosyanızı çok daha temiz tutar
 * ve versiyon çakışmalarını azaltır.
 *
 * Örneğin, bir web uygulaması için:
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * </dependency>
 * Bu starter, Tomcat, Spring MVC, Jackson (JSON işleme) gibi birçok bağımlılığı otomatik olarak getirir.
 *
 * Veri tabanı erişimi için:
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-data-jpa</artifactId>
 * </dependency>
 * Bu starter, Hibernate (ORM), Spring Data JPA ve bir veri tabanı driver'ı (örneğin H2) gibi bağımlılıkları içerir.
 */

/*
 * Otomatik Versiyon Yönetimi: 'spring-boot-starter-parent' veya 'spring-boot-dependencies' pom'unu kullandığınızda,
 * Spring Boot bu starter'ların ve içerdikleri bağımlılıkların uyumlu versiyonlarını sizin için otomatik olarak yönetir.
 * Bu, bağımlılık versiyonlarıyla uğraşma yükünü azaltır.
 */

/*
 * Örnek 'pom.xml' Yapısı (Bir Maven projesinin ana bağımlılık dosyası):
 * <?xml version="1.0" encoding="UTF-8"?>
 * <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
 * <modelVersion>4.0.0</modelVersion>
 * <parent>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-parent</artifactId>
 * <version>3.2.0</version> // Spring Boot versiyonu
 * <relativePath/> * </parent>
 * <groupId>com.example</groupId>
 * <artifactId>demo</artifactId>
 * <version>0.0.1-SNAPSHOT</version>
 * <name>demo</name>
 * <description>Demo project for Spring Boot</description>
 *
 * <properties>
 * <java.version>17</java.version>
 * </properties>
 *
 * <dependencies>
 * * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * </dependency>
 * * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-data-jpa</artifactId>
 * </dependency>
 * * <dependency>
 * <groupId>org.postgresql</groupId>
 * <artifactId>postgresql</artifactId>
 * <scope>runtime</scope>
 * </dependency>
 * * <dependency>
 * <groupId>org.projectlombok</groupId>
 * <artifactId>lombok</artifactId>
 * <optional>true</optional>
 * </dependency>
 * * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-actuator</artifactId>
 * </dependency>
 * * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-test</artifactId>
 * <scope>test</scope>
 * </dependency>
 * </dependencies>
 *
 * <build>
 * <plugins>
 * <plugin>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-maven-plugin</artifactId>
 * <configuration>
 * <excludes>
 * <exclude>
 * <groupId>org.projectlombok</groupId>
 * <artifactId>lombok</artifactId>
 * </exclude>
 * </excludes>
 * </configuration>
 * </plugin>
 * </plugins>
 * </build>
 * </project>
 */