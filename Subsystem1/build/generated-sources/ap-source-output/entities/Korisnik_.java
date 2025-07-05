package entities;

import entities.Mesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-07-05T18:01:50")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Integer> godiste;
    public static volatile SingularAttribute<Korisnik, Mesto> mestoId;
    public static volatile SingularAttribute<Korisnik, Integer> korisnikId;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile SingularAttribute<Korisnik, String> email;

}