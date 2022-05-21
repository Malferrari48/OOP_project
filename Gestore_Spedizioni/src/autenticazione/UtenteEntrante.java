package autenticazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *  <p>Title: UtenteEntrante</p>
 *  <p>Description: Frame per chiedere all'utente se vuole entrare nella schermata di login del cliente o quella dell'amministratore.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class UtenteEntrante extends JFrame implements ActionListener{
    /**
     * Costruttore della classe che crea il frame "Tipo di utente", contenente 2 bottoni a seconda se l'utente si voglia autenticare come amministratore o come cliente.
     */
    public UtenteEntrante() {
        super("Tipo di utente");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        JLabel testo = new JLabel("Selezionare il tipo di utente:");
        JButton amministratore = new JButton("Amministratore");
        JButton cliente = new JButton("Cliente");

        JPanel pannello1 = new JPanel();
        pannello1.add(testo);
        this.add(pannello1,BorderLayout.NORTH);

        JPanel pannello2 = new JPanel();
        pannello2.add(amministratore);
        this.add(pannello2,BorderLayout.CENTER);

        JPanel pannello3 = new JPanel();
        pannello3.add(cliente);
        this.add(pannello3,BorderLayout.SOUTH);

        amministratore.addActionListener(this);
        cliente.addActionListener(this);
    }

    /**
     * Funzione chiamata quando viene premuto uno dei due bottoni, se viene premuto il tasto "Amministratore" si potrà provare ad entrare come amministratore, invece "Cliente" permette di provare ad entrare come cliente.
     * @param actionEvent evento corrispondente alla pressione di un bottone.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if(actionEvent.getActionCommand().equals("Amministratore")) {
            AutenticazioneAmministratore aa = new AutenticazioneAmministratore();
            aa.setVisible(true);
            aa.pack();
            aa.setLocationRelativeTo(null);
        }
        else if(actionEvent.getActionCommand().equals("Cliente")) {
            AutenticazioneCliente ac = new AutenticazioneCliente();
            ac.setVisible(true);
            ac.pack();
            ac.setLocationRelativeTo(null);
        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
