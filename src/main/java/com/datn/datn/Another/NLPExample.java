package com.datn.datn.Another;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class NLPExample {

    public static void main(String[] args) {
        String sentence = "OpenNLP provides various tools for natural language processing.";

        // Tokenize the sentence
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(sentence);

        // Load the POS model
        try (FileInputStream modelIn = new FileInputStream("D:/SLTMD/KHOA CNTT/DATN/datn/src/main/java/com/datn/datn/Another/langdetect-183.bin")) {
            POSModel posModel = new POSModel(modelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel);

            // Tag the tokens
            String[] posTags = posTagger.tag(tokens);

            // Display the tokens and their POS tags
            for (int i = 0; i < tokens.length; i++) {
                System.out.println(tokens[i] + " - " + posTags[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        URL resource = NLPExample.class.getResource("langdetect-183.bin");
//
//        if (resource != null) {
//            System.out.println("Đường dẫn tuyệt đối: " + resource.getPath());
//        } else {
//            System.out.println("Không tìm thấy tệp.");
//        }
    }
}
