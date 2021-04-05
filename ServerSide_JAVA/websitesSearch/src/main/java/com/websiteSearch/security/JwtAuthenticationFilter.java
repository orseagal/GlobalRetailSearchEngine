package com.websiteSearch.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Inside doFilter");
		String jwt = getJwtFromRequest(request);
		System.out.println("Token "+jwt);
		//String jwt="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJUZXN0VXNlcjciLCJpYXQiOjE2MTU0ODQ3MTgsImV4cCI6MTYxNTQ4NTYxOH0.EOELXMR-OVNrhiiNY2neNJO35zcc1qX0zxP1BHlnyc5eEjGASZyIu3ze6toBAFf9vl4CijJbX2-iW6d2MztejcLVMm_pBF5rsDFuhvS7DwIl45RhwdEctKJSDl0bVQ60FHukaw49Fsbn5oOOVBxlZWyxD3_9Dst2dSvY_aQBjDzY664RkXvL6W4nxLp2tGVEl7gjJQwEN6ihAPZTYrx-I_4H7srdKIo7WUfdTlHt2RLZiWpX6IR_pQbDMf4fKn-EduJqDKhc2HUjgsLvXes2VuK6ZcAYzo34yfEYqoWSbyQCSdwWNKEqT8t3-QfjSl0VrNnCKLiz-bjxD4nJQGDL2Q";
		//System.out.println("Token "+jwt);
		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
			String username = jwtProvider.getUsernameFromJwt(jwt);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return bearerToken;
	}
}
