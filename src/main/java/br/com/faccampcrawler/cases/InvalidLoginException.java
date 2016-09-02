package br.com.faccampcrawler.cases;

public class InvalidLoginException extends Exception {
    public InvalidLoginException() {
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
