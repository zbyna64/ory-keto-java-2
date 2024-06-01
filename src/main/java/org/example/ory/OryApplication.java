package org.example.ory;

import org.example.ory.models.KetoNamespace;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sh.ory.keto.ApiClient;
import sh.ory.keto.api.RelationshipApi;
import sh.ory.keto.model.CreateRelationshipBody;

@SpringBootApplication
public class OryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            RelationshipApi relationshipApi = new RelationshipApi(new ApiClient().setBasePath("http://localhost:4467"));

            var body = new CreateRelationshipBody();
            body.setNamespace(KetoNamespace.USER.getValue());
            body.setObject("jane");
            body.setRelation("manager");
            body.setSubjectId("admin");
            relationshipApi.createRelationship(body);
        };
    }
}
