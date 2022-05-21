package spedizioni;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *  <p>Title: SpedizioneNormale</p>
 *  <p>Description: Classe che crea un'astrazione della spedizione non assicurata.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class SpedizioneNormale implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Il codice univoco della spedizione.
     */
    private String codice;
    /**
     * Indirizzo di destinazione.
     */
    private String destinazione;
    /**
     * Peso dell'oggetto in spedizione in kilogrammi.
     */
    private int peso;
    /**
     * Data e ora di creazione della spedizione formattata.
     */
    private String data_formattata;
    /**
     * Stato della spedizione scritto in numero.
     */
    protected int stato=0;
    /**
     * Nome del cliente che ha creato la spedizione.
     */
    private String nome;
    /**
     * ArrayList contenente gli stati che può assumere la spedizione.
     */
    transient protected StatoSpedizione stati;

    /**
     * Costruttore della classe che assegna agli attributi i valori passati come parametro, fissa l'ora della crezione e genera un codice univoco.
     * @param nome username del cliente che ha creato la spedizione.
     * @param destinazione indirizzo di destinazione.
     * @param peso peso dell'oggetto in spedizione in kilogrammi.
     */
    public SpedizioneNormale(String nome, String destinazione, int peso) {
        this.destinazione = destinazione;
        this.peso = peso;

        Date data = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a");
        data_formattata = dateFormatter.format(data);

        this.nome = nome;
        this.codice = nome+"_"+data_formattata;
    }

    /**
     * Metodo per ottenere lo username del cliente che ha creato la spedizione.
     * @return Username del cliente che ha creato la spedizione.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo per ottenere il codice univoco della spedizione.
     * @return Codice univoco della spedizione
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Metodo per ottenere l'indirizzo di destinazione della spedizione.
     * @return L'indirizzo di destinazione della spedizione.
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     * Metodo per ottenere il peso dell'oggetto trasporato nella spedizione.
     * @return Il peso dell'oggetto trasporato nella spedizione.
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Metodo per ottenere la data e ora della creazione della spedizione.
     * @return Data e ora della creazione della spedizione.
     */
    public String getData_formattata() {
        return data_formattata;
    }

    /**
     * Metodo per ottenere lo stato della spedizione.
     * @return Lo stato della spedizione a parole.
     */
    public String getStato() {
        stati = new StatoSpedizione();
        return stati.getStato(stato);
    }

    /**
     * Metodo per ottenere lo stato della spedizione attraverso un numero.
     * @return Numero corrispondente allo stato della spedizione.
     */
    public int getNumeroStato() {
        return stato;
    }

    /**
     * Metodo per modificare il numero corrispondente allo stato della spedizione. Lo stato per essere modificato deve essere tra 0 e 3.
     * @param stato Numero corrispondente al nuovo stato della spedizione.
     */
    public void setStato(int stato) {
        if(this.stato>=4 || this.stato<0)
            return;
        this.stato = stato;
    }
    /**
     * Metodo per modificare lo stato e avanzare di 1 nell'ArrayList degli stati disponibili della spedizione.
     */
    public void nextStatus() {
        this.stato = stato+1;
    }
}
