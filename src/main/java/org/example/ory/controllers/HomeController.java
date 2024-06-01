package org.example.ory.controllers;

import org.example.ory.models.Organization;
import org.example.ory.models.dtos.OrganizationDto;
import org.example.ory.services.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sh.ory.keto.ApiClient;
import sh.ory.keto.ApiException;
import sh.ory.keto.api.PermissionApi;

@RestController
public class HomeController {

    private final PermissionApi permissionApi;
    private final OrganizationService organizationService;


    public HomeController(OrganizationService organizationService) {
        this.organizationService = organizationService;
        this.permissionApi = new PermissionApi(new ApiClient().setBasePath("http://localhost:4466"));
    }

    @GetMapping("/")
    public String home(
            @RequestParam(name = "namespace") String namespace,
            @RequestParam(name = "object") String object,
            @RequestParam(name = "relation") String relation,
            @RequestParam(name = "subject") String subject
    ) throws ApiException {
        var result = permissionApi.checkPermission(namespace, object, relation, subject, null, null, null, null);
        return result.toJson();
    }

    @PostMapping("/")
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationDto organizationDto, Authentication authentication) throws ApiException {
        var organization = organizationService.createOrganization(organizationDto, authentication);
        return new ResponseEntity<>(organization, HttpStatus.CREATED);
    }

    @GetMapping("/organization/{id}")
    @PreAuthorize("hasPermission(#id, 'Organization', 'view')")
    public ResponseEntity<Organization> home(@PathVariable Integer id, Authentication authentication) throws ApiException {

        System.out.println("authentication = " + authentication.getName());
        System.out.println("authentication = " + authentication.toString());
        var body = organizationService.getOrganization(id, authentication);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
