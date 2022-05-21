package componenti;

import java.io.Serializable;

/**
 *  <p>Title: Cliente</p>
 *  <p>Description: Classe che crea un'astrazione del cliente.</p>
 *  @author Francesco Malferrari
 *  @version 1.0
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Username del cliente
     */
    private String username;
    /**
     * Password del cliente
     */
    private String password;
    /**
     * Indirizzo del cliente
     */
    private String indirizzo;

    /**
     * Costruttore della classe che assegna agli attributi i valori passati
     * @param username Nickname identificativo
     * @param password Password per entrare scritto al momento della registrazione
     * @param indirizzo Indirizzo scritto al momento della registrazione
     */
    public Cliente(String username, String password, String indirizzo) {
        this.username = username;
        this.password = password;
        this.indirizzo = indirizzo;
    }

    /**
     * Metodo per ottenere lo username del cliente
     * @return Lo username del cliente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Metodo per ottenere la password del cliente
     * @return La password del cliente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metodo per ottenere l'indirizzo del cliente
     * @return L'indirizzo del cliente
     */
    public String getIndirizzo() {
        return indirizzo;
    }
}
