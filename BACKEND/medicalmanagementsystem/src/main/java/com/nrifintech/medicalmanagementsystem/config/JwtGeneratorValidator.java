package com.nrifintech.medicalmanagementsystem.config;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nrifintech.medicalmanagementsystem.service.DefaultUserService;

import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtGeneratorValidator {
	
	@Autowired
	DefaultUserService userService;
	
    private final String SECRET = "3d3ce6d3d3666cf8286593bcd533a1cb01fad97c9d253a7c25dae651892ab494";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public Claims extractUserRole(String token) {
        return extractAllClaims(token);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) { 
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, authentication);
    }

    public String generateRefreshToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, authentication);
    }

    // private String createToken(Map<String, Object> claims, Authentication authentication) {
    // 	String role =authentication.getAuthorities().stream()
  	//      .map(r -> r.getAuthority()).collect(Collectors.toSet()).iterator().next();
    //     return Jwts.builder().claim("role",role).setSubject(authentication.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
    //             .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
    //             .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    // }

    private String createToken(Map<String, Object> claims, Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println("INSIDE createToken "+role);
        String token = Jwts.builder()
            .claim("role", role)
            .setSubject(authentication.getName())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*15))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
        System.out.println("Testing");
        return token;
    }
    


    private String createRefreshToken(Map<String, Object> extraClaims, Authentication authentication) {
    	String role =authentication.getAuthorities().stream()
  	     .map(r -> r.getAuthority()).collect(Collectors.toSet()).iterator().next();
        return Jwts.builder().setClaims(extraClaims).claim("role",role).setSubject(authentication.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

         Claims claims = extractAllClaims(token);

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
