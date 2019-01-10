import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\nSTART...\n\nInsira a query para pesquisa de ficheiros com similaridade: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = br.readLine();

        //leitura de ficheiros
        FileManager teste=new FileManager(7);
        teste.insertFile("um.txt");
        teste.insertFile("dois.txt");
        teste.insertFile("tres.txt");
        teste.insertFile("ListaCompras.txt");
        teste.insertFile("MenuRestaurante.txt");
        teste.insertFile("ListaAlunosESTG.txt");
        teste.insertFile("CreditosProjeto.txt");

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
        int opcao=0;

        //Menu de opções
        do {
            System.out.println("\nSelecione uma alternativa: \n\n1 - Imprimir Lista Completa \n2 - Imprimir Lista c/ Grau de Similaridade Superior a Valor Especifico" +
                    "\n3 - Imprimir Lista c/ Valor Especifico de Ficheiros \n4 - Sair\n");
            String readMenu =br.readLine();
            opcao =Integer.parseInt(readMenu);
            switch (opcao){
                //Imprimir Lista Completa
                case 1:
                    System.out.println("\nSelecionou *Imprimir Lista Completa*\nLista de Ficheiros Completa: \n");
                    System.out.println(teste.imprimirLCompleta(grauSim,teste.getFilesName()));
                    break;

                //Imprimir Lista c/ Grau de Semelhança Superior a Valor Especifico
                case 2:
                    //valorSem = valor de similaridade entre a query e ficheiros
                    double valorSim;
                    System.out.println("\nSelecionou *Imprimir Lista c/ Grau de Similaridade Superior a Valor Especifico*\n\nInsira um valor entre 0 e 1: ");

                    //input de valorSim
                    String read2 = br.readLine();
                    valorSim = Double.parseDouble(read2);

                    //número tem de ser 0<valorSem<1
                    if(valorSim>=0&&valorSim<=1){
                        System.out.println("Lista de Ficheiros c/ Grau de Similaridade Superior a "+valorSim+": \n");
                        System.out.println(teste.imprimirLGrauLimite(grauSim,teste.getFilesName(),valorSim));
                    }else{
                        System.out.println("Valor invalido");
                    }
                    break;

                //Selecionou *Imprimir Lista c/ Valor Especifico de Ficheiros
                case 3:
                    //nFiles = número de ficheiros a imprimir
                    int nFiles;
                    System.out.println("\nSelecionou *Imprimir Lista c/ Valor Especifico de Ficheiros*\n\nInsira um valor superior a 0: ");

                    //input de nFiles
                    String read3 =br.readLine();
                    nFiles = Integer.parseInt(read3);

                    //número tem de ser >0
                    if(nFiles>0) {
                        System.out.println("Lista c/ "+nFiles+" Ficheiros: \n");
                        System.out.println(teste.imprimirLLimitada(grauSim, teste.getFilesName(), nFiles));
                    }else{
                        System.out.println("Valor invalido");
                    }
                    break;

                //Sair
                case 4:
                    return ;
                default:
            }
        }while(true);



    }

}
