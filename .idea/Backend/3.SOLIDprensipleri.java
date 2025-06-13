🧱 3. SOLID Prensipleri
Yazılım tasarımını daha anlaşılır, esnek ve sürdürülebilir kılan beş temel prensiptir.

        S - Single Responsibility Principle (Tek Sorumluluk Prensibi): Bir sınıfın değişmek için tek bir nedeni olmalı.
        O - Open/Closed Principle (Açık/Kapalı Prensibi): Yazılım varlıkları (sınıflar, modüller) genişlemeye açık, değişime kapalı olmalı.
        L - Liskov Substitution Principle (Liskovun Yerine Geçme Prensibi): Alt sınıflar, üst sınıfların yerine herhangi bir sorun çıkarmadan kullanılabilmeli.
        I - Interface Segregation Principle (Arayüz Ayırma Prensibi): Bir sınıfa, kullanmadığı metotları içeren bir arayüzü implemente etmeye zorlanmamalı.
        D - Dependency Inversion Principle (Bağımlılıkların Tersine Çevrilmesi Prensibi): Üst seviye modüller, alt seviye modüllere bağımlı olmamalıdır. Her ikisi de soyutlamalara bağımlı olmalıdır.

Kod Örneği (Dependency Inversion):
// Kötü Tasarım: Yüksek seviyeli Notification sınıfı, düşük seviyeli Email'e doğrudan bağımlı.
public class Email {
    public void sendEmail() { /* ... */ }
}
public class Notification {
    private Email email = new Email();
    public void send() {
        email.sendEmail();
    }
}

// İyi Tasarım (DIP): İki sınıf da soyutlama (interface) üzerinden haberleşiyor.
public interface MessageProvider {
    void send();
}
public class EmailService implements MessageProvider {
    @Override
    public void send() { /* Email gönderme kodu */ }
}
public class SmsService implements MessageProvider {
    @Override
    public void send() { /* SMS gönderme kodu */ }
}

public class NotificationService {
    private final MessageProvider provider;

    // Dependency Injection (Bağımlılık Enjeksiyonu)
    public NotificationService(MessageProvider provider) {
        this.provider = provider;
    }

    public void notifyUser() {
        provider.send();
    }
}