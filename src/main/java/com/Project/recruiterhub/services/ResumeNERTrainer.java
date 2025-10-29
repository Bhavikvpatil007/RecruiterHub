package com.Project.recruiterhub.services;

import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ResumeNERTrainer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("trainFile", "C:\\C drive\\recruiterhub\\recruiterhub\\src\\main\\java\\com\\Project\\recruiterhub\\services\\resume.tsv");
        props.setProperty("serializeTo", "resume_experience_ner_model.ser.gz");
        props.setProperty("map", "word=0,answer=1");
        props.setProperty("useClassFeature", "true");
        props.setProperty("useWord", "true");
        props.setProperty("useNGrams", "true");
        props.setProperty("noMidNGrams", "true");
        props.setProperty("maxNGramLeng", "6");
        props.setProperty("usePrev", "true");
        props.setProperty("useNext", "true");
        props.setProperty("useSequences", "true");
        props.setProperty("usePrevSequences", "true");
        props.setProperty("useTypeSeqs", "true");
        props.setProperty("useTypeSeqs2", "true");
        props.setProperty("useTypeySequences", "true");
        props.setProperty("wordShape", "chris2useLC");
        props.setProperty("useDisjunctive", "true");
        props.setProperty("useWordPairs", "true");
        props.setProperty("useFirst", "true");
        props.setProperty("useLast", "true");
        props.setProperty("useTaggySequences", "true");
        props.setProperty("useOccurrencePatterns", "true");
        props.setProperty("maxLeft", "2");
        props.setProperty("maxRight", "2");
        props.setProperty("featureDiffThresh", "0.05");
        CRFClassifier<CoreLabel> classifier = new CRFClassifier<>(props);
        classifier.train();
        classifier.serializeClassifier("resume_experience_ner_model.ser.gz");
        System.out.println("NER model training completed!");
    }
}