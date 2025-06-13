🗃️ 6. SQL (DDL, DML, DQL)
Veritabanı ile iletişim kurma dili.



DDL (Data Definition Language): Veritabanı şemasını tanımlar. CREATE, ALTER, DROP.

CREATE TABLE Ogrenciler (
        id INT PRIMARY KEY AUTO_INCREMENT,
        ad VARCHAR(50) NOT NULL,
soyad VARCHAR(50)
);
--------------------------------------------------------------
DML (Data Manipulation Language): Verileri yönetir. INSERT, UPDATE, DELETE.

INSERT INTO Ogrenciler (ad, soyad) VALUES ('Ali', 'Veli');
UPDATE Ogrenciler SET soyad = 'Yılmaz' WHERE id = 1;
DELETE FROM Ogrenciler WHERE id = 1;

-------------------------------------------------------------

DQL (Data Query Language): Verileri sorgular. SELECT.

        SELECT id, ad, soyad FROM Ogrenciler WHERE ad = 'Ali';
