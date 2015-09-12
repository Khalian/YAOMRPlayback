import nu.xom.ParsingException;
import org.apache.commons.io.FileUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by asanyal on 9/12/15.
 *
 * Main runner class for yaomrplayback
 *
 * Contains the main method
 */
public class YAOMRMain {

    /**
     * Main driver method, all entry points go through here
     * @param args
     */
    public static void main(String args[]) throws ParsingException, IOException, ParserConfigurationException {

        System.out.println("Hello and welcome to yaomrplayback (Yet another Optical Music Recogntion playback App");
        System.out.println("Have you seen a piece of sheet music and wondered, what on earth is this? Use this script to play it back");
        System.out.println("YAOMRPlayback takes as input a music xml file and plays it back in a midi transcription. This gives the user an understanding of whats written");

        if (args.length < 1) {
            System.out.println("Usage : java -jar yaomrplayback sheetmusicfilelocation");
            System.exit(0);
        }

        try {
            // Audiveris sheet music file parsing
            AudiverisSheetMusicParser parser = new AudiverisSheetMusicParser(args[0]);
            parser.parseSheetMusicXML();
            final String musicXMLParsedLocation = parser.getMusicXMLParsedLocation();

            // Instantiate and playback
            JFuguePlayback playback = new JFuguePlayback(musicXMLParsedLocation);
            playback.play();
            FileUtils.forceDelete(new File(musicXMLParsedLocation));
        } catch (Exception e) {
            // All exceptions are treated the same way simply because
            System.out.println("Oops something went wrong. The error is" + e.getMessage());
        }
    }
}
