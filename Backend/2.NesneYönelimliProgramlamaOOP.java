// 🏛️ 2. Nesne Yönelimli Programlama (OOP) Notları
// Nesne Yönelimli Programlama (Object-Oriented Programming - OOP), gerçek dünyadaki nesneleri
// yazılıma modellememizi sağlar. Bu yaklaşım, kodu daha modüler, anlaşılır, yeniden kullanılabilir ve
// sürdürülebilir hale getirir. Dört temel prensibi vardır: Kalıtım, Çok Biçimlilik, Kapsülleme ve Soyutlama.

// --------------------------------------------------------------------------------------
// 1. Inheritance (Kalıtım)
// Bir sınıfın (alt sınıf/child class), başka bir sınıfın (üst sınıf/parent class) özelliklerini ve
// metotlarını miras almasıdır. Bu, kod tekrarını azaltır ve hiyerarşik bir yapı oluşturur.
// Java'da 'extends' anahtar kelimesi ile yapılır.
// --------------------------------------------------------------------------------------

// 2. Polymorphism (Çok Biçimlilik)
// Bir nesnenin farklı durumlarda farklı davranabilmesidir. Temelde iki yolu vardır:
//    - Method Overriding (Metot Geçersiz Kılma): Alt sınıfın, üst sınıftan miras aldığı bir metodu kendi
//      ihtiyaçlarına göre yeniden tanımlamasıdır. @Override annotation'ı ile belirtilir.
//    - Method Overloading (Metot Aşırı Yükleme): Aynı sınıf içinde aynı isimde birden fazla metodun
//      olması, ancak bu metodların parametre sayılarının veya tiplerinin farklı olmasıdır.
// --------------------------------------------------------------------------------------

// 3. Encapsulation (Kapsülleme)
// Verileri (sınıfın alanları/özellikleri) gizleyip, bu verilere sadece metotlar (genellikle getter ve setter)
// aracılığıyla kontrollü bir şekilde erişilmesini sağlamaktır. Bu, veri bütünlüğünü korur ve sınıfın
// iç işleyişini dışarıdan gelecek yanlış kullanıma karşı korur. 'private' erişim belirleyicisi kullanılır.
// --------------------------------------------------------------------------------------

// 4. Abstraction (Soyutlama)
// Karmaşık implementasyon detaylarını gizleyip, kullanıcıya (veya diğer geliştiricilere) sadece gerekli
// işlevselliği ve arayüzü sunmaktır. "Ne yapıldığını" gösterir, "nasıl yapıldığını" gizler.
// 'abstract' sınıflar ve 'interface'ler ile sağlanır.
//    - Abstract Sınıf: Tam olarak uygulanamayan (soyut) metotlar içerebilir. 'abstract' anahtar kelimesiyle tanımlanır.
//      Doğrudan nesnesi oluşturulamaz, miras alınması gerekir.
//    - Interface (Arayüz): Tamamen soyut metotlardan oluşan bir sözleşmedir. Bir sınıf bir arayüzü
//      implemente ettiğinde, o arayüzdeki tüm metotları sağlamak zorundadır.
// --------------------------------------------------------------------------------------


// --- Kod Örnekleri (Kalıtım, Çok Biçimlilik, Kapsülleme ve Soyutlama) ---

// Soyutlama ve Kalıtım için temel sınıf: Hayvan
abstract class Hayvan {
    private String ad; // Kapsülleme: 'ad' alanı dışarıdan doğrudan erişilemez

    public Hayvan(String ad) {
        this.ad = ad;
    }

    // Soyut Metot: Her hayvan farklı ses çıkaracağı için implementasyonu alt sınıflara bırakılır.
    public abstract void sesCikar();

    // Kapsülleme: 'ad' alanına kontrollü erişim sağlayan getter metodu
    public String getAd() {
        return ad;
    }

    // İsteğe bağlı olarak setter da eklenebilir, ama burada gösterim için sadece getter var.
    // public void setAd(String ad) { this.ad = ad; }
}

// Kedi sınıfı: Hayvan sınıfından kalıtım alır ve soyut metodu kendi için tanımlar.
class Kedi extends Hayvan { // Kalıtım: Kedi, Hayvan'ın özelliklerini ve davranışlarını miras alır.
    public Kedi(String ad) {
        super(ad); // Üst sınıfın constructor'ını çağırır.
    }

    @Override // Çok Biçimlilik (Overriding): 'sesCikar' metodu Kedi için özelleştirildi.
    public void sesCikar() {
        System.out.println(getAd() + " miyavlıyor... (Miyav!)");
    }

    // Çok Biçimlilik (Overloading): Aynı isme sahip ama farklı parametreli metot.
    public void sesCikar(int tekrarSayisi) {
        for (int i = 0; i < tekrarSayisi; i++) {
            System.out.print("Miyav ");
        }
        System.out.println();
    }
}

// Kopek sınıfı: Hayvan sınıfından kalıtım alır ve soyut metodu kendi için tanımlar.
class Kopek extends Hayvan { // Kalıtım
    public Kopek(String ad) {
        super(ad);
    }

    @Override // Çok Biçimlilik (Overriding): 'sesCikar' metodu Kopek için özelleştirildi.
    public void sesCikar() {
        System.out.println(getAd() + " havlıyor... (Hav hav!)");
    }
}

// --------------------------------------------------------------------------------------
// Record (Java 16+)
// Java 16 ve sonrası için gelen, değişmez (immutable) veri taşıyan sınıflar (data classes)
// oluşturmak için kısa bir sözdizimsel yapıdır. Kapsüllemeyi otomatik olarak sağlar
// (tüm alanlar 'private final' olur ve otomatik 'getter'lar, 'equals()', 'hashCode()', 'toString()' üretilir).
// --------------------------------------------------------------------------------------

public record Kullanici(Long id, String kullaniciAdi) {
    // Record'lar otomatik olarak constructor, getter'lar (id(), kullaniciAdi()),
    // equals(), hashCode() ve toString() metodlarını oluşturur.
    // Bu, veri taşıyan sınıflar için boilerplate kodu ortadan kaldırır.
}

// --------------------------------------------------------------------------------------
// Main Sınıfı (Örnekleri Çalıştırmak İçin)
// --------------------------------------------------------------------------------------
public class OOPPrinciplesExamples {
    public static void main(String[] args) {
        System.out.println("--- OOP Prensipleri Örnekleri ---");

        // Kalıtım ve Çok Biçimlilik Örnekleri
        System.out.println("\n--- Kalıtım ve Çok Biçimlilik ---");
        Hayvan kedi = new Kedi("Pamuk"); // Bir Kedi nesnesini Hayvan referansı olarak tutma (Polymorphism)
        Hayvan kopek = new Kopek("Karabaş");

        kedi.sesCikar(); // Kedi'nin kendi sesCikar metodu çağrılır (Overriding)
        kopek.sesCikar(); // Köpek'in kendi sesCikar metodu çağrılır (Overriding)

        // Kedi sınıfının özel Overloaded metodunu çağırma
        if (kedi instanceof Kedi) {
            ((Kedi) kedi).sesCikar(3); // Type casting gerekli
        }

        // Kapsülleme Örneği
        System.out.println("\n--- Kapsülleme ---");
        // Hayvan sınıfındaki 'ad' alanı 'private' olduğu için doğrudan erişilemez.
        // System.out.println(kedi.ad); // Hata verir!
        System.out.println("Kedi'nin adı: " + kedi.getAd()); // Getter metodu ile güvenli erişim.

        // Record (Kullanici) Örneği
        System.out.println("\n--- Record (Kullanici) Örneği ---");
        Kullanici user1 = new Kullanici(1L, "ahmet.y");
        System.out.println("Kullanıcı ID: " + user1.id() + ", Kullanıcı Adı: " + user1.kullaniciAdi());
        System.out.println("Kullanıcı Objesi: " + user1); // Otomatik toString() metodu
    }
}