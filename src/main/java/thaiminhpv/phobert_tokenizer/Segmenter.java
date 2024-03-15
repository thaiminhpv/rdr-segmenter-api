package thaiminhpv.phobert_tokenizer;

import org.jooq.lambda.Unchecked;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Segmenter {
    private static final String[] ANNOTATORS = new String[]{"wseg"};
    private VnCoreNLP model = null;

    @PostConstruct
    public void init() {
        try {
            this.model = new VnCoreNLP(ANNOTATORS);
            Annotation annotation = new Annotation("Init segmenter 1!");
            this.model.annotate(annotation); // avoid java.util.ConcurrentModificationException later if 2 concurrent init request
            Annotation annotation2 = new Annotation("Init segmenter done!");
            this.model.annotate(annotation2); // avoid java.util.ConcurrentModificationException later if 2 concurrent init request
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Segmenter initialized");
    }

    public List<String> segment(String text) throws IOException {
        Annotation annotation = new Annotation(text);
        synchronized (this) {
            this.model.annotate(annotation);
        }
        return annotation.getWords()
                .stream()
                .map(Word::getForm)
                .collect(Collectors.toList());
    }

    public List<List<String>> batch_segment(List<String> texts) throws IOException {
        return texts.stream()
                .map(Unchecked.function(this::segment))
                .collect(Collectors.toList());
    }
}
