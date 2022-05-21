package tabella;

import autenticazione.AutenticazioneAmministratore;
import autenticazione.AutenticazioneCliente;
import spedizioni.SpedizioneNormale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

/**
 *  <p>Title: TabellaSpedizioni</p>
 *  <p>Description: Schermata contente la tabella delle spedizioni.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class TabellaSpedizioni extends JFrame implements WindowListener, ActionListener {
    private static final long serialVersionUID = 1L;

    /**
     * Vettore che conterrà le spedizioni con il quale l'oggetto opera.
     */
    private Vector<SpedizioneNormale> spedizioni = new Vector<>();
    /**
     * Stringa che corrisponde al tipo di utente che accede alla tabella (amministratore o cliente).
     */
    private String tipoUtente;

    /**
     * Costruttore della classe che crea il frame "TabellaSpedizioni" contenente la tabella con le spedizioni presenti nel file "spedizioni.dat" (l'amministratore può vederle tutte, mentre il cliente solo quelle che ha creato). Inoltre è presente un tasto per tornare alla schermata precedente e un menù popup che compare premendo il tasto destro del mouse sulla tabella.
     * Con quest'ultimo si può eliminare una spedizione se è nello stato finale, da admin, o richiedere il rimborso se si è cliente e la spedizione è assicurata e fallita.
     * @param tipoUtente stringa che corrisponde al tipo di utente che accede alla tabella (amministratore o cliente).
     * @param nome nome dell'utente che è acceduto alla tabella.
     */
    public TabellaSpedizioni(String tipoUtente, String nome) {
        super("Tabella spedizioni");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.tipoUtente = tipoUtente;

        this.setLayout(new BorderLayout());

        JButton indietro = new JButton("Indietro");
        JPanel pannelloIndietro = new JPanel();

        // Il path del file
        File f = new File("");
        File file = new File(f.getAbsolutePath() + File.separator + "src" + File.separator + "spedizioni.dat");

        // Se non c'è il file lo crea
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            spedizioni = (Vector<SpedizioneNormale>)ois.readObject();
            ois.close();
        } catch (EOFException e) { // Se il file è vuoto non fa niente
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Vector<SpedizioneNormale> spedizioniCliente = new Vector<>();
        // Se è il cliente che sta accedendo alla tabella allora l'utente può vedere solo le spedizioni create da lui
        if(tipoUtente.equals("Cliente")) {
            for(SpedizioneNormale sn : spedizioni) {
                if(sn.getNome().equals(nome))
                    spedizioniCliente.add(sn);
            }
        }

        SpedizioniTableModel stm;
        if(tipoUtente.equals("Admin"))
            stm = new SpedizioniTableModel(spedizioni,"Admin"); // Tutte le spedizioni
        else
            stm = new SpedizioniTableModel(spedizioniCliente,"Cliente"); // Solo quelle create da lui

        JTable table = new JTable(stm);

        if(tipoUtente.equals("Admin"))
            table.setDefaultRenderer(Object.class, new MonCellRenderer(spedizioni)); // Tutte le spedizioni
        else
            table.setDefaultRenderer(Object.class, new MonCellRenderer(spedizioniCliente)); // Solo quelle create da lui

        JPopupMenu menuTendina = new JPopupMenu();

        TableMouseListener tml = new TableMouseListener(table);

        // Solo l'amministratore può provare ad eliminare le spedizioni
        if(tipoUtente.equals("Admin")) {
            JMenuItem elimima = new JMenuItem("Eliminare la spedizione");

            elimima.addActionListener(new ActionListener() {
                /**
                 * Una volta premuto il JMenuItem "Eliminare la spedizione", si proverà ad eliminare la spedizione selezionata.
                 */
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    stm.elimina(tml.getRiga(), tipoUtente);
                }
            });

            menuTendina.add(elimima);
        }

        // Solo il cliente può provare a chiedere il rimborso su una determinata spedizione
        else if(tipoUtente.equals("Cliente")) {
            JMenuItem rimnborso = new JMenuItem("Richiedi rimborso");

            rimnborso.addActionListener(new ActionListener() {
                /**
                 * Una volta premuto il JMenuItem "Richiedi rimborso", si proverà a chiedere il rimborso sulla spedizione selezionata.
                 */
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    stm.richiestaRimborso(table.getSelectedRow(), tipoUtente);
                }
            });

            menuTendina.add(rimnborso);
        }

        table.setComponentPopupMenu(menuTendina);

        table.addMouseListener(tml);

        this.add(new JScrollPane(table),BorderLayout.NORTH);

        pannelloIndietro.add(indietro);
        this.add(pannelloIndietro,BorderLayout.SOUTH);

        indietro.addActionListener(this);

        this.addWindowListener(this);
    }


    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    /**
     * Metodo invocato quando la finestra sta chiudendo e salva tutte le spedizioni in "spedizioni.dat".
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        salva();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    /**
     * Metodo invocato quando viene premuto il bottone "Indietro", torna al frame della classe "AutenticazioneAmministratore".
     * @param actionEvent evento corrispondente alla pressione del bottone "Indietro".
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(tipoUtente.equals("Admin")) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            AutenticazioneAmministratore aa = new AutenticazioneAmministratore();
            aa.pack();
            aa.setVisible(true);
            aa.setLocationRelativeTo(null);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(tipoUtente.equals("Cliente")) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            AutenticazioneCliente ac = new AutenticazioneCliente();
            ac.pack();
            ac.setVisible(true);
            ac.setLocationRelativeTo(null);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Salva tutte le spedizioni in "spedizioni.dat".
     */
    private void salva() {
        // Il path del file
        File f = new File("");
        File file = new File(f.getAbsolutePath() + File.separator + "src" + File.separator + "spedizioni.dat");

        // Se non c'è il file lo crea
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Salvataggio delle spedizioni
        try {
            ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file));
            ous.writeObject(spedizioni);
            ous.flush();
            ous.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
