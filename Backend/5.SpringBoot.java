🌱 5. Spring Boot ve Temelleri
 Spring Boot, minimum konfigürasyonla, üretime hazır Spring tabanlı uygulamalar oluşturmayı kolaylaştırır.

Ana Dosya ve Annotationlar:

@SpringBootApplication // @Configuration, @EnableAutoConfiguration ve @ComponentScan'i birleştirir.
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
------------------------------------
REST Controller:

Web isteklerini (HTTP) karşılayan ve yanıtlayan sınıflardır.
@RestController
@RequestMapping("/api/v1/products") // Bu controller'daki tüm endpoint'ler bu yolla başlar.
public class ProductController {

    private final ProductService productService;

    // Constructor Injection: Bağımlılıkları enjekte etmenin en iyi yolu.
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}") // HTTP GET -> /api/v1/products/1
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping // HTTP POST -> /api/v1/products
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}