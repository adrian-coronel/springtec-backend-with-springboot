package com.springtec.auth;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.payload.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
/*@CrossOrigin(
    origins = "http://127.0.0.1:5500",
    methods = { RequestMethod.POST },
    exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Authorization"},
    allowCredentials = "true",
    maxAge = 3400
)*/
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
   public ResponseEntity<?> register(
           @RequestBody RegisterRequest request
   ) {
        System.out.println(request);
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            // Utilizamos nuestra clase AuthenticationRequest -> es DTO(xq se encapsula los datos que se envian o reciben)
            @RequestBody AuthenticationRequest request
    ) {
       try {
          AuthenticationResponse token = authService.authenticate(request);
          return new ResponseEntity<>(
              MessageResponse.builder()
                  .message("Autenticado correctamente")
                  .body(token)
                  .build()
              , HttpStatus.OK
          );
       } catch (ElementNotExistInDBException e) {
          return new ResponseEntity<>(
              MessageResponse.builder()
                  .message(e.getMessage())
                  .build()
              , HttpStatus.OK
          );
       }
    }

    @PostMapping("/verifytoken")
    public ResponseEntity<?> verifyToken(@RequestBody AuthenticationRequest request) {
       boolean verify = authService.verifyToken(request);

       return new ResponseEntity<>(
           MessageResponse.builder()
               .message("Verificación satisfactoria")
               .body(verify)
               .build()
           , HttpStatus.OK
       );
    }
}
