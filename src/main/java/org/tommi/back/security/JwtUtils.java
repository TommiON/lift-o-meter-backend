package org.tommi.back.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${org.tommi.back.app.jwtSecret}")
    private String jwtSecret;

    @Value("${org.tommi.back.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println("*** Rakennetaan tokenia!");
        System.out.println("*** jwtSecret: " + jwtSecret);
        System.out.println("*** jwtExpirationMs: " + jwtExpirationMs);
        System.out.println("*** expiration-aika: " + (new Date((new Date()).getTime() + jwtExpirationMs)));
        System.out.println("*** sama vielä merkkijonona: " + (new Date((new Date()).getTime() + jwtExpirationMs)).toString());

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Virheellinen JWT-signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Virheellinen JWT-token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT-token on vanhentunut: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT-tokenia ei tueta: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims-merkkijono on tyhjä: {}", e.getMessage());
        }

        return false;
    }


}
