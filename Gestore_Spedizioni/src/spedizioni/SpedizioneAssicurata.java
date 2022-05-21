package spedizioni;

/**
 *  <p>Title: SpedizioneAssicurata</p>
 *  <p>Description: Classe che crea un'astrazione della spedizione assicurata, è un'estensione della spedizione senza assicurazione.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class SpedizioneAssicurata extends SpedizioneNormale {
    /**
     * L’ammontare in denaro che il cliente può richiedere alla compagnia di spedizioni in caso di fallimento della spedizione.
     */
    private float valore_assicurato;

    /**
     * Costruttore della classe che assegna i valori della spedizione attraverso il costruttore della classe "SpedizioneNormale" e del valore assicurato.
     * @param nome username del cliente che ha creato la spedizione.
     * @param destinazione indirizzo di destinazione.
     * @param peso peso dell'oggetto in spedizione in kilogrammi.
     * @param valore_assicurato valore assicurato sulla spedizione.
     */
    public SpedizioneAssicurata(String nome, String destinazione, int peso, int valore_assicurato) {
        super(nome, destinazione, peso);
        this.valore_assicurato = valore_assicurato;
    }

    /**
     * Metodo per ottenere lo stato della spedizione con due stati aggiuntivi: "RIMBORSO RICHIESTO" e "RIMBORSO EROGATO".
     * @return Lo stato della spedizione a parole.
     */
    public String getStato() {
        stati = new StatoSpedizione();
        stati.addStatus("RIMBORSO RICHIESTO");
        stati.addStatus("RIMBORSO EROGATO");
        return stati.getStato(stato);
    }

    /**
     * Metodo per ottenere il valore assicurato.
     * @return Valore assicurato.
     */
    public float getValore_assicurato() {
        return valore_assicurato;
    }

    /**
     * Metodo per modificare il numero corrispondente allo stato della spedizione, se è 2 ("RICEVUTA") o 5 ("RIMBORSO EROGATO") non si può più modificare.
     * @param stato Numero corrispondente al nuovo stato della spedizione.
     */
    public void setStato(int stato) {
        if(this.stato==2 || this.stato>=5 || this.stato<0)
            return;
        this.stato = stato;
    }
}
