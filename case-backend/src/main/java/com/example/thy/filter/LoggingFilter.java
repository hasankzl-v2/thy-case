package com.example.thy.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        log.info("Incoming Request: {} {}", req.getMethod(), req.getRequestURI());


        chain.doFilter(request, response);

        log.info("Outgoing Response: {} {}", res.getStatus(), req.getRequestURI());
    }
}