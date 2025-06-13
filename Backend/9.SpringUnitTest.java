✅ 9. Spring Unit Test
Kodun küçük parçalarını (metotlar, sınıflar) izole bir şekilde test etmektir. JUnit ve Mockito en popüler kütüphanelerdir.

@SpringBootTest: Tüm Spring context'ini yükler (integration test için daha uygun).
@WebMvcTest: Sadece web katmanını (Controller'lar) yükler. Servis katmanını mock'lamak gerekir. Daha hızlıdır.
@MockBean: Spring context'ine bir mock nesne ekler. Gerçek bean yerine bu mock kullanılır.
 Mockito: Nesneleri taklit etmek (mock) için kullanılır. when(...).thenReturn(...) en sık kullanılan yapısıdır.
-------------------------------------------
Controller Test Örneği:

@WebMvcTest(ProductController.class) // Sadece ProductController'ı test et
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // HTTP isteklerini simüle etmek için

    @MockBean // ProductService'in sahtesini (mock) oluştur ve context'e ekle
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper; // Java nesnelerini JSON'a çevirmek için

    @Test
    void getProductById_whenProductExists_shouldReturnProduct() throws Exception {
        // Hazırlık (Arrange)
        Product product = new Product(1L, "Test Product", 100.0);
        when(productService.findById(1L)).thenReturn(product); // Servis çağrıldığında bu ürünü dön

        // Eylem (Act) & Doğrulama (Assert)
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }
}

-------------------------------------------