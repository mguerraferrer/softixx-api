package softixx.api.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.util.UBase64;

@Slf4j
public class JwtManager {
	private JwtManager() {
		throw new IllegalStateException("Utility class");
	}

	public static String createJWT(String id, String issuer, String subject, long ttlMillis, String secretKey) {
		return createJWT(id, issuer, subject, ttlMillis, secretKey, new HashMap<String, Object>());
	}
	
	public static String createJWT(String id, String issuer, String subject, long ttlMillis, String secretKey, Map<String, Object> claims) {
		try {
			
			// The JWT signature algorithm we will be using to sign the token
	        val signatureAlgorithm = SignatureAlgorithm.HS256;

	        long nowMillis = System.currentTimeMillis();
	        val now = new Date(nowMillis);

	        // We will sign our JWT with our ApiKey secret
	        byte[] apiKeySecretBytes = UBase64.stringToByte(secretKey);
	        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	        // Let's set the JWT Claims
	        val jwtBuilder = Jwts.builder()
	        					 .setClaims(claims)
	        					 .setId(id)
	        					 .setIssuedAt(now)
	        					 .setSubject(subject)
	        					 .setIssuer(issuer)
	        					 .signWith(signatureAlgorithm, signingKey);

	        // If it has been specified, let's add the expiration
	        if (ttlMillis >= 0) {
	            long expMillis = nowMillis + ttlMillis;
	            val exp = new Date(expMillis);
	            jwtBuilder.setExpiration(exp);
	        }

	        // Builds the JWT and serializes it to a compact, URL-safe string
	        return jwtBuilder.compact();
			
		} catch (Exception e) {
			log.error("JwtManager#createJWT error {}", e.getMessage());
		}
        return null;
    }

    public static Claims decodeJWT(String jwt, String secretKey) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parser()
                   .setSigningKey(UBase64.stringToByte(secretKey))
                   .parseClaimsJws(jwt).getBody();
    }
    
}