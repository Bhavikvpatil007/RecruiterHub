package com.Project.recruiterhub.services;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class NLPpipeline {
    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma";
    private static StanfordCoreNLP stanfordCoreNLP;

    private NLPpipeline() {

    }

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    @Bean(name = "stanfordCoreNLP")
    public static StanfordCoreNLP stanfordCoreNLP() {
        if (stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
