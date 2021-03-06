package pokeraidbot.infrastructure;

import dataimport.GymDataImportTool;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokeraidbot.domain.gym.Gym;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class CSVGymDataReader {
    private final InputStream inputStream;
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVGymDataReader.class);
    private final String resourceName;

    public CSVGymDataReader(String resourceName) {
        try {
            this.resourceName = resourceName;
            inputStream = CSVGymDataReader.class.getResourceAsStream(resourceName);
            if (inputStream == null) {
                throw new FileNotFoundException(resourceName);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public CSVGymDataReader(InputStream inputStream) {
        Validate.notNull(inputStream, "Input stream may not be null");
        this.inputStream = inputStream;
        this.resourceName = inputStream.getClass().getSimpleName();
    }

    public Set<Gym> readAll() {
        String line;
        Set<Gym> gyms = new HashSet<>();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            while ((line = br.readLine()) != null) {
                String[] rowElements = line.split(GymDataImportTool.separator);
                if (rowElements[0].equalsIgnoreCase("ID")) {
                    // This is the header of the file, ignore
                } else {
                    try {
                        String id = rowElements[0].trim();
                        String x = rowElements[1].replaceAll("\"", "").trim();
                        String y = rowElements[2].replaceAll("\"", "").trim();
                        String name = rowElements[3].trim();
                        String image = rowElements[4].trim();
                        Gym gym = new Gym(name, id, x, y, image);
                        gyms.add(gym);
                    } catch (Throwable t) {
                        LOGGER.warn("Encountered problem for this row: " + line + " - error: " + t.getMessage());
                        throw new RuntimeException(t);
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error while trying to open gym file input stream " + resourceName + ": " + e.getMessage());
        }

        LOGGER.info("Parsed " + gyms.size() + " gyms from \"" + resourceName + "\".");

        return gyms;
    }
}
