🔄 7. Spring Data JPA, Hibernate ORM ve Gelişmiş İlişkiler

JPA (Java Persistence API): Java nesnelerini veritabanı tablolarına eşlemek (map) için bir standarttır (spesifikasyon).
Hibernate: JPA'nın en popüler implementasyonudur (ORM aracı).
--------------------------------------------------------
Entity ve Repository:
@Entity // Bu sınıfın bir veritabanı tablosuna karşılık geldiğini belirtir.
@Table(name = "kullanicilar")
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kullanici_adi", unique = true, nullable = false)
    private String kullaniciAdi;

    // ... constructors, getters, setters //
}

@Repository // Spring'e bu interface'in bir veritabanı deposu olduğunu söyler.
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    // Spring Data JPA, metot isminden sorgu üretir.
    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);
}

----------------------------------------------------------------
İlişkisel Eşleme (Advanced Mapping):

@OneToMany (Bire Çok): Bir yazarın birden çok kitabı olabilir.
@ManyToOne (Çoka Bir): Birden çok kitap bir yazara ait olabilir.
@ManyToMany (Çoka Çok): Bir öğrenci birden çok derse, bir ders de birden çok öğrenciye sahip olabilir.

Kod Örneği (@OneToMany / @ManyToOne):
// Yazar Entity'si
@Entity
public class Yazar {
    @Id @GeneratedValue
    private Long id;
    private String ad;

    @OneToMany(mappedBy = "yazar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kitap> kitaplar = new ArrayList<>();
    // ...
}

// Kitap Entity'si
@Entity
public class Kitap {
    @Id @GeneratedValue
    private Long id;
    private String baslik;

    @ManyToOne(fetch = FetchType.LAZY) // Birçok kitap tek bir yazara ait
    @JoinColumn(name = "yazar_id") // İlişkiyi kuracak foreign key
    private Yazar yazar;
    // ...
}


