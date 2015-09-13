import nu.xom.ParsingException;
import org.jfugue.integration.MusicXmlParser_J;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by asanyal on 9/12/15.
 * <p/>
 * JFugue playback class
 */
public class JFuguePlayback {

    // An instance of the music xml file
    private File musicXMLFile;

    /**
     * Default constructor
     *
     * @param sheetLocation
     */
    public JFuguePlayback(final String sheetLocation) {
        musicXMLFile = new File(sheetLocation);
    }

    /**
     * Playback method, takes an input argument music xml format and plays
     * the file using jfugue playback
     *
     * @throws ParserConfigurationException
     * @throws ParsingException
     * @throws IOException
     */
    public void play() throws ParserConfigurationException, ParsingException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(false);
        dbf.setFeature("http://xml.org/sax/features/namespaces", false);
        dbf.setFeature("http://xml.org/sax/features/validation", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        MusicXmlParser_J parser_j = new MusicXmlParser_J();
        StaccatoParserListener listener = new StaccatoParserListener();
        parser_j.addParserListener(listener);
        parser_j.parse(musicXMLFile);
        Player player = new Player();
        final Pattern musicXMLPattern = listener.getPattern();
        player.play(musicXMLPattern);
    }
}
