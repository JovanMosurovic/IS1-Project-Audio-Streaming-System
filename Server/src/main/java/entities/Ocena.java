package entities;

import java.io.Serializable;
import java.util.Date;


public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ocenaId;

    private int vrednost;

    private Date datumVremeOcene;

    private AudioSnimak audioId;

    private Korisnik korisnikId;

    public Ocena() {
    }

    public Ocena(Integer ocenaId) {
        this.ocenaId = ocenaId;
    }

    public Ocena(Integer ocenaId, int vrednost, Date datumVremeOcene) {
        this.ocenaId = ocenaId;
        this.vrednost = vrednost;
        this.datumVremeOcene = datumVremeOcene;
    }

    public Integer getOcenaId() {
        return ocenaId;
    }

    public void setOcenaId(Integer ocenaId) {
        this.ocenaId = ocenaId;
    }

    public int getVrednost() {
        return vrednost;
    }

    public void setVrednost(int vrednost) {
        this.vrednost = vrednost;
    }

    public Date getDatumVremeOcene() {
        return datumVremeOcene;
    }

    public void setDatumVremeOcene(Date datumVremeOcene) {
        this.datumVremeOcene = datumVremeOcene;
    }

    public AudioSnimak getAudioId() {
        return audioId;
    }

    public void setAudioId(AudioSnimak audioId) {
        this.audioId = audioId;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocenaId != null ? ocenaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.ocenaId == null && other.ocenaId != null) || (this.ocenaId != null && !this.ocenaId.equals(other.ocenaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ocena[ ocenaId=" + ocenaId + " ]";
    }
    
}
