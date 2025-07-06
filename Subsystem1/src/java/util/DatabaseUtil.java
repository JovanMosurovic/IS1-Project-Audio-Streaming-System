package util;

import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DatabaseUtil {

    private EntityManagerFactory emf;

    public DatabaseUtil() {
        try {
            emf = Persistence.createEntityManagerFactory("subsystem1PU");
            System.out.println("[INFO] Database EntityManagerFactory created");
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // CREATE operations
    public Object createMesto(String naziv) {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();

            Mesto mesto = new Mesto();
            mesto.setNaziv(naziv);

            em.persist(mesto);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new mesto: " + naziv + " with ID: " + mesto.getMestoId());
            return mesto;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating mesto: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object createKorisnik(String ime, String email, int godiste, String pol, int mestoId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Check if mesto exists
            Mesto mesto = em.find(Mesto.class, mestoId);
            if (mesto == null) {
                System.err.println("[ERROR] Mesto with ID " + mestoId + " not found");
                em.getTransaction().rollback();
                return new DatabaseError(404, "Mesto with ID " + mestoId + " does not exist");
            }

            // Check if email already exists
            TypedQuery<Long> emailQuery = em.createQuery(
                    "SELECT COUNT(k) FROM Korisnik k WHERE k.email = :email", Long.class);
            emailQuery.setParameter("email", email);
            Long emailCount = emailQuery.getSingleResult();

            if (emailCount > 0) {
                System.err.println("[ERROR] Email " + email + " already exists");
                return new DatabaseError(409, "Email " + email + " already exists in the system");
            }

            // Create new korisnik
            Korisnik korisnik = new Korisnik();
            korisnik.setIme(ime);
            korisnik.setEmail(email);
            korisnik.setGodiste(godiste);
            korisnik.setPol(pol);
            korisnik.setMestoId(mesto);

            em.persist(korisnik);
            em.getTransaction().commit();

            System.out.println("[INFO] Created new korisnik: " + ime + " with ID: " + korisnik.getKorisnikId());
            return korisnik;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error creating korisnik: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // READ operations - GET ALL
    public List<Mesto> getAllMesta() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Mesto> query = em.createNamedQuery("Mesto.findAll", Mesto.class);
            List<Mesto> mesta = query.getResultList();
            System.out.println("[INFO] Retrieved " + mesta.size() + " mesta from database");
            return mesta;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all mesta: " + e.getMessage());
            throw new RuntimeException("Error getting all mesta", e);
        } finally {
            em.close();
        }
    }

    public List<Korisnik> getAllKorisnici() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT k FROM Korisnik k JOIN FETCH k.mestoId";
            TypedQuery<Korisnik> query = em.createQuery(jpql, Korisnik.class);
            List<Korisnik> korisnici = query.getResultList();
            System.out.println("[INFO] Retrieved " + korisnici.size() + " korisnici from database");
            return korisnici;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all korisnici: " + e.getMessage());
            throw new RuntimeException("Error getting all korisnici", e);
        } finally {
            em.close();
        }
    }

    // READ operations - GET BY ID
    public Mesto getMestoById(int mestoId) {
        EntityManager em = emf.createEntityManager();
        try {

            Mesto mesto = em.find(Mesto.class, mestoId);
            if (mesto != null) {
                System.out.println("[INFO] Retrieved mesto with ID: " + mestoId);
            } else {
                System.out.println("[WARN] Mesto with ID " + mestoId + " not found");
            }
            return mesto;

        } catch (Exception e) {
            System.err.println("[ERROR] Error getting mesto by ID " + mestoId + ": " + e.getMessage());
            throw new RuntimeException("Error getting mesto by ID", e);
        } finally {
            em.close();
        }
    }

    public Korisnik getKorisnikById(int korisnikId) {
        EntityManager em = emf.createEntityManager();
        try {

            String jpql = "SELECT k FROM Korisnik k JOIN FETCH k.mestoId WHERE k.korisnikId = :korisnikId";
            TypedQuery<Korisnik> query = em.createQuery(jpql, Korisnik.class);
            query.setParameter("korisnikId", korisnikId);
            List<Korisnik> results = query.getResultList();

            if (!results.isEmpty()) {
                Korisnik korisnik = results.get(0);
                System.out.println("[INFO] Retrieved korisnik with ID: " + korisnikId);
                return korisnik;
            } else {
                System.out.println("[WARN] Korisnik with ID " + korisnikId + " not found");
                return null;
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting korisnik by ID " + korisnikId + ": " + e.getMessage());
            throw new RuntimeException("Error getting korisnik by ID", e);
        } finally {
            em.close();
        }
    }

    // UPDATE operations
    public Object updateKorisnikEmail(int korisnikId, String newEmail) {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if new email already exists (excluding current korisnik)
            TypedQuery<Long> emailQuery = em.createQuery(
                    "SELECT COUNT(k) FROM Korisnik k WHERE k.email = :email AND k.korisnikId != :korisnikId", Long.class);
            emailQuery.setParameter("email", newEmail);
            emailQuery.setParameter("korisnikId", korisnikId);
            Long emailCount = emailQuery.getSingleResult();

            if (emailCount > 0) {
                System.err.println("[ERROR] Email " + newEmail + " already exists for another korisnik");
                return new DatabaseError(409, "Email " + newEmail + " already exists in the system");
            }

            // Update email
            korisnik.setEmail(newEmail);
            em.merge(korisnik);
            em.getTransaction().commit();

            System.out.println("[INFO] Updated korisnik " + korisnikId + " email to: " + newEmail);
            return korisnik;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error updating korisnik email: " + e.getMessage());
            return new DatabaseError(500, "Database error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Object updateKorisnikMesto(int korisnikId, int newMestoId) {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();

            // Check if korisnik exists
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik == null) {
                System.err.println("[ERROR] Korisnik with ID " + korisnikId + " not found");
                return new DatabaseError(404, "Korisnik with ID " + korisnikId + " does not exist");
            }

            // Check if new mesto exists
            Mesto newMesto = em.find(Mesto.class, newMestoId);
            if (newMesto == null) {
                System.err.println("[ERROR] Mesto with ID " + newMestoId + " not found");
                return new DatabaseError(404, "Mesto with ID " + newMestoId + " does not exist");
            }

            // Update mesto
            korisnik.setMestoId(newMesto);
            em.merge(korisnik);
            em.getTransaction().commit();

            System.out.println("[INFO] Updated korisnik " + korisnikId + " mesto to: " + newMesto.getNaziv());
            return korisnik;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("[ERROR] Error updating korisnik mesto: " + e.getMessage());
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
