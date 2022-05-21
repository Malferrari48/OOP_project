package main;

import autenticazione.UtenteEntrante;

/**
 *  <p>Title: MainClass</p>
 *  <p>Description: Classe di partenza.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class MainClass {
    /**
     * Metodo main di partenza che crea il primo frame UtenteEntrante.
     * @param args Eventuali argomenti passati da linea di comando che però non influiranno sull'andamento del codice.
     */
    public static void main(String[] args) {
        UtenteEntrante ue = new UtenteEntrante();
        ue.setVisible(true);
        ue.pack();
        ue.setLocationRelativeTo(null);
    }
}
