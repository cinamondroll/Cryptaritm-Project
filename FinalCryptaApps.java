//Final
import java.util.Scanner;
import java.lang.Thread;

public class FinalCryptaApps {
    static Scanner user = new Scanner(System.in);   //General
    static int score = 0;       //Game
    static int lives = 3;       //Game
    static String jawaban = ""; //Game
    static int batasAwal;       //Game
    static int batasAkhir;      //Game
    static boolean[] penggunaanDigit = new boolean[15];     //Kalkulator
    static char[] varHuruf = new char[15];                  //Kalkulator
    static int[] nilaiHuruf = new int[15];                  //Kalkulator      
    static int banyakVarHuruf = 0;                          //Kalkulator

    /**
     * GENERAL METHOD
     * Metode {@code delay} membuat program delay selama 3 detik dan mencetak
     * titik (".") setiap detik selama delay.
     * Metode ini menggunakan {@link Thread#sleep(long)} untuk menghentikan sementara
     * eksekusi program, dalam method akan delay selama 1 detik di setiap iterasi dalam loop.
     * 
     * Jika thread terganggu selama delay, pesan kesalahan akan dicetak.
     * @throws InterruptedException jika thread terganggu saat delay.
     * 
     * Baris baru akan dicetak setelah looping berakhir
     */
    public static void delay (){
        for (int i = 0; i < 3; i++) {
            try {
                System.out.print("."); 
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                System.out.println("The process was interrupted.");
            }
        }
        System.out.println(" ");
    }

    /**
     * METHOD GAME CRYPTA
     * Array 2 dimensi untuk menyimpan soal-soal game crypta
     */
    public static String[][] puzzles = {
        // Format: {soal, subJawban, jawaban, petunjuk, level}
        { "      AA\n    + BB\n    = CC", "A, B, C", "1,2,3", "A = 1", "1" },
        { "       AA\n    +  AA\n    = BAC", "A, B, C", "9,1,8", "C = 8", "1" },
        { "      AB\n    + AB\n    = CA", "A, B, C", "2,6,5", "A = 2", "1" },
        { "       AB\n    +  BA\n    = CAC", "A, B, C", "2,9,1", "C = 1", "1" },
        { "      AB\n    + BC\n    = DA", "A, B, C, D", "3,1,2,4", "D = 4", "1" },
        { "      AB\n    - CA\n    =  D", "A, B, C, D", "3,1,2,8", "A = 3", "1" },
        { "      AA\n    - AB\n    =  B", "A, B", "1,2", "B = 1", "1" },
        { "      AA\n    - BB\n    = BB", "A, B", "2,1", "A = 2", "1" },
        { "      AA\n    - BC\n    = CB", "A, B, C", "3,1,2", "A = 3", "1" },
        { "      AB\n    - BC\n    = DA", "A, B, C, D", "4,2,8,1", "C = 8", "1" },
        { "      ADA\n    +  DI\n    = DIA", "A, D, I", "4,5,0", "A = 4", "2" },
        { "       II\n    +  II\n    = HIU", "I, H, U", "9,1,8", "H = 1", "2" },
        { "       AB\n    +  AB\n    = BCC", "A, B, C", "6,1,2", "C = 2", "2" },
        { "      ARA\n    + ABA\n    = BAR", "A, B, R", "3,7,6", "A = 3", "2" },
        { "      AKU\n    + KAU\n    = UUD", "A, K, U, D", "1,2,3,6", "D = 6", "2" },
        { "       MATH\n    +  MATH\n    = HABIT", "M, A, T, H, B, I", "7,5,2,1,0,4", "M = 7", "3" },
        
    };
    
    /**
     * METHOD GAME CRYPTA
     * Metode ini digunakan untuk mengecek jawaban pemain apakah benar atau salah
     * 
     * Metode ini akan memeriksa apakah ada elemen false di dalam array 
     * {@code toCheckValue}. Jika ditemukan, metode ini akan mengembalikan {@code yue}, 
     * jika tidak, akan mengembalikan {@code false}.
     *
     * @param arr Array boolean yang akan diperiksa, berisi jawaban dari pemain.
     * @param toCheckValue Nilai boolean yang akan dicari dalam array, disini adalah nilai false.
     * @return {@code true} jika {@code toCheckValue} ditemukan dalam array, 
     *         {@code false} jika tidak ditemukan.
     * 
     * Jadi jika terdapat 1 jawaban salah dari pemain, maka seluruh jawaban akan salah.
     */
    private static boolean check(boolean[] arr, boolean toCheckValue) {
        for (boolean element : arr) {
            if (element == toCheckValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * METHOD GAME CRYPTA
     * Menampilkan soal dan menangani interaksi pengguna untuk menjawab soal.
     * 
     * Metode ini menampilkan soal dari array `puzzles` berdasarkan indeks yang diberikan oleh 
     * parameter {@code batasAwal} dan {@code batasAkhir}. sekaligus meminta Pengguna untuk memberikan jawaban
     * atau memilih opsi seperti "hint", "skip", atau "exit". Jika jawaban benar, skor bertambah, 
     * jika salah, nyawa berkurang. Setiap soal memiliki sub-soal yang harus dijawab secara terpisah.
     * 
     * @param batasAwal Batas indeks awal soal yang akan ditampilkan.
     * @param batasAkhir Batas indeks akhir soal yang akan ditampilkan.
     */
    static void printSoal(int batasAwal, int batasAkhir) {
        for (int i = batasAwal; i <= batasAkhir; i++) {
            String[] jawabanBenar = puzzles[i][2].split(",");
            String[] subSoal = puzzles[i][1].split(",");
            String[] jawaban = new String[subSoal.length];
            boolean[] cek = new boolean[subSoal.length];
            System.out.println("||===========================================================||");
            System.out.printf("||                        Soal ke - %d                        ||\n",(i + 1));
            System.out.println("||===========================================================||");
            System.out.println(puzzles[i][0]);
            System.out.println("|| Lanjut jawab soal? (ya/hint/skip/exit)                    ||");
            System.out.print("   >> ");
            String respon = user.nextLine();

            while (!(respon.equals("ya")|| respon.equals("hint")||respon.equals("skip")||respon.equals("exit"))) {
                System.out.println(
                """
                        ||                MASUKKAN RESPON YANG BENAR                 ||"""
                );
                System.out.print("   >> ");
                respon = user.nextLine();
            }

            if (respon.equals("skip")) {
                System.out.print("|| Loading soal berikutnya");
                delay();
                continue;

            } else if (respon.equals("exit")) {
                System.out.print("|| Menghentikan permainan");
                delay();
                gameOver();
                System.out.print("|| Kembali ke manu utama game ");
                delay();
                GameRun();

            } else if (respon.equals("hint")) {
                score -= 5;
                System.out.println("||===========================================================||");
                System.out.printf("||                     HITN : %S                          ||\n", puzzles[i][3]);
                System.out.println("|| Setelah Memilih HINT tidak ada kesempatan untuk skip/exit ||");
                System.out.println("||===========================================================||");
                for (int j = 0; j < subSoal.length; j++) {
                    System.out.printf("|| Masukkan nilai %s: ", subSoal[j]);
                    jawaban[j] = user.nextLine();
                    if (jawaban[j].equals(jawabanBenar[j])) {
                        cek[j] = true;
                    } else {
                        cek[j] = false;
                    }
                }

            } else if (respon.equals("ya")){
                for (int j = 0; j < subSoal.length; j++) {
                    System.out.printf("|| Masukkan nilai %s: ", subSoal[j]);
                    jawaban[j] = user.nextLine();
                    if (jawaban[j].equals(jawabanBenar[j])) {
                        cek[j] = true;
                    } else {
                        cek[j] = false;
                    }
                }
            } 
            

            if (check(cek, false) == true) {
                System.out.println(
                """
                    ||===========================================================||
                    ||                      JAWABAN  SALAH                       ||
                    ||===========================================================||"""
                );
                lives -= 1;
                
            } else {
                System.out.println(
                """
                    ||===========================================================||
                    ||                      JAWABAN  BENAR                       ||
                    ||===========================================================||"""
                );
                if (puzzles[i][4].equals("1")){
                    score += 10;
                }else if(puzzles[i][4].equals("2")){
                    score += 20;
                }else if(puzzles[i][4].equals("3")){
                    score += 33;
                }   
            }
            
            System.out.printf("||===== Sisa Lives Anda %d ==== || ====== Score Anda %d ======||\n", lives, score);
            System.out.println("||===========================================================||");
            if (i == batasAkhir){
                System.out.print("|| Game selesai");
                delay();
            }
            System.out.print("|| Loading soal berikutnya");
            delay();
        }
    }

    /**
     * METHOD GAME CRYPTA
     * Menampilkan pesan "Game Over" dan skor akhir pemain.
     * 
     * Metode ini dipanggil ketika permainan selesai atau pemain kehabisan nyawa. 
     * Setelah menampilkan pesan "Game Over", metode ini menampilkan skor akhir pemain dan memberikan 
     * umpan balik berdasarkan skor yang dicapai. 
     * - Jika skor lebih dari atau sama dengan 50, umpan baliknya "Luar biasa! Anda sangat pandai!".
     * - Jika skor lebih dari atau sama dengan 30 tetapi kurang dari 50, umpan baliknya "Bagus! Anda cukup mahir!".
     * - Jika skor kurang dari 30, umpan baliknya "Jangan menyerah, coba lagi!".
     * 
     * Setelah Game over, Score dan lives akan direset.
     */
    static void gameOver() {
        System.out.println(
                """
                    ||===========================================================||
                    ||                         GAME OVER                         ||
                    ||===========================================================||""");
        System.out.printf("|| Score akhir Anda: %d                                      ||\n", score);
        if (score >= 80) {
            System.out.println("|| Luar biasa! Anda sangat pandai!                           ||");
        } else if (score >= 30 && score < 80) {
            System.out.println("|| Bagus! Anda cukup mahir!                                  ||");
        } else {
            System.out.println("|| Jangan menyerah, coba lagi!                               ||");
        }
        System.out.println("||===========================================================||\n\n");

        //reset score dan lives 
        score = 0;    
        lives = 3;
    }
    

    /**
     * METHOD GAME CRYPTA
     * Menjalankan permainan dengan memilih level soal.
     * 
     * Metode ini memungkinkan pengguna untuk memilih level permainan: Easy, Medium, Hard, atau Exit. 
     * Berdasarkan pilihan pengguna, metode ini akan menampilkan soal sesuai dengan level yang dipilih 
     * dan memberikan kesempatan kepada pengguna untuk menjawab. Jika lives habis atau jika pengguna memilih 
     * untuk keluar dari permainan, maka permainan akan berakhir. 
     * 
     * Proses yang terjadi di dalam metode ini:
     * 1. Menampilkan pilihan level soal.
     * 2. Menunggu user pengguna untuk memilih level soal.
     * 3. Berdasarkan pilihan level soal, soal akan ditampilkan dengan batasan soal yang sesuai.
     * 4. Jika pemain memilih keluar (Exit), maka akan diberikan pilihan untuk keluar atau melanjutkan permainan.
     * 5. Jika pilihan tidak valid, permainan akan mengulang meminta user.
     * 6. Setelah permainan selesai atau jika nyawa habis, metode ini akan memanggil `gameOver()`.
     */
    public static void GameRun (){

        while (lives > 0) {
            System.out.println(
                    """
                    ||===========================================================||
                    ||                      PILIH LEVEL SOAL                     ||
                    ||===========================================================||
                    || 1. Easy                                                   ||
                    || 2. Medium                                                 ||
                    || 3. Hard                                                   ||
                    || 4. Exit                                                   ||
                    ||===========================================================||""");
        
            System.out.print("|| Pilih angka 1-4: ");
            int Levelgame = user.nextInt();
            user.nextLine();

            while (Levelgame <= 0 || Levelgame > 4) {
                System.out.println("|| PILIHAN TIDAK VALID                                       ||");
                System.out.print("|| ");
                Levelgame = user.nextInt();
                user.nextLine();
            }
        
            if (Levelgame == 1) {
                batasAwal = 0;
                batasAkhir = 9;
                printSoal(batasAwal, batasAkhir);
        
            } else if (Levelgame == 2) {
                batasAwal = 10;
                batasAkhir = 14;
                printSoal(batasAwal, batasAkhir);
        
            } else if (Levelgame == 3) {
                batasAwal = 15;
                batasAkhir = 17;
                printSoal(batasAwal, batasAkhir);
        
            } else if (Levelgame == 4) {
                System.out.println(
                    """
                    ||===========================================================||
                    || Yakin ingin keluar game?                                  ||
                    || 1. Ya                                                     ||
                    || 2. Tidak                                                  ||
                    ||===========================================================||""");
                System.out.print("|| ");
                int choice = user.nextInt();
                while (choice <= 0 || choice > 2) {
                    System.out.println("|| PILIHAN TIDAK VALID                                       ||");
                    System.out.print("|| ");
                    choice = user.nextInt();
                    user.nextLine();
                }

                if (choice == 1) {
                    main(null);
                }

                continue;
                
            } 
            gameOver();
            System.out.print("|| Exit game");
            delay();
            
        }
        gameOver(); // Memanggil gameOver setelah keluar dari loop
        
    }

    /**
     * METHOD KALKULATOR CRYPTA
     * Memvalidasi apakah user kata memenuhi kriteria.
     * 
     * Fungsi ini memeriksa apakah kata yang di user hanya mengandung huruf 
     * tidak lebih dari 15 huruf berbeda. Fungsi ini akan mengembalikan `true` jika semua huruf yang 
     * digunakan dalam gabungan ketiga kata tersebut tidak lebih dari 15 huruf berbeda, 
     * serta mengembalikan `false` jika ada karakter selain huruf atau jika lebih dari 15 huruf yang berbeda digunakan.
     *
     * @param kata1 Kata pertama untuk digabungkan dalam pemeriksaan.
     * @param kata2 Kata kedua untuk digabungkan dalam pemeriksaan.
     * @param kataHasil Kata hasil untuk digabungkan dalam pemeriksaan.
     * @return `true` jika user valid (huruf tidak lebih dari 15 huruf berbeda), 
     *         `false` jika user tidak valid (terdapat lebih dari 15 huruf unik atau karakter selain huruf).
     */
    static boolean validasiuser(String kata1, String kata2, String kataHasil) {
        String gabung = kata1 + kata2 + kataHasil;
        boolean[] cek = new boolean[26];  
        int varHuruf = 0;
        
        for (int i = 0; i < gabung.length(); i++) {
            char c = gabung.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                if (!cek[c - 'A']) {
                    cek[c - 'A'] = true;
                    varHuruf++;
                    if (varHuruf > 15) {
                        return false;
                    }
                }
            } else {
                return false;  
            }
        }
        return true;
    }

    /**
     * METHOD KALKULATOR CRYPTA
     * Memvalidasi apakah kata hanya mengandung huruf kapital (A-Z).
     * 
     * Fungsi ini memeriksa apakah kata yang diberikan hanya terdiri dari huruf kapital (A-Z).
     * Jika kata mengandung karakter selain huruf kapital, fungsi ini akan mengembalikan `false`.
     * Jika kata hanya mengandung huruf kapital, maka fungsi ini akan mengembalikan `true`.
     *
     * @param kata Kata yang akan divalidasi.
     * @return `true` jika kata hanya mengandung huruf kapital (A-Z), 
     *         `false` jika kata mengandung karakter selain huruf kapital.
     */
    static boolean validasiKata(String kata) {
        for (int i = 0; i < kata.length(); i++) {
            char c = kata.charAt(i);
            if (c < 'A' || c > 'Z') {
                return false;  
            }
        }
        return true;
    }

    /**
     * METHOD KALKULATOR CRYPTA
     * Menemukan dan menyimpan huruf-huruf yang terdapat dalam sebuah string.
     * 
     * Fungsi ini akan mencari huruf-huruf unik dalam string yang diberikan (gabung), 
     * memastikan setiap huruf hanya dimasukkan satu kali, dan menyimpannya dalam array `varHuruf`. 
     * Setiap huruf unik yang ditemukan akan disimpan dalam urutan yang ditemukan pertama kali.
     * Fungsi ini juga menghitung banyaknya huruf unik yang ditemukan dan menyimpannya di dalam variabel `banyakVarHuruf`.
     *
     * @param gabung String yang berisi huruf-huruf yang akan diperiksa.
     */
    static void temukanVarHuruf(String gabung) {
        banyakVarHuruf = 0;
        boolean[] cek = new boolean[26];  
        
        for (int i = 0; i < gabung.length(); i++) {
            char c = gabung.charAt(i);
            if (!cek[c - 'A']) {
                cek[c - 'A'] = true;
                varHuruf[banyakVarHuruf] = c;
                banyakVarHuruf++;
            }
        }
    }

     /**
      * METHOD KALKULATOR CRYPTA
     * Mengembalikan nilai yang terkait dengan huruf tertentu.
     * 
     * Fungsi ini mencari huruf yang diberikan dalam array `varHuruf` dan mengembalikan nilai yang sesuai dari array `nilaiHuruf` 
     * yang memiliki indeks yang sama. 
     * Jika huruf yang dicari tidak ditemukan, fungsi ini akan mengembalikan nilai -1.
     *
     * @param huruf Huruf yang akan dicari dalam array `varHuruf`.
     * @return Nilai yang terkait dengan huruf yang ditemukan dalam array `nilaiHuruf`, atau -1 jika huruf tidak ditemukan.
     */
    static int kasiNilai(char huruf) {
        for (int i = 0; i < banyakVarHuruf; i++) {
            if (varHuruf[i] == huruf) {
                return nilaiHuruf[i];
            }
        }
        return -1;
    }

    /**
     * METHOD KALKULATOR CRYPTA
     * Mengonversi sebuah string yang berisi huruf-huruf menjadi angka berdasarkan nilai yang terkait dengan masing-masing huruf.
     * 
     * Fungsi ini mengiterasi setiap karakter dalam string `word`, kemudian mencari nilai terkait untuk setiap huruf 
     * menggunakan fungsi `kasiNilai()`.Setiap nilai yang ditemukan kemudian digabungkan untuk membentuk angka dengan cara 
     * mengalikan nilai sebelumnya dengan 10 dan menambahkannya dengan nilai baru.
     * 
     * @param word String yang berisi huruf-huruf yang akan dikonversi menjadi angka.
     * @return Angka yang terbentuk dari gabungan nilai huruf-huruf dalam string, atau -1 jika terdapat huruf yang 
     * tidak terdaftar di dalam nilai huruf.
     */
    static int hurufKeAngka(String word) {
        int number = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            number = number * 10 + kasiNilai(c);
        }
        return number;
    }

    /**
     * METHOD KALKULATOR CRYPTA
     * Menampilkan solusi dari permainan atau teka-teki, termasuk nilai setiap huruf yang ditemukan.
     * 
     * Fungsi ini akan menampilkan pesan yang memberitahukan bahwa jawaban telah ditemukan. Kemudian, untuk setiap huruf yang 
     * telah teridentifikasi dalam `varHuruf`, 
     * nilai yang terkait dengan huruf tersebut akan ditampilkan bersama dengan hurufnya. Fungsi ini mengasumsikan bahwa 
     * variabel `banyakVarHuruf`,`varHuruf`, dan `nilaiHuruf` sudah terisi sebelumnya.
     */
    static void printPenyelesaian() {
        System.out.println("||===========================================================||");
        System.out.println("|| Yeyy jawabannya ketemu!!                                  ||");
        System.out.println("||                                                           ||");
        System.out.println("|| Nilai setiap huruf adalah:                                ||");
        for (int i = 0; i < banyakVarHuruf; i++) {
            System.out.printf("|| %-3s = %-3d                                                 ||\n", varHuruf[i], nilaiHuruf[i]);
        }
        System.out.println("||                                                           ||");
    }

    /**
     * METHOD KALKULATOR CRYPTA (METHOD INTI KALKULATOR)
     * Fungsi untuk menyelesaikan teka-teki matematis berdasarkan kata yang diberikan. Fungsi ini akan mencoba untuk 
     * menyelesaikan teka-teki dengan menggantikan huruf-huruf pada kata yang diberikan dengan angka dan memeriksa apakah
     * hasilnya sesuai dengan operasi yang diberikan.
     * 
     * @param position Posisi huruf yang sedang diproses.
     * @param kata1 Kata pertama yang terlibat dalam operasi matematis.
     * @param kata2 Kata kedua yang terlibat dalam operasi matematis.
     * @param kataHasil Hasil dari operasi matematis.
     * @param operator Operator matematika yang digunakan (+, -, x).
     * @return boolean Mengembalikan true jika penyelesaian ditemukan, false jika tidak.
     * 
     * Fungsi ini melakukan rekursi untuk mencoba setiap kombinasi angka yang mungkin untuk huruf dalam kata, 
     * kemudian memeriksa apakah hasil perhitungan sesuai dengan hasil yang diinginkan. 
     */
    static boolean penyelesaian(int position, String kata1, String kata2, String kataHasil, String operator) {
        if (position == banyakVarHuruf) {
            
            if (kasiNilai(kata1.charAt(0)) == 0 ||
                kasiNilai(kata2.charAt(0)) == 0 ) {
                return false;
            }
            //|| kasiNilai(kataHasil.charAt(0)) == 0
            int num1 = hurufKeAngka(kata1);
            int num2 = hurufKeAngka(kata2);
            int sum = hurufKeAngka(kataHasil);
            
            if (operator.equals("+") && num1 + num2 == sum) {
                printPenyelesaian();
                return true;
            } else if (operator.equals("-") && num1 - num2 == sum) {
                if (sum == 0){
                    printPenyelesaian();
                    return true;
                }
                printPenyelesaian();
                return true;
            } else if (operator.equals("x") && num1 * num2 == sum) {
                printPenyelesaian();
                return true;
            }
            return false;
        }
        
        for (int i = 0; i < 10; i++) {
            if (!penggunaanDigit[i]) {
                penggunaanDigit[i] = true;
                nilaiHuruf[position] = i;
                
                if (penyelesaian(position + 1, kata1, kata2, kataHasil, operator)) {
                    return true;
                }
                
                penggunaanDigit[i] = false;
            }
        }
        return false;
    }

    /**
     * Fungsi utama untuk menjalankan kalkulator enkripsi yang memecahkan teka-teki berdasarkan kata dan operator.
     * Fungsi ini meminta input dari pengguna berupa dua kata dan operator matematika, kemudian mencari nilai 
     * huruf yang memenuhi persamaan yang diberikan. Fungsi ini juga menyediakan loop untuk mengulangi perhitungan 
     * atau keluar sesuai pilihan pengguna.
     * 
     * Alur kerja fungsi:
     * 1. Meminta pengguna untuk memasukkan kata pertama, kata kedua, operator, dan kata hasil.
     * 2. Validasi input yang dimasukkan untuk memastikan kata hanya berisi huruf kapital dan operator valid.
     * 3. Memanggil fungsi untuk menyelesaikan teka-teki dan mencari penyelesaian dari persamaan yang diberikan.
     * 4. Jika penyelesaian ditemukan, hasil akan ditampilkan. Jika tidak, pemberitahuan bahwa tidak ada solusi.
     * 5. Memungkinkan pengguna untuk mencoba soal lain atau keluar dari kalkulator.
     * 
     * Setelah penyelesaian selesai atau tidak ditemukan, pengguna diberikan pilihan untuk mencoba soal lain atau keluar.
     */
    public static void kalkulatorRun (){
        String kata1, kata2, kataHasil, operator;

        while (true) {
            //reset kalkulator
            penggunaanDigit = new boolean[15];     
            varHuruf = new char[15];                  
            nilaiHuruf = new int[15];                       
            banyakVarHuruf = 0;     
            
            do {
                System.out.println(
                    """
                    ||===========================================================||
                    ||       SILAHKAN INPUT SESUAI DENGAN PERINTAH SISTEM        ||
                    ||===========================================================||"""
                );
                System.out.print("\n|| Masukkan kata pertama :");
                kata1 = user.nextLine().toUpperCase();

                while ((!(validasiKata(kata1)))) {
                    System.out.println("\n|| HEY HEYY INPUT YANG BENER");
                    System.out.print("\n|| Masukkan kata pertama :");
                    kata1 = user.nextLine().toUpperCase();
                }
                
                System.out.print("\n|| Masukkan kata kedua :");
                kata2 = user.nextLine().toUpperCase();

                while ((!(validasiKata(kata2)))) {
                    System.out.println("\n|| HEY HEYY INPUT YANG BENER");
                    System.out.print("\n|| Masukkan kata kedua :");
                    kata2 = user.nextLine().toUpperCase();
                }

                System.out.print("\n|| Masukkan operator (+ atau - atau x):");
                operator = user.nextLine();

                    while (!(operator.equals("+") || operator.equals("-") || operator.equals("x"))) {
                        System.out.println("\n|| Operator macam apa itu?? Ga kenal aku :(\n|| ");
                        System.out.println("|| INPUT YANG BENER!!");
                        System.out.print("\n|| Masukkan operator (+ atau - atau x):");
                        operator = user.nextLine();
                    }

                System.out.print("\n|| Masukkan kata hasil :");
                kataHasil = user.nextLine().toUpperCase();
                while ((!(validasiKata(kataHasil)))) {
                    System.out.println("\n|| HEY HEYY INPUT YANG BENER");
                    System.out.print("\n|| Masukkan kata hasil :");
                    kataHasil = user.nextLine().toUpperCase();
                }
                
                if (!validasiuser(kata1, kata2, kataHasil)) {
                    System.out.println("||===========================================================||");
                    System.out.println("\n|| WADUH.. VARIABELNYA KEBANYAKAN :( input soal lain ya..    ||");
                }
                
            } while (!validasiuser(kata1, kata2, kataHasil));

            System.out.println("\n||===========================================================||");
            System.out.println("|| Owkeyy mari kita cari penyelesaian soal ini               ||");
            System.out.println("||   " + kata1);
            System.out.println("|| "+ operator + " " + kata2);
            System.out.println("|| = " + kataHasil);
            System.out.print("|| Bot Sedang Berpikir");
            delay();

            temukanVarHuruf(kata1 + kata2 + kataHasil);
            
            boolean hasSolution = false;
            hasSolution = penyelesaian(0, kata1, kata2, kataHasil, operator);
        
            if (!hasSolution) {
                System.out.println("||===========================================================||");
                System.out.println("|| Soal macam apa itu?? ga ada solusinya :(                  ||\n");
            }

            System.out.println(
                """
                ||===========================================================||
                || Ingin menyelesaikan soal lagi??                           ||
                || 1. Ya                                                     ||
                || 2. Exit Kalkulator                                        ||
                ||===========================================================||
                """
            );
            System.out.print("|| ");
            int pilihan = user.nextInt();
            user.nextLine();

            while (pilihan <= 0 || pilihan > 2) {
                System.out.println("|| Pilihan Tidak Valid");
                System.out.print("|| ");
                pilihan = user.nextInt();
                user.nextLine();
            }

            if (pilihan == 1){        
                continue;

            } else{
                System.out.println(
                    """
                    ||===========================================================||
                    ||      TERIMAKASIH TELAH MENGGUNAKAN KALKULATOR CRYPTA      ||
                    ||===========================================================||
                    """
                );
                break;
            }  
        }
    }

    /**
     * Fungsi utama yang menjalankan aplikasi Cryptarithm. Program ini menawarkan dua fitur utama:
     * 1. Game Cryptarithm - Pemain harus memecahkan soal cryptarithm dengan mengikuti aturan permainan.
     * 2. Kalkulator Cryptarithm - Pengguna dapat menghitung solusi dari soal cryptarithm yang diberikan.
     * 
     * Program akan terus berjalan dalam sebuah loop hingga pengguna memilih untuk keluar.
     * 
     * Alur Kerja:
     * - Menu utama menampilkan tiga pilihan: Game Cryptarithm, Kalkulator Cryptarithm, atau keluar dari aplikasi.
     * - Jika pengguna memilih Game Cryptarithm, permainan dimulai dengan aturan tertentu.
     * - Jika memilih Kalkulator Cryptarithm, pengguna dapat memecahkan soal cryptarithm dengan variabel huruf.
     * - Program meminta pengguna untuk memilih validasi dan melanjutkan ke bagian yang sesuai.
     * - Jika pengguna memilih untuk keluar (pilihan 3), program akan berhenti dan menampilkan pesan penutupan.
    */
    public static void main(String[] args) {
        int pilihan;
        int choice;
        while (true) {
            System.out.println("""

                ||===========================================================||
                ||   = === =  SELAMAT DATANG DI CRYPTARITHM APPS   = === =   ||
                ||===========================================================||
                || Cryptarithm Apps adalah sebuah program yang terdiri dari  ||
                || dua bagian yaitu CRYPTARITH MGAME dan KALKULATOR          ||
                || CRYPTARITHM                                               ||
                ||                                                           ||
                || Anda ingin masuk ke menu apa?                             ||
                || 1. Game Cryptarithm                                       ||
                || 2. Kalkulator Cryptarithm                                 ||
                || 3. Exit Apps                                              ||
                ||===========================================================||"""
            );

            System.out.print("|| ");
            pilihan = user.nextInt();
            user.nextLine();

            while (pilihan <= 0 || pilihan > 3) {
            System.out.println("|| PILIHAN TIDAK VALID                                       ||");
            System.out.println("|| Masukkan Pilihan Yang Benar                               || ");
            System.out.print("|| ");
            pilihan = user.nextInt();
            user.nextLine();
            }

            if (pilihan == 1) {
                System.out.println(
                """
                        ||===========================================================||
                        ||                     GAME CRYPTARITHM                      ||
                        ||===========================================================||
                        || Aturan permainan:                                         ||
                        || >> Anda memiliki 3 Lives                                  ||
                        || >> Setiap jawaban benar mendapat 10 poin                  ||
                        || >> Jawaban salah mengurangi 1 nyawa                       ||
                        || >> Ketik 'hint' untuk mendapat petunjuk                   ||
                        || >> Ketik 'exit' untuk kembali ke menu utama               ||
                        || >> Ketik 'skip' untuk skip soal"                          ||
                        ||===========================================================||
                        || Lanjut / Exit?                                            ||
                        || 1. Lanjut                                                 ||
                        || 2. Exit                                                   ||
                        ||===========================================================||""");
                System.out.print("|| ");
                choice = user.nextInt();
                user.nextLine();

                while (choice <= 0 || choice > 2) {
                    System.out.println("|| PILIHAN TIDAK VALID                                       ||");
                    System.out.print("|| ");
                    pilihan = user.nextInt();
                    user.nextLine();
                }

                if (choice == 1) {
                    GameRun();
                }

                continue;

            } else if (pilihan == 2) {
                System.out.println(
                    """
                    ||===========================================================||
                    ||                   KALKULATOR CRYPTARITHM                  ||
                    ||===========================================================||
                    || Kalkulator ini akan memberikan solusi dari soal           ||
                    || cryptarithm yang hanya terdiri dari satu operari, dimana  ||
                    || variabel huruf dalam soal tidak lebih dari 15 variabel    ||
                    || huruf                                                     ||
                    ||                                                           ||
                    || Contoh kasus:                                             ||
                    || ABCDEF + GHIJK = LMNOPQ                                   ||
                    || tidak akan diproses karena banyak variabel huruf dalam    ||
                    || soal lebih dari 15.                                       ||
                    ||                                                           ||
                    || Jika kasus tersebut terjadi, program akan meminta user    ||
                    || untuk menginput ulang soal yang sesuai dengan kriteria    ||
                    || program.                                                  ||
                    ||                                                           ||
                    || 1. Lanjut                                                 ||
                    || 2. Exit                                                   ||
                    ||===========================================================||""");
                System.out.print("|| ");
                choice = user.nextInt();
                user.nextLine();

                while (choice <= 0 || choice > 2) {
                    System.out.println("|| PILIHAN TIDAK VALID                                       ||");
                    System.out.println("|| Masukkan Pilihan Yang Benar                               || ");
                    System.out.print("|| ");
                    pilihan = user.nextInt();
                    user.nextLine();
                }

                if (choice == 1) {
                    kalkulatorRun();
                }

                continue;

            } else if (pilihan == 3) {
                //user.nextLine();
                System.out.print(
                    """
                    ||===========================================================||
                    ||      TERIMAKASIH TELAH MENGGUNAKAN CRYPTARITHM APPS       ||
                    ||===========================================================||
            
                    Mengakhiri Program""");
                delay();
                System.exit(0);
                break;
            }   
        
        user.close();  
        }
    }
}
