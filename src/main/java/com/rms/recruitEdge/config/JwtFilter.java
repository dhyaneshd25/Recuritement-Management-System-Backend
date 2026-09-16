package com.rms.recruitEdge.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // No token at all -> let it pass through unauthenticated;
        // Spring Security's entry point will reject it with a plain 401
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            String email = jwtUtil.extractEmail(token);

            var userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response); // <-- was missing on the success path

        } catch (ExpiredJwtException e) {
            // Distinct error code so the frontend knows to call /api/auth/refresh
            sendJsonError(response, "TOKEN_EXPIRED");

        } catch (JwtException | IllegalArgumentException e) {
            sendJsonError(response, "TOKEN_INVALID");
        }
    }

    private void sendJsonError(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, String> body = new HashMap<>();
        body.put("error", code);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}






// package com.rms.recruitEdge.config;

// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class JwtFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;
//     private final CustomUserDetailsService userDetailsService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

    
//     String path = request.getServletPath();

//     if (path.startsWith("/api/auth")) {
//         filterChain.doFilter(request, response);
//         return;
//     }    
                
//     try{

//         String header = request.getHeader("Authorization");
        
//         if (header != null && header.startsWith("Bearer ")) {
            
//         String token = header.substring(7);
        
//         String email = jwtUtil.extractEmail(token);
 
//             var userDetails = userDetailsService.loadUserByUsername(email);

//             UsernamePasswordAuthenticationToken auth =
//             new UsernamePasswordAuthenticationToken(
//                 userDetails,
//                 null,
//                 userDetails.getAuthorities()
//             );
            
//             SecurityContextHolder.getContext().setAuthentication(auth);
//         }
//     }
//     catch (JwtException | IllegalArgumentException e) {
//         filterChain.doFilter(request, response);
//         return;
//     }

//     }
// }
