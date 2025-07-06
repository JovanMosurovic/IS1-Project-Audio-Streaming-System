package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pretplataId;

    private Date datumVremePocetka;

    private BigDecimal placenaCena;

    private Korisnik korisnikId;

    private Paket paketId;

    public Pretplata() {
    }

    public Pretplata(Integer pretplataId) {
        this.pretplataId = pretplataId;
    }

    public Pretplata(Integer pretplataId, Date datumVremePocetka, BigDecimal placenaCena) {
        this.pretplataId = pretplataId;
        this.datumVremePocetka = datumVremePocetka;
        this.placenaCena = placenaCena;
    }

    public Integer getPretplataId() {
        return pretplataId;
    }

    public void setPretplataId(Integer pretplataId) {
        this.pretplataId = pretplataId;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public BigDecimal getPlacenaCena() {
        return placenaCena;
    }

    public void setPlacenaCena(BigDecimal placenaCena) {
        this.placenaCena = placenaCena;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Paket getPaketId() {
        return paketId;
    }

    public void setPaketId(Paket paketId) {
        this.paketId = paketId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pretplataId != null ? pretplataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.pretplataId == null && other.pretplataId != null) || (this.pretplataId != null && !this.pretplataId.equals(other.pretplataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pretplata[ pretplataId=" + pretplataId + " ]";
    }
    
}
