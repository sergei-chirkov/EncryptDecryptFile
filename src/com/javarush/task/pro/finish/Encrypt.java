package com.javarush.task.pro.finish;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.javarush.task.pro.finish.Alphabet.ALPHABET;

public class Encrypt {
    public static void encrypt(Path path, int key) {
        try (BufferedReader fileReader = Files.newBufferedReader(path)) {
            String nameEncryptFile = getNameEncryptFile(path.toString());
            Path encryptFile = Path.of(nameEncryptFile);
            if (Files.notExists(encryptFile)) {
                Files.createFile(encryptFile);
            }
            try (FileWriter fileWriter = new FileWriter(encryptFile.toFile())) {
                while (fileReader.ready()) {
                    int data = fileReader.read();
                    int outputData = encryptData(data, key);
                    fileWriter.write(outputData);
                }
                printResult(nameEncryptFile);
            }
        } catch (IOException e) {
            System.out.println("Файл не существует");
            Main.caseEncypt();
        }

    }


    /*
     * метод получения имени зашифрованного файла
     */
    private static String getNameEncryptFile(String oldFile) {
        int dotIndex = oldFile.lastIndexOf(".");
        return oldFile.substring(0, dotIndex) + "Encrypt" + oldFile.substring(dotIndex);
    }

    /*
     * метод зашифрования информации
     */
    private static int encryptData(int data, int key) {
        int index = ALPHABET.indexOf((char) data);
        int result = data;
        if (index >= 0) {
            int newIndex = (index + key) % ALPHABET.length();
            result = ALPHABET.charAt(newIndex);
        }
        return result;
    }

    /*
     * Вывод результата работы
     */
    private static void printResult(String nameEncryptFile) {
        System.out.println("Зашифрованный файл " + nameEncryptFile);
    }
}
