package by.varyvoda.matvey.common.command_line;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommandLine {

    private static final Scanner in = new Scanner(System.in);
    private static final Lock writeLock = new ReentrantLock();
    private static final Lock readLock = new ReentrantLock();
    private static String inputMessage = "";

    public static void setInputMessage(String inputMessage) {
        writeLock.lock();
        clearInputMessage();
        CommandLine.inputMessage = inputMessage;
        printInputField();
        writeLock.unlock();
    }

    public static void println(String line) {
        writeLock.lock();
        clearInputMessage();
        System.out.println(line);
        printInputField();
        writeLock.unlock();
    }

    public static void printStackTrace(Exception e) {
        writeLock.lock();
        clearInputMessage();
        e.printStackTrace(System.out);
        printInputField();
        writeLock.unlock();
    }

    public static String readLine() throws IOException {
        readLock.lock();
        String line = in.nextLine();
        printInputField();
        readLock.unlock();
        return line;
    }

    private static void printInputField() {
        System.out.print(inputMessage);
        System.out.flush();
    }

    private static void clearInputMessage() {
        System.out.print("\r" + " ".repeat(inputMessage.length()) + "\r");
        System.out.flush();
    }
}
