package entities;

import java.io.Serializable;
import java.util.Date;


public class AudioSnimak implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer audioId;

    private String naziv;

    private int trajanje;

    private Date datumVremePostavljanja;

    private Korisnik vlasnikId;

    public AudioSnimak() {
    }

    public AudioSnimak(Integer audioId) {
        this.audioId = audioId;
    }

    public AudioSnimak(Integer audioId, String naziv, int trajanje, Date datumVremePostavljanja) {
        this.audioId = audioId;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumVremePostavljanja = datumVremePostavljanja;
    }

    public Integer getAudioId() {
        return audioId;
    }

    public void setAudioId(Integer audioId) {
        this.audioId = audioId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumVremePostavljanja() {
        return datumVremePostavljanja;
    }

    public void setDatumVremePostavljanja(Date datumVremePostavljanja) {
        this.datumVremePostavljanja = datumVremePostavljanja;
    }

    public Korisnik getVlasnikId() {
        return vlasnikId;
    }

    public void setVlasnikId(Korisnik vlasnikId) {
        this.vlasnikId = vlasnikId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (audioId != null ? audioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudioSnimak)) {
            return false;
        }
        AudioSnimak other = (AudioSnimak) object;
        if ((this.audioId == null && other.audioId != null) || (this.audioId != null && !this.audioId.equals(other.audioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AudioSnimak[ audioId=" + audioId + " ]";
    }
    
}
