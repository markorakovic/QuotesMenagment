package auxServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuxServer {
    private static final int PORT = 9000;
    private static final String QUOTE_1 = "If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you. — Steve Jobs";
    private static final String QUOTE_2 = "Success is getting what you want, happiness is wanting what you get. ―W. P. Kinsella";
    private static final String QUOTE_3 = "Believe you can and you're halfway there. - Theodore Roosevelt";
    private static final String QUOTE_4 = "We cannot solve problems with the kind of thinking we employed when we came up with them. — Albert Einstein";
    private static final List<String> quotes = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize the list of quotes
        quotes.add(QUOTE_1);
        quotes.add(QUOTE_2);
        quotes.add(QUOTE_3);
        quotes.add(QUOTE_4);


        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Wait for a client to connect
                System.out.println("Waiting for a client to connect...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Handle the client request
                handleClientRequest(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Greska u pokretanju servera: " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {

        // Get the input and output streams of the client socket
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send a random quote to the client
        String randomQuote = getRandomQuote();
        String jsonResponse = "{\"quote\": \"" + randomQuote + "\"}";
        System.out.println(jsonResponse);
        out.println(jsonResponse);

        // Close the streams and the client socket
        in.close();
        out.close();
        clientSocket.close();
    }

    private static String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size());
        return quotes.get(index);
    }
}