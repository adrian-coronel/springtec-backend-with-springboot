package com.springtec.config;

import com.springtec.models.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Clave generada en desde una plataforma: https://generate-random.org/encryption-key-generator
    private static final String SECRET_KEY = "argi4mM5i0TYXps7nKB/MTuDxuYzW5C/eYQoUDoHOfmXOZ76miVDPTD1rbb5lptMvh8fD5TPspz0fycodcT4KIjkYHwzj1ZvrjjJ17NZBQOhR1/iA75JeCXD3QCvx86pzB6eqWQnWyNufC3XOEn/Yb6KoFWX/QA35VkOqQUy52+75Z+UvDspvUIffKjd/qed4LKr0kKsEKEiJOYphOn5mzxCe9And+t36c9Ody4Vxh7ppoMyspl0r1aCQhU5ncyqNQ7bKoowwnNW/k1NobDDF6DzrWg35Nm2PQccrEb6PnFFKzJK17UJ9F7uOztCtfGapzBW5yJFCPZJO/FWd4RHSL82WboJzvHZSWkNgOHXPw86BGVLisdyGYvVrPnpQPJX4ziCBppIJHoq/puYRQ2Qtg==";


    /**
     * Extraer el USERNAME del TOKEN
     * */
    public String extractUsername(String token) {
        // Se extrae el subject que generalmente contiene el username
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generar un token sin Claims
     * */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generar un token con Claims Extras
     * */
    public String generateToken(
            Map<String, Object> extraClaims, //Parámetro para incluir reclamos(claims) adicionales
            UserDetails userDetails) {

        User user = (User) userDetails; // Castear UserDetails a tu clase de usuario personalizada
        extraClaims.put("userId", user.getId()); // Agregar el ID del usuario como reclamo adicional
        extraClaims.put("role", user.getRole().getName()); // Agregar el rol del usuario como reclamo adicional

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(((User) userDetails).getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha en que se generó el token, le pasamos los milisegundos actuales
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Establecemos la FIRMA y el algoritmo de firma
                .compact(); // "compact" Generará y devuelve el TOKEN
    }

    public boolean isTokenvalid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Comprueba si la fecha de EXPIRACIÓN es anteorior a la actual es TRUE
     * */
    private boolean isTokenExpired(String token) {
        // "before" Prueba si esta fecha es anterior a la fecha especificada.
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de EXPIRACION del TOKEN
     * */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un solo Claims del token
     * */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims); //Permite extraer un claim específico del token según la función
        // proporcionada y devolverlo en el formato deseado (de tipo T).
    }

    /**
     * Extrae todos los claims del token
     * */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey( getSignInKey() ) // PASAMOS LA FIRMA
                .build()
                .parseClaimsJws(token) // Obtenemos los claims(elementos de información q contiene el token)
                .getBody(); // Obtenemos el cuerpo
    }

    /**
     * Devuelve la CLAVE DE FIRMA
     * */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Pasamos el formato de SECRET_KEY, de BASE64 a Bytes
        // hmacSha... crea una clave HMAC (Hash-based Message Authentication Code) a partir de los bytes
        return Keys.hmacShaKeyFor(keyBytes); //HMAC se utiliza para firmar y verificar tokens de seguridad.
    }
}
