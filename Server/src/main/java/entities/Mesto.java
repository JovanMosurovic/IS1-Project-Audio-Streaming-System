package entities;

import java.io.Serializable;
import java.util.List;


public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mestoId;
    private String naziv;

    public Mesto() {
    }

    public Mesto(Integer mestoId) {
        this.mestoId = mestoId;
    }

    public Mesto(Integer mestoId, String naziv) {
        this.mestoId = mestoId;
        this.naziv = naziv;
    }

    public Integer getMestoId() {
        return mestoId;
    }

    public void setMestoId(Integer mestoId) {
        this.mestoId = mestoId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mestoId != null ? mestoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.mestoId == null && other.mestoId != null) || (this.mestoId != null && !this.mestoId.equals(other.mestoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mesto[ mestoId=" + mestoId + " ]";
    }
    
}
