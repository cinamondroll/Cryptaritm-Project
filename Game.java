import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner player = new Scanner(System.in);

        int score = 0;
        int lives = 3;
        String jawaban = "";

        System.out.println("=======================================");
        System.out.println("     SELAMAT DATANG DI CRYPTARITHM     ");
        System.out.println("=======================================");
        System.out.println("Aturan permainan:");
        System.out.println("1. Anda memiliki 3 nyawa");
        System.out.println("2. Setiap jawaban benar mendapat 10 poin");
        System.out.println("3. Jawaban salah mengurangi 1 nyawa");
        System.out.println("4. Ketik 'hint' untuk mendapat petunjuk");
        System.out.println("5. Ketik 'exit' untuk keluar");
        System.out.println("6. Ketik 'skip' untuk skip soal");
        System.out.println("=======================================\n");

        System.out.println("pilih level (easy, medium, expert)");
        String Levelgame = player.nextLine();

        if (lives > 0){
            if (Levelgame.equals("easy")){
                for(int i = 0; i <= 10; i++){
                System.out.println("soal ke "+ i);
                System.out.println( Part_soal.puzzles[i][0]);
                jawaban = player.nextLine();

                if (jawaban.equals(Part_soal.puzzles[i][1])){
                    System.out.println("Jawaban Benar!!");
                    score += 10;
                } else {
                    System.out.println("Jawaban salah");
                    lives -= 1;
                }
                System.out.println("Score anda: "+ score);
                }

            } else if (Levelgame.equals("medium")){

            } else if (Levelgame.equals("expert")){

            } else {System.out.println("Level tidak valid");}





        } else if (lives<=0){
        System.out.println("=======================================");
        System.out.println("            GAME OVER                 ");
        System.out.println("=======================================");
        System.out.println("Score akhir Anda: " + score);
        }
        
        // Tampilkan pesan berdasarkan score
        if (score >= 50) {
            System.out.println("Luar biasa! Anda sangat pandai!");
        } else if (score >= 30) {
            System.out.println("Bagus! Anda cukup mahir!");
        } else {
            System.out.println("Jangan menyerah, coba lagi!");
        }
        System.out.println("=======================================");
    }
    
}