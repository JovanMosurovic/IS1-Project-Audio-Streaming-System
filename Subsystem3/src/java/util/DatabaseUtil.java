package util;

import entities.Paket;
import entities.Pretplata;
import entities.IstorijaSlusanja;
import entities.OmiljeniAudio;
import entities.Ocena;
import entities.Korisnik;
import entities.AudioSnimak;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DatabaseUtil {

    private EntityManagerFactory emf;

    public DatabaseUtil() {
        try {
            emf = Persistence.createEntityManagerFactory("subsystem3PU");
            System.out.println("[INFO] Database EntityManagerFactory created");
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // CREATE operations
    public Object createPaket(double trenutnaCena) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Paket paket = new Paket();
            paket.setTrenutnaCena(BigDecimal.valueOf(trenutnaCena));

            em.persist(paket);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new paket with ID: " + paket.getPaketId());
            return paket;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating paket: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object createPretplata(int korisnikId, int paketId, double placenaCena) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if paket exists
            Paket paket = em.find(Paket.class, paketId);
            if (paket == null) {
                System.err.println("[ERROR] Paket with ID " + paketId + " not found");
                return new DatabaseError(404, "Paket with ID " + paketId + " does not exist");
            }

            // Check if korisnik already has active pretplata
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(p) FROM Pretplata p WHERE p.korisnikId = :korisnikId", Long.class);
            query.setParameter("korisnikId", korisnik);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.err.println("[ERROR] Korisnik " + korisnikId + " already has an active pretplata");
                return new DatabaseError(409, "Korisnik already has an active pretplata");
            }

            // Create new pretplata
            Pretplata pretplata = new Pretplata();
            pretplata.setKorisnikId(korisnik);
            pretplata.setPaketId(paket);
            pretplata.setPlacenaCena(BigDecimal.valueOf(placenaCena));
            pretplata.setDatumVremePocetka(new Date());

            em.persist(pretplata);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new pretplata with ID: " + pretplata.getPretplataId());
            return pretplata;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating pretplata: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object createSlusanje(int korisnikId, int audioId, int pocetniSekund, int brojOdslusanihSekundi) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Create new istorija slusanja
            IstorijaSlusanja slusanje = new IstorijaSlusanja();
            slusanje.setKorisnikId(korisnik);
            slusanje.setAudioId(audioSnimak);
            slusanje.setPocetniSekund(pocetniSekund);
            slusanje.setBrojOdslusanihSekundi(brojOdslusanihSekundi);
            slusanje.setDatumVremePocetka(new Date());

            em.persist(slusanje);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new slusanje with ID: " + slusanje.getIstorijaSlusanjaId());
            return slusanje;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating slusanje: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object addOmiljeniAudio(int korisnikId, int audioId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Check if already in favorites
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(oa) FROM OmiljeniAudio oa WHERE oa.korisnikId = :korisnikId AND oa.audioId = :audioId", Long.class);
            query.setParameter("korisnikId", korisnik);
            query.setParameter("audioId", audioSnimak);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.err.println("[ERROR] Audio " + audioId + " already in favorites for korisnik " + korisnikId);
                return new DatabaseError(409, "Audio snimak is already in favorites");
            }

            // Create new omiljeni audio
            OmiljeniAudio omiljeniAudio = new OmiljeniAudio();
            omiljeniAudio.setKorisnikId(korisnik);
            omiljeniAudio.setAudioId(audioSnimak);

            em.persist(omiljeniAudio);
            em.getTransaction().commit();

            System.out.println("[INFO] Added audio " + audioId + " to favorites for korisnik " + korisnikId);
            return omiljeniAudio;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error adding omiljeni audio: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object createOcena(int korisnikId, int audioId, int vrednost) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Check if korisnik already rated this audio
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(o) FROM Ocena o WHERE o.korisnikId = :korisnikId AND o.audioId = :audioId", Long.class);
            query.setParameter("korisnikId", korisnik);
            query.setParameter("audioId", audioSnimak);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.err.println("[ERROR] Korisnik " + korisnikId + " already rated audio " + audioId);
                return new DatabaseError(409, "You have already rated this audio snimak");
            }

            // Create new ocena
            Ocena ocena = new Ocena();
            ocena.setKorisnikId(korisnik);
            ocena.setAudioId(audioSnimak);
            ocena.setVrednost(vrednost);
            ocena.setDatumVremeOcene(new Date());

            em.persist(ocena);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new ocena with ID: " + ocena.getOcenaId());
            return ocena;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating ocena: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // READ operations - GET ALL
    public List<Paket> getAllPaketi() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Paket> query = em.createNamedQuery("Paket.findAll", Paket.class);
            List<Paket> paketi = query.getResultList();
            System.out.println("[INFO] Retrieved " + paketi.size() + " paketi from database");
            return paketi;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all paketi: " + e.getMessage());
            throw new RuntimeException("Error getting all paketi", e);
        } finally {
            em.close();
        }
    }

    public List<Pretplata> getAllPretplateForKorisnik(int korisnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Pretplata p JOIN FETCH p.korisnikId JOIN FETCH p.paketId WHERE p.korisnikId.korisnikId = :korisnikId";
            TypedQuery<Pretplata> query = em.createQuery(jpql, Pretplata.class);
            query.setParameter("korisnikId", korisnikId);
            List<Pretplata> pretplate = query.getResultList();
            System.out.println("[INFO] Retrieved " + pretplate.size() + " pretplate for korisnik " + korisnikId);
            return pretplate;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting pretplate for korisnik " + korisnikId + ": " + e.getMessage());
            throw new RuntimeException("Error getting pretplate for korisnik", e);
        } finally {
            em.close();
        }
    }

    public List<IstorijaSlusanja> getAllSlusanjaForAudio(int audioId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT i FROM IstorijaSlusanja i JOIN FETCH i.korisnikId JOIN FETCH i.audioId WHERE i.audioId.audioId = :audioId";
            TypedQuery<IstorijaSlusanja> query = em.createQuery(jpql, IstorijaSlusanja.class);
            query.setParameter("audioId", audioId);
            List<IstorijaSlusanja> slusanja = query.getResultList();
            System.out.println("[INFO] Retrieved " + slusanja.size() + " slusanja for audio " + audioId);
            return slusanja;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting slusanja for audio " + audioId + ": " + e.getMessage());
            throw new RuntimeException("Error getting slusanja for audio", e);
        } finally {
            em.close();
        }
    }

    public List<Ocena> getAllOceneForAudio(int audioId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT o FROM Ocena o JOIN FETCH o.korisnikId JOIN FETCH o.audioId WHERE o.audioId.audioId = :audioId";
            TypedQuery<Ocena> query = em.createQuery(jpql, Ocena.class);
            query.setParameter("audioId", audioId);
            List<Ocena> ocene = query.getResultList();
            System.out.println("[INFO] Retrieved " + ocene.size() + " ocene for audio " + audioId);
            return ocene;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting ocene for audio " + audioId + ": " + e.getMessage());
            throw new RuntimeException("Error getting ocene for audio", e);
        } finally {
            em.close();
        }
    }

    public List<OmiljeniAudio> getOmiljeniAudioForKorisnik(int korisnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT oa FROM OmiljeniAudio oa JOIN FETCH oa.korisnikId JOIN FETCH oa.audioId WHERE oa.korisnikId.korisnikId = :korisnikId";
            TypedQuery<OmiljeniAudio> query = em.createQuery(jpql, OmiljeniAudio.class);
            query.setParameter("korisnikId", korisnikId);
            List<OmiljeniAudio> omiljeniAudio = query.getResultList();
            System.out.println("[INFO] Retrieved " + omiljeniAudio.size() + " omiljeni audio for korisnik " + korisnikId);
            return omiljeniAudio;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting omiljeni audio for korisnik " + korisnikId + ": " + e.getMessage());
            throw new RuntimeException("Error getting omiljeni audio for korisnik", e);
        } finally {
            em.close();
        }
    }

    // UPDATE operations
    public Object updatePaketCena(int paketId, double novaCena) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if paket exists
            Paket paket = em.find(Paket.class, paketId);
            if (paket == null) {
                System.err.println("[ERROR] Paket with ID " + paketId + " not found");
                return new DatabaseError(404, "Paket with ID " + paketId + " does not exist");
            }

            // Update cena
            paket.setTrenutnaCena(BigDecimal.valueOf(novaCena));
            em.merge(paket);
            em.getTransaction().commit();

            System.out.println("[INFO] Updated paket " + paketId + " cena to: " + novaCena);
            return paket;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error updating paket cena: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object updateOcena(int ocenaId, int korisnikId, int novaVrednost) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if ocena exists
            Ocena ocena = em.find(Ocena.class, ocenaId);
            if (ocena == null) {
                System.err.println("[ERROR] Ocena with ID " + ocenaId + " not found");
                return new DatabaseError(404, "Ocena with ID " + ocenaId + " does not exist");
            }

            // Check if user is the owner
            if (!ocena.getKorisnikId().getKorisnikId().equals(korisnikId)) {
                System.err.println("[ERROR] User " + korisnikId + " is not the owner of ocena " + ocenaId);
                return new DatabaseError(403, "You can only update your own ocene");
            }

            // Update vrednost
            ocena.setVrednost(novaVrednost);
            ocena.setDatumVremeOcene(new Date());
            em.merge(ocena);
            em.getTransaction().commit();

            System.out.println("[INFO] Updated ocena " + ocenaId + " vrednost to: " + novaVrednost);
            return ocena;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error updating ocena: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // DELETE operations
    public Object deleteOcena(int ocenaId, int korisnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if ocena exists
            Ocena ocena = em.find(Ocena.class, ocenaId);
            if (ocena == null) {
                System.err.println("[ERROR] Ocena with ID " + ocenaId + " not found");
                return new DatabaseError(404, "Ocena with ID " + ocenaId + " does not exist");
            }

            // Check if user is the owner
            if (!ocena.getKorisnikId().getKorisnikId().equals(korisnikId)) {
                System.err.println("[ERROR] User " + korisnikId + " is not the owner of ocena " + ocenaId);
                return new DatabaseError(403, "You can only delete your own ocene");
            }

            // Delete the ocena
            em.remove(ocena);
            em.getTransaction().commit();

            System.out.println("[INFO] Deleted ocena " + ocenaId);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error deleting ocena: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
        System.out.println("[INFO] Database EntityManagerFactory closed");
    }
}