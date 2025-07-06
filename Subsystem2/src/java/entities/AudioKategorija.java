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
@Table(name = "audio_kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudioKategorija.findAll", query = "SELECT a FROM AudioKategorija a")})
public class AudioKategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "audio_kategorija_id")
    private Integer audioKategorijaId;
    @JoinColumn(name = "audio_id", referencedColumnName = "audio_id")
    @ManyToOne(optional = false)
    private AudioSnimak audioId;
    @JoinColumn(name = "kategorija_id", referencedColumnName = "kategorija_id")
    @ManyToOne(optional = false)
    private Kategorija kategorijaId;

    public AudioKategorija() {
    }

    public AudioKategorija(Integer audioKategorijaId) {
        this.audioKategorijaId = audioKategorijaId;
    }

    public Integer getAudioKategorijaId() {
        return audioKategorijaId;
    }

    public void setAudioKategorijaId(Integer audioKategorijaId) {
        this.audioKategorijaId = audioKategorijaId;
    }

    public AudioSnimak getAudioId() {
        return audioId;
    }

    public void setAudioId(AudioSnimak audioId) {
        this.audioId = audioId;
    }

    public Kategorija getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Kategorija kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (audioKategorijaId != null ? audioKategorijaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudioKategorija)) {
            return false;
        }
        AudioKategorija other = (AudioKategorija) object;
        if ((this.audioKategorijaId == null && other.audioKategorijaId != null) || (this.audioKategorijaId != null && !this.audioKategorijaId.equals(other.audioKategorijaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AudioKategorija[ audioKategorijaId=" + audioKategorijaId + " ]";
    }
    
}
