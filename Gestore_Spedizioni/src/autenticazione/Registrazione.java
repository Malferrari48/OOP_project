package autenticazione;

import componenti.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

/**
 *  <p>Title: Registrazione</p>
 *  <p>Description: Schermata per la registrazione del nuovo cliente.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class Registrazione extends JFrame implements ActionListener {
    private JTextField indirizzo;
    private JTextField username;
    private JPasswordField password;

    /**
     * Costruttore della classe che crea il frame "Registrazione" contenente i vari campi da inserire (username, password e indirizzo), un bottone per completare la registrazione e uno per andare nella schermata di autenticazione.
     */
    public Registrazione() {
        super("Registrazione");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        indirizzo = new JTextField("Indirizzo",25);
        password = new JPasswordField("Password",25);
        username = new JTextField("Nome utente",25);
        JButton registrazione = new JButton("Clicca qui per registrarti");
        JButton autenticazione = new JButton("Clicca qui per autenticarti");

        JPanel pannello1 = new JPanel();
        pannello1.add(username);
        this.add(pannello1, BorderLayout.NORTH);

        JPanel pannello2 = new JPanel();
        pannello2.add(password);
        pannello2.add(indirizzo);
        this.add(pannello2, BorderLayout.CENTER);

        JPanel pannello3 = new JPanel();
        pannello3.add(registrazione);
        pannello3.add(autenticazione);
        this.add(pannello3, BorderLayout.SOUTH);

        JFrame frame = this;

        registrazione.addActionListener(this);
        autenticazione.addActionListener(new ActionListener() {
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
     * Metodo invocato alla pressione del bottone "Clicca qui per registrarti", salva le credenziali nel file "credenziali.dat". Se lo username è già presente non è possibile completare la registrazione.
     * @param actionEvent evento corrispondende alla pressione del bottone "Clicca qui per registrarti".
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String passwordDigitata = new String(password.getPassword());

        if (passwordDigitata.equals("") || username.getText().equals("") || indirizzo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Registrazione fallita");
            return;
        }

        // Il path del file
        File f = new File("");
        File file = new File(f.getAbsolutePath() + File.separator + "src" + File.separator + "credenziali.dat");

        // Se non c'è il file lo crea
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cliente cliente;

        Vector<Cliente> clientiRegistrati = new Vector<>();

        // Se il file è vuoto lo salva di default
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        } catch (EOFException e) {
            try {
                ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file));
                clientiRegistrati.add(new Cliente(username.getText(),passwordDigitata,indirizzo.getText()));
                ous.writeObject(clientiRegistrati);
                ous.flush();
                ous.close();
                JOptionPane.showMessageDialog(null, "Registrazione completata");
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Se il file non è vuoto controlla che non ci sia già un cliente con quell'username
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            clientiRegistrati = (Vector<Cliente>) ois.readObject();
            ois.close();
            for(Object o : clientiRegistrati) {
                cliente = (Cliente) o;
                if (cliente.getUsername().equals(username.getText())) {
                    JOptionPane.showMessageDialog(null, "Questo username e' già salvato");
                    return;
                }
            }
            // Non è stata trovata nessuna corrispondenza
            ObjectOutputStream ous;
            Cliente clienteDaAggiungere = new Cliente(username.getText(),passwordDigitata,indirizzo.getText());
            clientiRegistrati.add(clienteDaAggiungere);
            ous = new ObjectOutputStream(new FileOutputStream(file));
            ous.writeObject(clientiRegistrati);
            ous.flush();
            ous.close();
            JOptionPane.showMessageDialog(null, "Registrazione completata");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
