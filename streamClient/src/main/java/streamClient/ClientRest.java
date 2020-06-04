package streamClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

public class ClientRest {
	
	public static boolean isConnected = true;

	public ClientRest() throws IOException {
		
		String input;
		
			final ClientConfig config = new ClientConfig();
			final Client client = ClientBuilder.newClient(config);
			commandHandler com = new commandHandler();
    		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    		
    		while(isConnected){
        		
        		System.out.println("Enter your command : ");
        		input = inFromUser.readLine();
        		com.checkCommand(input, client);
        		
       }
	}
}
