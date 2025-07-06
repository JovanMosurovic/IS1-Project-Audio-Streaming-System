package entities;

import java.io.Serializable;


public class AudioKategorija implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer audioKategorijaId;

    private AudioSnimak audioId;

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
