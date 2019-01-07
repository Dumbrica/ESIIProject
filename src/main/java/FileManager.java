import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileManager {

    private String[] files;
    private String[] filesName;
    private static int DEFAULT_SIZE = 5;
    private int filesCount = 0;
    private String totalWords ="";
    private String query="";
    public FileManager() {
        files = new String[DEFAULT_SIZE];
        filesName=new String[DEFAULT_SIZE];
    }

    public FileManager(int files_size){
        files = new String[files_size];
        filesName=new String[files_size];
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
        filesName[filesCount]=Paths.get(filePath).getFileName().toString();
        totalWords = totalWords  + files[filesCount] + " ";
        filesCount++;

        return  true;
    }
    //método para inserir query
    public String insertQuery(String query){
        query=removeChars(query);
        query=removeDigits(query);
        query=query.toLowerCase();
        totalWords=totalWords + query + " ";
        this.query=query;
        return this.query;
    }
    //método para ler ficheiro
    private String readFile(String filePath) throws IOException {

        String file = "";

        file = new String(Files.readAllBytes(Paths.get(filePath)));

        return file;
    }
    //método para remover digitos
    public String removeDigits(String texto){
        texto=texto.replaceAll("[0-9]","");
        return texto;
    }
    //método para remover carateres especiais
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
    //método para obter a quantidade de cada palavra nos ficheiros
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
    //metodo para obter a quantidade de cada palavra na query
    public int[] matrizOrganizer(String query,String[] uniqueWords){
        int count,h;
        int[] queryArray=new int[uniqueWords.length];
        String[] queroA=query.split(" ");
        for(int i=0;i<uniqueWords.length;i++){
            count=0;
            h=0;
            while(h<queroA.length){
                if(uniqueWords[i].compareTo(queroA[h])==0){
                    count++;
                }
                h++;
            }
            queryArray[i]=count;
        }
        return queryArray;
    }
    //método para alterar os valores da matrizM para os da formula
    public double[][] matrizModifier(int[][] matrizM,String[] totalWordsM){
        int contadoc=0;
        double[][] matrizOut=new double[filesCount][totalWordsM.length];
        for(int i=0;i<filesCount;i++){
            for(int j=0;j< totalWordsM.length;j++){
                contadoc=0;
                for(int h=0;h<filesCount;h++){
                    if(matrizM[h][j]>0)contadoc++;
                }
                matrizOut[i][j]=matrizM[i][j]*(1+Math.log10((filesCount/contadoc)));
            }
        }
        return matrizOut;
    }
    //método para alterar os valores da matrizQ para os da formula
    public double[] matrizModifier(int[] queryArray,int[][] matrizM,String[] totalWordsM){
        double[] matrizOut=new double[totalWordsM.length];
        int contadoc=0;
        for(int i=0;i<totalWordsM.length;i++){
            contadoc=0;
            for(int h=0;h<filesCount;h++){
                if(matrizM[h][i]>0)contadoc++;
            }
            matrizOut[i]=queryArray[i]*(1+Math.log10((filesCount/contadoc)));
        }

        return matrizOut;
    }
    public double[] calculoGrauS(double[][] matrizMFiles,double[] matrizMQuery){
        double[] grauSim=new double[filesCount];
        for(int i=0;i<filesCount;i++){
            double cima=0;
            for(int l=0;l<matrizMQuery.length;l++){
                cima+=matrizMFiles[i][l]*matrizMQuery[l];
            }
            double baixo=0;
            double esq=0,dir=0;
            for(int l=0;l<matrizMQuery.length;l++){
                esq+=Math.pow(matrizMFiles[i][l],2);
                dir+=Math.pow(matrizMQuery[l],2);
            }
            baixo=(Math.sqrt(esq))*(Math.sqrt(dir));
            grauSim[i]=cima/baixo;
        }
        return grauSim;
    }
    public String getTotalWords(){
        return totalWords;
    }
    public String getQuery(){ return query; }
}
