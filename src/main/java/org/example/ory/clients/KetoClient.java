package org.example.ory.clients;

import org.springframework.stereotype.Service;
import sh.ory.keto.ApiClient;
import sh.ory.keto.api.PermissionApi;
import sh.ory.keto.api.RelationshipApi;

@Service
public class KetoClient {

    private final PermissionApi permissionApi;
    private final RelationshipApi relationshipApi;

    public KetoClient() {
        this.permissionApi = new PermissionApi(new ApiClient().setBasePath("http://localhost:4466"));
        this.relationshipApi = new RelationshipApi(new ApiClient().setBasePath("http://localhost:4467"));
    }
}
