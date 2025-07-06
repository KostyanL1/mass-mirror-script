package org.legenkiy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    public String initInputDir() {
        String result = "";
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            File propsFile = new File(currentDir, "props.txt");
            BufferedReader fileReader = new BufferedReader(new FileReader(propsFile));
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (line.startsWith("INPUT:")) {
                    result = line.substring(6).trim();
                    System.out.println(drawBox("Input directory loaded:\n" + result));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String initOutputDir() {
        String result = "";
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            File propsFile = new File(currentDir, "props.txt");
            BufferedReader fileReader = new BufferedReader(new FileReader(propsFile));
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (line.startsWith("OUTPUT:")) {
                    result = line.substring(7).trim();
                    System.out.println(drawBox("Output directory loaded:\n" + result));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        Main main = new Main();

        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            System.out.println(drawBox("Application running from:\n" + jarFile.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        File inputDir = new File(main.initInputDir());
        File outputDir = new File(main.initOutputDir());

        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            if (created) {
                System.out.println(drawBox("Output directory was created."));
            } else {
                System.out.println(drawBox("Failed to create output directory."));
            }
        }

        File[] files = inputDir.listFiles((dir, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")
                    || lower.endsWith(".bmp") || lower.endsWith(".gif") || lower.endsWith(".tif") || lower.endsWith(".webp");
        });

        if (files == null || files.length == 0) {
            System.out.println(drawBox("No supported images found in the input folder."));
            return;
        }

        System.out.println(drawBox("Found " + files.length + " image(s). Starting processing..."));

        for (File file : files) {
            try {
                BufferedImage original = ImageIO.read(file);
                if (original == null) {
                    System.out.println(drawBox("Skipped unreadable image: " + file.getName()));
                    continue;
                }

                BufferedImage mirrored = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = mirrored.createGraphics();
                g.drawImage(original, original.getWidth(), 0, -original.getWidth(), original.getHeight(), null);
                g.dispose();

                String baseName = file.getName().replaceAll("\\.[^.]+$", "");
                File output = new File(outputDir, "mirrored_" + baseName + ".png");

                boolean written = ImageIO.write(mirrored, "png", output);
                if (written) {
                    System.out.println(drawBox("Processed: " + file.getName()));
                } else {
                    System.out.println(drawBox("Failed to write: " + file.getName()));
                }

            } catch (IOException e) {
                System.out.println(drawBox("Error while processing file: " + file.getName()));
                e.printStackTrace();
            }
        }

        System.out.println(drawBox("All supported images have been successfully mirrored."));
    }

    private static String drawBox(String message) {
        String[] lines = message.split("\n");
        int maxLen = 0;
        for (String line : lines) {
            if (line.length() > maxLen) maxLen = line.length();
        }
        StringBuilder box = new StringBuilder();
        box.append("┌").append("─".repeat(maxLen + 2)).append("┐\n");
        for (String line : lines) {
            box.append("│ ").append(padRight(line, maxLen)).append(" │\n");
        }
        box.append("└").append("─".repeat(maxLen + 2)).append("┘\n");
        return box.toString();
    }

    private static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}
