package thaiminhpv.phobert_tokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    private ResponseEntity<?> createResponse(String text) {
        // Use the autowired, singleton Segmenter instance
        List<String> responseText = null;
        try {
            responseText = this.segmenter.segment(text);
            return ResponseEntity.ok().body(Map.of("text", responseText));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }

    }
}
