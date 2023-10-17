package com.govstack.information_mediator.pubsub.management.web.keycloak;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * This class implements a Jwt to GrantedAuthority Converter interface by doing a
 * one-to-one mapping from KeyCloak realm_access roles to Spring Security GrantedAuthorities.
 * Optionally a prefix can be added, and the realm_access role name can be converted to upper or lower case.
 * <p>
 * By default, the realm_access role is prefixed with "ROLE_" unless it already starts with
 * "ROLE_", and no case conversion is done.
 */
public class KeycloakRealmRolesGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>>,
    InitializingBean {

    private String authorityPrefix = "ROLE_";

    private boolean convertToUpperCase = false;

    private boolean convertToLowerCase = false;

    public KeycloakRealmRolesGrantedAuthoritiesConverter() { }

    @Override
    public void afterPropertiesSet() {
        Assert.isTrue(!(this.convertToUpperCase && this.convertToLowerCase),
            "Either convertToUpperCase or convertToLowerCase can be set to true, but not both");
    }

    /**
     * Get authorities from the {@code realm_access.roles} jwt claim
     *
     * @param source the source object to convert, which must be an instance of {@link Jwt} (never {@code null})
     * @return collection of {@link GrantedAuthority}
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realmAccess = source.getClaim("realm_access");
        if (Objects.isNull(realmAccess)) {
            return Collections.emptySet();
        }

        Object roles = realmAccess.get("roles");
        if (Objects.isNull(roles) || !Collection.class.isAssignableFrom(roles.getClass())) {
            return Collections.emptySet();
        }

        var rolesCollection = (Collection<?>) roles;

        return rolesCollection.stream()
            // The realm_access.role is supposed to be a list of string, for good measure we double-check that
            .filter(String.class::isInstance)
            .map(attribute -> mapAuthority((String) attribute))
            .collect(Collectors.toSet());
    }

    private GrantedAuthority mapAuthority(String attribute) {
        if (this.convertToUpperCase) {
            attribute = attribute.toUpperCase();
        } else if (this.convertToLowerCase) {
            attribute = attribute.toLowerCase();
        }

        if (!authorityPrefix.isEmpty() && !attribute.startsWith(authorityPrefix)) {
            attribute = authorityPrefix + attribute;
        }

        return new SimpleGrantedAuthority(attribute);
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
    }

    /**
     * Whether to convert the authority value to upper case in the mapping.
     *
     * @param convertToUpperCase defaults to {@code false}
     */
    public void setConvertToUpperCase(boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }

    /**
     * Whether to convert the authority value to lower case in the mapping.
     *
     * @param convertToLowerCase defaults to {@code false}
     */
    public void setConvertToLowerCase(boolean convertToLowerCase) {
        this.convertToLowerCase = convertToLowerCase;
    }

}
