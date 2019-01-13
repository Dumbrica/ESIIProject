
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Teste {

    //Instânciar FileManager
    FileManager fm;
    FileManager fm2;

    //Executa antes de cada teste
    @BeforeEach
    public void init(){
        fm = new FileManager();
        fm2 = new FileManager(20);
    }

    //Teste para insertFile com caminho para documento válido()
    @Test
    public void test1(){
        assertEquals(true,fm.insertFile("DOC.txt"));
    }

    //Teste para insertFile com caminho inválido()
    @Test
    public void test2(){
        assertEquals(false,fm.insertFile("DO.txt"));
    }

    //Teste para insertFile com caminho inválido()
    @Test
    public void test3(){
        assertEquals(false,fm.insertFile(""));
    }

    //Teste para removeDigits()
    @Test
    public void test4(){

        assertEquals("um dois tres",fm.removeDigits("um1 dois2 tres3"));
    }

    //Teste para removeChars()
    @Test
    public void test5(){

        assertEquals("um dois tres",fm.removeChars("um? dois! tres#"));
    }

    //Teste para uniqueWords()
    @Test
    public void test6() {
        String[] teste = {"um", "dois", "tres"};
        String[] teste2=fm.uniqueWords("um dois tres tres");
        assertAll(
                () -> assertTrue((teste[0].compareTo(teste2[0])) == 0),
                () -> assertTrue((teste[1].compareTo(teste2[1])) == 0),
                () -> assertTrue((teste[2].compareTo(teste2[2])) == 0)
        );

    }

    //Teste para uniqueWords() (aux)
    @Test
    public void test7(){
        String[] teste = {"HelloWorld"};
        String[] teste2=fm.uniqueWords("HelloWorld");
        assertTrue((teste[0].compareTo(teste2[0])) == 0);


    }

    //Teste para matrizOrganizer() para ficheiros
    @Test
    public void test8(){
        fm.insertFile("DOC.txt");
        fm.insertFile("DOC2.txt");
        int[][] aux=fm.matrizOrganizer(fm.uniqueWords(fm.getTotalWords()));

        assertAll(
                () -> assertEquals(1,aux[0][0]),
                () -> assertEquals(3,aux[0][1]),
                () -> assertEquals(1,aux[0][2]),
                () -> assertEquals(0,aux[0][3]),
                () -> assertEquals(0,aux[0][4]),
                () -> assertEquals(2,aux[1][0]),
                () -> assertEquals(1,aux[1][1]),
                () -> assertEquals(0,aux[1][2]),
                () -> assertEquals(1,aux[1][3]),
                () -> assertEquals(1,aux[1][4])
        );
    }

    //Teste para matrizOrganizer() para query
    @Test
    public void test9(){
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres");
        int[] matrizQQuery=fm.matrizOrganizer("um dois tres",fm.uniqueWords(fm.getTotalWords()));
        assertAll(
                () -> assertEquals(1,matrizQQuery[0]),
                () -> assertEquals(1,matrizQQuery[1]),
                () -> assertEquals(1,matrizQQuery[2]),
                () -> assertEquals(0,matrizQQuery[3]),
                () -> assertEquals(0,matrizQQuery[4])
        );
    }

    //Teste para insertQuery() quando query válida
    @Test
    public void test10(){
        assertTrue(fm.insertQuery("Um2! dois Tres"));
    }

    //Teste para insertQuery() quando query inválida
    @Test
    public void test11(){
        assertFalse(fm.insertQuery(""));
    }


    //Teste para getQuery()
    @Test
    public void test12(){
        fm.insertQuery("Um1 dois2 tres3!");
        assertEquals("um dois tres", fm.getQuery());
    }

    //Teste para matrizModifier() para matrizM
    @Test
    public void test13(){
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);

        double [][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        assertAll(
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMFiles[0][0]),
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMFiles[0][1]),
                () -> assertEquals((1*(1+Math.log10(2/2))),matrizMFiles[0][2]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMFiles[0][3]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMFiles[0][4]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMFiles[1][0]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMFiles[1][1]),
                () -> assertEquals((1*(1+Math.log10(2/2))),matrizMFiles[1][2]),
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMFiles[1][3]),
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMFiles[1][4])

        );

    }

    //Teste para matrizModifier() para matrizQ
    @Test
    public void test14(){
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);

        double [] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
        assertAll(
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMQuery[0]),
                () -> assertEquals((1*(1+Math.log10(2/1))),matrizMQuery[1]),
                () -> assertEquals((1*(1+Math.log10(2/2))),matrizMQuery[2]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMQuery[3]),
                () -> assertEquals((0*(1+Math.log10(2/1))),matrizMQuery[4])

        );
    }

    //Teste para calculoGrauS
    @Test
    public void test15(){
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
        double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);

        double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

        assertAll(
                () -> assertEquals(1.0000000000000002,grauSim[0]),
                () -> assertEquals(0.22803154893427774,grauSim[1])
        );

        }

        @Test// Teste imprimirLCompleta
        public void test16(){

            fm.insertFile("BikeShop.txt");
            fm.insertFile("um.txt");
            fm.insertFile("dois.txt");
            fm.insertQuery("um dois tres ");
            String query=fm.getQuery();
            String[] totalWords =fm.uniqueWords(fm.getTotalWords());
            int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
            int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
            double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
            double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);

            double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

            grauSim = fm.orderGrauS(grauSim);
            assertEquals("Ficheiro | Grau\num.txt | 1.0\ndois.txt | 0.18643588\nBikeShop.txt | 0.0\n",fm.imprimirLCompleta(grauSim,fm.getFilesName()));

    }

    @Test// Teste imprimirLLimitada
    public void test17(){

        fm.insertFile("BikeShop.txt");
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres ");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
        double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
        double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

        grauSim = fm.orderGrauS(grauSim);
        assertEquals("Ficheiro | Grau\num.txt | 1.0\ndois.txt | 0.18643588\n",fm.imprimirLLimitada(grauSim,fm.getFilesName(),2));

    }


    @Test// Teste imprimirLGrauLimite
    public void test18(){

        fm.insertFile("BikeShop.txt");
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres ");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
        double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
        double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

        grauSim = fm.orderGrauS(grauSim);
        assertEquals("Ficheiro | Grau\num.txt | 1.0\n",fm.imprimirLGrauLimite(grauSim,fm.getFilesName(),0.95));


    }

    @Test// Teste imprimirLLimitada com menos ficheiros do que a quantidade que queremos imprimir
    public void test19(){

        fm.insertFile("BikeShop.txt");
        fm.insertFile("um.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres ");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
        double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
        double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

        grauSim = fm.orderGrauS(grauSim);
        assertEquals("Ficheiro | Grau\num.txt | 1.0\ndois.txt | 0.18643588\nBikeShop.txt | 0.0\n",fm.imprimirLLimitada(grauSim,fm.getFilesName(),6));

    }

    @Test// Teste imprimirLGrauLimite com nenhum ficheiro superior ou igual ao grau enviado
    public void test20(){

        fm.insertFile("BikeShop.txt");
        fm.insertFile("ListaAlunosESII.txt");
        fm.insertFile("MenuCafe.txt");
        fm.insertFile("dois.txt");
        fm.insertQuery("um dois tres ");
        String query=fm.getQuery();
        String[] totalWords =fm.uniqueWords(fm.getTotalWords());
        int[][] matrizQFiles=fm.matrizOrganizer(totalWords);
        int[] matrizQQuery=fm.matrizOrganizer(query,totalWords);
        double[][] matrizMFiles=fm.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=fm.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
        double[] grauSim=fm.calculoGrauS(matrizMFiles,matrizMQuery);

        grauSim = fm.orderGrauS(grauSim);
        assertEquals("Ficheiro | Grau\n",fm.imprimirLGrauLimite(grauSim,fm.getFilesName(),0.95));
    }



}