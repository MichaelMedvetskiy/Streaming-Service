package streamServer;

import streamServer.IServer.WrappedList;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class StreamHandler {

	public static void startStream(String netStr) throws Exception{
		
			String media = "C:\\content\\"+ new WrappedList().items.get(Integer.parseInt(StreamHandler.strParce(netStr,1))-1);
	        String options = formatRtpStream(StreamHandler.strParce(netStr,0), Integer.parseInt(StreamHandler.strParce(netStr,2)));

	        System.out.println("Streaming '" + media + "' to '" + options + "'");

	        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
	        MediaPlayer mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();

	        mediaPlayer.media().play(media,
	           options,
	           ":no-sout-rtp-sap",
	           ":no-sout-standard-sap",
	           ":sout-all",
	           ":sout-keep"
	        );

	        // Don't exit
	        Thread.currentThread().join();
		
	}

	private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }
	static String strParce(String inFromUser, int i) {
		String[] arr;
		String temp;
		String delimeter = ":";
		arr = inFromUser.split(delimeter);
		//0-IP; 1-ID; 2-Port;
		return arr[i];
	}

}
