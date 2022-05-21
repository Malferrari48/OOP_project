package tabella;

import spedizioni.SpedizioneNormale;

import java.util.TimerTask;

/**
 *  <p>Title: CambioStato</p>
 *  <p>Description: Classe che gestisce il cambio degli stati periodicamente.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class CambioStato extends TimerTask {

    private SpedizioniTableModel stm;

    /**
     * Costruttore della classe che ottiene l'oggetto di "SpedizioniTableModel" sul quale deve operare.
     * @param stm oggetto di "SpedizioniTableModel".
     */
    public CambioStato(SpedizioniTableModel stm) {
        this.stm = stm;
    }

    /**
     * Metodo invocato periodicamente per regolare l'avanzamento dello stato di una spedizione scelta randomicamente. Non procede con l'operazione se non c'è nessuna spedizione o se si è nello stato "RICEVUTA", "FALLITA" o "RIMBORSO EROGATO".
     */
    @Override
    public void run() {
        int riga = 0;
        SpedizioneNormale spedizione = null;
        do {
            if(stm.getSpedizioni().size()!=0) {
                riga = (int) (Math.random() * (stm.getSpedizioni().size())); // Numero randomico tra 0 e la lunghezza del vettore-1
                spedizione = stm.getSpedizioni().get(riga);
            }
        } while(stm.getSpedizioni().size()==0 || spedizione.getStato().equals("RICEVUTA") || spedizione.getStato().equals("FALLITA") || spedizione.getStato().equals("RIMBORSO EROGATO"));
        stm.changeStatus(riga);
    }
}
