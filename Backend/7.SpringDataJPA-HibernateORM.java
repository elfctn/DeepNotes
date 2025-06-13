🔄 7. Spring Data JPA, Hibernate ORM ve Gelişmiş İlişkiler Notları
// Java uygulamaları ile ilişkisel veritabanları arasında köprü kurmak,
// veri kalıcılığını sağlamak ve nesne-ilişkisel eşleme (ORM) yapmak için kullanılır.

// JPA (Java Persistence API): Java nesnelerini veritabanı tablolarına eşlemek için bir Jave EE standardıdır (spesifikasyon).
//                              Geliştiricilerin doğrudan SQL yazmadan veritabanı işlemleri yapmasını sağlar.
// Hibernate: JPA'nın en popüler ve yaygın olarak kullanılan implementasyonudur (ORM aracı).
//            JPA spesifikasyonunu uygulayarak Java objelerini veritabanı satırlarına dönüştürür ve tersi.
// Spring Data JPA: JPA'nın üzerine inşa edilmiş, veri erişim katmanını daha da basitleştiren bir Spring projesidir.
//                 Repository interface'leri aracılığıyla veri tabanı işlemlerini otomatik olarak sağlar.

import jakarta.persistence.*; // JPA (Jakarta Persistence API) annotation'ları
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA
import org.springframework.stereotype.Repository; // Spring Core annotation'ı
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Nullable dönüş değerleri için

// --- Entity ve Repository ---
// Entity sınıfları, veritabanı tablolarına karşılık gelen Java objeleridir.
// Repository interface'leri ise bu entity'ler üzerinde CRUD (Create, Read, Update, Delete)
// operasyonlarını ve daha karmaşık sorguları tanımlar.

@Entity // Bu sınıfın bir veritabanı tablosuna karşılık geldiğini belirtir.
@Table(name = "kullanicilar") // Veritabanındaki tablo adını belirtir (isteğe bağlı, varsayılan sınıf adıdır).
public class Kullanici {
    @Id // Bu alanın tablonun birincil anahtarı (Primary Key) olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin veritabanı tarafından otomatik artırılacağını belirtir.
    // IDENTITY: Veritabanının kendi otomatik artırma özelliğini kullanır (MySQL, PostgreSQL).
    // AUTO: Veritabanına göre otomatik seçer (varsayılan).
    // SEQUENCE: Veritabanı sekanslarını kullanır.
    // TABLE: Bir yardımcı tablo kullanır.
    private Long id;

    @Column(name = "kullanici_adi", unique = true, nullable = false, length = 50) // Sütun özelliklerini belirler.
    // unique: Bu sütundaki değerlerin benzersiz olmasını sağlar.
    // nullable: Bu sütunun boş olamayacağını (NOT NULL) belirtir.
    // length: VARCHAR tipi için maksimum uzunluğu belirler.
    private String kullaniciAdi;

    private String email; // Varsayılan olarak sütun adı 'email' olur

    // --- Constructor'lar ---
    public Kullanici() {
        // JPA/Hibernate, no-arg constructor'ı gerektirir.
    }

    public Kullanici(String kullaniciAdi, String email) {
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
    }

    // --- Getter ve Setter Metotları (Kapsülleme için) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Kullanici{" +
                "id=" + id +
                ", kullaniciAdi='" + kullaniciAdi + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

@Repository // Spring'e bu interface'in bir veritabanı deposu (Data Access Object - DAO) olduğunu söyler.
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    // Spring Data JPA'nın gücü burada devreye girer:
    // Metot isminden otomatik olarak SQL sorgusu üretir (Query Derivation).
    // findByKullaniciAdi metodu, "SELECT * FROM kullanicilar WHERE kullanici_adi = ?" sorgusuna dönüşür.
    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);

    // Diğer örnekler:
    List<Kullanici> findByEmailContaining(String keyword); // E-postası belirli bir kelime içeren kullanıcıları bul
    long countByKullaniciAdi(String kullaniciAdi); // Belirli bir kullanıcı adına sahip kaç kullanıcı olduğunu say
}


// --- İlişkisel Eşleme (Advanced Mapping) ---
// Veritabanındaki tablolar arasındaki ilişkileri (One-to-Many, Many-to-One, Many-to-Many)
// Java Entity'leri arasında modellemeyi sağlar.

// @OneToMany (Bire Çok): Bir Yazarın birden çok kitabı olabilir.
// @ManyToOne (Çoka Bir): Birden çok Kitap bir Yazara ait olabilir.
// @ManyToMany (Çoka Çok): Bir Öğrenci birden çok Derse katılabilir, bir Ders de birden çok öğrenciye sahip olabilir.

// --------------------------------------------------------------------------------------
// Kod Örneği (@OneToMany / @ManyToOne)
// Yazar ve Kitap arasında bire-çok ilişkiyi gösterir.
// --------------------------------------------------------------------------------------

// Yazar Entity'si: Bir Yazar'ın birden çok Kitabı olabilir.
@Entity
@Table(name = "yazarlar")
class Yazar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ad;

    // @OneToMany: Bir Yazar'ın birden çok Kitabı vardır.
    // mappedBy = "yazar": Kitap sınıfındaki "yazar" alanı, bu ilişkinin sahibidir.
    //                      Bu, Kitap tablosunda Yazar ID'sini tutan foreign key'in nerede olduğunu belirtir.
    // cascade = CascadeType.ALL: Yazar üzerinde yapılan tüm işlemlerin (oluşturma, güncelleme, silme)
    //                            ilişkili Kitaplar üzerinde de uygulanmasını sağlar.
    // orphanRemoval = true: Bir Yazar'dan bir Kitap kaldırıldığında, o Kitabın veritabanından da silinmesini sağlar.
    @OneToMany(mappedBy = "yazar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kitap> kitaplar = new ArrayList<>(); // Bir Yazar'ın Kitap listesi

    // --- Constructor'lar ---
    public Yazar() {}
    public Yazar(String ad) { this.ad = ad; }

    // --- Getter ve Setter Metotları ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public List<Kitap> getKitaplar() { return kitaplar; }
    // Setter yerine, kitap ekleme/çıkarma için yardımcı metotlar tercih edilir:
    public void addKitap(Kitap kitap) {
        kitaplar.add(kitap);
        kitap.setYazar(this); // İlişkinin iki tarafını da bağlar
    }
    public void removeKitap(Kitap kitap) {
        kitaplar.remove(kitap);
        kitap.setYazar(null); // İlişkinin bağını koparır
    }

    @Override
    public String toString() {
        return "Yazar{" + "id=" + id + ", ad='" + ad + '\'' + '}';
    }
}

// Kitap Entity'si: Bir Kitabın tek bir Yazarı olabilir.
@Entity
@Table(name = "kitaplar")
class Kitap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String baslik;

    // @ManyToOne: Birçok Kitap tek bir Yazara ait olabilir.
    // fetch = FetchType.LAZY: Yazar objesinin, Kitap objesi çekildiğinde hemen değil,
    //                         gerçekten ihtiyaç duyulduğunda (erişildiğinde) yüklenmesini sağlar.
    //                         (EAGER ise hemen yükler, performans düşünülerek Lazy tercih edilir).
    // @JoinColumn(name = "yazar_id"): Bu sütunun, Yazar tablosundaki primary key'i referans alan
    //                                  foreign key olduğunu belirtir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yazar_id")
    private Yazar yazar; // Kitabın ait olduğu Yazar objesi

    // --- Constructor'lar ---
    public Kitap() {}
    public Kitap(String baslik, Yazar yazar) {
        this.baslik = baslik;
        this.yazar = yazar;
    }
    public Kitap(String baslik) {
        this.baslik = baslik;
    }

    // --- Getter ve Setter Metotları ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public Yazar getYazar() { return yazar; }
    public void setYazar(Yazar yazar) { this.yazar = yazar; }

    @Override
    public String toString() {
        return "Kitap{" + "id=" + id + ", baslik='" + baslik + '\'' + '}';
    }
}


// --- Main Sınıfı (Örnek Kullanım Simülasyonu) ---
// Bu sınıf, Entity ve Repository'lerin nasıl kullanılacağına dair bir simülasyon sunar.
// Gerçek bir Spring Boot uygulamasında, bu işlemler Repository'ler aracılığıyla bir servis katmanında yapılır.
public class JpaAndRelationshipsExample {

    public static void main(String[] args) {
        System.out.println("--- Spring Data JPA ve İlişkiler Örnekleri ---");

        // Kullanici Entity ve Repository örneği (simülasyon)
        System.out.println("\n--- Kullanici Entity ve Repository ---");
        Kullanici yeniKullanici = new Kullanici("developer_ahmet", "ahmet@example.com");
        // Gerçekte: kullaniciRepository.save(yeniKullanici);
        yeniKullanici.setId(1L); // ID'nin DB tarafından atandığını simüle ediyoruz.
        System.out.println("Oluşturulan Kullanıcı: " + yeniKullanici);

        Optional<Kullanici> bulunanKullanici = Optional.of(yeniKullanici); // findByKullaniciAdi simülasyonu
        if (bulunanKullanici.isPresent()) {
            System.out.println("Bulunan Kullanıcı (developer_ahmet): " + bulunanKullanici.get());
        }

        // Yazar ve Kitap İlişkisi Örneği (simülasyon)
        System.out.println("\n--- Yazar ve Kitap İlişkisi (@OneToMany / @ManyToOne) ---");

        Yazar yazar1 = new Yazar("Stephen King");
        yazar1.setId(101L); // ID'nin DB tarafından atandığını simüle ediyoruz.

        Kitap kitap1 = new Kitap("It");
        Kitap kitap2 = new Kitap("The Shining");

        // İlişkileri iki taraflı kurma (bi-directional mapping)
        yazar1.addKitap(kitap1);
        yazar1.addKitap(kitap2);

        // Gerçekte: yazarRepository.save(yazar1);
        // Bu save işlemi cascade ayarı sayesinde kitapları da kaydederdi.

        System.out.println(yazar1.getAd() + " tarafından yazılan kitaplar:");
        for (Kitap kitap : yazar1.getKitaplar()) {
            System.out.println("- " + kitap.getBaslik() + " (Yazar: " + kitap.getYazar().getAd() + ")");
        }

        // Bir kitabın yazar bilgisine erişim
        System.out.println(kitap1.getBaslik() + " kitabının yazarı: " + kitap1.getYazar().getAd());

        // İlişkiden kitap kaldırma ve orphanRemoval etkisi (simülasyon)
        // yazar1.removeKitap(kitap1);
        // Bu durumda 'kitap1' veritabanından da silinirdi (orphanRemoval=true sayesinde).
    }
}