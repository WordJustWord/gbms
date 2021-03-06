package com.pigmo.gbms.security.bean;

import com.pigmo.gbms.security.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final String tokenHeader;
    private final String loginPath;

    public JwtAuthorizationTokenFilter(
            @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            @Value("${jwt.header}") String tokenHeader,
            @Value("${jwt.auth.path}") String loginPath
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
        if (loginPath.startsWith("/")) {
            this.loginPath = "/auth" + loginPath;
        } else {
            this.loginPath = "/auth/" + loginPath;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rep, FilterChain chain)
            throws ServletException, IOException {
        final String requestHeader = req.getHeader(this.tokenHeader);

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ") && !req.getServletPath().equals(this.loginPath)) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            JwtUser userDetails = (JwtUser) this.userDetailsService.loadUserByUsername(username);

            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, rep);
    }
}
