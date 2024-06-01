package org.example.ory.services;

import org.example.ory.models.KetoNamespace;
import org.example.ory.models.Organization;
import org.example.ory.models.dtos.OrganizationDto;
import org.example.ory.repositories.OrganizationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sh.ory.keto.ApiClient;
import sh.ory.keto.ApiException;
import sh.ory.keto.api.PermissionApi;
import sh.ory.keto.api.RelationshipApi;
import sh.ory.keto.model.CreateRelationshipBody;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final RelationshipApi relationshipApi;

    private final PermissionApi permissionApi;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        this.relationshipApi = new RelationshipApi(new ApiClient().setBasePath("http://localhost:4467"));
        this.permissionApi = new PermissionApi(new ApiClient().setBasePath("http://localhost:4466"));
    }

    public Organization createOrganization(OrganizationDto organizationDto, Authentication authentication) throws ApiException {
        Organization organization = new Organization();
        organization.setName(organizationDto.getName());
        organization = organizationRepository.save(organization);

        var body = new CreateRelationshipBody();
        body.setNamespace(KetoNamespace.ORGANIZATION.getValue());
        body.setObject(String.valueOf(organization.getId()));
        body.setRelation("owners");
        body.setSubjectId(((JwtAuthenticationToken) authentication).getToken().getClaim("preferred_username"));
        relationshipApi.createRelationship(body);

        return organization;
    }

    public Organization getOrganization(Integer id, Authentication authentication) throws ApiException {
//        var result = permissionApi.checkPermission(KetoNamespace.ORGANIZATION.getValue(), String.valueOf(id), "view", authentication.getName(), null, null, null, null);
//
//        if (!result.getAllowed()) {
//            throw new ApiException("Unauthorized", 401, null, result.toJson());
//        }
        return organizationRepository.findById(id).orElseThrow();
    }
}
