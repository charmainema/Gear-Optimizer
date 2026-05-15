package com.gear_optimizer.gear_optimizer;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OptimizeController {

    @PostMapping("/optimize")
    public Map<String, String> optimize(@RequestBody Map<String, String> body) {
        String input = body.get("input");

        // TODO: just return for now
        return Map.of("result", "You sent: " + input);
    }
}