package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kategorija_id")
    private Integer kategorijaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kategorijaId")
    private List<AudioKategorija> audioKategorijaList;

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

    @XmlTransient
    public List<AudioKategorija> getAudioKategorijaList() {
        return audioKategorijaList;
    }

    public void setAudioKategorijaList(List<AudioKategorija> audioKategorijaList) {
        this.audioKategorijaList = audioKategorijaList;
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
