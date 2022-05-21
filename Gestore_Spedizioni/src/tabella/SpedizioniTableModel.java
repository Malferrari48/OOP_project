package tabella;

import spedizioni.SpedizioneAssicurata;
import spedizioni.SpedizioneNormale;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Timer;
import java.util.Vector;

/**
 *  <p>Title: SpedizioniTableModel</p>
 *  <p>Description: Classe che gestisce la tabella contenente le spedizioni.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class SpedizioniTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    /**
     * Vettore che conterrà le spedizioni con il quale l'oggetto opera.
     */
    private Vector<SpedizioneNormale> spedizioni;

    /**
     * Costruttore della classe che ottiene il vettore contenente le spedizioni con cui lavorare e, se si è entrati come amministratore, ogni 5 secondi avviene un cambio di stato ad una spedizione scelta randomicamente.
     * @param spedizioni vettore contenente le spedizioni da mostrare nella tabella.
     * @param tipoUtente stringa che corrisponde al tipo di utente che accede alla tabella (amministratore o cliente).
     */
    public SpedizioniTableModel(Vector<SpedizioneNormale> spedizioni, String tipoUtente) {
        this.spedizioni = spedizioni;
        if (tipoUtente.equals("Admin")) {
            CambioStato cambioStato = new CambioStato(this); // Estende TimerTask
            Timer timer = new Timer(); // Per generare un evento periodico
            timer.scheduleAtFixedRate(cambioStato, 5000, 5000); // Esprime la periodicità del codice di animatore
            // Periodicità = 5000ms = 5s
        }
    }

    /**
     * Restituisce il numero di righe che corrisponde al numero di spedizioni.
     * @return Numero di righe della tabella.
     */
    @Override
    public int getRowCount() {
        return spedizioni.size();
    }

    /**
     * Restituisce il numero di colonne che corrisponde alle caratteristiche da mostrare delle spedizioni (5).
     * @return Il numero di colonne della tabella (5).
     */
    @Override
    public int getColumnCount() {
        return 5;
    }

    /**
     * Restituisce per ogni posizione della tabella un valore corrispondente all'attributo, rappresentato dalle colonne, e alla spedizione, rappresentata dalle righe.
     * @param riga numero della riga (numero della spedizione all'interno del vettore).
     * @param colonna numero della colonna (numero dell'attributo di ciascuna spedizione).
     */
    @Override
    public Object getValueAt(int riga, int colonna) {
        switch (colonna) {
            case 0: return spedizioni.get(riga).getCodice();
            case 1: return spedizioni.get(riga).getDestinazione();
            case 2: return spedizioni.get(riga).getPeso();
            case 3: return spedizioni.get(riga).getData_formattata();
            case 4: return spedizioni.get(riga).getStato();
        }
        return "";
    }

    /**
     * Metodo per ottenere un'intestazione per ogni colonna della tabella.
     * @param colonna numero della colonna.
     * @return Stringa contenente il nome della colonna.
     */
    @Override
    public String getColumnName(int colonna) {
        switch (colonna) {
            case 0: return "Codice";
            case 1: return "Indirizzo";
            case 2: return "Peso";
            case 3: return "Data";
            case 4: return "Stato";
        }
        return "";
    }

    /**
     * Funzione per cambiare lo stato di una spedizione particolare in base al suo stato attuale. Se è "IN TRANSITO" in modo randomico può fallire o andare a buon fine. Per il resto se non è "FALLITA","RICEVUTA" o "RIMBORSO EROGATO" va avanti di uno step. Le modifiche vengono aggiornate nella tabella.
     * @param riga numero di riga (numero della spedizione).
     */
    public void changeStatus(int riga) {
        if(spedizioni.size()!=0) {
            SpedizioneNormale spedizione = spedizioni.get(riga);
            if (spedizione.getStato().equals("IN TRANSITO")) { // Se è nello stato "IN TRANSITO"
                int risultato = (int) (Math.random() * (2)); // Numero randomico tra 0 ("RICEVUTA") e 1 ("FALLITA")
                if (risultato == 0)
                    spedizione.nextStatus();
                else {
                    spedizione.nextStatus();
                    spedizione.nextStatus();
                }
            } else if (spedizione.getStato().equals("IN PREPARAZIONE") || spedizione.getStato().equals("RIMBORSO RICHIESTO")) // Se è "IN PREPARAZIONE" o "RIMBORSO RICHIESTO"
                spedizione.nextStatus();
            this.fireTableDataChanged();
        }
    }

    /**
     * Restituisce il vettore contente le spedizioni con il quale si lavora.
     * @return Vettore contente le spedizioni con il quale si lavora.
     */
    public Vector<SpedizioneNormale> getSpedizioni() {
        return spedizioni;
    }

    /**
     * Metodo invocato dal cliente attraverso la pressione della casella "Richiedi rimborso" che va a buon fine solo se la spedizione coinvolta è fallita e assicurata.
     * @param riga numero di riga (numero della spedizione).
     * @param tipoUtente stringa che corrisponde al tipo di utente che accede alla tabella (amministratore o cliente).
     */
    public void richiestaRimborso(int riga,String tipoUtente) {
        SpedizioneNormale spedizione = spedizioni.get(riga);
        if(tipoUtente.equals("Cliente")) {
            if (spedizione instanceof SpedizioneAssicurata && spedizione.getStato().equals("FALLITA")) {
                spedizione.nextStatus(); // "RIMBORSO RICHIESTO"
                this.fireTableDataChanged();
            } else if (!(spedizione instanceof SpedizioneAssicurata)) {
                JOptionPane.showMessageDialog(null, "La spedizione selezionata non e' assicurata");
            }
            else {
                JOptionPane.showMessageDialog(null, "Non si puo' richiedere il rimborso per una spedizione che non e' fallita");
            }
        }
    }

    /**
     * Metodo invocato dall'amministratore attraverso la pressione della casella "Eliminare la spedizione" che va a buon fine solo se la spedizione coinvolta è arrivata allo stato finale.
     * @param riga numero di riga (numero della spedizione).
     * @param tipoUtente stringa che corrisponde al tipo di utente che accede alla tabella (amministratore o cliente).
     */
    public void elimina(int riga,String tipoUtente) {
        if(tipoUtente.equals("Admin")) {
            SpedizioneNormale spedizione = spedizioni.get(riga);
            if (spedizione.getStato().equals("RICEVUTA") || spedizione.getStato().equals("RIMBORSO EROGATO") ||
                    (spedizione.getStato().equals("FALLITA") && !(spedizione instanceof SpedizioneAssicurata))) {
                spedizioni.remove(riga);
                this.fireTableDataChanged();
            }
            else if (spedizione.getStato().equals("FALLITA") && spedizione instanceof SpedizioneAssicurata)
                JOptionPane.showMessageDialog(null, "Il cliente ne puo' chiedere il rimborso");
            else if (spedizione.getStato().equals("RIMBORSO RICHIESTO"))
                JOptionPane.showMessageDialog(null, "In attesa che il rimborso venga erogato");
            else
                JOptionPane.showMessageDialog(null, "La spedizione e' ancora in corso");
        }
    }
}
