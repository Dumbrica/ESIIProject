public class Main {
    public static void main(String[] args) {
        FileManager teste=new FileManager(2);
        teste.insertFile("um.txt");
        teste.insertFile("dois.txt");
        String query="um dois tres";
        teste.insertQuery(query);
        query=teste.getQuery();

        String[] totalWords =teste.uniqueWords(teste.getTotalWords());

        //Matriz com a quantidade de palavras nos ficheiros
        int[][] matrizQFiles=teste.matrizOrganizer(totalWords);
        int[] matrizQQuery=teste.matrizOrganizer(query,totalWords);

        double[][] matrizMFiles=teste.matrizModifier(matrizQFiles,totalWords);
        double[] matrizMQuery=teste.matrizModifier(matrizQQuery,matrizQFiles,totalWords);

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

        double[] grauSim=teste.calculoGrauS(matrizMFiles,matrizMQuery);
        for(double i : grauSim){
            System.out.println(i);
        }
    }

}
