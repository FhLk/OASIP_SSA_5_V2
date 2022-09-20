package oasip.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import oasip.DTO.UserDTOwithPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*",allowCredentials = "true",
        methods = {RequestMethod.POST})
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String access_token = jwtTokenUtil.generateToken(userDetails);
        final String refresh_token = jwtTokenUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(access_token,refresh_token));
    }


    private void authenticate(String mail, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String access_token = null;
        String refresh_token = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            refresh_token = requestTokenHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(refresh_token);
        }

        if (username != null ) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            access_token = jwtTokenUtil.generateToken(userDetails);
            refresh_token = jwtTokenUtil.generateRefreshToken(userDetails);

            if (jwtTokenUtil.validateToken(refresh_token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            throw new RuntimeException("RefreshToken is missing");
        }
        return ResponseEntity.status(200).body(new JwtResponse(access_token,refresh_token));
    }

}
