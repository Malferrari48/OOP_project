package autenticazione;

import componenti.Cliente;
import spedizioni.CreazioneSpedizione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

/**
 *  <p>Title: AutenticazioneCliente</p>
 *  <p>Description: Schermata di login del cliente.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class AutenticazioneCliente extends JFrame implements ActionListener{

    private JTextField utente;
    private JPasswordField passwd;

    /**
     * Costruttore della classe che crea il frame "Login del cliente" con i vari campi per entrare (username e password), un bottone per confermare, uno per tornare indietro e uno per andare alla schermata di registrazione.
     */
    public AutenticazioneCliente() {
        super("Login cliente");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        utente = new JTextField("Nome utente",25);
        passwd = new JPasswordField("Password",25);
        JButton invio = new JButton("Invio");
        JButton registrarsi = new JButton("Clicca qui per registrarti");
        JButton indietro = new JButton("Indietro");

        JPanel pannello1 = new JPanel();
        pannello1.add(utente);
        this.add(pannello1, BorderLayout.NORTH);

        JPanel pannello2 = new JPanel();
        pannello2.add(passwd);
        this.add(pannello2, BorderLayout.CENTER);

        JPanel pannello3 = new JPanel();
        pannello3.add(invio);
        pannello3.add(registrarsi);
        pannello3.add(indietro);
        this.add(pannello3, BorderLayout.SOUTH);

        JFrame frame = this;

        registrarsi.addActionListener(new ActionListener() {
            /**
             * Una volta premuto il JButton "Clicca qui per registrarti", si aprirà il frame Registrazione e si chiuderà questo frame
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                Registrazione registrazione = new Registrazione();
                registrazione.pack();
                registrazione.setVisible(true);
                registrazione.setLocationRelativeTo(null);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        indietro.addActionListener(new ActionListener() {
            /**
             * Una volta premuto il JButton "Indietro", si aprirà il frame precedente (UtenteEntrante) e si chiuderà questo frame
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
     * Metodo chiamato alla pressione del bottone "Invio" e controlla se le credenziali inserite sono presenti nel file "credenziali.dat", se corrisponde il cliente può creare o vedere le proprie spedizioni.
     * @param actionEvent evento corrispondente alla pressione del tasto "Invio".
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Il path del file
        File f = new File("");
        File file = new File(f.getAbsolutePath() + File.separator + "src" + File.separator + "credenziali.dat");

        // Se non c'è il file lo crea
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Se il file è vuoto non fa niente
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        } catch (EOFException e) {
            JOptionPane.showMessageDialog(null, "Queste credenziali non sono salvate");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Se il file esiste stabilisce se sono salvate o no le credenziali
        Cliente cliente;
        Vector<Cliente> clientiRegistrati = new Vector<>();
        Cliente clienteTrovato = null;

        String passwordDigitata = new String(passwd.getPassword());

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            clientiRegistrati = (Vector<Cliente>) ois.readObject();
            ois.close();
            for (Object o : clientiRegistrati) {
                cliente = (Cliente) o;
                if (cliente.getUsername().equals(utente.getText())) {
                    if (cliente.getPassword().equals(passwordDigitata)) {
                        clienteTrovato = cliente;
                        break;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Sbagliata la password");
                        return;
                    }
                }
            }
        }
        catch(EOFException e) {
            JOptionPane.showMessageDialog(null, "Queste credenziali non sono salvate");
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Queste credenziali non sono salvate");
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if(clienteTrovato==null)
            JOptionPane.showMessageDialog(null, "Utente e/o password errati");
        else {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            CreazioneSpedizione cs = new CreazioneSpedizione(clienteTrovato.getUsername());
            cs.setVisible(true);
            cs.pack();
            cs.setLocationRelativeTo(null);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
