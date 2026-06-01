public class Enunciado3 {
    private static void computeLPSArray(String pat, int M, int[] lps, long[] counters) {
        int len = 0;
        int i = 1;
        lps[0] = 0;
        counters[1] += 3;

        while (i < M) {
            counters[0]++;
            counters[1]++;
            
            counters[2]++;
            counters[1] += 3;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
                counters[1] += 3;
            } else {
                counters[1]++;
                if (len != 0) {
                    len = lps[len - 1];
                    counters[1] += 2;
                } else {
                    lps[i] = len;
                    i++;
                    counters[1] += 2;
                }
            }
        }
        counters[1]++;
    }

    public static MatchResult search(String txt, String pat) {
        long startTime = System.nanoTime();
        
        int M = pat.length();
        int N = txt.length();
        
        long[] counters = new long[3];
        counters[1] += 8;

        if (N < M) {
            counters[1]++;
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1e6;
            return new MatchResult(-1, counters[0], counters[1], counters[2], durationMs);
        }

        int[] lps = new int[M];
        counters[1]++;
        
        computeLPSArray(pat, M, lps, counters);
        counters[1]++;

        int j = 0;
        int i = 0;
        counters[1] += 2;

        while (i < N) {
            counters[0]++;
            counters[1]++;

            counters[2]++;
            counters[1] += 3;
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
                counters[1] += 2;
            }

            counters[1]++;
            if (j == M) {
                long endTime = System.nanoTime();
                double durationMs = (endTime - startTime) / 1e6;
                counters[1]++;
                return new MatchResult(i - j, counters[0], counters[1], counters[2], durationMs);
            }

            counters[1]++;
            if (i < N) {
                counters[2]++;
                counters[1] += 4;
                if (pat.charAt(j) != txt.charAt(i)) {
                    counters[1]++;
                    if (j != 0) {
                        j = lps[j - 1];
                        counters[1] += 2;
                    } else {
                        i = i + 1;
                        counters[1]++;
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;
        counters[1] += 2;
        return new MatchResult(-1, counters[0], counters[1], counters[2], durationMs);
    }
}
