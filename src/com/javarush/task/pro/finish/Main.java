package com.javarush.task.pro.finish;

import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.task.pro.finish.Alphabet.ALPHABET;

public class Main {
    private static final String MENU = "Выбирете режим работы\n \"1\" - зашифрование шифром Цезаря,\n \"2\" - раcшифрование шифром Цезаря,\n" +
            " \"3\" - разшифрование методом BruteForse,\n для выхода введите \"exit\".";

    /**
     * Меню программы
     */
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

    /**
     * режим зашифрования файла
     */
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
        if (key >= 0 && key <= ALPHABET.length() - 1) {
            Encrypt.encrypt(path, key);
        }else{
            System.out.println("Некорректный ключ");
        }
    }

    /**
     * Режим раcшифрования
     */
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
        if (key >= 0 && key <= ALPHABET.length() - 1 ) {
            Decrypt.decryptFile(path, key);
        }
        else{
            System.out.println("Некорректный ключ");
        }
    }

    /**
     * Режим раcшифрования методм brute force
     */
    protected static void caseDecryptBruteForce() {
        System.out.println("Введете путь к файлу...");
        Scanner scanner = new Scanner(System.in);
        Path path = Path.of(scanner.nextLine());
        Decrypt.decryptBruteForce(path);
    }
}




