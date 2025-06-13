🏛️ 2. 🏛️Nesne Yönelimli Programlama (OOP)
OOP, gerçek dünyadaki nesneleri yazılıma modellememizi sağlar. Dört temel prensibi vardır:

Inheritance (Kalıtım): Bir sınıfın özelliklerini ve metotlarını başka bir sınıfa miras bırakmasıdır. extends anahtar kelimesi ile yapılır.
Polymorphism (Çok Biçimlilik): Bir nesnenin farklı durumlarda farklı davranabilmesidir. (Method Overriding ve Overloading)
Encapsulation (Kapsülleme): Verileri (alanları) gizleyip sadece metotlar aracılığıyla erişilmesini sağlamaktır. (private ve getter/setter metotları)
Abstraction (Soyutlama): Karmaşık implementasyon detaylarını gizleyip kullanıcıya sadece gerekli işlevselliği sunmaktır. (abstract sınıflar ve interface'ler ile sağlanır.)

// Soyutlama (Abstraction) ve Kalıtım (Inheritance)
abstract class Hayvan {
    private String ad;

    public Hayvan(String ad) { this.ad = ad; }

    public abstract void sesCikar(); // Soyut metot

    public String getAd() { return ad; } // Kapsülleme (Encapsulation)
}

class Kedi extends Hayvan {
    public Kedi(String ad) { super(ad); }

    @Override // Çok Biçimlilik (Polymorphism - Overriding)
    public void sesCikar() {
        System.out.println(getAd() + " miyavlıyor...");
    }
}

class Kopek extends Hayvan {
    public Kopek(String ad) { super(ad); }

    @Override
    public void sesCikar() {
        System.out.println(getAd() + " havlıyor...");
    }
}

// Record (Java 16+): Değişmez (immutable) veri taşıyan sınıflar için kısa yol.
public record Kullanici(Long id, String kullaniciAdi) {}