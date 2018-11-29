package CodiceFiscale;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Parser {

    private String codiceFiscale;
    private String codiceFiscaleNormalizzato;
    private String caratteriOmocodia;

    private Parser() {
    }

    public CodiceFiscale parse(String codiceFiscale) throws Exception {

        this.setParser();

        this.codiceFiscale = codiceFiscale.toUpperCase();

        this.controllaFormatoCodice();
        this.controllaNominativo();
        this.controllaCodiceControllo();

        boolean omocodice = this.controllaOmocodia();

        int livelloOmocodia = 0;

        if (omocodice) {

            this.codiceFiscaleNormalizzato = this.normalizzaCodiceFiscale();

            livelloOmocodia = this.calcolaLivelloOmocodia();

        } else {

            this.codiceFiscaleNormalizzato = this.codiceFiscale;

        }

        int giornoNascita = this.estraiGiornoNascita();
        int annoNascita = this.estraiAnnoNascita();
        int meseNascita = this.estraiMeseNascita();

        String sesso = this.calcolaSesso(giornoNascita);

        if (sesso.equals(Constants.CARATTERE_SESSO_FEMMINILE)) {
            giornoNascita = giornoNascita - 40;
        }

        Calendar dataNascita = this.calcolaDataNascita(giornoNascita, meseNascita, annoNascita);

        return new CodiceFiscale(this.codiceFiscale, this.codiceFiscaleNormalizzato, dataNascita, sesso, livelloOmocodia);

    }

    private void controllaNominativo() throws Exception {

        String string = this.codiceFiscale.substring(Constants.INIZIO_COGNOME, Constants.FINE_COGNOME);

        if (!string.matches(Constants.REGEX_NOMINATIVO_1)) {

            this.controllaFormatoNominativo(string);
        }
        string = this.codiceFiscale.substring(Constants.INIZIO_NOME, Constants.FINE_NOME);

        if (!string.matches(Constants.REGEX_NOMINATIVO_1)) {

            this.controllaFormatoNominativo(string);
        }

    }

    private void controllaFormatoNominativo(String string) throws Exception {

        boolean b = false;

        b = b || string.matches(Constants.REGEX_NOMINATIVO_1);
        b = b || string.matches(Constants.REGEX_NOMINATIVO_2);
        b = b || string.matches(Constants.REGEX_NOMINATIVO_3);
        b = b || string.matches(Constants.REGEX_NOMINATIVO_4);
        b = b || string.matches(Constants.REGEX_NOMINATIVO_5);
        b = b || string.matches(Constants.REGEX_NOMINATIVO_6);

        if (b == false) {
            throw new Exception("Nominativo non valido: " + string);
        }
    }

    private int calcolaLivelloOmocodia() {

        int livelloOmocodia = 0;

        for (int i = 0; i < this.caratteriOmocodia.length(); i++) {

            if (Character.isAlphabetic(this.caratteriOmocodia.charAt(i))) {
                livelloOmocodia++;
            }
        }

        return livelloOmocodia;
    }

    private String calcolaSesso(int giornoNascita) {

        String sesso;

        if (giornoNascita > 40) {
            sesso = Constants.CARATTERE_SESSO_FEMMINILE;
        } else {
            sesso = Constants.CARATTERE_SESSO_MASCHILE;

        }
        return sesso;

    }

    private Calendar calcolaDataNascita(int giornoNascita, int meseNascita, int annoNascita) throws Exception {

        String string = giornoNascita + "/" + meseNascita + "/" + annoNascita;

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMATO_DATA_NASCITA);

        sdf.setLenient(false);
        Date data = sdf.parse(string);

        Calendar dataNascita = GregorianCalendar.getInstance();
        dataNascita.setTime(data);

        return dataNascita;
    }

    private void setParser() {

        this.codiceFiscale = "";
        this.codiceFiscaleNormalizzato = "";
        this.caratteriOmocodia = "";

    }

    private int estraiAnnoNascita() {

        int annoNascita = Integer.valueOf(this.codiceFiscaleNormalizzato.substring(Constants.INIZIO_ANNO_NASCITA, Constants.FINE_ANNO_NASCITA));

        return annoNascita;
    }

    private int estraiMeseNascita() {

        String mese = this.codiceFiscale.substring(Constants.INIZIO_MESE_NASCITA, Constants.FINE_MESE_NASCITA);

        int i = Constants.ALFABETO_MESE_NASCITA.indexOf(mese) + 1;

        return i;
    }

    private void controllaCodiceControllo() throws Exception {

        String c = this.calcolaCodiceControllo(this.codiceFiscale);

        if (this.codiceFiscale.substring(Constants.INIZIO_CODICE_CONTROLLO, Constants.FINE_CODICE_CONTROLLO).equals(c) == false) {
            throw new Exception("Carattere di controllo non valido, carattere di controllo previsto: " + c);
        }
    }

    private String calcolaCodiceControllo(String codiceFiscale) {

        int somma = this.sommaValoreCaratteri(codiceFiscale);

        int i = somma % 26;

        char c = Constants.ALFABETO.charAt(i);

        return String.valueOf(c);
    }

    private int sommaValoreCaratteri(String codiceFiscale) {

        int somma = 0;

        for (int i = 0; i < Constants.LUNGHEZZA_CODICE - 1; i++) {

            char c = codiceFiscale.charAt(i);
            int p = Character.getNumericValue(c);

            if (i % 2 == 0) {

                somma = somma + Constants.VALORI_CARATTERE_DISPARI[p];

            } else {

                somma = somma + Constants.VALORI_CARATTERE_PARI[p];

            }
        }

        return somma;

    }

    private String normalizzaCodiceFiscale() {

        String caratteriOmocodiaNormalizzati = this.normalizzaCaratteriOmocodia();

        String string = "";

        string = string + this.codiceFiscale.substring(Constants.INIZIO_COGNOME, Constants.FINE_NOME);
        string = string + caratteriOmocodiaNormalizzati.substring(0, 2);
        string = string + this.codiceFiscale.substring(Constants.INIZIO_MESE_NASCITA, Constants.FINE_MESE_NASCITA);
        string = string + caratteriOmocodiaNormalizzati.substring(2, 4);
        string = string + this.codiceFiscale.substring(Constants.INIZIO_CODICE_BELFIORE, Constants.INIZIO_CODICE_BELFIORE + 1);
        string = string + caratteriOmocodiaNormalizzati.substring(4, 7);

        String carattereControllo = this.calcolaCodiceControllo(string);

        string = string + carattereControllo;

        return string;
    }

    private String normalizzaCaratteriOmocodia() {

        String string = this.caratteriOmocodia;

        for (int i = 0; i < string.length(); i++) {

            char c = string.charAt(i);

            if (Character.isAlphabetic(c)) {

                char v = Character.forDigit(Constants.ALFABETO_OMOCODIA.indexOf(c), Character.MAX_RADIX);

                string = string.replace(c, v);
            }
        }

        return string;
    }

    private boolean controllaOmocodia() throws Exception {

        String string = this.estraiCaratteriOmocodia();

        if (!string.matches("[0-9]{7}")) {

            if (this.controllaValiditaOmocodia(string)) {

                this.caratteriOmocodia = string;
                return true;
            }
        }
        return false;
    }

    private String estraiCaratteriOmocodia() {

        String string;

        string = this.codiceFiscale.substring(Constants.INIZIO_ANNO_NASCITA, Constants.FINE_ANNO_NASCITA);
        string = string + this.codiceFiscale.substring(Constants.INIZIO_GIORNO_NASCITA, Constants.FINE_GIORNO_NASCITA);
        string = string + this.codiceFiscale.substring(Constants.INIZIO_CODICE_BELFIORE + 1, Constants.FINE_CODICE_BELFIORE);

        return string;
    }

    private void controllaFormatoCodice() throws Exception {

        if (this.codiceFiscale.matches(Constants.REGEX_CODICE_FISCALE) == false) {

            throw new Exception("Formato codice fiscale non valido");
        }

    }

    private int estraiGiornoNascita() {

        int giornoNascita = Integer.valueOf(this.codiceFiscaleNormalizzato.substring(Constants.INIZIO_GIORNO_NASCITA, Constants.FINE_GIORNO_NASCITA));

        return giornoNascita;

    }

    private boolean controllaValiditaOmocodia(String caratteriOmocodia) throws Exception {

        while (Character.isDigit(caratteriOmocodia.charAt(0))) {

            caratteriOmocodia = caratteriOmocodia.substring(1);
        }

        if (!caratteriOmocodia.matches("[^\\d]+")) {

            throw new Exception("Formato omocodice non valido");

        }

        return true;
    }

    public static Parser getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final Parser INSTANCE = new Parser();

    }
}
