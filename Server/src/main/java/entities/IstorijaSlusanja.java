package entities;

import java.io.Serializable;
import java.util.Date;


public class IstorijaSlusanja implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer istorijaSlusanjaId;

    private Date datumVremePocetka;

    private int pocetniSekund;

    private int brojOdslusanihSekundi;

    private AudioSnimak audioId;

    private Korisnik korisnikId;

    public IstorijaSlusanja() {
    }

    public IstorijaSlusanja(Integer istorijaSlusanjaId) {
        this.istorijaSlusanjaId = istorijaSlusanjaId;
    }

    public IstorijaSlusanja(Integer istorijaSlusanjaId, Date datumVremePocetka, int pocetniSekund, int brojOdslusanihSekundi) {
        this.istorijaSlusanjaId = istorijaSlusanjaId;
        this.datumVremePocetka = datumVremePocetka;
        this.pocetniSekund = pocetniSekund;
        this.brojOdslusanihSekundi = brojOdslusanihSekundi;
    }

    public Integer getIstorijaSlusanjaId() {
        return istorijaSlusanjaId;
    }

    public void setIstorijaSlusanjaId(Integer istorijaSlusanjaId) {
        this.istorijaSlusanjaId = istorijaSlusanjaId;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public int getPocetniSekund() {
        return pocetniSekund;
    }

    public void setPocetniSekund(int pocetniSekund) {
        this.pocetniSekund = pocetniSekund;
    }

    public int getBrojOdslusanihSekundi() {
        return brojOdslusanihSekundi;
    }

    public void setBrojOdslusanihSekundi(int brojOdslusanihSekundi) {
        this.brojOdslusanihSekundi = brojOdslusanihSekundi;
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
        hash += (istorijaSlusanjaId != null ? istorijaSlusanjaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IstorijaSlusanja)) {
            return false;
        }
        IstorijaSlusanja other = (IstorijaSlusanja) object;
        if ((this.istorijaSlusanjaId == null && other.istorijaSlusanjaId != null) || (this.istorijaSlusanjaId != null && !this.istorijaSlusanjaId.equals(other.istorijaSlusanjaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.IstorijaSlusanja[ istorijaSlusanjaId=" + istorijaSlusanjaId + " ]";
    }
    
}
