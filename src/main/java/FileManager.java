import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 */
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

    /**
     * Método de gestão de ficheiros
     *
     * @param files_size
     */
    public FileManager(int files_size){
        files = new String[files_size];
        filesName=new String[files_size];
    }

    /**
     * Método de inserção de ficheiros
     *
     * @param filePath
     * @return
     */
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

    /**
     * Método para inserção de uma query
     *
     * @param query
     * @return
     */
    public Boolean insertQuery(String query){
        if(query.compareTo("")==0)return false;
        query=removeChars(query);
        query=removeDigits(query);
        query=query.toLowerCase();
        totalWords=totalWords + query + " ";
        this.query=query;
        return true;
    }

    /**
     * Método para leitura de um ficheiro
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private String readFile(String filePath) throws IOException {

        String file = "";

        file = new String(Files.readAllBytes(Paths.get(filePath)));

        return file;
    }

    /**
     * Método de remoção de dígitos
     *
     * @param texto
     * @return
     */
    public String removeDigits(String texto){
        texto=texto.replaceAll("[0-9]","");
        return texto;
    }

    /**
     * Método para remover caracteres especiais de uma String
     *
     * @param texto
     * @return
     */
    public String removeChars(String texto){
        texto=texto.replaceAll("[\"\\,\\.\\?\\!\\|\\[\\]\\{\\}\\/\\;\\:\\«\\»\\<\\>\\@\\£\\€\\§\\#\\$\\%\\&\\=\\)\\(\\*\\+\\~\\^\\_\\-]","");
        return texto;

    }

    /**
     * Método para remover palavras repetidas de uma String
     *
     * @param texto
     * @return
     */
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

    /**
     * Método para obter a quantidade de cada palavra nos ficheiros
     *
     * @param uniqueWords
     * @return
     */
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

    /**
     * Método para obter a quantidade de cada palavra na query
     *
     * @param query
     * @param uniqueWords
     * @return
     */
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

    /**
     * Método para alterar os valores da matrizM para os valores da fórmula
     *
     * @param matrizM
     * @param totalWordsM
     * @return
     */
    public double[][] matrizModifier(int[][] matrizM,String[] totalWordsM){
        int contadoc=0;
        double[][] matrizOut=new double[filesCount][totalWordsM.length];
        for(int i=0;i<filesCount;i++){
            for(int j=0;j< totalWordsM.length;j++){
                contadoc=0;
                for(int h=0;h<filesCount;h++){
                    if(matrizM[h][j]>0)contadoc++;
                }
                if(contadoc== 0){
                    matrizOut[i][j]=0;
                }else {
                    matrizOut[i][j] = matrizM[i][j] * (1 + Math.log10((filesCount / contadoc)));
                }
            }
        }
        return matrizOut;
    }

    /**
     * Método para alterar os valores da matrizQ para os valores da fórmula
     *
     * @param queryArray
     * @param matrizM
     * @param totalWordsM
     * @return
     */
    public double[] matrizModifier(int[] queryArray,int[][] matrizM,String[] totalWordsM){
        double[] matrizOut=new double[totalWordsM.length];
        int contadoc=0;
        for(int i=0;i<totalWordsM.length;i++){
            contadoc=0;
            for(int h=0;h<filesCount;h++){
                if(matrizM[h][i]>0)contadoc++;
            }
            if(contadoc== 0) {
                matrizOut[i] = 0;
            }else {
                matrizOut[i] = queryArray[i] * (1 + Math.log10((filesCount / contadoc)));
            }
        }

        return matrizOut;
    }

    /**
     * Método para calcular o grau de similaridade
     *
     * @param matrizMFiles
     * @param matrizMQuery
     * @return
     */
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

    /**
     * Método para retornar as palavras totais
     *
     * @return
     */
    public String getTotalWords(){
        return totalWords;
    }

    /**
     * Métood para obter uma query
     *
     * @return
     */
    public String getQuery(){ return query; }


    public double[] orderGrauS(double[] grauS){

        double temp;
        String auxa;
        for (int i = 0; i <= grauS.length; i++)
        {
            for (int j = i+1; j < grauS.length; j++)
            {
                if (grauS[j] > grauS[i])
                {
                    System.out.println(grauS[j]+"-"+grauS[i]);
                    temp = grauS[i];
                    grauS[i] = grauS[j];
                    grauS[j] = temp;

                    auxa=filesName[i];
                    filesName[i]=filesName[j];
                    filesName[j]=auxa;
                }
            }
        }
        return  grauS;
    }
    public String[] getFilesName(){
        return filesName;
    }
    public String imprimirLCompleta(double[] grauS,String[] files){
        String imprime="Ficheiro | Grau\n";

            for(int i=0;i<grauS.length;i++){
                imprime+=files[i]+" | " + (float)grauS[i]+"\n";
            }
        return imprime;
    }
    public String imprimirLLimitada(double[] grauS,String[] files,int quant){

        String imprime="Ficheiro | Grau\n";

        for(int i=0;i<grauS.length && i<quant;i++){
            imprime+=files[i]+" | " + (float)grauS[i]+"\n";
        }
        return imprime;
    }
    public String imprimirLGrauLimite(double[] grauS,String[] files,double limite){
        String imprime="";
        for(int i=0;i<grauS.length && grauS[i]>limite;i++){
            imprime+=files[i]+" | " + (float)grauS[i]+"\n";
        }
        return imprime;
    }
}
