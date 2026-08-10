package com.example.mini_jira.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.mini_jira.service.CustomUserDetailsService;
import com.example.mini_jira.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService){
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            if(jwtService.validateToken(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken authTokenIdCard =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authTokenIdCard.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authTokenIdCard);
            }
        }

        filterChain.doFilter(request, response);

    }

}
