
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HelloRedirectLoop {

    public static void main(String[] args) throws Exception {
                ServerSocket server = new ServerSocket(8080);
 
        int requestCounter = 1;
 
        while (true) {
            Socket req = server.accept();
            System.out.println("Request: " + requestCounter);
            requestCounter++;
 
            Scanner s = new Scanner(req.getInputStream());
 
            String p = s.nextLine();
 
            if (p.contains("quit")) {
                break;
            }
 
            PrintWriter pw = new PrintWriter(req.getOutputStream());
            pw.println("HTTP/1.1 302 Found");
            pw.println("Location: http://localhost:8080"); 
            pw.flush();
 
            pw.close();
            req.close();
            s.close();
        }

    }
}
