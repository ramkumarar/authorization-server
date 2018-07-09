package com.rbsg.ob.aspspbank.authorisation.configuration;

import com.rbsg.ob.aspspbank.authorisation.model.Payment;
import com.rbsg.ob.aspspbank.authorisation.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Stores the oauth authorizationRequest in the session so that it can
 * to continue with the authorization flow.
 */
public class TwoFactorAuthenticationFilter extends OncePerRequestFilter {
	


	private static final Logger LOG = LoggerFactory.getLogger(TwoFactorAuthenticationFilter.class);
    public static final String TWOFACTOR_PATH = "/secure/two_factor_authentication";


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private OAuth2RequestFactory oAuth2RequestFactory;
    
    //These next two are added as a test to avoid the compilation errors that happened when they were not defined.
    public static final String ROLE_TWO_FACTOR_AUTHENTICATED = "ROLE_TWO_FACTOR_AUTHENTICATED";
    public static final String ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED = "ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED";


    @Autowired
    private PaymentService paymentService;

    @Autowired
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        oAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    private boolean twoFactorAuthenticationEnabled(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().anyMatch(
            authority -> ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED.equals(authority.getAuthority())
        );
    }

 

    private Map<String, String> paramsFromRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }
        return params;
    }


    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		
		// Check if the user hasn't done the two factor authentication.
        if (isAuthenticated() && !hasAuthority(ROLE_TWO_FACTOR_AUTHENTICATED)) {
            AuthorizationRequest authorizationRequest = oAuth2RequestFactory.createAuthorizationRequest(paramsFromRequest(request));
            String state = request.getParameter("state");
            Payment payment= fetchPayment(state);
            request.getSession().setAttribute("STATE", state);
            request.getSession().setAttribute("payment", payment);

            /* Check if the client's authorities (authorizationRequest.getAuthorities()) or the user's ones
               require two factor authentication. */
            if (twoFactorAuthenticationEnabled(authorizationRequest.getAuthorities()) ||
                    twoFactorAuthenticationEnabled(SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                // Save the authorizationRequest in the session. This allows the CustomOAuth2RequestFactory
                // to return this saved request to the AuthenticationEndpoint after the user successfully
                // did the two factor authentication.
                request.getSession().setAttribute(CustomOAuth2RequestFactory.SAVED_AUTHORIZATION_REQUEST_SESSION_ATTRIBUTE_NAME, authorizationRequest);


                LOG.debug("doFilterInternal(): redirecting to {}", TWOFACTOR_PATH);
                
                // redirect the the page where the user needs to enter the two factor authentication code
                redirectStrategy.sendRedirect(request, response,
                        TWOFACTOR_PATH
                           );
                return;
            } 
        }

		LOG.debug("doFilterInternal(): without redirect.");
       
		filterChain.doFilter(request, response);
	}
	
	public boolean isAuthenticated(){
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
	
	private boolean hasAuthority(String checkedAuthority){

		
    	return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                authority -> checkedAuthority.equals(authority.getAuthority())
    			);
    }

	private Payment fetchPayment(String paymentId){
        Payment payment= paymentService.fetchPayment(paymentId);
        return  payment;
    }
	
	
}

