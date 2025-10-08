package com.beymen.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtil {
    private static final Logger logger = LogManager.getLogger(FileUtil.class);
    private static final String OUTPUT_DIR = "test-output";

    public static void writeProductInfo(String productName, String productPrice) {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = OUTPUT_DIR + "/product_info_" + timestamp + ".txt";

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("=== ÜRÜN BİLGİLERİ ===\n\n");
                writer.write("Ürün Adı: " + productName + "\n");
                writer.write("Ürün Fiyatı: " + productPrice + "\n");
                writer.write("\nTarih: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n");

                logger.info("Product info written to file: " + fileName);
            }
        } catch (IOException e) {
            logger.error("Error writing to file", e);
        }
    }
}