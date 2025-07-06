package entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "omiljeni_audio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OmiljeniAudio.findAll", query = "SELECT o FROM OmiljeniAudio o")})
public class OmiljeniAudio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "omiljeni_audio_id")
    private Integer omiljeniAudioId;
    @JoinColumn(name = "audio_id", referencedColumnName = "audio_id")
    @ManyToOne(optional = false)
    private AudioSnimak audioId;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnik_id")
    @ManyToOne(optional = false)
    private Korisnik korisnikId;

    public OmiljeniAudio() {
    }

    public OmiljeniAudio(Integer omiljeniAudioId) {
        this.omiljeniAudioId = omiljeniAudioId;
    }

    public Integer getOmiljeniAudioId() {
        return omiljeniAudioId;
    }

    public void setOmiljeniAudioId(Integer omiljeniAudioId) {
        this.omiljeniAudioId = omiljeniAudioId;
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
        hash += (omiljeniAudioId != null ? omiljeniAudioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OmiljeniAudio)) {
            return false;
        }
        OmiljeniAudio other = (OmiljeniAudio) object;
        if ((this.omiljeniAudioId == null && other.omiljeniAudioId != null) || (this.omiljeniAudioId != null && !this.omiljeniAudioId.equals(other.omiljeniAudioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OmiljeniAudio[ omiljeniAudioId=" + omiljeniAudioId + " ]";
    }
    
}
