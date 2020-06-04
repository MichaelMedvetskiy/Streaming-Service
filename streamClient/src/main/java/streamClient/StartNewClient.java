package streamClient;

import java.io.IOException;

import streamClient.ClientRest;

public class StartNewClient {

	public static void main(String[] args) {
		try {
			ClientRest client = new ClientRest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
