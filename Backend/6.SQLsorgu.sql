-- 🗃️ 6. SQL: Veritabanı ile İletişim Dili

-- SQL (Structured Query Language), ilişkisel veritabanları ile iletişim kurmak,
-- veri depolamak, yönetmek ve almak için kullanılan standart bir dildir.
-- Üç ana alt dile ayrılır: DDL, DML ve DQL.

-------------------------------------------------------------------------------
-- DDL (Data Definition Language - Veri Tanımlama Dili)
-- Veritabanı şemasını (yapısını) tanımlar ve yönetir.
-- Tablolar, indeksler, view'ler ve diğer veritabanı objeleri gibi yapısal
-- öğeleri oluşturmak, değiştirmek veya silmek için kullanılır.
-------------------------------------------------------------------------------

-- CREATE TABLE: Yeni bir tablo oluşturur.
-- 'id' sütunu otomatik artan birincil anahtardır (PRIMARY KEY).
-- 'ad' sütunu 50 karakterli ve boş bırakılamaz (NOT NULL).
-- 'soyad' sütunu 50 karakterlidir.
-- Not: AUTO_INCREMENT yerine PostgreSQL gibi veritabanlarında SERIAL veya IDENTITY kullanılabilir.
CREATE TABLE Ogrenciler (
    id INT PRIMARY KEY AUTO_INCREMENT, -- MySQL/H2/SQLite için AUTO_INCREMENT
    -- PostgreSQL için: id SERIAL PRIMARY KEY, veya id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ad VARCHAR(50) NOT NULL,
    soyad VARCHAR(50)
);

-- ALTER TABLE: Mevcut bir tablonun yapısını değiştirir.
-- 'Ogrenciler' tablosuna 'email' adında, 100 karakterli yeni bir sütun ekler.
ALTER TABLE Ogrenciler ADD COLUMN email VARCHAR(100);

-- ALTER TABLE ... MODIFY COLUMN: Mevcut bir sütunun özelliklerini değiştirir (örneğin boyutunu).
-- Not: Bu sözdizimi veritabanına göre değişebilir (örneğin PostgreSQL'de ALTER COLUMN TYPE).
-- Örnek (MySQL uyumlu):
-- ALTER TABLE Ogrenciler MODIFY COLUMN ad VARCHAR(75);

-- DROP TABLE: Mevcut bir tabloyu siler.
-- Bu işlem geri alınamaz! Tabloyu ve tüm verilerini kalıcı olarak siler.
-- DROP TABLE Ogrenciler;

-------------------------------------------------------------------------------
-- DML (Data Manipulation Language - Veri Yönetim Dili)
-- Veritabanındaki verileri yönetir. Mevcut tablolara veri eklemek,
-- güncellemek veya silmek için kullanılır.
-------------------------------------------------------------------------------

-- INSERT INTO: Bir tabloya yeni veri satırları ekler.
INSERT INTO Ogrenciler (ad, soyad) VALUES ('Ali', 'Veli');
INSERT INTO Ogrenciler (ad, soyad, email) VALUES ('Ayşe', 'Demir', 'ayse@example.com');
INSERT INTO Ogrenciler (ad, soyad, email) VALUES ('Can', 'Yılmaz', 'can@example.com');
INSERT INTO Ogrenciler (ad, soyad) VALUES ('Deniz', 'Çelik');


-- UPDATE: Mevcut veri satırlarını değiştirir.
-- WHERE koşulu ile hangi satırların güncelleneceği belirlenir.
-- ID'si 1 olan öğrencinin soyadını 'Yılmaz' olarak günceller.
UPDATE Ogrenciler SET soyad = 'Yılmaz' WHERE id = 1;

-- Birden fazla sütunu aynı anda güncelleme örneği:
-- Adı 'Ali' olan tüm öğrencilerin e-postasını ve adını günceller.
-- (id=1'deki Ali'nin adı 'Mehmet' olacak, email'i de set edilecek)
UPDATE Ogrenciler SET email = 'ali.veli@example.com', ad = 'Mehmet' WHERE ad = 'Ali';


-- DELETE FROM: Bir tablodan veri satırlarını siler.
-- WHERE koşulu ile hangi satırların silineceği belirlenir.
-- ID'si 4 olan öğrenciyi siler.
DELETE FROM Ogrenciler WHERE id = 4;

-- Soyadı 'Demir' olan tüm öğrencileri siler.
-- DELETE FROM Ogrenciler WHERE soyad = 'Demir';


-------------------------------------------------------------------------------
-- DQL (Data Query Language - Veri Sorgulama Dili)
-- Veritabanından veri almak (sorgulamak) için kullanılır.
-- Bu, SQL'in en sık kullanılan ve en önemli parçasıdır.
-------------------------------------------------------------------------------

-- SELECT: Veritabanından veri seçer ve alır.

-- 'Ogrenciler' tablosundaki tüm ID, ad ve soyadları getirir.
SELECT id, ad, soyad FROM Ogrenciler;

-- Tüm sütunları getirmek için '*' kullanılır.
-- Adı 'Ayşe' olan tüm öğrencilerin tüm bilgilerini getirir.
SELECT * FROM Ogrenciler WHERE ad = 'Ayşe';

-- Belirli bir koşula uyan verileri sıralama (ORDER BY)
-- Soyadı 'Yılmaz' olanları ada göre alfabetik sıralar.
SELECT id, ad, soyad FROM Ogrenciler WHERE soyad = 'Yılmaz' ORDER BY ad ASC;

-- Tekrar eden değerleri göstermeden (DISTINCT) benzersiz soyadları getirir.
SELECT DISTINCT soyad FROM Ogrenciler;

-- Veri gruplama ve toplama fonksiyonları (GROUP BY, COUNT, AVG, SUM, MAX, MIN)
-- Soyada göre gruplayarak her soyadın kaç kez tekrar ettiğini sayar.
SELECT soyad, COUNT(*) AS ogrenci_sayisi FROM Ogrenciler GROUP BY soyad;

-- Belirli bir koşula uyan satır sayısını sayar.
-- Soyadı 'Veli' olan öğrenci sayısını sayar.
SELECT COUNT(*) FROM Ogrenciler WHERE soyad = 'Veli';

-- Tüm öğrencilerin sayısını sayar.
SELECT COUNT(*) FROM Ogrenciler;