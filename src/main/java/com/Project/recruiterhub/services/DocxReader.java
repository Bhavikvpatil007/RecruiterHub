package com.Project.recruiterhub.services;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class DocxReader {

    public static String readDocument(MultipartFile file) throws IOException {
        String filename = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        if (filename.endsWith(".docx")) {
            return readDocxFile(file);
        } else if (filename.endsWith(".pdf")) {
            return readPdfFile(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
    }
    private static String readDocxFile(MultipartFile file) throws IOException {
        StringBuilder content = new StringBuilder();

        try (InputStream fis = file.getInputStream();
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    if (run.getText(0) != null)
                        content.append(run.getText(0));
                }
                content.append("\n");
            }
        }

        return content.toString();
    }

    private static String readPdfFile(MultipartFile file) throws IOException {
        try (InputStream fis = file.getInputStream();
             PDDocument document = PDDocument.load(fis)) {

            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }
}
