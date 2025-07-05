package entities;

import java.io.Serializable;


public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer korisnikId;

    private String ime;

    private String email;

    private int godiste;

    private String pol;

    private Mesto mestoId;

    public Korisnik() {
    }

    public Korisnik(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Korisnik(Integer korisnikId, String ime, String email, int godiste, String pol) {
        this.korisnikId = korisnikId;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
    }

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Mesto getMestoId() {
        return mestoId;
    }

    public void setMestoId(Mesto mestoId) {
        this.mestoId = mestoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnikId != null ? korisnikId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.korisnikId == null && other.korisnikId != null) || (this.korisnikId != null && !this.korisnikId.equals(other.korisnikId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik[ korisnikId=" + korisnikId + " ]";
    }
    
}
