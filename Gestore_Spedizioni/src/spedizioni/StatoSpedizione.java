package spedizioni;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  <p>Title: StatoSpedizione</p>
 *  <p>Description: Classe che gestisce gli stati delle spedizioni.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class StatoSpedizione implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Insieme degli stati che una spedizione può assumere.
     */
    private ArrayList<String> stato = new ArrayList<>();

    /**
     * Costruttore della classe che inserisce gli stati della spedizione non assicurata.
     */
    public StatoSpedizione() {
        stato.add("IN PREPARAZIONE");
        stato.add("IN TRANSITO");
        stato.add("RICEVUTA");
        stato.add("FALLITA");
    }

    /**
     * Metodo per ottenere lo stato a parole attraverso il suo indice.
     * @param i numero corrispondente allo stato.
     * @return Stato a parole.
     */
    public String getStato(int i) {
        return stato.get(i);
    }

    /**
     * Metodo per aggiungere nuovi stati come stringa.
     * @param stringa stringa corrispondente al nuovo stato da aggiungere.
     */
    public void addStatus(String stringa) {
        stato.add(stringa);
    }
}
