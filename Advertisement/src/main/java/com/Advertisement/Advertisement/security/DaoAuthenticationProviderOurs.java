package com.Advertisement.Advertisement.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import com.Advertisement.Advertisement.service.LoginInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.util.Assert;


public class DaoAuthenticationProviderOurs extends AbstractUserDetailsAuthenticationProvider {


    private PasswordEncoder passwordEncoder;

    private volatile String userNotFoundEncodedPassword;

    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginInfoService loginInfoService;


  @Bean

  public UserDetails generated(){
  UserDetails userDetails=new UserDetails(){
  
    @Override
    public boolean isEnabled() {
      // TODO Auto-generated method stub
      return true;
    }
  
    @Override
    public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }
  
    @Override
    public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
    }
  
    @Override
    public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }
  
    @Override
    public String getUsername() {
      // TODO Auto-generated method stub
      return null;
    }
  
    @Override
    public String getPassword() {
      // TODO Auto-generated method stub
      return null;
    }
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      // TODO Auto-generated method stub
      return null;
    }
  };

  return userDetails;
}

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
          /*      if (authentication.getCredentials() == null) {
                    logger.debug("Authentication failed: no credentials provided");
        
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
                */
        
                String presentedPassword = authentication.getCredentials().toString();
        
           //     String nasSalt=loginInfoService.findSaltByUsername(userDetails.getUsername());

         //       System.out.println("NAS SALT JE ===="+nasSalt);
                
                
         /*       if (!passwordEncoder.matches(presentedPassword+nasSalt, userDetails.getPassword())) {
                    logger.debug("Authentication failed: password does not match stored value");
        
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
                */

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
                prepareTimingAttackProtection();
                try {
                    UserDetails loadedUser =new UserDetails(){
                    
                      @Override
                      public boolean isEnabled() {
                        // TODO Auto-generated method stub
                        return true;
                      }
                    
                      @Override
                      public boolean isCredentialsNonExpired() {
                        // TODO Auto-generated method stub
                        return true;
                      }
                    
                      @Override
                      public boolean isAccountNonLocked() {
                        // TODO Auto-generated method stub
                        return true;
                      }
                    
                      @Override
                      public boolean isAccountNonExpired() {
                        // TODO Auto-generated method stub
                        return true;
                      }
                    
                      @Override
                      public String getUsername() {
                        // TODO Auto-generated method stub
                        return null;
                      }
                    
                      @Override
                      public String getPassword() {
                        // TODO Auto-generated method stub
                        return null;
                      }
                    
                      @Override
                      public Collection<? extends GrantedAuthority> getAuthorities() {
                        // TODO Auto-generated method stub
                        return null;
                      }
                    };
                  
                    return loadedUser;
                }
                catch (UsernameNotFoundException ex) {
                    mitigateAgainstTimingAttack(authentication);
                    throw ex;
                }
                catch (InternalAuthenticationServiceException ex) {
                    throw ex;
                }
                catch (Exception ex) {
                    throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
                }
    }

    private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
		}
    }
    
    protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
    }
    
    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
    }
    
    public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
    }
    
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

    
}