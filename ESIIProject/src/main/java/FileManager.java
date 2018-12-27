import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    private String[] files;
    private static int DEFAULT_SIZE = 5;
    private int filesCount = 0;
    private String totalWords = "";

    public FileManager() {
        files = new String[DEFAULT_SIZE];
    }

    public FileManager(int files_size) {
        files = new String[files_size];
    }

    public boolean insertFile(String filePath) {

        String aux;

        try {
            aux = readFile(filePath);
        } catch (IOException ex) {
            return false;
        }

        aux = removeDigits(aux);
        files[filesCount] = aux;
        totalWords = totalWords + files[filesCount];
        filesCount++;

        System.out.println(totalWords);
        return true;
    }

    private String readFile(String filePath) throws IOException {

        String file = "";

        file = new String(Files.readAllBytes(Paths.get(filePath)));

        return file;
    }

    public String removeDigits(String texto)
    {
        texto=texto.replaceAll("[0-9]", "");
        return texto;
    }
}

