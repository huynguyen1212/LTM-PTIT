package ptit.base;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ptit.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstants.JWT_HEADER);
        if (header != null && header.startsWith(JwtConstants.JWT_TOKEN_PREFIX)){

            String token = header.substring(JwtConstants.JWT_TOKEN_PREFIX.length());
            String username = JwtUtils.extractUsername(token);
            MyUserDetails myUserDetails = (MyUserDetails) userService.loadUserByUsername(username);

            //System.out.println(JwtUtils.validateToken(token, myUserDetails)+" <<<<<<<<<<");

            if (JwtUtils.validateToken(token, myUserDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null , myUserDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}