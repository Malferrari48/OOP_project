package autenticazione;

import tabella.TabellaSpedizioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *  <p>Title: AutenticazioneAmministratore</p>
 *  <p>Description: Schermata per l'autenticazione dell'amministratore.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class AutenticazioneAmministratore extends JFrame implements ActionListener {

    private JTextField utente;
    private JPasswordField passwd;
    private JButton invio;

    /**
     * Costruttore della classe che crea il frame "Amministratore" contenente il campo username e password da completare correttamente per accedere, un bottone per loggare e uno per tornare al frame precendente.
     */
    public AutenticazioneAmministratore() {
        super("Amministratore");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JFrame frame = this;

        utente = new JTextField("Nome utente",25);
        passwd = new JPasswordField("Password",25);
        invio = new JButton("Invio");
        JButton indietro = new JButton("Indietro");

        JPanel pannello1 = new JPanel();
        pannello1.add(utente);
        this.add(pannello1, BorderLayout.NORTH);

        JPanel pannello2 = new JPanel();
        pannello2.add(passwd);
        this.add(pannello2, BorderLayout.CENTER);

        JPanel pannello3 = new JPanel();
        pannello3.add(invio);
        pannello3.add(indietro);
        this.add(pannello3, BorderLayout.SOUTH);

        indietro.addActionListener(new ActionListener() {
            /**
             * Una volta premuto il JButton "Indietro", si tornerà al frame precedente (UtenteEntrante) e si chiuderà questo frame
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                UtenteEntrante ue = new UtenteEntrante();
                ue.pack();
                ue.setVisible(true);
                ue.setLocationRelativeTo(null);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        invio.addActionListener(this);
    }

    /**
     * Metodo invocato alla pressione del bottone "Invio" che fa accedere al frame contenente la tabella di tutte le spedizioni se username e password corrispondono con quelle di amministratore (username "root" e password "Zimbabwe").
     * @param actionEvent evento corrispondente alla pressione del bottone "Invio".
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String username = "root"; // Usernmame dell'amministratore
        String password = "Zimbabwe"; // Password dell'amministratore
        String passwordDigitata = new String(passwd.getPassword());
        if(utente.getText().equals(username)) {
            if(passwordDigitata.equals(password)) {
                this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                TabellaSpedizioni ts = new TabellaSpedizioni("Admin","root");
                ts.pack();
                ts.setVisible(true);
                ts.setLocationRelativeTo(null);
                invio.setEnabled(false);
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
            else
                JOptionPane.showMessageDialog(null, "Sbagliata la password");
        }
        else
            JOptionPane.showMessageDialog(null, "Utente e/o password errati");
    }
}
