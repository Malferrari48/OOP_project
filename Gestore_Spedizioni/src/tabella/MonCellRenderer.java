package tabella;

import spedizioni.SpedizioneNormale;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;

/**
 *  <p>Title: MonCellRenderer</p>
 *  <p>Description: Classe che gestisce i colori delle righe della tabella.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class MonCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    /**
     * Vettore che conterrà le spedizioni con il quale l'oggetto opera.
     */
    private Vector<SpedizioneNormale> spedizioni;

    /**
     * Costruttore della classe che ottiene il vettore delle spedizioni con il quale deve operare.
     * @param spedizioni vettore delle spedizioni.
     */
    public MonCellRenderer( Vector<SpedizioneNormale> spedizioni) {
        this.spedizioni =  spedizioni;
    }

    /**
     * Funzione che assegna per ogni riga, corrispondente a una spedizione, un colore in base al suo stato.
     * @param riga riga della tabella, corrispondente a una spedizione.
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int riga, int colonna) {
        super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, riga, colonna);

        if (spedizioni.get(riga).getStato().equals("FALLITA")) {
            setBackground(Color.RED);
        }
        else if (spedizioni.get(riga).getStato().equals("RICEVUTA")) {
            setBackground(Color.GREEN);
        }
        else if (spedizioni.get(riga).getStato().equals("IN TRANSITO")) {
            setBackground(Color.YELLOW);
        }
        else if(spedizioni.get(riga).getStato().equals("IN PREPARAZIONE")) {
            setBackground(Color.LIGHT_GRAY);
        }
        else if(spedizioni.get(riga).getStato().equals("RIMBORSO RICHIESTO")) {
            setBackground(Color.ORANGE);
        }
        else if(spedizioni.get(riga).getStato().equals("RIMBORSO EROGATO")) {
            setBackground(Color.WHITE);
        }
        return this;
    }
}
