☕ 1. Java Temelleri ve Metotlar
Java'daki her şeyin başlangıcı. Değişkenler, döngüler, koşul ifadeleri ve tabii ki metotlar.

--------------------------------------
Temel Söz Dizimi (Syntax):

// Java 17 ile gelen bazı yenilikler: Record, Text Blocks, Switch Expressions

// Değişkenler ve Veri Tipleri
String mesaj = "Merhaba Dünya!";
int sayi = 10;
final double PI = 3.14; // Sabit

// Koşul İfadeleri
if (sayi > 5) {
        System.out.println("Sayı 5'ten büyüktür.");
} else {
        System.out.println("Sayı 5'ten küçük veya eşittir.");
}

// Switch Expressions (Java 14+)
String gunTipi = switch (gun) {
    case "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma" -> "Hafta içi";
    case "Cumartesi", "Pazar" -> "Hafta sonu";
    default -> "Geçersiz gün";
};

// Döngüler
for (int i = 0; i < 5; i++) {
        System.out.println("Döngü: " + i);
}

// Text Blocks (Java 15+)
String json = """
{
    "name": "Ahmet",
    "age": 30
}
""";
--------------------------------------
Metotlar:
Belirli bir işi yapan kod bloklarıdır.
public class Hesaplayici {

    /**
     * İki sayıyı toplayan metot.
     * @param a Toplanacak ilk sayı
     * @param b Toplanacak ikinci sayı
     * @return İki sayının toplamı
     */
    public int topla(int a, int b) {
        return a + b;
    }

    // Overloading (Aşırı Yükleme): Aynı isim, farklı parametreler
    public double topla(double a, double b) {
        return a + b;
    }
}