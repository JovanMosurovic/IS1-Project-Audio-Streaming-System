package entities;

import entities.AudioKategorija;
import entities.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-07-06T14:30:02")
@StaticMetamodel(AudioSnimak.class)
public class AudioSnimak_ { 

    public static volatile SingularAttribute<AudioSnimak, Korisnik> vlasnikId;
    public static volatile SingularAttribute<AudioSnimak, Integer> audioId;
    public static volatile SingularAttribute<AudioSnimak, Integer> trajanje;
    public static volatile SingularAttribute<AudioSnimak, String> naziv;
    public static volatile SingularAttribute<AudioSnimak, Date> datumVremePostavljanja;
    public static volatile ListAttribute<AudioSnimak, AudioKategorija> audioKategorijaList;

}