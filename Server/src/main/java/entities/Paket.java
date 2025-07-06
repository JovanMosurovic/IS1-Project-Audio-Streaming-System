package entities;

import java.io.Serializable;
import java.math.BigDecimal;


public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer paketId;

    private BigDecimal trenutnaCena;

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
