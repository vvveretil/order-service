package ua.com.diplomka.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.com.diplomka.orderservice.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${orders.directory}")
    private String ordersDirectory;

    public List<Path> saveOrderFiles(Long orderId, List<MultipartFile> files) {
        String orderDirectoryName = String.valueOf(orderId);
        Path orderDirectory = Paths.get(ordersDirectory).resolve(orderDirectoryName);

        if (!Files.exists(orderDirectory)) {
            createOrderDirectory(orderDirectory);
        }

        return copyToFolder(orderDirectory, files);
    }

    private List<Path> copyToFolder(Path orderDirectory, List<MultipartFile> files) {
        return files.stream()
                .map(file -> copyToFolder(orderDirectory, file))
                .toList();
    }

    private Path copyToFolder(Path orderDirectory, MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (!StringUtils.hasLength(fileName)) {
            throw new FileStorageException("Uploaded file name is empty");
        }

        Path targetPath = orderDirectory.resolve(fileName);

        try {
            file.transferTo(targetPath);
            return targetPath;
        } catch (IOException e) {
            throw new FileStorageException(targetPath, e);
        }
    }

    private void createOrderDirectory(Path orderDirectory) {
        try {
            Files.createDirectory(orderDirectory);
        } catch (IOException e) {
            log.error("Failed to create order directory", e);
        }
    }
}
