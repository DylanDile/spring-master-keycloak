package com.example.springmasterv01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =  new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principalAttribute;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities  = Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractAuthorities(jwt).stream()).collect(Collectors.toSet());

        System.out.println(authorities);

        return new JwtAuthenticationToken(jwt, authorities, getPrincipal(jwt));

    }

    private String getPrincipal(Jwt jwt) {
        String claim = JwtClaimNames.SUB;
        if(principalAttribute != null){
            claim = principalAttribute;
        }
        return jwt.getClaim(claim);
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> resourcesAccess;
        Map<String , Object> resource;
        Collection<String> resourceRoles;

        if(jwt.getClaim("resource_access") == null){
            return Set.of();
        }

        resourcesAccess = jwt.getClaim("resource_access");
        if(resourcesAccess.get(resourceId) == null){
            return Set.of();
        }

        resource = (Map<String, Object>) resourcesAccess.get(resourceId);
        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());

    }
}
