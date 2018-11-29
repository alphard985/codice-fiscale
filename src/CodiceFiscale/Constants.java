package CodiceFiscale;

public final class Constants {

    public final static String REGEX_CODICE_FISCALE = "[A-Z]{6}[0-9L-N-P-V]{2}[A-DEHLMPR-T]{1}[0-7L-N-P-T]{1}[0-9L-N-P-V]{1}[A-Z]{1}[0-9L-N-P-V]{3}[A-Z]{1}";
    public final static String REGEX_NOMINATIVO_1 = "[B-DF-HJKL-NP-TV-Z]{3}";
    public final static String REGEX_NOMINATIVO_2 = "[B-DF-HJKL-NP-TV-Z]{2}[AEIOU]{1}";
    public final static String REGEX_NOMINATIVO_3 = "[B-DF-HJKL-NP-TV-Z]{1}[AEIOU]{2}";
    public final static String REGEX_NOMINATIVO_4 = "[AEIOU]{3}";
    public final static String REGEX_NOMINATIVO_5 = "[B-DF-HJKL-NP-TV-Z]{1}[AEIOU]{1}X";
    public final static String REGEX_NOMINATIVO_6 = "[AEIOU]{2}X";

    public final static String REGEX_ANNO_NASCITA = "[0-9LN-P-V]{2}";
    public final static String REGEX_MESE_NASCITA = "[A-DEHLMPR-T]{1}";
    public final static String REGEX_GIORNO_NASCITA = "[0-7LN-P-V]{1}[0-9LN-P-T]{1}";
    public final static String REGEX_CODICE_BELFIORE = "[A-Z]{1}[0-9LN-P-V]{3}";
    public final static String REGEX_CODICE_CONTROLLO = "[A-Z]{1}";

    public final static String FORMATO_DATA_NASCITA = "dd/MM/yyyy";
    public final static String ALFABETO = "ABCDEFGHIJKLMNOPQRSYUVWXYZ";
    public final static String ALFABETO_MESE_NASCITA = "ABCDEHLMPRST";
    public final static String ALFABETO_OMOCODIA = "LMNPQRSTUV";

    public final static String CARATTERE_SESSO_FEMMINILE = "F";
    public final static String CARATTERE_SESSO_MASCHILE = "M";

    public final static int VALORI_CARATTERE_DISPARI[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
    public final static int VALORI_CARATTERE_PARI[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};

    public final static int INIZIO_COGNOME = 0;
    public final static int FINE_COGNOME = 3;
    public final static int INIZIO_NOME = 3;
    public final static int FINE_NOME = 6;
    public final static int INIZIO_ANNO_NASCITA = 6;
    public final static int FINE_ANNO_NASCITA = 8;
    public final static int INIZIO_MESE_NASCITA = 8;
    public final static int FINE_MESE_NASCITA = 9;
    public final static int INIZIO_GIORNO_NASCITA = 9;
    public final static int FINE_GIORNO_NASCITA = 11;
    public final static int INIZIO_CODICE_BELFIORE = 11;
    public final static int FINE_CODICE_BELFIORE = 15;
    public final static int INIZIO_CODICE_CONTROLLO = 15;
    public final static int FINE_CODICE_CONTROLLO = 16;

    public final static int LUNGHEZZA_CODICE = 16;

}
