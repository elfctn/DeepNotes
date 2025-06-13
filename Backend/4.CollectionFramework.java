// 🏗️ 3. Veri Yapıları ve Collection Framework Notları
// Java'da verileri verimli bir şekilde saklamak, yönetmek ve işlemek için kullanılan güçlü araçlardır.
// Farklı senaryolara göre en uygun veri yapısını seçmek, uygulamanın performansını ve okunabilirliğini artırır.

import java.util.ArrayList; // List interface'inin yaygın implementasyonu
import java.util.HashMap;   // Map interface'inin yaygın implementasyonu
import java.util.HashSet;   // Set interface'inin yaygın implementasyonu
import java.util.List;      // Sıralı koleksiyonlar için arayüz
import java.util.Map;       // Anahtar-değer çiftleri için arayüz
import java.util.Set;       // Benzersiz elemanlar için arayüz
import java.util.LinkedList; // List interface'inin başka bir implementasyonu
import java.util.TreeSet;    // Sıralı Set implementasyonu
import java.util.TreeMap;    // Sıralı Map implementasyonu

public class CollectionsAndDataStructures {

    public static void main(String[] args) {

        // --------------------------------------------------------------------------------------
        // 1. List (Liste)
        // Sıralı bir koleksiyondur (elemanların eklenme sırası korunur).
        // Tekrar eden elemanlara (duplicates) izin verir.
        // Elemanlara indeks (konum) üzerinden erişilebilir.
        // Yaygın implementasyonları: ArrayList (dinamik dizi tabanlı, hızlı erişim),
        //                          LinkedList (bağlantılı liste tabanlı, hızlı ekleme/silme).
        // --------------------------------------------------------------------------------------
        System.out.println("--- List Örneği (ArrayList) ---");
        List<String> sehirler = new ArrayList<>(); // String tipli bir ArrayList oluşturma
        sehirler.add("İstanbul"); // Eleman ekleme
        sehirler.add("Ankara");
        sehirler.add("İzmir");
        sehirler.add("İstanbul"); // Tekrar eden eleman eklenebilir
        System.out.println("Şehirler Listesi: " + sehirler); // Listeyi yazdırma
        System.out.println("İlk şehir: " + sehirler.get(0)); // İndeks ile elemana erişim
        System.out.println("Liste boyutu: " + sehirler.size()); // Liste boyutu
        sehirler.remove("Ankara"); // Eleman silme
        System.out.println("Ankara silindikten sonra: " + sehirler);


        // --------------------------------------------------------------------------------------
        // 2. Set (Küme)
        // Sırasız bir koleksiyondur (elemanların sırası garanti edilmez).
        // Tekrar eden elemanlara **izin vermez**; aynı elemanı birden fazla eklerseniz, yalnızca bir tanesi tutulur.
        // Elemanların benzersiz olması gereken durumlarda kullanılır.
        // Yaygın implementasyonları: HashSet (hızlı ekleme/arama, sırasız),
        //                          TreeSet (elemanları doğal sıralamasına göre veya belirtilen bir Comparator'a göre sıralar).
        // --------------------------------------------------------------------------------------
        System.out.println("\n--- Set Örneği (HashSet) ---");
        Set<Integer> sayilar = new HashSet<>(); // Integer tipli bir HashSet oluşturma
        sayilar.add(10); // Eleman ekleme
        sayilar.add(20);
        sayilar.add(30);
        sayilar.add(10); // Tekrar eklenmez, küme boyutu değişmez
        System.out.println("Sayılar Kümesi: " + sayilar); // Kümeyi yazdırma (sıra garanti değil)
        System.out.println("Kümede 20 var mı? " + sayilar.contains(20)); // Eleman kontrolü
        System.out.println("Küme boyutu: " + sayilar.size()); // Küme boyutu


        // --------------------------------------------------------------------------------------
        // 3. Map (Harita)
        // Anahtar-değer (key-value) çiftlerini tutar.
        // Her anahtar benzersiz olmalıdır; aynı anahtarla yeni bir değer eklerseniz, eski değerin üzerine yazılır.
        // Değerler tekrar edebilir.
        // Yaygın implementasyonları: HashMap (hızlı erişim, sırasız),
        //                          TreeMap (anahtarları sıralı tutar).
        // --------------------------------------------------------------------------------------
        System.out.println("\n--- Map Örneği (HashMap) ---");
        Map<String, String> telefonKodlari = new HashMap<>(); // String anahtar, String değer tipli HashMap
        telefonKodlari.put("TR", "+90"); // Anahtar-değer çifti ekleme
        telefonKodlari.put("DE", "+49");
        telefonKodlari.put("US", "+1");
        telefonKodlari.put("TR", "0090"); // "TR" anahtarı zaten olduğu için eski değeri günceller
        System.out.println("Türkiye'nin kodu: " + telefonKodlari.get("TR")); // Anahtar ile değere erişim
        System.out.println("Almanya'nın kodu var mı? " + telefonKodlari.containsKey("DE")); // Anahtar kontrolü
        System.out.println("Tüm Map: " + telefonKodlari); // Tüm Map'i yazdırma
        telefonKodlari.remove("US"); // Anahtar ile silme
        System.out.println("US silindikten sonra: " + telefonKodlari);


        // --------------------------------------------------------------------------------------
        // Java 9+ Kolay Oluşturma Metotları (Immutable Collections)
        // Java 9 ile gelen 'of()' metotları, daha kısa ve daha güvenli (değiştirilemez) koleksiyonlar oluşturmayı sağlar.
        // Oluşturulduktan sonra eleman eklenemez, silinemez veya değiştirilemezler.
        // --------------------------------------------------------------------------------------
        System.out.println("\n--- Java 9+ Immutable Koleksiyonlar ---");
        List<String> immutableList = List.of("Elma", "Armut", "Kiraz");
        System.out.println("Değiştirilemez Liste: " + immutableList);
        // immutableList.add("Muz"); // Bu satır UnsupportedOperationException hatası verir!

        Set<Double> immutableSet = Set.of(1.1, 2.2, 3.3);
        System.out.println("Değiştirilemez Küme: " + immutableSet);

        Map<Integer, String> immutableMap = Map.of(1, "Bir", 2, "İki");
        System.out.println("Değiştirilemez Map: " + immutableMap);
    }
}