🏗️ 3. Veri Yapıları ve Collection Framework
Verileri verimli bir şekilde saklamak ve işlemek için kullanılır.


 List: Sıralı bir koleksiyondur, tekrar eden elemanlara izin verir. (ArrayList, LinkedList)
Set: Sırasız bir koleksiyondur, tekrar eden elemanlara izin vermez. (HashSet, TreeSet)
Map: Anahtar-değer (key-value) çiftlerini tutar. Anahtarlar benzersiz olmalıdır. (HashMap, TreeMap)


        Kod Örneği:

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsOrnek {
    public static void main(String[] args) {
        // List: Sıralı, tekrar eden eleman olabilir
        List<String> sehirler = new ArrayList<>();
        sehirler.add("İstanbul");
        sehirler.add("Ankara");
        sehirler.add("İstanbul"); // Tekrar eklenebilir
        System.out.println("Şehirler Listesi: " + sehirler);
        System.out.println("İlk şehir: " + sehirler.get(0));

        // Set: Sırasız, elemanlar benzersiz
        Set<Integer> sayilar = new HashSet<>();
        sayilar.add(10);
        sayilar.add(20);
        sayilar.add(10); // Tekrar eklenmez
        System.out.println("Sayılar Kümesi: " + sayilar);

        // Map: Anahtar-Değer
        Map<String, String> telefonKodlari = new HashMap<>();
        telefonKodlari.put("TR", "+90");
        telefonKodlari.put("DE", "+49");
        System.out.println("Türkiye'nin kodu: " + telefonKodlari.get("TR"));

        // Java 9+ kolay oluşturma
        List<String> immutableList = List.of("Elma", "Armut");
    }
}