-- Tabela za mesto
CREATE TABLE mesto (
    mesto_id INT PRIMARY KEY AUTO_INCREMENT,
    naziv VARCHAR(100) NOT NULL
);

-- Tabela za korisnike
CREATE TABLE korisnik (
    korisnik_id INT PRIMARY KEY AUTO_INCREMENT,
    ime VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    godiste INT NOT NULL, 
    pol ENUM('MUSKI', 'ZENSKI', 'DRUGO') NOT NULL,
    mesto_id INT NOT NULL,
    FOREIGN KEY (mesto_id) REFERENCES mesto(mesto_id) ON DELETE CASCADE
);

-- Tabela za audio snimke 
CREATE TABLE audio_snimak (
    audio_id INT PRIMARY KEY AUTO_INCREMENT,
    naziv VARCHAR(200) NOT NULL,
    trajanje INT NOT NULL CHECK (trajanje > 0), -- u sekundama
    vlasnik_id INT NOT NULL,
    datum_vreme_postavljanja DATETIME NOT NULL,
    FOREIGN KEY (vlasnik_id) REFERENCES korisnik(korisnik_id) ON DELETE CASCADE
);

-- Tabela za kategorije
CREATE TABLE kategorija (
    kategorija_id INT PRIMARY KEY AUTO_INCREMENT,
    naziv VARCHAR(100) NOT NULL UNIQUE
);

-- Veza tabela za audio snimke i kategorije (many-to-many)
CREATE TABLE audio_kategorija (
		audio_kategorija_id INT PRIMARY KEY AUTO_INCREMENT,
    audio_id INT NOT NULL,
    kategorija_id INT NOT NULL,
    FOREIGN KEY (audio_id) REFERENCES audio_snimak(audio_id) ON DELETE CASCADE,
    FOREIGN KEY (kategorija_id) REFERENCES kategorija(kategorija_id) ON DELETE CASCADE,
    UNIQUE KEY (audio_id, kategorija_id)
);

-- Tabela za pakete
CREATE TABLE paket (
    paket_id INT PRIMARY KEY AUTO_INCREMENT,
    trenutna_cena DECIMAL(10,2) NOT NULL CHECK (trenutna_cena > 0)
);

-- Tabela za pretplate *
CREATE TABLE pretplata (
    pretplata_id INT PRIMARY KEY AUTO_INCREMENT,
    korisnik_id INT NOT NULL UNIQUE,
    paket_id INT NOT NULL,
    datum_vreme_pocetka DATETIME NOT NULL,
    placena_cena DECIMAL(10,2) NOT NULL CHECK (placena_cena > 0),
    FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id) ON DELETE CASCADE,
    FOREIGN KEY (paket_id) REFERENCES paket(paket_id) ON DELETE CASCADE
);

-- Tabela za istoriju slušanja
CREATE TABLE istorija_slusanja (
    istorija_slusanja_id INT PRIMARY KEY AUTO_INCREMENT,
    korisnik_id INT NOT NULL,
    audio_id INT NOT NULL,
    datum_vreme_pocetka DATETIME NOT NULL,
    pocetni_sekund INT NOT NULL CHECK (pocetni_sekund >= 0),
    broj_odslusanih_sekundi INT NOT NULL CHECK (broj_odslusanih_sekundi >= 0),
    FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id) ON DELETE CASCADE,
    FOREIGN KEY (audio_id) REFERENCES audio_snimak(audio_id) ON DELETE CASCADE
);

-- Tabela za ocene *
CREATE TABLE ocena (
    ocena_id INT PRIMARY KEY AUTO_INCREMENT, 
    korisnik_id INT NOT NULL,
    audio_id INT NOT NULL,
    vrednost INT NOT NULL CHECK (vrednost BETWEEN 1 AND 5),
    datum_vreme_ocene DATETIME NOT NULL,
    FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id) ON DELETE CASCADE,
    FOREIGN KEY (audio_id) REFERENCES audio_snimak(audio_id) ON DELETE CASCADE,
    UNIQUE KEY (korisnik_id, audio_id)
);

-- Tabela za omiljene audio snimke (many-to-many)
CREATE TABLE omiljeni_audio (
		omiljeni_audio_id INT PRIMARY KEY AUTO_INCREMENT,
    korisnik_id INT NOT NULL,
    audio_id INT NOT NULL,
    FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id) ON DELETE CASCADE,
    FOREIGN KEY (audio_id) REFERENCES audio_snimak(audio_id) ON DELETE CASCADE,
    UNIQUE KEY (korisnik_id, audio_id)
);

-- Indeksi za poboljšanje performansi
CREATE INDEX idx_korisnik_email ON korisnik(email);
CREATE INDEX idx_audio_vlasnik ON audio_snimak(vlasnik_id);
CREATE INDEX idx_pretplata_korisnik_datum ON pretplata(korisnik_id, datum_vreme_pocetka);
CREATE INDEX idx_istorija_slusanja_korisnik ON istorija_slusanja(korisnik_id);
CREATE INDEX idx_ocena_audio ON ocena(audio_id);