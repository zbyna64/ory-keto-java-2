package org.example.ory.configuration;

import lombok.SneakyThrows;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import sh.ory.keto.ApiClient;
import sh.ory.keto.api.PermissionApi;

import java.io.Serializable;

@Component
public class KetoPermissionEvaluator implements PermissionEvaluator {

    private final PermissionApi permissionApi;

    public KetoPermissionEvaluator() {
        this.permissionApi = new PermissionApi(new ApiClient().setBasePath("http://localhost:4466"));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @SneakyThrows
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return permissionApi.checkPermission(targetType, targetId.toString(), permission.toString(), ((JwtAuthenticationToken) authentication).getToken().getClaim("preferred_username"), null, null, null, null)
                .getAllowed();
    }
}
