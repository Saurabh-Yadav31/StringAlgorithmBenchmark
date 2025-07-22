//import algorithm.BoyerMoore;
//import algorithm.KMP;
import algorithm.SuffixTree;
import utils.FileReaderUtil;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String text = FileReaderUtil.readFile("benchmarks/sample_text3.txt");
            String pattern = FileReaderUtil.readFile("benchmarks/overlap.txt").trim();
            //BoyerMoore bm = new BoyerMoore();
            //KMP kmp = new KMP();

            SuffixTree suffixTree = new SuffixTree(text);

            //List<Integer> indices = bm.searchAll(text, pattern);
            //List<Integer> matches = kmp.KMPSearch(text, pattern);

            List<Integer> occurrences = suffixTree.search(pattern);
            if (occurrences.isEmpty()) {  // need to use indices for BM and KMP
                System.out.println("Pattern not found.");
            } else {
                System.out.println("Pattern occurs at indices: " + occurrences);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
