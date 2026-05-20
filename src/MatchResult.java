public class MatchResult {
    private final int position;
    private final long iterations;
    private final long instructions;
    private final long comparisons;
    private final double durationMs;

    public MatchResult(int position, long iterations, long instructions, long comparisons, double durationMs) {
        this.position = position;
        this.iterations = iterations;
        this.instructions = instructions;
        this.comparisons = comparisons;
        this.durationMs = durationMs;
    }

    public int getPosition() {
        return position;
    }

    public long getIterations() {
        return iterations;
    }

    public long getInstructions() {
        return instructions;
    }

    public long getComparisons() {
        return comparisons;
    }

    public double getDurationMs() {
        return durationMs;
    }

    @Override
    public String toString() {
        return String.format(
            "Posição: %d | Iterações: %,d | Instruções: %,d | Comparações: %,d | Tempo: %.3f ms",
            position, iterations, instructions, comparisons, durationMs
        );
    }
}
