package streamServer;

import java.io.IOException;

import streamServer.RestServer;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to RST test REST WS Server. Press ENTER to shutdown.");

		try (RestServer server = RestServer.createInstance(args)) {
			server.run();
			System.in.read();
		}
		
		System.out.println("The server was shut down.");
	}
}