package entities;

import java.io.Serializable;


public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer kategorijaId;

    private String naziv;

    public Kategorija() {
    }

    public Kategorija(Integer kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    public Kategorija(Integer kategorijaId, String naziv) {
        this.kategorijaId = kategorijaId;
        this.naziv = naziv;
    }

    public Integer getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Integer kategorijaId) {
        this.kategorijaId = kategorijaId;
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
        hash += (kategorijaId != null ? kategorijaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.kategorijaId == null && other.kategorijaId != null) || (this.kategorijaId != null && !this.kategorijaId.equals(other.kategorijaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kategorija[ kategorijaId=" + kategorijaId + " ]";
    }
    
}
