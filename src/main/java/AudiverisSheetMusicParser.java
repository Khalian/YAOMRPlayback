import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by asanyal on 9/12/15.
 *
 * A parser using audiveris sheet music parser
 */
public class AudiverisSheetMusicParser {

    // Sheet file location
    private String sheetFileLocation;

    // Music xml file location
    private String intermediateMusicXMLLocation;

    // Runner jar for audiveris
    private static JarRunner jarRunner;

    // String telling the location of the audiveris jar location
    private static final String AUDIVERIS_JAR_LOCATION = System.getenv("AUDIVERIS_JAR_LOCATION");

    static {
        final String audiverisJarLocation = AUDIVERIS_JAR_LOCATION + File.separator + "audiveris.jar";
        try {
            jarRunner = new JarRunner(new File(audiverisJarLocation));
        } catch (ClassNotFoundException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Default constructor
     * @param sheetFileLocation
     */
    public AudiverisSheetMusicParser(final String sheetFileLocation) {
        this.sheetFileLocation = sheetFileLocation;
        intermediateMusicXMLLocation = FileUtils.getTempDirectoryPath() + File.separator
                + RandomStringUtils.randomAlphabetic(7) +FilenameUtils.getBaseName(sheetFileLocation) + ".xml";
    }

    /**
     * Method to parse sheet music xml file
     */
    public void parseSheetMusicXML() {
        try {
            jarRunner.run(new String[]{"-batch", "-input", sheetFileLocation, "-export", intermediateMusicXMLLocation});
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Music xml file location, parsed by audiveris
     *
     * @return
     */
    public String getMusicXMLParsedLocation() {
        return intermediateMusicXMLLocation;
    }
}
