package util.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class Subsystem1ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    private static final List<String> VALID_POL_VALUES = Arrays.asList("MUSKI", "ZENSKI", "DRUGO");

    // Mesto validation
    public Response validateMestoData(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            return Response.status(400).entity("Mesto naziv is required").build();
        }

        if (naziv.length() > 100) {
            return Response.status(400).entity("Mesto naziv must be less than 100 characters").build();
        }

        return null;
    }

    // Korisnik validation
    public Response validateKorisnikData(String ime, String email, Object godisteObj, String pol, Object mestoIdObj) {
        // Basic null/empty checks
        if (ime == null || ime.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik ime is required").build();
        }
        if (email == null || email.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik email is required").build();
        }
        if (pol == null || pol.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik pol is required").build();
        }
        if (godisteObj == null || mestoIdObj == null) {
            return Response.status(400).entity("Korisnik godiste and mestoId are required").build();
        }

        // Length checks
        if (ime.length() > 100) {
            return Response.status(400).entity("Ime must be less than 100 characters").build();
        }

        if (email.length() > 100) {
            return Response.status(400).entity("Email must be less than 100 characters").build();
        }

        // Email format check
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return Response.status(400).entity("Korisnik email format is invalid").build();
        }

        // Pol validation
        if (!VALID_POL_VALUES.contains(pol.trim().toUpperCase())) {
            return Response.status(400).entity("Korisnik pol must be one of: " + String.join(", ", VALID_POL_VALUES)).build();
        }

        // Number validations
        try {
            int godiste = ((Number) godisteObj).intValue();
            int mestoId = ((Number) mestoIdObj).intValue();

            int currentYear = java.time.Year.now().getValue();
            if (godiste < 1900 || godiste > currentYear) {
                return Response.status(400).entity("Korisnik godiste must be between 1900 and " + currentYear).build();
            }

            if (mestoId <= 0) {
                return Response.status(400).entity("Korisnik mestoId must be a positive number").build();
            }

        } catch (Exception e) {
            return Response.status(400).entity("Korisnik godiste and mestoId must be valid numbers").build();
        }

        return null;
    }

    // Email validation for updates
    public Response validateEmailUpdate(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Response.status(400).entity("Email is required").build();
        }
        if (email.length() > 100) {
            return Response.status(400).entity("Email must be less than 100 characters").build();
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return Response.status(400).entity("Email format is invalid").build();
        }

        return null;
    }
}
