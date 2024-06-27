package com.project.promotion.chatbot;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextSimilarityUtil {
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
            "은", "는", "이", "가", "을", "를", "에", "의", "과", "와", "하고", "에서", "으로", "까지", "부터", "한테", "에게", "께", "뿐", "인데", "게", "에서", "것", "들", "만", "또는", "아니면", "듯", "말", "보다", "처럼", "같이", "요", "이랑"
    ));
    private static final Analyzer analyzer = new StandardAnalyzer();

    public static ByteBuffersDirectory createIndex(String[] texts) throws IOException {
        ByteBuffersDirectory directory = new ByteBuffersDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);

        for (String text : texts) {
            Document doc = new Document();
            doc.add(new TextField("text", text, Field.Store.YES));
            writer.addDocument(doc);
        }

        writer.close();
        return directory;
    }

    public static Set<String> extractKeywords(String text) throws IOException {
        Set<String> keywords = new HashSet<>();
        TokenStream tokenStream = analyzer.tokenStream(null, text);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();

        while (tokenStream.incrementToken()) {
            String term = charTermAttribute.toString();
            if (!STOPWORDS.contains(term)) {
                keywords.add(term);
            }
        }

        tokenStream.end();
        tokenStream.close();

        return keywords;
    }

    public static String preprocessQuestion(String question) {
        // 특수문자 제거 및 소문자 변환
        question = question.replaceAll("[^a-zA-Z0-9가-힣\\s]", "").toLowerCase().trim();
        return removeStopwords(question); // 불용어 제거 후
    }

    public static String removeStopwords(String text) {
        StringBuilder sb = new StringBuilder();
        for (String word : text.split("\\s+")) {
            if (!STOPWORDS.contains(word)) {
                sb.append(word).append(" ");
            }
        }
        return sb.toString().trim();
    }
}