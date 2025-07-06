package entities;

import entities.AudioSnimak;
import entities.Kategorija;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-06T14:30:02")
@StaticMetamodel(AudioKategorija.class)
public class AudioKategorija_ { 

    public static volatile SingularAttribute<AudioKategorija, Integer> audioKategorijaId;
    public static volatile SingularAttribute<AudioKategorija, Kategorija> kategorijaId;
    public static volatile SingularAttribute<AudioKategorija, AudioSnimak> audioId;

}