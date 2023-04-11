package com.javarush.task.pro.finish;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.javarush.task.pro.finish.Alphabet.ALPHABET;


public class Decrypt {
    /**
     * раcшифрование файла методом brute force
     *
     * @param path - абсолютный путь к зашифрованному файлу
     */
    public static void decryptBruteForce(Path path) {
        File file = new File(String.valueOf(path));
        if (file.isFile()) {
            String resultDecrypt = "";
            for (int key = 0; key < ALPHABET.length(); key++) {
                resultDecrypt = decryptData(path, key);
                if (checkDecryptFile(resultDecrypt)) {
                    int resultKey = (ALPHABET.length() - key) % ALPHABET.length();
                    System.out.println("Ключ расшифрования " + resultKey);
                    String nameDecryptFile = getNameDecryptFile(path.toString());
                    Path decryptFile = Path.of(nameDecryptFile);
                    printFile(resultDecrypt, decryptFile);
                }
            }
        } else {
            System.out.println("Файл не существует");
            Main.caseDecryptBruteForce();
        }
    }

    /**
     * раcшифрование файла в случае если известный ключ шифрования
     *
     * @param path - абсолютный путь к зашифрованному файлу
     * @param key  - ключ шифрования
     */
    public static void decryptFile(Path path, int key) {
        File file = new File(String.valueOf(path));
        if (file.isFile()) {
            String resultDecrypt = "";
            resultDecrypt = decryptData(path, ALPHABET.length() - key);
            if (checkDecryptFile(resultDecrypt)) {
                String nameDecryptFile = getNameDecryptFile(path.toString());
                Path decryptFile = Path.of(nameDecryptFile);
                printFile(resultDecrypt, decryptFile);
                System.out.println("Успешно");
            } else {
                System.out.println("Ключ не подошел.");
            }
        } else {
            System.out.println("Файл не существует");
            Main.caseDecrypt();
        }

    }

    /**
     * метод расшифрования данных
     *
     * @param path - абсолютный путь к зашифрованному файлу
     * @param key  - ключ шифрования
     * @return - возвращвет расшифрованные данные ключем key
     */
    private static String decryptData(Path path, int key) {
        StringBuilder bufferedWriter = new StringBuilder();
        try (BufferedReader files = Files.newBufferedReader(path)) {
            while (files.ready()) {
                int oldChar = files.read();
                int index = ALPHABET.indexOf(oldChar);
                int newIndex = (index + key) % ALPHABET.length();
                if (index >= 0) {
                    bufferedWriter.append(ALPHABET.charAt(newIndex));
                } else {
                    bufferedWriter.append((char) oldChar);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter.toString();
    }

    /**
     * проверка расшифрованного файла
     */
    private static boolean checkDecryptFile(String bufferedWriter) {
        String[] strings = bufferedWriter.split("[ ]+|\n");
        for (String string : strings) {
            // рекурентное выражение для слов
            Pattern r = Pattern.compile("^[А-ЯA-Z]?[\"]?[0-9]*[а-яa-z]*[-]?[а-яa-z]*([.]|[,]|[!]|[?]|[:]|[-]|[;]|[\"])?$");
            Matcher matcher = r.matcher(string);
            if (!matcher.find()) {
                return false;
            }
        }
        return true;
    }

    /**
     * получение имени расшифрованного файла
     *
     * @param oldFile - имя зашифрованного файла
     * @return - имя расшифрованного файла
     */
    private static String getNameDecryptFile(String oldFile) {
        int dotIndex = oldFile.lastIndexOf(".");
        return oldFile.substring(0, dotIndex) + "Decrypt" + oldFile.substring(dotIndex);
    }

    /**
     * вывод результата раcшифрования
     *
     * @param resultData - расшифрованные данные
     * @param path       - абсолютный путь файла куда будут записаны расшифрованные данные
     */
    private static void printFile(String resultData, Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter(path.toFile())) {
            fileWriter.write(resultData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Расшифрованный файл " + path);
    }
}
