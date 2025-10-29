package com.Project.recruiterhub.services;

import com.Project.recruiterhub.model.WorkExperience;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class ExperienceExtractor {
    public static String extractTextFromPDF(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public List<WorkExperience> processResumeText(String text, CRFClassifier<CoreLabel> classifier) {
        String[] lines = text.split("\\n");
        List<WorkExperience> experienceList = new ArrayList<>();

        StringBuilder block = new StringBuilder();
        String lastCompany = "", lastPosition = "", lastDuration = "";

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            block.append(line).append(" ");
            // trigger tagging on enough content or end of file
            if (line.endsWith(".") || block.length() > 100 || i == lines.length - 1) {
                String sentence = block.toString().trim();

                String[] result = extractFields(sentence, classifier);
                String company = result[0].trim();
                String position = result[1].trim();
                String duration = result[2].trim();

                if (position.isEmpty()) {
                    for (int j = Math.max(0, i - 2); j < i; j++) {
                        String prevLine = lines[j].trim();
                        String[] prevResult = extractFields(prevLine, classifier);
                        if (!prevResult[1].trim().isEmpty()) {
                            position = prevResult[1].trim();
                            break;
                        }
                    }
                }

                if (duration.isEmpty()) {
                    for (int j = 1; j <= 2 && (i + j) < lines.length; j++) {
                        String nextLine = lines[i + j].trim();
                        String[] fallback = extractFields(nextLine, classifier);
                        if (!fallback[2].trim().isEmpty()) {
                            duration = fallback[2].trim();
                            break;
                        }
                    }
                }


                boolean isDuplicate = company.equals(lastCompany)
                        && position.equals(lastPosition)
                        && duration.equals(lastDuration);

                if (!company.isEmpty() || !position.isEmpty() || !duration.isEmpty()) {
                    if (!isDuplicate && isValidExperience(company, position, duration)) {
                        WorkExperience exp = new WorkExperience(company, position, duration);
                        experienceList.add(exp);
                        lastCompany = company;
                        lastPosition = position;
                        lastDuration = duration;
                    }
                }
                block.setLength(0); // reset buffer
            }
        }

        if (experienceList.isEmpty()) {
            return null;
        } else {
            return experienceList;
        }
    }
    public static String[] extractFields(String line, CRFClassifier<CoreLabel> classifier) {
        String tagged = classifier.classifyWithInlineXML(line);
        String position = extractEntity(tagged, "POSITION");
        String company = extractEntity(tagged, "COMPANY");
        String duration = extractEntity(tagged, "DATE");
        return new String[]{company, position, duration};
    }
    public static String extractEntity(String tagged, String label) {
        Pattern pattern = Pattern.compile("<[BI]-" + label + ">(.+?)</[BI]-" + label + ">");
        Matcher matcher = pattern.matcher(tagged);
        List<String> dates = new ArrayList<>();
        while (matcher.find()) {
            dates.add(matcher.group(1).trim());
        }
        if (label.equals("DATE")) {
            if (tagged.toLowerCase().contains("present") && !dates.contains("Present")) {
                dates.add("Present");
            }
            if (tagged.toLowerCase().contains("now") && !dates.contains("Now")) {
                dates.add("Now");
            }
            List<String> formatted = new ArrayList<>();
            for (int i = 0; i < dates.size(); i += 2) {
                if (i + 1 < dates.size()) {
                    formatted.add(dates.get(i) + " " + dates.get(i + 1));
                } else {
                    formatted.add(dates.get(i));
                }
            }

            return String.join(" - ", formatted);
        }

        return String.join(" ", dates).trim();
    }
    public static boolean isValidExperience(String company, String position, String duration) {
        int filled = 0;
        if (!company.isEmpty()) filled++;
        if (!position.isEmpty()) filled++;
        if (!duration.isEmpty()) filled++;

        if (filled < 2) return false;
        if (!position.isEmpty()) return true;
        if (position.toLowerCase().contains("academic qualifications") || company.toLowerCase().contains("academic qualifications")) {
            return false;
        }
        if (duration.toLowerCase().contains("university") || duration.toLowerCase().contains("bachelor") || duration.matches(".*\\d{4}.*\\d{4}.*")) {
            return false;
        }
        if (position.isEmpty() && !company.isEmpty() && !duration.isEmpty()) {
            if (duration.equalsIgnoreCase("now") || duration.equalsIgnoreCase("present")) return false;
        }
        if (duration.split("-").length > 5) return false;

        String block = (company + " " + position + " " + duration).toLowerCase();
        String[] keywords = {
                "project", "2048", "cs97", "university", "coursera", "mooc", "members",
                "judge", "stanford", "assignment", "online", "course", "club"
        };
        String[] educationKeywords = {
                "bachelor", "master", "b.tech", "degree", "education", "information technology",
                "computer science", "gpa", "grade", "university", "college"
        };
        for (String kw : educationKeywords) {
            if (block.contains(kw)) return false;
        }
        for (String kw : keywords) {
            if (block.contains(kw)) return false;
        }
        return true;
    }

}
