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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ocena_id")
    private Integer ocenaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vrednost")
    private int vrednost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_ocene")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeOcene;
    @JoinColumn(name = "audio_id", referencedColumnName = "audio_id")
    @ManyToOne(optional = false)
    private AudioSnimak audioId;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnik_id")
    @ManyToOne(optional = false)
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
