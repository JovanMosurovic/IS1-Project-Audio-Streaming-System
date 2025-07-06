package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "audio_snimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudioSnimak.findAll", query = "SELECT a FROM AudioSnimak a")})
public class AudioSnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "audio_id")
    private Integer audioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_postavljanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePostavljanja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audioId")
    private List<AudioKategorija> audioKategorijaList;
    @JoinColumn(name = "vlasnik_id", referencedColumnName = "korisnik_id")
    @ManyToOne(optional = false)
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

    @XmlTransient
    public List<AudioKategorija> getAudioKategorijaList() {
        return audioKategorijaList;
    }

    public void setAudioKategorijaList(List<AudioKategorija> audioKategorijaList) {
        this.audioKategorijaList = audioKategorijaList;
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
