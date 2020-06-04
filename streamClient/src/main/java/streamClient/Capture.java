package streamClient;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *This class is from Vlcj-examples - https://github.com/caprica/vlcj-examples
 */
public class Capture{

    private final JFrame frame;
    private final JPanel contentPane;
    private final Canvas canvas;
    private final MediaPlayerFactory factory;
    private final EmbeddedMediaPlayer mediaPlayer;
    private final VideoSurface videoSurface;

    public static void startStream(final String inet) {
      
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
				
                new Capture().start("rtp://" + inet);
            }
        });
    }

    public Capture() {
    	
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        contentPane = new JPanel();
        contentPane.setBackground(Color.black);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas, BorderLayout.CENTER);

        frame = new JFrame("Capture");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setSize(800, 600);

        factory = new MediaPlayerFactory();
        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();

        videoSurface = factory.videoSurfaces().newVideoSurface(canvas);

        mediaPlayer.videoSurface().set(videoSurface);
    }

    private void start(String mrl) {
        frame.setVisible(true);

        File dir = new File(System.getProperty("user.home"), "Videos");
        dir.mkdirs();

        DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fileName = dir.getAbsolutePath() + "/Capture-" + df.format(new Date()) + ".mpg";

        // Tweak the options depending on your encoding requirements and audio
        // capture device (ALSA is not likely to work on Windows of course)
        String[] options = {":sout=#transcode{vcodec=mp4v,vb=4096,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=file{dst=" + fileName + "},dst=display}", ":input-slave=alsa://hw:0,0"};

        mediaPlayer.media().play(mrl, options);
    }
}
