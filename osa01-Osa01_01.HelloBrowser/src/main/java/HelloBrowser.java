
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HelloBrowser {

    public static void main(String[] args) throws Exception {
        
        //try{
            // Käyttäjän syöte
            Scanner input = new Scanner(System.in);
            System.out.println("Kirjoita osoite:");
            String osoite = input.nextLine();

            // Muodostetaan yhteys palvelimeen
            Socket socket = new Socket(osoite, 80);

            // Lähetetään viesti palvelimelle
            PrintWriter kirjoittaja = new PrintWriter(socket.getOutputStream());
            kirjoittaja.println("GET / HTTP/1.1");
            kirjoittaja.println("Host: " + osoite);
            kirjoittaja.println();
            kirjoittaja.flush();

            // Luetaan vastaus
            Scanner lukija = new Scanner(socket.getInputStream());
            while (lukija.hasNextLine()) {
                System.out.println(lukija.nextLine());
            }
        }
        //catch(java.net.UnknownHostException e) {
        //    System.out.println("Palvelinta ei löydy");
        //}
        //catch(Exception e) {
        //    System.out.println("Jotain muuta häikkää");
        //}
    //}
}
