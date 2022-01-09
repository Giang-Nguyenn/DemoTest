package com.example.DemoTest.core.auth.jwt;

import com.example.DemoTest.exception.UnauthorizedException;
import com.example.DemoTest.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("doFilter");
        try {
//        if(true ) throw new UnauthorizedException("không có quyền");

            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Claims claims=tokenProvider.getClaimsFromToken(jwt);
                Long userId = tokenProvider.getUserIdFromJWT(claims);
//                if(tokenProvider.accessTime(claims.getExpiration().getTime())){
//                    System.out.println("Còn hạn");
//                }
//                else {
//                    System.out.println("Hết hạn");
//                }
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails
                                    .getAuthorities());
                    System.out.println(authentication);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
//            System.out.println(ex.toString());
            log.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}