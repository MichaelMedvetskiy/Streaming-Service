package streamClient;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


public class commandHandler {
	static String response;

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class UserInfo {
	String login;
	String password;
	}
public static class WrappedList{
		
		public List<String> items;
		
		public WrappedList(){}
		
		public WrappedList(List<String> items){
			this.items = items;
		}
		
		public WrappedList(String[] items){
			this.items = new ArrayList<>(Arrays.asList(items));
		}
		@Override
		public String toString() {
			System.out.println("Available content:");
			
			for(int i = 0; i < items.size(); i++) {
				System.out.print("ID = " + (i+1));
				System.out.print("       " + items.get(i));
				System.out.println();
			}
			return "";
			
		}
	}

public void checkCommand(String input, Client client) {
		
	
		switch(commandHandler.getCommandID(input)) {
		case ("exit"):
			client.close();
			ClientRest.isConnected = false;
			System.out.println("Client stopped");break;
		case ("ping"): 
			try {
			String responsePing = client.target("http://localhost:8080/chat/server/ping") 
					.request(MediaType.TEXT_PLAIN_TYPE)
					.get(String.class);
			System.out.println(responsePing);break;
			} catch (Exception e) {
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
			}
		case ("echo"):
			try {
			String	responseEcho = client.target("http://localhost:8080/chat/server/echo") 
					.request(MediaType.TEXT_PLAIN_TYPE) 
					.post(Entity.text(commandHandler.getContentId(input)), String.class);
			System.out.println(responseEcho);break;
			} catch (Exception e) {
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
			}
		case ("login"):
			try {
			UserInfo userInfo = new UserInfo();
			userInfo.login = commandHandler.getLog(input);
			userInfo.password = commandHandler.getPass(input);
			Entity userInfoEntity = Entity.entity(userInfo, MediaType.APPLICATION_JSON_TYPE);
			WebTarget myResource = client.target("http://localhost:8080/chat/server/user");
			Response response = myResource.request(MediaType.TEXT_PLAIN)
			        .put(userInfoEntity);
			client.register(HttpAuthenticationFeature.basic(userInfo.login, userInfo.password));
			if (response.getStatus() == Status.CREATED.getStatusCode()) { 
				System.out.println("New user registered");
			}
			else System.out.println("Welcome back, " + userInfo.login);break;
			} catch (Exception e) {
				if(e.getMessage().contains("out of bounds")) { System.out.println("Check your Login and Pass");break;}
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
			}
		case ("stream"):
			try {
				InetAddress ip;
				ip = InetAddress.getLocalHost();
				String strIp = ip.getHostAddress();
				String	responseStream = client.target("http://localhost:8080/chat/server/stream") 
						.request(MediaType.TEXT_PLAIN_TYPE) 
						.post(Entity.text(commandHandler.addStr(strIp, commandHandler.getContentId(input),commandHandler.getPort(input))), String.class);
				
				Capture.startStream(responseStream);
				System.out.println("Capturing stream");break;
			} catch (Exception e) {
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
				if(e.getMessage().equals("HTTP 401 Unauthorized")) { System.out.println("Login first!");break;}
				if(e.getMessage().equals("HTTP 204 No Content")) { System.out.println("There is no content");break;}
			}
		case ("list"):
			try {
			WrappedList users = client.target("http://localhost:8080/chat/server/content") 
					.request(MediaType.APPLICATION_JSON_TYPE) .get(WrappedList.class);
			users.toString();break;
			} catch (Exception e) {
				
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
				if(e.getMessage().equals("HTTP 401 Unauthorized")) { System.out.println("Login first!");break;}
				if(e.getMessage().equals("HTTP 204 No Content")) {System.out.println("There is no content");break;}
			}
		//This case made to test server for bigger than 1 client
		//Use random func to generate need amount Id content and doit in loop
		case ("test"):
			try {
				InetAddress ip;
				ip = InetAddress.getLocalHost();
				String strIp = ip.getHostAddress();
				String	responseStream = client.target("http://localhost:8080/chat/server/stream") 
						.request(MediaType.TEXT_PLAIN_TYPE) 
						.post(Entity.text(commandHandler.addStr(strIp, commandHandler.getContentId(input),commandHandler.getPort(input))), String.class);
				
				Capture.startStream(responseStream);
				System.out.println("Capturing stream");break;
			} catch (Exception e) {
				if(e.getMessage().contains("Connection refused")) { System.out.println("Could not conncet to server");break;}
				if(e.getMessage().equals("HTTP 401 Unauthorized")) { System.out.println("Login first!");break;}
				if(e.getMessage().equals("HTTP 204 No Content")) { System.out.println("There is no content");break;}
			}
		default: 
			System.out.println("Wrong command");
	}
	
}

private static String getPass(String input) {
	String[] logPass;
	String temp;
    String delimeter = " "; 
    temp = input.substring(input.indexOf(" "), input.length()).trim();
    logPass = temp.split(delimeter);
    
    return logPass[1];
}
private static String getLog(String input) {
	String[] logPass;
	String temp;
    String delimeter = " "; 
    temp = input.substring(input.indexOf(" "), input.length()).trim();
    logPass = temp.split(delimeter);
    
    return logPass[0];
}
public static String getCommandID(String inFromUser) {
	String[] subStr;
    String delimeter = " "; 
    subStr = inFromUser.split(delimeter); 
    
    return subStr[0];
}
public static String getPort(String inFromUser) {
	String[] subStr;
    String delimeter = " "; 
    subStr = inFromUser.split(delimeter);
    
    return subStr[1];
	}
public static String getContentId(String inFromUser) {
	String[] subStr;
    String delimeter = " "; 
    subStr = inFromUser.split(delimeter);
    
    return subStr[2];
	}
public static String addStr(String ip ,String port, String id) {
	return ip+":"+port+":"+id;
}

}
