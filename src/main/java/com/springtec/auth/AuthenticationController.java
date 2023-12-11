package com.springtec.auth;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.payload.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
   public ResponseEntity<?> register(
          @Valid @RequestBody RegisterRequest request
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
              , HttpStatus.BAD_REQUEST
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

   /**
    *  Spring Boot llamará a este método cuando el objeto Usuario especificado no sea válido .
    * @param ex
    * @return
    */
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(MethodArgumentNotValidException.class) // Especificamos como se va a manejar esta excepcion
   public Map<String, String> handleValidationExceptions(
       MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
         // Obtenemos el nombre y mensaje para enviarla
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      });
      return errors;
   }
}
