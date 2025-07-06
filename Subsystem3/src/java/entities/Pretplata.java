package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pretplata_id")
    private Integer pretplataId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_pocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "placena_cena")
    private BigDecimal placenaCena;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnik_id")
    @OneToOne(optional = false)
    private Korisnik korisnikId;
    @JoinColumn(name = "paket_id", referencedColumnName = "paket_id")
    @ManyToOne(optional = false)
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
