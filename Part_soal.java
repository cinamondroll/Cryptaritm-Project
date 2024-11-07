public class Part_soal {
    public static String[][] puzzles = {
        // Format: {soal, jawaban, petunjuk, level}
        {"   AA\n + BB\n = CC", "A=1, B=2, C=3", "A=1", "1"},
        {"    AA\n +  AA\n = CAB", "A=9, B=1, C=8", "C=8", "1"},
        {"   AB\n + AB\n = CA", "A=2, B=6, C=5", "A=2", "1"},
        {"    AB\n +  BA\n = CAC", "A=2, B=9, C=1", "C=1", "1"},
        {"   AB\n + BC\n = DA", "A=3, B=1, C=2, D=4", "D=4", "1"},
    };

    public static void main(String[] args) {
        System.out.println(puzzles[4][0]);
    }
}
