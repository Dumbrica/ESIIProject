import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Insira a query");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = br.readLine();

        FileManager teste=new FileManager(2);
        teste.insertFile("um.txt");
        teste.insertFile("dois.txt");
        teste.insertQuery(query);
        query=teste.getQuery();

        String[] totalWords =teste.uniqueWords(teste.getTotalWords());

        //Matriz com a quantidade de palavras nos ficheiros
        int[][] matrizQFiles=teste.matrizOrganizer(totalWords);
        int[] matrizQQuery=teste.matrizOrganizer(query,totalWords);

        double[][] matrizMFiles=teste.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=teste.matrizModifier(matrizQQuery,matrizQFiles,totalWords);
/*
        for(double[] i :matrizMFiles){
            for(double j: i){
                System.out.print(j + "|");
            }
            System.out.println();
        }
        for(double i :matrizMQuery){
            System.out.print(i+"|");
        }
        System.out.println();
*/
        double[] grauSim=teste.calculoGrauS(matrizMFiles,matrizMQuery);
        /*for(double i : grauSim){
            System.out.println(i);
        }*/
        grauSim=teste.orderGrauS(grauSim);
        int opcao=0,quantFMostrar=3;
        double limite=0.2;

        do {
            System.out.println("Escolha opção: 1 - Lista Completa, 2 - Lista Acima de x, 3 - Lista de x Ficheiros, 4 - Sair");
            String read=br.readLine();
            opcao =read.
            switch (opcao){
                case 1:
                    System.out.println(teste.imprimirLCompleta(grauSim,teste.getFilesName()));
                    break;
                case 2:
                    //fazer reader com limite entre 0 e 1
                    System.out.println(teste.imprimirLGrauLimite(grauSim,teste.getFilesName(),limite));
                    break;
                case 3:
                    //fazer reader com numero maximo de ficheiros a mostrar >0
                    System.out.println(teste.imprimirLLimitada(grauSim,teste.getFilesName(),quantFMostrar));break;
                case 0:
                    return ;
                default:
            }
        }while(true);



    }

}
