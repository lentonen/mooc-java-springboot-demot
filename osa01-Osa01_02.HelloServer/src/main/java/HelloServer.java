

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class HelloServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        Socket socket = server.accept();

        // luetaan pyyntö
        Scanner lukija = new Scanner(socket.getInputStream());
        if (lukija.toString().equals("/quit")) {
            //
        }
        
        else {
            PrintWriter kirjoittaja = new PrintWriter(socket.getOutputStream());
            File index = new File("index.html");
            BufferedReader reader = new BufferedReader(new FileReader(index));
            
            kirjoittaja.println("HTTP/1.1 200 OK");
            String line = reader.readLine();
            while (line != null) {
                kirjoittaja.println(line);
                line = reader.readLine();
            }
            kirjoittaja.flush();
            reader.close();
            kirjoittaja.close();
        }

        // suljetaan resurssit
        lukija.close();
        socket.close();
        server.close();

    }
}
