package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "istorija_slusanja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IstorijaSlusanja.findAll", query = "SELECT i FROM IstorijaSlusanja i")})
public class IstorijaSlusanja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "istorija_slusanja_id")
    private Integer istorijaSlusanjaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_pocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pocetni_sekund")
    private int pocetniSekund;
    @Basic(optional = false)
    @NotNull
    @Column(name = "broj_odslusanih_sekundi")
    private int brojOdslusanihSekundi;
    @JoinColumn(name = "audio_id", referencedColumnName = "audio_id")
    @ManyToOne(optional = false)
    private AudioSnimak audioId;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnik_id")
    @ManyToOne(optional = false)
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
