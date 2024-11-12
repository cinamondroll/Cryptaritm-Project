//Kalkulator Cryptarithm ini masih 70%, dan merupakan 1/3 dari keseluruhan project sehingga masih akan terjadi perubahan
//sampai batas akhir waktu pengumpulan

import java.util.Scanner;

public class KalkulatorCrypta {

    static boolean[] penggunaanDigit = new boolean[10];
    static char[] varHuruf = new char[10];  
    static int[] nilaiHuruf = new int[10];           
    static int banyakVarHuruf = 0;

    static boolean validasiInput(String kata1, String kata2, String kataHasil) {
        String gabung = kata1 + kata2 + kataHasil;
        boolean[] cek = new boolean[26];  
        int varHuruf = 0;
        
        for (int i = 0; i < gabung.length(); i++) {
            char c = gabung.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                if (!cek[c - 'A']) {
                    cek[c - 'A'] = true;
                    varHuruf++;
                    if (varHuruf > 10) {
                        return false;
                    }
                }
            } else {
                return false;  
            }
        }
        return true;
    }

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

    static int kasiNilai(char huruf) {
        for (int i = 0; i < banyakVarHuruf; i++) {
            if (varHuruf[i] == huruf) {
                return nilaiHuruf[i];
            }
        }
        return -1;
    }
    
    static int hurufKeAngka(String word) {
        int number = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            number = number * 10 + kasiNilai(c);
        }
        return number;
    }

    static void printPenyelesaian() {
        System.out.println("\nYeyy jawabannya ketemu!!\n");
        System.out.println("Nilai setiap huruf adalah: ");
        for (int i = 0; i < banyakVarHuruf; i++) {
            System.out.println(varHuruf[i] + " = " + nilaiHuruf[i]);
        }
        System.out.println(" ");
    }

    static boolean penyelesaian(int position, String kata1, String kata2, String kataHasil, String operator) {
        if (position == banyakVarHuruf) {
            
            if (kasiNilai(kata1.charAt(0)) == 0 ||
                kasiNilai(kata2.charAt(0)) == 0 ||
                kasiNilai(kataHasil.charAt(0)) == 0) {
                return false;
            }
            
            int num1 = hurufKeAngka(kata1);
            int num2 = hurufKeAngka(kata2);
            int sum = hurufKeAngka(kataHasil);
            
            if (operator.equals("+")){
                if (num1 + num2 == sum) {
                    printPenyelesaian();
                    return true;
                }
            } else if (operator.equals("-")){
                if (num1 - num2 == sum) {
                    printPenyelesaian();
                    return true;
                }
            } else if (operator.equals("x")){
                if (num1 * num2 == sum) {
                    printPenyelesaian();
                    return true;
                }
            } else {
                
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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        String kata1, kata2, kataHasil, operator;

        System.out.println("\n======== KALKULATOR CRYPTARITHM ========");
        System.out.println("Kalkulator ini akan memberikan solusi \ndari soal cryptarithm yang hanya terdiri \ndari satu operasi");
        System.out.println(" \nPerlu diketahui bahwa kalkulator ini \nhanya bisa menyelesaikan soal-soal yang \nterdiri dari 1-10 variabel huruf");
        System.out.println("contohnya ABC+DEF=GHIJ, jika lebih dari \n10 jenis variabel huruf maka program \nakan meminta input kembali");



        do {
            System.out.println("\nMasukkan kata pertama :");
            kata1 = input.nextLine().toUpperCase();
            
            System.out.println("\nMasukkan kata kedua :");
            kata2 = input.nextLine().toUpperCase();

            System.out.println("\nMasukkan operator (+ atau - atau x):");
            operator = input.nextLine();

                while (!(operator.equals("+") || operator.equals("-") || operator.equals("x"))) {
                    System.out.println("\nOperator macam apa itu?? Ga kenal aku :(\n");
                    System.out.println("INPUT YANG BENER!!");
                    operator = input.nextLine();
                }

            System.out.println("\nMasukkan kata hasil :");
            kataHasil = input.nextLine().toUpperCase();
            
            if (!validasiInput(kata1, kata2, kataHasil)) {
                System.out.println("\nHEY HEYY INPUT YANG BENER");
            }
            
        } while (!validasiInput(kata1, kata2, kataHasil));
        
        System.out.println("\nOwkeyy mari kita cari penyelesaian soal ini");
        System.out.println("  " + kata1);
        System.out.println(operator + " " + kata2);
        System.out.println("= " + kataHasil);
        System.out.println("");

        temukanVarHuruf(kata1 + kata2 + kataHasil);
        
        boolean hasSolution = false;
        hasSolution = penyelesaian(0, kata1, kata2, kataHasil, operator);
    
        if (!hasSolution) {
            System.out.println("Soal macam apa itu?? ga ada solusinya :( \n");
        }

        input.close();
    }
    
}
