package br.com.letscode.matheus.criticasdefilme.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilterAuthorization extends OncePerRequestFilter {

    private static final String API_AUTH = "http://localhost:8081/check-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/registration") || request.getRequestURI().startsWith("/h2-console")){
            filterChain.doFilter(request, response);
            return;
        }
        String url = API_AUTH;

        HttpHeaders headers = new HttpHeaders();

        RestTemplate restTemplate = new RestTemplate();

        headers.set("Authorization", request.getHeader("Authorization"));

        HttpEntity<Boolean> httpEntityentity = new HttpEntity<Boolean>(true,headers);
        restTemplate.exchange(url, HttpMethod.GET, httpEntityentity, Boolean.class);
        filterChain.doFilter(request, response);
    }
}
