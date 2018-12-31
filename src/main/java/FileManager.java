import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileManager {

    private String[] files;
    private static int DEFAULT_SIZE = 5;
    private int filesCount = 0;
    private String totalWords ="";

    public FileManager() {
        files = new String[DEFAULT_SIZE];
    }

    public FileManager(int files_size){
        files = new String[files_size];
    }

    public boolean insertFile(String filePath){

        String aux;

        try {
            aux = readFile(filePath);
        }catch (IOException ex){
            return false;
        }

        aux = removeDigits(aux);
		aux = removeChars(aux);
		aux = aux.toLowerCase();
        files[filesCount] = aux;
        totalWords = totalWords  + files[filesCount] + " ";
        filesCount++;

        return  true;
    }

    private String readFile(String filePath) throws IOException {

        String file = "";

        file = new String(Files.readAllBytes(Paths.get(filePath)));

        return file;
    }
    public String removeDigits(String texto){
        texto=texto.replaceAll("[0-9]","");
        return texto;
    }

	public String removeChars(String texto){
		texto=texto.replaceAll("[\"\\,\\.\\?\\!\\|\\[\\]\\{\\}\\/\\;\\:\\«\\»\\<\\>\\@\\£\\€\\§\\#\\$\\%\\&\\=\\)\\(\\*\\+\\~\\^\\_\\-]","");
		return texto;
		
	}

        //Método para limpar palavras repetidas de uma string
	public String[] uniqueWords(String texto){
        String[] aux=texto.split(" ");
        if(aux.length<2)
            return aux;
        Set<String> unique=new LinkedHashSet<String>();
        for(String i : aux){
            if(!unique.contains(i)) {
                unique.add(i);
            }
        }
        Iterator itr=unique.iterator();

        String[] aux2=unique.toArray(new String[0]);
            return aux2;
    }
    public int[][] matrizOrganizer(String[] uniqueWords){
        int numeroDoc=filesCount,numeroPalavras=uniqueWords.length,count,h;
        String[] aux;

        int[][] matrizM=new int[numeroDoc][numeroPalavras];
        for(int i=0;i<numeroDoc;i++){
            aux=files[i].split(" ");
            for(int j=0;j<numeroPalavras;j++){
                count=0;
                h=0;
                while(h<aux.length){
                    if(uniqueWords[j].compareTo(aux[h])==0){
                        count++;
                    }

                    h++;
                }
                matrizM[i][j]=count;
            }
        }


        return matrizM;
    }
    public String getTotalWords(){
        return totalWords;
    }
}
