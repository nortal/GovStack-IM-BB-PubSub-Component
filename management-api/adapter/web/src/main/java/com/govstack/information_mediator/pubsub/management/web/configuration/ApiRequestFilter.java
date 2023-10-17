package com.govstack.information_mediator.pubsub.management.web.configuration;

import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiRequestFilter extends OncePerRequestFilter {

    private final RequestMatcher uriMatcher = new AntPathRequestMatcher("/api/**");

    private final UserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            userContext.setUsername(getUsername());
            filterChain.doFilter(request, response);
        } finally {
            userContext.clear();
        }
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        var matcher = new NegatedRequestMatcher(uriMatcher);
        return matcher.matches(request);
    }
}
