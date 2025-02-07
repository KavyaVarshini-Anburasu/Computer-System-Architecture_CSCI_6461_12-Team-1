import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileController {

    /**
 * Reads the content of a file line by line and stores it in an ArrayList for return.
 * @param filePath The path of the file to be read.
 * @param removeComment If true, comments will be removed from each line.
 * @return An ArrayList containing the contents of the file.
 */

    public static ArrayList<String> readLinesFromFile(String filePath, boolean removeComment) {
        ArrayList<String> contentLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (removeComment) {
                    int commentIndex = currentLine.indexOf(';');
                    if (commentIndex != -1) {
                        currentLine = currentLine.substring(0, commentIndex).trim();
                        if (!currentLine.isEmpty()) {
                            contentLines.add(currentLine);
                        }
                    } else {
                        contentLines.add(currentLine.trim());
                    }
                } else {
                    contentLines.add(currentLine.trim());
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return contentLines;
    }

/**
 * Writes content to a file line by line.
 * @param filePath The path of the file where content will be written.
 * @param contentLines An ArrayList containing the lines to be written to the file.
 */
    public static void writeLinesToFile(String filePath, ArrayList<String> contentLines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : contentLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
