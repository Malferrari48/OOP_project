package tabella;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

/**
 *  <p>Title: TableMouseListener</p>
 *  <p>Description: Classe che gestisce la pressione del tasto destro del mouse sulla tabella.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class TableMouseListener extends MouseAdapter {

    /**
     * Tabella che diventerà l'oggetto JTable contenente le spedizioni sul quale deve operare.
     */
    private JTable tabella;
    private int riga;

    /**
     * Costruttore della classe che ottiene la tabella su cui deve lavorare.
     * @param tabella tabella contente le spedizioni.
     */
    public TableMouseListener(JTable tabella) {
        this.tabella = tabella;
    }

    /**
     * Metodo invocato per ogni pressione del tasto destro del mouse sulla tabella, ottiene la riga che è stata premuta.
     * @param mouseEvent evento che corrisponde alla pressione del tasto destro del mouse sulla tabella.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point punto = mouseEvent.getPoint();
        riga = tabella.rowAtPoint(punto); // Riga della tabella dove è stato premuto il tasto destro del mouse
        tabella.setRowSelectionInterval(riga,riga);
    }

    /**
     * Metodo che restituisce il valore dell'attributo "riga".
     * @return valore della variabile "riga".
     */
    public int getRiga() {
        return riga;
    }
}