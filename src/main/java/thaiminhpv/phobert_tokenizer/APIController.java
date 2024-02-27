package thaiminhpv.phobert_tokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class APIController {
    private final Segmenter segmenter;

    // Autowire the Segmenter bean through the constructor
    @Autowired
    public APIController(Segmenter segmenter) {
        this.segmenter = segmenter;
    }

    @GetMapping("/word_segment")
    public ResponseEntity<?> getMessage(@RequestParam String text) {
        return createResponse(text);
    }

    @PostMapping("/word_segment")
    public ResponseEntity<?> postMessage(@RequestBody MessageRequest messageRequest) {
        return createResponse(messageRequest.getText());
    }

    @PostMapping("/batch_word_segment")
    public ResponseEntity<?> postMessage(@RequestBody BatchRequest messageRequest) {
        return createResponse(messageRequest.getTexts());
    }
    private ResponseEntity<?> createResponse(String text) {
        List<String> responseText = null;
        try {
            responseText = this.segmenter.segment(text);
            return ResponseEntity.ok().body(Map.of("tokens", responseText));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }

    }
    private ResponseEntity<?> createResponse(List<String> texts) {
        List<List<String>> responseText = null;
        try {
            responseText = this.segmenter.batch_segment(texts);
            return ResponseEntity.ok().body(Map.of("tokens", responseText));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }
}
