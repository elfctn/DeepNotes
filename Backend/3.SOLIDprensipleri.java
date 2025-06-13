// 🧱 3. SOLID Prensipleri Notları
// Yazılım tasarımını daha anlaşılır, esnek ve sürdürülebilir kılan beş temel prensiptir.

// Not: Bu tek bir Java dosyası, örnekleri ve açıklamaları bir arada tutmak için
// gerçek bir projenin yapısını taklit etmez. Normalde her sınıf kendi dosyasında olur.
// Lombok gibi dış kütüphaneler için gerekli import'lar ve bağımlılıklar (pom.xml) varsayılmıştır.

// --------------------------------------------------------------------------------------
// S - Single Responsibility Principle (Tek Sorumluluk Prensibi)
// Bir sınıfın değişmek için tek bir nedeni olmalı.
// --------------------------------------------------------------------------------------

// Kötü Tasarım: Hem kullanıcı bilgilerini yönetiyor hem de rapor oluşturuyor.
class BadUserReportManager {
    public void createUser(String username) {
        System.out.println("Kullanıcı oluşturuldu: " + username);
        // Kullanıcı veritabanına kaydetme mantığı
    }
    public void generateUserReport(String username) {
        System.out.println("Kullanıcı raporu oluşturuldu: " + username);
        // Rapor oluşturma ve biçimlendirme mantığı
    }
}

// İyi Tasarım: Her sınıfın tek bir sorumluluğu var.
class UserRepository { // Sadece kullanıcı verilerini yönetir
    public void createUser(String username) {
        System.out.println("UserRepository: Kullanıcı oluşturuldu: " + username);
        // Kullanıcı veritabanına kaydetme mantığı
    }
    public User getUser(String username) { // Basit bir User objesi döndürüyor
        System.out.println("UserRepository: Kullanıcı alındı: " + username);
        return new User(username); // Varsayımsal bir User sınıfı
    }
}

class UserReportGenerator { // Sadece kullanıcı raporu oluşturur
    public void generateReport(User user) {
        System.out.println("UserReportGenerator: " + user.getName() + " için rapor oluşturuldu.");
        // Rapor oluşturma ve biçimlendirme mantığı
    }
}

// Varsayımsal User sınıfı (Lombok ile daha kısa yazılabilir)
class User {
    private String name;
    public User(String name) { this.name = name; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}


// --------------------------------------------------------------------------------------
// O - Open/Closed Principle (Açık/Kapalı Prensibi)
// Yazılım varlıkları (sınıflar, modüller) genişlemeye açık, değişime kapalı olmalı.
// --------------------------------------------------------------------------------------

// Kötü Tasarım: Yeni şekil ekledikçe 'drawShape' metodu değişmek zorunda.
class BadDrawing {
    public void drawShape(String type) {
        if ("Circle".equals(type)) {
            System.out.println("Çember çizildi.");
        } else if ("Square".equals(type)) {
            System.out.println("Kare çizildi.");
        }
        // Yeni bir şekil gelirse burası değişmek zorunda (örneğin "Triangle")
    }
}

// İyi Tasarım: Genişlemeye açık (yeni şekiller eklenebilir), değişime kapalı (Drawing sınıfı değişmez).
interface Shape {
    void draw(); // Tüm şekillerin 'draw' metodu olmalı
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle çizildi.");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square çizildi.");
    }
}

class GoodDrawing {
    public void drawShape(Shape shape) {
        shape.draw(); // Polimorfizm sayesinde mevcut kodu değiştirmeyiz.
    }
}


// --------------------------------------------------------------------------------------
// L - Liskov Substitution Principle (Liskov'un Yerine Geçme Prensibi)
// Alt sınıflar, üst sınıfların yerine herhangi bir sorun çıkarmadan kullanılabilmeli.
// --------------------------------------------------------------------------------------

// Kötü Tasarım: Penguen uçamaz, ama Bird sınıfı 'fly' metoduna sahip.
class BadBird {
    public void fly() {
        System.out.println("Kuş uçuyor.");
    }
}

class BadPenguin extends BadBird {
    // Penguen uçamadığı için bu metod ya boş kalacak ya da hata fırlatacak.
    // Bu, beklenen davranışı bozar.
    @Override
    public void fly() {
        // throw new UnsupportedOperationException("Penguenler uçamaz."); // Hata fırlatmak LSP'yi bozar
        System.out.println("Penguen uçamıyor."); // Davranışı değiştirmek de LSP'yi bozar
    }
}

// İyi Tasarım: Ortak yetenekleri soyutlayarak daha spesifik hiyerarşiler oluşturmak.
interface Flyable { // Uçma yeteneği olanlar için
    void fly();
}

class FlyingBird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Uçan kuş (Flyable) uçuyor.");
    }
}

class PenguinBird { // Uçma yeteneği olmayan kuşlar için ayrı bir sınıf.
    public void swim() {
        System.out.println("Penguen yüzüyor.");
    }
}

// Bu durumda, bir 'fly' bekleyen kod sadece 'Flyable' arayüzünü implemente eden sınıfları kullanır.
// BadBird badBird = new BadPenguin();
// badBird.fly(); // Bu çağrı problem yaratabilir.


// --------------------------------------------------------------------------------------
// I - Interface Segregation Principle (Arayüz Ayırma Prensibi)
// Bir sınıfa, kullanmadığı metotları içeren bir arayüzü implemente etmeye zorlanmamalı.
// --------------------------------------------------------------------------------------

// Kötü Tasarım: Hem yazıcı hem de tarayıcı metodlarını içeren tek bir arayüz.
interface BadMachine {
    void print();
    void scan();
    void fax();
}

class SimplePrinter implements BadMachine { // Sadece yazdırma yeteneği var
    @Override
    public void print() {
        System.out.println("SimplePrinter: Yazdırma işlemi yapılıyor.");
    }
    @Override
    public void scan() {
        // Bu metot boş kalmak zorunda, çünkü SimplePrinter tarama yapmaz.
        System.out.println("SimplePrinter: Tarama desteklenmiyor.");
    }
    @Override
    public void fax() {
        // Bu metot boş kalmak zorunda.
        System.out.println("SimplePrinter: Faks desteklenmiyor.");
    }
}

// İyi Tasarım: Daha küçük ve odaklanmış arayüzler.
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

interface FaxMachine {
    void fax();
}

class GoodSimplePrinter implements Printer {
    @Override
    public void print() {
        System.out.println("GoodSimplePrinter: Yazdırma işlemi yapılıyor.");
    }
}

class MultiFunctionPrinter implements Printer, Scanner, FaxMachine {
    @Override
    public void print() {
        System.out.println("MultiFunctionPrinter: Yazdırma işlemi yapılıyor.");
    }
    @Override
    public void scan() {
        System.out.println("MultiFunctionPrinter: Tarama işlemi yapılıyor.");
    }
    @Override
    public void fax() {
        System.out.println("MultiFunctionPrinter: Faks işlemi yapılıyor.");
    }
}


// --------------------------------------------------------------------------------------
// D - Dependency Inversion Principle (Bağımlılıkların Tersine Çevrilmesi Prensibi)
// Üst seviye modüller, alt seviye modüllere bağımlı olmamalıdır. Her ikisi de soyutlamalara
// bağımlı olmalıdır.
// --------------------------------------------------------------------------------------

// Kötü Tasarım: Yüksek seviyeli Notification sınıfı, düşük seviyeli Email'e doğrudan bağımlı.
class BadEmail { // Düşük seviyeli modül
    public void sendEmail(String message) {
        System.out.println("BadEmail: Email gönderildi: " + message);
    }
}

class BadNotification { // Yüksek seviyeli modül
    private BadEmail email = new BadEmail(); // Doğrudan bağımlılık - değişimi zorlaştırır
    public void send(String message) {
        email.sendEmail(message);
    }
}

// İyi Tasarım (DIP): İki sınıf da soyutlama (interface) üzerinden haberleşiyor.
interface MessageSender { // Soyutlama (Abstraction)
    void send(String message);
}

class EmailService implements MessageSender { // Düşük seviyeli modül, soyutlamaya bağımlı
    @Override
    public void send(String message) {
        System.out.println("EmailService: Email gönderildi: " + message);
    }
}

class SmsService implements MessageSender { // Düşük seviyeli modül, soyutlamaya bağımlı
    @Override
    public void send(String message) {
        System.out.println("SmsService: SMS gönderildi: " + message);
    }
}

class NotificationService { // Yüksek seviyeli modül, soyutlamaya bağımlı
    private final MessageSender sender; // Soyutlamaya bağımlılık

    // Bağımlılık Enjeksiyonu (Constructor Injection)
    // NotificationService, hangi MessageSender implementasyonunu kullandığını bilmek zorunda değil.
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String message) {
        sender.send(message); // Hangi servis olduğunun önemi yok, soyutlama üzerinden çağırır.
    }
}


// --- Main Sınıfı (Örnek Çalıştırma) ---
// Bu sınıf, yukarıdaki prensipleri ve örnekleri çalıştırmak için kullanılır.
public class SolidPrincipleExamples {
    public static void main(String[] args) {
        System.out.println("--- SOLID Prensip Örnekleri ---");

        // Single Responsibility Principle
        System.out.println("\n--- Single Responsibility Principle ---");
        UserRepository userRepository = new UserRepository();
        UserReportGenerator reportGenerator = new UserReportGenerator();
        userRepository.createUser("JohnDoe");
        User john = userRepository.getUser("JohnDoe");
        reportGenerator.generateReport(john);

        // Open/Closed Principle
        System.out.println("\n--- Open/Closed Principle ---");
        GoodDrawing goodDrawing = new GoodDrawing();
        goodDrawing.drawShape(new Circle());
        goodDrawing.drawShape(new Square());
        // Yeni bir şekil (örn: Triangle) eklemek için 'GoodDrawing' sınıfını değiştirmemize gerek yok.
        // Sadece 'Triangle' sınıfını implemente eden yeni bir 'Shape' oluştururuz.

        // Liskov Substitution Principle
        System.out.println("\n--- Liskov Substitution Principle ---");
        Flyable flyingBird = new FlyingBird();
        flyingBird.fly();
        // Flyable flyablePenguin = new PenguinBird(); // Bu hata verir, çünkü PenguinBird Flyable değil.
        PenguinBird penguin = new PenguinBird();
        penguin.swim();

        // Interface Segregation Principle
        System.out.println("\n--- Interface Segregation Principle ---");
        Printer printer = new GoodSimplePrinter();
        printer.print();
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        mfp.print();
        mfp.scan();
        mfp.fax();

        // Dependency Inversion Principle
        System.out.println("\n--- Dependency Inversion Principle ---");
        NotificationService emailNotification = new NotificationService(new EmailService());
        emailNotification.notifyUser("E-posta ile selamlar!");

        NotificationService smsNotification = new NotificationService(new SmsService());
        smsNotification.notifyUser("SMS ile selamlar!");
    }
}