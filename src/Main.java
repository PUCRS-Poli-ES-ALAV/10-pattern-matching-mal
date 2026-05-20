import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("          BENCHMARK DE ALGORITMOS DE PATTERN MATCHING                 ");
        System.out.println("======================================================================\n");

        Random rnd = new Random(42);

        System.out.println("--- Cenário 1: Exemplo do Enunciado (Pequeno) ---");
        String txt1 = "ABCDCBDCBDACBDABDCBADF";
        String pat1 = "ADF";
        System.out.println("Texto (tamanho " + txt1.length() + "): " + txt1);
        System.out.println("Padrão (tamanho " + pat1.length() + "): " + pat1);
        runAndPrintAll(txt1, pat1, true);

        System.out.println("\n--- Cenário 2: DNA Aleatório (Médio - 10.000 chars) ---");
        String pat2 = generateDNA(100, rnd);
        String txt2 = generateDNA(9900, rnd) + pat2;
        System.out.println("Texto (tamanho " + txt2.length() + ")");
        System.out.println("Padrão (tamanho " + pat2.length() + ")");
        runAndPrintAll(txt2, pat2, true);

        System.out.println("\n--- Cenário 3: Grande (Ambas > 500.000 chars) ---");
        int sizePat3 = 500000;
        String pat3 = generateDNA(sizePat3, rnd);
        String prefix3 = "ACGTA";
        String txt3 = prefix3 + pat3;
        System.out.println("Texto (tamanho " + txt3.length() + ")");
        System.out.println("Padrão (tamanho " + pat3.length() + ")");
        runAndPrintAll(txt3, pat3, true);

        System.out.println("\n--- Cenário 4: Pior Caso Grande (Sem ocorrência, ambas > 500.000 chars) ---");
        int sizeTxt4 = 1000000;
        int sizePat4 = 500000;
        
        StringBuilder sbTxt = new StringBuilder();
        for (int i = 0; i < sizeTxt4; i++) sbTxt.append('A');
        String txt4 = sbTxt.toString();

        StringBuilder sbPat = new StringBuilder();
        for (int i = 0; i < sizePat4 - 1; i++) sbPat.append('A');
        sbPat.append('B');
        String pat4 = sbPat.toString();

        System.out.println("Texto (tamanho " + txt4.length() + ")");
        System.out.println("Padrão (tamanho " + pat4.length() + ")");
        runAndPrintAll(txt4, pat4, false);
    }

    private static String generateDNA(int length, Random rnd) {
        char[] dna = {'A', 'C', 'G', 'T'};
        char[] sb = new char[length];
        for (int i = 0; i < length; i++) {
            sb[i] = dna[rnd.nextInt(4)];
        }
        return new String(sb);
    }

    private static void runAndPrintAll(String txt, String pat, boolean runLents) {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s | %-10s | %-15s | %-18s | %-15s | %-12s\n", 
            "Algoritmo", "Posição", "Iterações", "Instruções", "Comparações", "Tempo (ms)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        if (runLents) {
            MatchResult naiveRes = Enunciado1.search(txt, pat);
            printResultRow("Naive (Enunciado 1)", naiveRes);

            MatchResult rkNaiveRes = Enunciado2.searchNaive(txt, pat);
            printResultRow("Rabin-Karp (Enun 2)", rkNaiveRes);
        } else {
            System.out.printf("%-25s | %-80s\n", "Naive (Enunciado 1)", "[Ignorado devido à lentidão extrema]");
            System.out.printf("%-25s | %-80s\n", "Rabin-Karp (Enun 2)", "[Ignorado devido à lentidão extrema]");
        }

        MatchResult rkRollingRes = Enunciado2.searchRolling(txt, pat);
        printResultRow("Rabin-Karp Rolling", rkRollingRes);

        MatchResult kmpRes = Enunciado3.search(txt, pat);
        printResultRow("KMP (Enunciado 3)", kmpRes);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
    }

    private static void printResultRow(String name, MatchResult res) {
        System.out.printf("%-25s | %-10d | %-15s | %-18s | %-15s | %-12.3f\n",
            name,
            res.getPosition(),
            String.format("%,d", res.getIterations()),
            String.format("%,d", res.getInstructions()),
            String.format("%,d", res.getComparisons()),
            res.getDurationMs()
        );
    }
}
