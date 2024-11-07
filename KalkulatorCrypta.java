import java.util.Scanner;
public class KalkulatorCrypta {

    // Global array to store used digits
    static boolean[] usedDigits = new boolean[10];
    
    public static void main(String[] args) {
        Scanner HH = new Scanner(System.in);
        
        // Input kata
        System.out.println("Masukkan Kata Pertama:");
        String word1 = HH.nextLine().toUpperCase();
        
        System.out.println("Masukkan kata kedua:");
        String word2 = HH.nextLine().toUpperCase();
        
        System.out.println("Masukkan jawaban:");
        String result = HH.nextLine().toUpperCase();
        
        // Create arrays of unique letters
        char[] uniqueLetters = getUniqueLetters(word1, word2, result);
        int[] values = new int[uniqueLetters.length];
        
        System.out.println("\nSolusi dari soal:");
        System.out.println("  " + word1);
        System.out.println("+ " + word2);
        System.out.println("= " + result);
        
        // Try to solve
        if (solve(uniqueLetters, values, 0, word1, word2, result)) {
            printSolution(uniqueLetters, values);
        } else {
            System.out.println("\nTidak menemukan solusi!");
        }
    }
    
    // Method to get unique letters from all words
    private static char[] getUniqueLetters(String word1, String word2, String result) {
        String combined = word1 + word2 + result;
        String unique = "";
        
        for (char c : combined.toCharArray()) {
            if (unique.indexOf(c) == -1) {
                unique += c;
            }
        }
        
        return unique.toCharArray();
    }
    
    // Method to convert word to number based on letter values
    private static int wordToNumber(String word, char[] letters, int[] values) {
        int num = 0;
        for (char c : word.toCharArray()) {
            num = num * 10 + getValueForLetter(c, letters, values);
        }
        return num;
    }
    
    // Procedure to get value for a letter
    private static int getValueForLetter(char letter, char[] letters, int[] values) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letter) {
                return values[i];
            }
        }
        return -1;
    }
    
    // Recursive method to solve the cryptarithm
    private static boolean solve(char[] letters, int[] values, int position, 
                               String word1, String word2, String result) {
        // Base case: if all letters have values assigned
        if (position == letters.length) {
            // Check if first digits are not zero
            if (getValueForLetter(word1.charAt(0), letters, values) == 0 ||
                getValueForLetter(word2.charAt(0), letters, values) == 0 ||
                getValueForLetter(result.charAt(0), letters, values) == 0) {
                return false;
            }
            
            // Convert words to numbers and check if equation is satisfied
            int num1 = wordToNumber(word1, letters, values);
            int num2 = wordToNumber(word2, letters, values);
            int sum = wordToNumber(result, letters, values);
            
            return (num1 + num2 == sum);
        }
        
        // Try different digits for current position
        for (int i = 0; i < 10; i++) {
            if (!usedDigits[i]) {
                usedDigits[i] = true;
                values[position] = i;
                
                if (solve(letters, values, position + 1, word1, word2, result)) {
                    return true;
                }
                
                usedDigits[i] = false;
            }
        }
        
        return false;
    }
    
    // Procedure to print the solution
    private static void printSolution(char[] letters, int[] values) {
        System.out.println("\nSolusi ditemukan!");
        System.out.println("Letter assignments:");
        for (int i = 0; i < letters.length; i++) {
            System.out.println(letters[i] + " = " + values[i]);
        }
    }
}