package spedizioni;

import autenticazione.AutenticazioneCliente;
import tabella.TabellaSpedizioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

/**
 *  <p>Title: CreazioneSpedizione</p>
 *  <p>Description: Classe che permette al cliente loggato di creare spedizioni.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class CreazioneSpedizione extends JFrame implements ActionListener {
    /**
     * Indirizzo di destinazione.
     */
    private JTextField destinazione;
    /**
     * Peso dell'oggetto in spedizione in kilogrammi.
     */
    private JTextField peso;
    /**
     * Da selezionare se la spedizione da creare deve essere assicurata.
     */
    private JCheckBox assicurata;
    /**
     * Da riempire con un valore numerico nel caso in cui la checkbox "assicurata" sia selezionato.
     */
    private JTextField valoreAssicurato;

    /**
     * Username dell'utente loggato.
     */
    private String nomeUtente;

    /**
     * Costruttore della classe che crea il frame "Creazione spedizione" contenente i campi per l'inserimento delle caratteristiche di una spedizione (destinazione, peso in Kg e il valore assicurato nel caso in cui la spedizione sia assicurata), un bottone per la conferma e uno per vedere la tabella contenente altre spedizioni create dallo stesso utente.
     * @param nomeUtente username dell'utente loggato.
     */
    public CreazioneSpedizione(String nomeUtente) {
        super("Creazione spedizione");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.nomeUtente = nomeUtente;

        destinazione = new JTextField("Destinazione",25);
        peso = new JTextField("Peso in Kg",25);
        assicurata = new JCheckBox("Assicurata?");
        valoreAssicurato = new JTextField("Valore assicurato in euro");
        valoreAssicurato.setEditable(false);
        JButton invio = new JButton("Invio");
        JButton tabella = new JButton("Tabella spedizioni");
        JButton indietro = new JButton("Indietro");

        JPanel pannello1 = new JPanel();
        pannello1.add(destinazione);
        pannello1.add(peso);
        this.add(pannello1, BorderLayout.NORTH);

        JPanel pannello2 = new JPanel();
        pannello2.add(assicurata);
        pannello2.add(valoreAssicurato);
        this.add(pannello2,BorderLayout.CENTER);

        JPanel pannello3 = new JPanel();
        pannello3.add(invio);
        pannello3.add(tabella);
        pannello3.add(indietro);
        this.add(pannello3,BorderLayout.SOUTH);

        JFrame frame = this;

        assicurata.addActionListener(new ActionListener() {
            /**
             * Una volta selezionato il JCheckBox "Assicurata?", la JTextField valoreAssicurato sarà editabile.
             * Una volta deselezionato,  valoreAssicurato non sarà editabile.
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(assicurata.isSelected())
                    valoreAssicurato.setEditable(true);
                else {
                    valoreAssicurato.setEditable(false);
                    valoreAssicurato.setText("Valore assicurato");
                }
            }
        });
        invio.addActionListener(this);
        tabella.addActionListener(new ActionListener() {
            /**
             * Una volta premuto il JButton "Tabella spedizioni" si aprirà il frame TabellaSpedizioni e questo si chiuderà.
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                TabellaSpedizioni ts = new TabellaSpedizioni("Cliente",nomeUtente);
                ts.pack();
                ts.setVisible(true);
                ts.setLocationRelativeTo(null);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        indietro.addActionListener(new ActionListener() {
            /**
             * Una volta premuto il JButton "Indietro", si aprirà il frame precedente (AutenticazioneCliente) e si chiuderà questo frame
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                AutenticazioneCliente ac = new AutenticazioneCliente();
                ac.pack();
                ac.setVisible(true);
                ac.setLocationRelativeTo(null);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    /**
     * Metodo per effettuare il salvataggio della spedizione creata nel file "spedizioni.dat".
     * @param sn la spedizione appena creata.
     */
    private void RegistrazioneSpedizione(SpedizioneNormale sn) {

        // Il path del file
        File f = new File("");
        File file = new File(f.getAbsolutePath() + File.separator + "src" + File.separator + "spedizioni.dat");

        // Se non c'è il file lo crea
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<SpedizioneNormale> spedizioni = new Vector<>();

        // Se il file è vuoto lo salva di default
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        } catch (EOFException e) {
            try {
                ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file));
                spedizioni.add(sn);
                ous.writeObject(spedizioni);
                ous.flush();
                ous.close();
                JOptionPane.showMessageDialog(null, "Salvataggio completato");
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Salvataggio con file non vuoto
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            spedizioni = (Vector<SpedizioneNormale>)ois.readObject();
            ois.close();
            spedizioni.add(sn);
            ObjectOutputStream ous;
            ous = new ObjectOutputStream(new FileOutputStream(file));
            ous.writeObject(spedizioni);
            ous.flush();
            ous.close();
            JOptionPane.showMessageDialog(null, "Salvataggio completato");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo invocato dalla pressione del bottone "Invio" che controlla se i dati inseriti sono in un range accettabile e, se risulta positivo, vengono salvate nel file "spedizioni.dat".
     * @param actionEvent evento corrispondente alla pressione del bottone "Invio".
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            int pesoInt = Integer.parseInt(peso.getText());
            if(pesoInt < 0) {
                JOptionPane.showMessageDialog(null, "I dati non sono stati inseriti correttamente");
                return;
            }
            SpedizioneNormale spedizione;
            if(assicurata.isSelected()) {
                int valoreAssicuratoInt = Integer.parseInt(valoreAssicurato.getText());
                if(valoreAssicuratoInt < 0) {
                    JOptionPane.showMessageDialog(null, "I dati non sono stati inseriti correttamente");
                    return;
                }
                spedizione = new SpedizioneAssicurata(nomeUtente,destinazione.getText(),pesoInt,valoreAssicuratoInt);
            }
            else {
                spedizione = new SpedizioneNormale(nomeUtente,destinazione.getText(),pesoInt);
            }
            RegistrazioneSpedizione(spedizione);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "I dati non sono stati inseriti correttamente");
        }
    }
}
