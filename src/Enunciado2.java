public class Enunciado2 {
    public static final int R = 256;
    public static final long Q = 2147483647;

    private static long hash(String s, int M, long[] counters) {
        long h = 0;
        counters[1]++;
        for (int j = 0; j < M; j++) {
            counters[0]++;
            counters[1] += 2;
            h = (h * R + s.charAt(j)) % Q;
            counters[1] += 4;
        }
        return h;
    }

    public static MatchResult searchNaive(String txt, String pat) {
        long startTime = System.nanoTime();
        long comparisons = 0;
        
        int N = txt.length();
        int M = pat.length();
        
        long[] counters = new long[2];
        counters[1] += 7;

        if (N < M) {
            counters[1]++;
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1e6;
            return new MatchResult(-1, counters[0], counters[1], comparisons, durationMs);
        }

        long patHash = hash(pat, M, counters);
        counters[1] += 2;

        for (int i = 0; i <= N - M; i++) {
            counters[0]++;
            counters[1] += 2;

            String sub = txt.substring(i, i + M);
            counters[1] += 2;
            long txtHash = hash(sub, M, counters);
            counters[1] += 2;
            
            counters[1]++;
            if (patHash == txtHash) {
                boolean match = true;
                counters[1]++;
                for (int k = 0; k < M; k++) {
                    counters[0]++;
                    counters[1] += 2;
                    comparisons++;
                    counters[1] += 3;
                    if (txt.charAt(i + k) != pat.charAt(k)) {
                        match = false;
                        counters[1]++;
                        break;
                    }
                }
                counters[1]++;
                if (match) {
                    long endTime = System.nanoTime();
                    double durationMs = (endTime - startTime) / 1e6;
                    counters[1] += 2;
                    return new MatchResult(i, counters[0], counters[1], comparisons, durationMs);
                }
            }
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;
        counters[1] += 2;
        return new MatchResult(-1, counters[0], counters[1], comparisons, durationMs);
    }

    public static MatchResult searchRolling(String txt, String pat) {
        long startTime = System.nanoTime();
        long comparisons = 0;
        
        int N = txt.length();
        int M = pat.length();
        
        long[] counters = new long[2];
        counters[1] += 7;

        if (N < M) {
            counters[1]++;
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1e6;
            return new MatchResult(-1, counters[0], counters[1], comparisons, durationMs);
        }

        long RM = 1;
        counters[1]++;
        for (int i = 1; i <= M - 1; i++) {
            counters[0]++;
            counters[1] += 2;
            RM = (RM * R) % Q;
            counters[1] += 3;
        }

        long patHash = hash(pat, M, counters);
        long txtHash = hash(txt.substring(0, M), M, counters);
        counters[1] += 4;

        for (int i = 0; i <= N - M; i++) {
            counters[0]++;
            counters[1] += 2;

            counters[1]++;
            if (patHash == txtHash) {
                boolean match = true;
                counters[1]++;
                for (int k = 0; k < M; k++) {
                    counters[0]++;
                    counters[1] += 2;
                    comparisons++;
                    counters[1] += 3;
                    if (txt.charAt(i + k) != pat.charAt(k)) {
                        match = false;
                        counters[1]++;
                        break;
                    }
                }
                counters[1]++;
                if (match) {
                    long endTime = System.nanoTime();
                    double durationMs = (endTime - startTime) / 1e6;
                    counters[1] += 2;
                    return new MatchResult(i, counters[0], counters[1], comparisons, durationMs);
                }
            }

            if (i < N - M) {
                counters[1] += 4;
                long term = (txt.charAt(i) * RM) % Q;
                long diff = (txtHash - term + Q) % Q;
                txtHash = (diff * R + txt.charAt(i + M)) % Q;
            }
            counters[1]++;
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;
        counters[1] += 2;
        return new MatchResult(-1, counters[0], counters[1], comparisons, durationMs);
    }
}
