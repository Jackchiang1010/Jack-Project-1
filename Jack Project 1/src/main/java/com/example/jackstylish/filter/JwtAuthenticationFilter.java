package com.example.jackstylish.filter;

import com.example.jackstylish.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
//@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            //log.info("jwtUtils: {}", jwtUtils);

            final String authHeader = request.getHeader("Authorization");
            final String jwtToken;
            final String userEmail;

            // doesn't have take Token request
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
//                throw new UnsupportedJwtException("No token");
                return;
            }

            jwtToken = authHeader.substring(7); //get Token behind "Bearer " from index 7

            log.info("JWT Token: {}", jwtToken);

            userEmail = jwtUtils.extractUsername(jwtToken);

            log.info("userEmail: {}", userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            List.of()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);


                } catch (ExpiredJwtException ex) {
                    throw ex;
                }
            } else {
                log.info("JWT Token is empty");
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");

            return;
        } catch (MalformedJwtException e) {
            log.error("Invalid token format: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token format");
            filterChain.doFilter(request, response);
            return;
        } catch (UnsupportedJwtException e) {
            log.error("No token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No token");
            return;
        } catch (JwtException e) {
            log.error("Wrong token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Wrong token");
            return;
        } catch (Exception e) {
            log.error("Token processing error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Token processing error");
            return;
        }


    }

}
