public class Enunciado1 {
    public static MatchResult search(String txt, String pat) {
        long startTime = System.nanoTime();
        
        long iterations = 0;
        long instructions = 0;
        long comparisons = 0;
        
        int N = txt.length();
        int M = pat.length();
        
        instructions += 5;

        for (int i = 0; i <= N - M; i++) {
            iterations++;
            instructions += 2; 
            
            boolean found = true;
            instructions++; 
            
            for (int j = 0; j < M; j++) {
                iterations++;
                instructions += 2;
                
                comparisons++;
                instructions += 3; 
                
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    found = false;
                    instructions++; 
                    break;
                }
            }
            
            instructions++; 
            if (found) {
                long endTime = System.nanoTime();
                double durationMs = (endTime - startTime) / 1e6;
                instructions += 2; 
                return new MatchResult(i, iterations, instructions, comparisons, durationMs);
            }
        }
        
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;
        instructions += 2; 
        return new MatchResult(-1, iterations, instructions, comparisons, durationMs);
    }
}
