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
        System.out.println("Segmenter initialized");
        try {
            this.model = new VnCoreNLP(ANNOTATORS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> segment(String text) throws IOException {
        Annotation annotation = new Annotation(text);
        this.model.annotate(annotation);
        return annotation.getWords()
                .stream()
                .map(Word::getForm)
                .collect(Collectors.toList());
    }

    public List<List<String>> batch_segment(List<String> texts) throws IOException {
        return texts.parallelStream()
                .map(Unchecked.function(this::segment))
                .collect(Collectors.toList());
    }
}
