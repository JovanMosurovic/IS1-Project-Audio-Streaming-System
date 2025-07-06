package util;

import entities.AudioSnimak;
import entities.Kategorija;
import entities.AudioKategorija;
import entities.Korisnik;
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
            emf = Persistence.createEntityManagerFactory("subsystem2PU");
            System.out.println("[INFO] Database EntityManagerFactory created");
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // CREATE operations
    public Object createKategorija(String naziv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if kategorija with same naziv already exists
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(k) FROM Kategorija k WHERE k.naziv = :naziv", Long.class);
            query.setParameter("naziv", naziv);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.err.println("[ERROR] Kategorija with naziv '" + naziv + "' already exists");
                return new DatabaseError(409, "Kategorija with naziv '" + naziv + "' already exists");
            }

            Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(naziv);

            em.persist(kategorija);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new kategorija: " + naziv + " with ID: " + kategorija.getKategorijaId());
            return kategorija;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating kategorija: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object createAudioSnimak(String naziv, int trajanje, int vlasnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, vlasnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + vlasnikId + " not found");
                em.getTransaction().rollback();
                return new DatabaseError(404, "Korisnik with ID " + vlasnikId + " does not exist");
            }

            // Create new audio snimak
            AudioSnimak audioSnimak = new AudioSnimak();
            audioSnimak.setNaziv(naziv);
            audioSnimak.setTrajanje(trajanje);
            audioSnimak.setVlasnikId(korisnik);
            audioSnimak.setDatumVremePostavljanja(new Date());

            em.persist(audioSnimak);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new audio snimak: " + naziv + " with ID: " + audioSnimak.getAudioId());
            return audioSnimak;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating audio snimak: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object addKategorijaToAudio(int audioId, int kategorijaId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Check if kategorija exists
            Kategorija kategorija = em.find(Kategorija.class, kategorijaId);
            if (kategorija == null) {
                System.err.println("[ERROR] Kategorija with ID " + kategorijaId + " not found");
                return new DatabaseError(404, "Kategorija with ID " + kategorijaId + " does not exist");
            }

            // Check if relation already exists
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(ak) FROM AudioKategorija ak WHERE ak.audioId = :audioId AND ak.kategorijaId = :kategorijaId", Long.class);
            query.setParameter("audioId", audioSnimak);
            query.setParameter("kategorijaId", kategorija);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.err.println("[ERROR] Kategorija " + kategorijaId + " already assigned to audio " + audioId);
                return new DatabaseError(409, "Kategorija is already assigned to this audio snimak");
            }

            // Create new audio-kategorija relation
            AudioKategorija audioKategorija = new AudioKategorija();
            audioKategorija.setAudioId(audioSnimak);
            audioKategorija.setKategorijaId(kategorija);

            em.persist(audioKategorija);
            em.getTransaction().commit();

            System.out.println("[INFO] Added kategorija " + kategorijaId + " to audio " + audioId);
            return audioKategorija;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error adding kategorija to audio: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // READ operations - GET ALL
    public List<Kategorija> getAllKategorije() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Kategorija> query = em.createNamedQuery("Kategorija.findAll", Kategorija.class);
            List<Kategorija> kategorije = query.getResultList();
            System.out.println("[INFO] Retrieved " + kategorije.size() + " kategorije from database");
            return kategorije;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all kategorije: " + e.getMessage());
            throw new RuntimeException("Error getting all kategorije", e);
        } finally {
            em.close();
        }
    }

    public List<AudioSnimak> getAllAudioSnimci() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT a FROM AudioSnimak a JOIN FETCH a.vlasnikId";
            TypedQuery<AudioSnimak> query = em.createQuery(jpql, AudioSnimak.class);
            List<AudioSnimak> audioSnimci = query.getResultList();
            System.out.println("[INFO] Retrieved " + audioSnimci.size() + " audio snimci from database");
            return audioSnimci;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all audio snimci: " + e.getMessage());
            throw new RuntimeException("Error getting all audio snimci", e);
        } finally {
            em.close();
        }
    }

    public List<Kategorija> getKategorijeForAudio(int audioId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT k FROM Kategorija k JOIN AudioKategorija ak ON k.kategorijaId = ak.kategorijaId.kategorijaId WHERE ak.audioId.audioId = :audioId";
            TypedQuery<Kategorija> query = em.createQuery(jpql, Kategorija.class);
            query.setParameter("audioId", audioId);
            List<Kategorija> kategorije = query.getResultList();
            System.out.println("[INFO] Retrieved " + kategorije.size() + " kategorije for audio " + audioId);
            return kategorije;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting kategorije for audio " + audioId + ": " + e.getMessage());
            throw new RuntimeException("Error getting kategorije for audio", e);
        } finally {
            em.close();
        }
    }

    // UPDATE operations
    public Object updateAudioSnimakNaziv(int audioId, String newNaziv, int vlasnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Check if user is the owner
            if (!audioSnimak.getVlasnikId().getKorisnikId().equals(vlasnikId)) {
                System.err.println("[ERROR] User " + vlasnikId + " is not the owner of audio snimak " + audioId);
                return new DatabaseError(403, "You can only update your own audio snimci");
            }

            // Update naziv
            audioSnimak.setNaziv(newNaziv);
            em.merge(audioSnimak);
            em.getTransaction().commit();

            System.out.println("[INFO] Updated audio snimak " + audioId + " naziv to: " + newNaziv);
            return audioSnimak;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error updating audio snimak naziv: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // DELETE operations
    public Object deleteAudioSnimak(int audioId, int vlasnikId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if audio snimak exists
            AudioSnimak audioSnimak = em.find(AudioSnimak.class, audioId);
            if (audioSnimak == null) {
                System.err.println("[ERROR] Audio snimak with ID " + audioId + " not found");
                return new DatabaseError(404, "Audio snimak with ID " + audioId + " does not exist");
            }

            // Check if user is the owner
            if (!audioSnimak.getVlasnikId().getKorisnikId().equals(vlasnikId)) {
                System.err.println("[ERROR] User " + vlasnikId + " is not the owner of audio snimak " + audioId);
                return new DatabaseError(403, "You can only delete your own audio snimci");
            }

            // Delete all audio-kategorija relations first
            TypedQuery<AudioKategorija> query = em.createQuery(
                    "SELECT ak FROM AudioKategorija ak WHERE ak.audioId = :audioId", AudioKategorija.class);
            query.setParameter("audioId", audioSnimak);
            List<AudioKategorija> relations = query.getResultList();
            
            for (AudioKategorija relation : relations) {
                em.remove(relation);
            }

            // Delete the audio snimak
            em.remove(audioSnimak);
            em.getTransaction().commit();

            System.out.println("[INFO] Deleted audio snimak " + audioId + " and " + relations.size() + " kategorija relations");
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error deleting audio snimak: " + e.getMessage());
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