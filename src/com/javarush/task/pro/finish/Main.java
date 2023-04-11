package com.javarush.task.pro.finish;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.task.pro.finish.Alphabet.ALPHABET;

public class Main {
    private static final String MENU = "Выберете режим работы \"1\" - зашифрование \"2\" - разшифрование,\n" +
            " \"3\" - разшифрование методом BruteForse, для выхода введите \"exit\".";

    public static void main(String[] args) {

        System.out.println(MENU);
        Scanner console = new Scanner(System.in);
        String mode;
        while (!(mode = console.next()).equals("exit")) {
            switch (mode) {
                case "1" -> caseEncypt();
                case "2" -> caseDecrypt();
                case "3" -> caseDecryptBruteForce();
                default -> System.out.println("Некоректно введены данные");
            }
            System.out.println(MENU);
        }
    }

    protected static void caseEncypt() {
        System.out.println("Введете путь к файлу...");
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        System.out.printf("Введите ключ шифрования от 1 до %d \n", ALPHABET.length() - 1);
        int key = 0;
        while (!scanner.hasNextInt()) {
            System.out.printf("Введите ключ шифрования от 1 до %d \n", ALPHABET.length() - 1);
        }
        key = scanner.nextInt();
        Encrypt.encrypt(path, key);

    }

    protected static void caseDecrypt() {
        System.out.println("Введете путь к файлу...");
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        System.out.println("Введите известный ключ шифрования");
        int key = 0;
        while (!scanner.hasNextInt()) {
            System.out.println("Введите известный ключ шифрования");
        }
        key = scanner.nextInt();
        Decrypt.decryptFile(path, key);
    }

    protected static void caseDecryptBruteForce() {
        System.out.println("Введете путь к файлу...");
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        Decrypt.decryptBruteForce(path);
    }
}




