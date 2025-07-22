//import algorithm.BoyerMoore;
import algorithm.KMP;
import utils.FileReaderUtil;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String text = FileReaderUtil.readFile("benchmarks/sample_text3.txt");
            String pattern = FileReaderUtil.readFile("benchmarks/pattern.txt").trim();
            //BoyerMoore bm = new BoyerMoore();
            KMP kmp = new KMP();
            List<Integer> indices = kmp.KMPSearch(text, pattern);
            if (indices.isEmpty()) {
                System.out.println("Pattern not found.");
            } else {
                System.out.println("Pattern occurs at indices: " + indices);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
