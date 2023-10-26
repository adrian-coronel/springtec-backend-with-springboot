package com.springtec.auth;

import com.springtec.models.payload.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
   public ResponseEntity<?> register(
           @RequestBody RegisterRequest request
    ) {
        try {
            var token = authService.register(request);
            return new ResponseEntity<>(
                    // MessageResponse es mi PAYLOAD personalizado
                    MessageResponse.builder()
                            .message("Registrado correctamente")
                            .body(token)
                            .build()
                    , HttpStatus.CREATED
            );
        } catch (Exception ex){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(ex.getMessage())
                            .body(null)
                            .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }

}
