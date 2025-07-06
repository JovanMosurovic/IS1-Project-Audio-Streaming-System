package command;

public class Subsystem2Commands {
    // Kategorija commands
    public static final String CREATE_KATEGORIJA = "CREATE_KATEGORIJA";
    public static final String GET_ALL_KATEGORIJE = "GET_ALL_KATEGORIJE";
    
    // Audio snimak commands
    public static final String CREATE_AUDIO_SNIMAK = "CREATE_AUDIO_SNIMAK";
    public static final String UPDATE_AUDIO_SNIMAK_NAZIV = "UPDATE_AUDIO_SNIMAK_NAZIV";
    public static final String DELETE_AUDIO_SNIMAK = "DELETE_AUDIO_SNIMAK";
    public static final String GET_ALL_AUDIO_SNIMCI = "GET_ALL_AUDIO_SNIMCI";
    
    // Audio-Kategorija commands
    public static final String ADD_KATEGORIJA_TO_AUDIO = "ADD_KATEGORIJA_TO_AUDIO";
    public static final String GET_KATEGORIJE_FOR_AUDIO = "GET_KATEGORIJE_FOR_AUDIO";
    
    // Test commands
    public static final String TEST_MESSAGE = "TEST_MESSAGE";
}