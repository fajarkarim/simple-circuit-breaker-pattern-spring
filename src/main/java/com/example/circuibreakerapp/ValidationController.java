package com.example.circuibreakerapp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ValidationController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/jfast/validate")
    @HystrixCommand(fallbackMethod = "fetchValidationFallback")
    public ResponseEntity fetchValidation() {
        try {
            URI uri = new URI("http://localhost:8082/validate");
            final ResponseEntity<ResponseDTO> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, ResponseDTO.class);
            return response;
        } catch (URISyntaxException e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity fetchValidationFallback() {
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("ini gantinya");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
