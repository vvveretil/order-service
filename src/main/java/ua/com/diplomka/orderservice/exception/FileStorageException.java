package ua.com.diplomka.orderservice.exception;

import java.nio.file.Path;

public class FileStorageException extends RuntimeException {

    public FileStorageException(Path file, Throwable throwable) {
        super("Failed to store file: " + file, throwable);
    }

    public FileStorageException(String message) {
        super(message);
    }
}
