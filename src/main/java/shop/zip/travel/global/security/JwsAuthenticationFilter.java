package shop.zip.travel.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.zip.travel.global.error.BusinessException;
import shop.zip.travel.global.error.ErrorResponse;

public class JwsAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwsManager jwsManager;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String jws = getJwsFromRequest(request);
      String memberId = jwsManager.getMemberIdFromAccessToken(jws);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);
      Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails,
          null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (BusinessException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
      response.setCharacterEncoding("UTF-8");
      objectMapper.writeValue(response.getWriter(),
          new ErrorResponse(e.getErrorCode().getMessage()));
    }

    filterChain.doFilter(request, response);
  }

  private String getJwsFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
