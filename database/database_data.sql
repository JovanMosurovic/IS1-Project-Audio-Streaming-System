-- Unos gradova
INSERT INTO mesto (naziv) VALUES 
('Beograd'),
('Toronto'),
('London'),
('Kragujevac');

-- Unos korisnika
INSERT INTO korisnik (ime, email, godiste, pol, mesto_id) VALUES
('Marko Markovic', 'marko@email.com', 1995, 'MUSKI', 1),
('Ana Anic', 'ana@email.com', 1998, 'ZENSKI', 2),
('Jovan Jovanovic', 'jovan@email.com', 1990, 'MUSKI', 3),
('Mila Milic', 'mila@email.com', 1993, 'ZENSKI', 1);

-- Unos kategorija
INSERT INTO kategorija (naziv) VALUES
('Muzika'),
('Podkast'),
('Audio knjiga'),
('Predavanje');

-- Unos audio snimaka
INSERT INTO audio_snimak (naziv, trajanje, vlasnik_id, datum_vreme_postavljanja) VALUES
('Moja prva pesma', 180, 1, '2024-02-01 12:00:00'),
('Istorija Srbije podcast', 1800, 2, '2024-02-02 15:30:00'),
('Gospodar prstenova - Poglavlje 1', 3600, 3, '2024-02-03 10:00:00'),
('Uvod u programiranje', 2700, 4, '2024-02-04 14:45:00');

-- Povezivanje audio snimaka sa kategorijama
INSERT INTO audio_kategorija (audio_id, kategorija_id) VALUES
(1, 1), -- Pesma -> Muzika
(2, 2), -- Podkast -> Podkast
(3, 3), -- Knjiga -> Audio knjiga
(4, 4); -- Predavanje -> Predavanje

-- Unos paketa pretplate
INSERT INTO paket (trenutna_cena) VALUES
(999.99),  -- Osnovni paket
(1999.99), -- Premium paket
(2999.99); -- Pro paket

-- Unos pretplata korisnika
INSERT INTO pretplata (korisnik_id, paket_id, datum_vreme_pocetka, placena_cena) VALUES
(1, 1, '2024-01-01 00:00:00', 999.99),
(2, 2, '2024-01-15 00:00:00', 1999.99),
(3, 3, '2024-02-01 00:00:00', 2999.99);

-- Unos istorije slusanja
INSERT INTO istorija_slusanja (korisnik_id, audio_id, datum_vreme_pocetka, pocetni_sekund, broj_odslusanih_sekundi) VALUES
(1, 2, '2024-02-05 18:00:00', 0, 900),
(2, 3, '2024-02-05 20:00:00', 0, 1800),
(3, 1, '2024-02-06 10:00:00', 0, 180),
(4, 4, '2024-02-06 14:00:00', 0, 2700);

-- Unos ocena
INSERT INTO ocena (korisnik_id, audio_id, vrednost, datum_vreme_ocene) VALUES
(1, 2, 5, '2024-02-05 19:00:00'),
(2, 3, 4, '2024-02-05 21:00:00'),
(3, 1, 5, '2024-02-06 11:00:00'),
(4, 4, 4, '2024-02-06 15:00:00');

-- Unos omiljenih audio snimaka
INSERT INTO omiljeni_audio (korisnik_id, audio_id) VALUES
(1, 2),
(2, 3),
(3, 1),
(4, 4);