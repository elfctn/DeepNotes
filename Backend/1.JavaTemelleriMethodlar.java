// ☕ 1. Java Temelleri ve Metotlar Notları
// Java, platform bağımsızlığı, nesne yönelimli yapısı ve güçlü ekosistemiyle bilinen,
// geniş kullanım alanına sahip bir programlama dilidir.
// Bu notlar, Java'daki her şeyin başlangıcı olan temel söz dizimi, değişkenler,
// döngüler, koşul ifadeleri ve metotlar gibi konuları kapsar.

public class JavaBasicsAndMethods {

    // --- Temel Söz Dizimi (Syntax) ---

    public static void main(String[] args) {
        // Her Java uygulaması 'main' metodu ile başlar.
        // 'public static void' erişim belirleyicileri, 'main' metodunun dışarıdan
        // çağrılabileceğini ve herhangi bir obje oluşturmaya gerek kalmadan
        // çalıştırılabileceğini belirtir.

        // Değişkenler ve Veri Tipleri
        // Değişkenler, verileri depolamak için kullanılan bellek konumlarıdır.
        // Java, statik tiplidir, yani bir değişkenin tipi derleme zamanında belirlenir.
        String mesaj = "Merhaba Dünya!"; // Metin dizileri için String tipi
        int sayi = 10;                  // Tam sayılar için int tipi
        boolean aktifMi = true;         // Doğru/yanlış değerler için boolean tipi
        char ilkHarf = 'J';             // Tek karakterler için char tipi
        double PI = 3.14159;            // Ondalıklı sayılar için double tipi

        // 'final' anahtar kelimesi: Bir değişkeni sabit (değiştirilemez) yapar.
        final int MAX_HIZ = 200; // Artık MAX_HIZ değeri değiştirilemez.

        System.out.println("Mesaj: " + mesaj);
        System.out.println("Sayı: " + sayi);
        System.out.println("PI sabiti: " + PI);
        System.out.println("Maksimum Hız: " + MAX_HIZ);


        // Koşul İfadeleri (If-Else)
        // Belirli bir koşula göre farklı kod bloklarını çalıştırmak için kullanılır.
        System.out.println("\n--- Koşul İfadeleri ---");
        if (sayi > 5) {
            System.out.println("Sayı 5'ten büyüktür.");
        } else if (sayi == 5) { // Başka bir koşul
            System.out.println("Sayı 5'e eşittir.");
        } else {
            System.out.println("Sayı 5'ten küçük veya eşittir.");
        }


        // Switch Expressions (Java 14+)
        // Daha modern ve özlü bir 'switch' ifadesi biçimi. Değer döndürebilir.
        System.out.println("\n--- Switch Expressions ---");
        String gun = "Pazartesi"; // Denemek için bir gün değişkeni
        String gunTipi = switch (gun) {
            case "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma" -> "Hafta içi";
            case "Cumartesi", "Pazar" -> "Hafta sonu";
            default -> "Geçersiz gün"; // Varsayılan durum
        };
        System.out.println(gun + " günü: " + gunTipi);


        // Döngüler (For Loop)
        // Belirli bir kod bloğunu belirli sayıda veya bir koşul karşılanana kadar tekrarlamak için kullanılır.
        System.out.println("\n--- Döngüler (For Loop) ---");
        for (int i = 0; i < 5; i++) {
            System.out.println("Döngü adımı: " + i);
        }

        // Diğer döngü türleri: while, do-while, enhanced for-loop (for-each)
        int j = 0;
        while (j < 3) {
            System.out.println("While döngüsü: " + j);
            j++;
        }


        // Text Blocks (Java 15+)
        // Çok satırlı string'leri daha okunabilir bir şekilde tanımlamak için kullanılır.
        // Üç tırnak işareti (") ile başlar ve biter.
        System.out.println("\n--- Text Blocks ---");
        String json = """
{
    "name": "Ahmet Yılmaz",
    "age": 30,
    "city": "İstanbul"
}
        """; // Metin bloğu burada biter. Boşluklar korunur.
        System.out.println("JSON verisi:\n" + json);

        // Record (Java 16+) - Değişmez veri taşıyan sınıflar için kısa söz dizimi.
        // Detaylı örnek için OOP notlarına bakınız.
        record Point(int x, int y) {}
        Point p = new Point(10, 20);
        System.out.println("Record Point: " + p.x() + ", " + p.y());


        // --- Metotlar ---
        // 'main' metodundan diğer metotları çağırabiliriz.
        System.out.println("\n--- Metotlar ---");
        Hesaplayici hesaplayici = new Hesaplayici(); // Hesaplayici sınıfından bir nesne oluşturma

        int toplamInt = hesaplayici.topla(5, 7); // int versiyonunu çağırma
        System.out.println("5 + 7 = " + toplamInt);

        double toplamDouble = hesaplayici.topla(10.5, 20.3); // double versiyonunu çağırma
        System.out.println("10.5 + 20.3 = " + toplamDouble);

        // Bir metot, başka bir metodu da çağırabilir.
        hesaplayici.gosterMesaj("Metot çağrısı başarılı!");
    }
}

// --- Metotlar Sınıfı ---
// Belirli bir işi yapan kod bloklarıdır. Kodun tekrarını önler, okunabilirliği artırır.
// Metotlar, bir sınıf içinde tanımlanır.
class Hesaplayici {

    /**
     * İki tam sayıyı toplayan metot.
     * Metot imzası: erişim belirleyici (public) + geri dönüş tipi (int) + metot adı (topla) + parametreler (int a, int b)
     * @param a Toplanacak ilk sayı
     * @param b Toplanacak ikinci sayı
     * @return İki sayının toplamı (int)
     */
    public int topla(int a, int b) {
        return a + b; // Toplamı döndürür
    }

    /**
     * Overloading (Aşırı Yükleme): Aynı isme sahip, ancak farklı parametre listelerine (sayısı, tipi veya sırası)
     * sahip metotlar tanımlanabilir. Java, hangi metodu çağıracağını parametrelerine göre belirler.
     * İki ondalıklı sayıyı toplayan metot.
     * @param a Toplanacak ilk sayı (double)
     * @param b Toplanacak ikinci sayı (double)
     * @return İki sayının toplamı (double)
     */
    public double topla(double a, double b) {
        return a + b;
    }

    /**
     * Parametre alan ve bir şey döndürmeyen (void) metot örneği.
     * @param mesaj Ekrana yazdırılacak metin
     */
    public void gosterMesaj(String mesaj) {
        System.out.println("Hesaplayıcı Mesajı: " + mesaj);
    }
}