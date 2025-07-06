package entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "paket_id")
    private Integer paketId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "trenutna_cena")
    private BigDecimal trenutnaCena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paketId")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer paketId) {
        this.paketId = paketId;
    }

    public Paket(Integer paketId, BigDecimal trenutnaCena) {
        this.paketId = paketId;
        this.trenutnaCena = trenutnaCena;
    }

    public Integer getPaketId() {
        return paketId;
    }

    public void setPaketId(Integer paketId) {
        this.paketId = paketId;
    }

    public BigDecimal getTrenutnaCena() {
        return trenutnaCena;
    }

    public void setTrenutnaCena(BigDecimal trenutnaCena) {
        this.trenutnaCena = trenutnaCena;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paketId != null ? paketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.paketId == null && other.paketId != null) || (this.paketId != null && !this.paketId.equals(other.paketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Paket[ paketId=" + paketId + " ]";
    }
    
}
