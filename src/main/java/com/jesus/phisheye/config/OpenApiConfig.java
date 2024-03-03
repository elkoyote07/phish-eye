package com.jesus.phisheye.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI phishEyeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Phish Eye")
                        .description(descriptionConfig())
                        .version("1.0.0")
                );
    }

    private Contact contactConfig() {
        Contact contact = new Contact();
        contact.setEmail("jesus.sgonzalez@owlsline.com");
        contact.setName("Owl's Contact");
        return contact;
    }

    private String descriptionConfig() {
        return "**INTRODUCTION** \n\n\n\n" +
                "**VERBS** \n\n\n\n\n" +
                "As such, the API uses http methods as **verbs** as follows:\n\n" +
                "* **GET** - To retrieve a resource or list of resources\n\n" +
                "* **POST** - To create or update a resource\n\n" +
                "* **PUT** - To update a resource\n\n"+
                "* **DELETE** - To delete a resource\n\n" +
                "\n\n\n\n**STATUS CODES**\n\n\n\n" +
                "It also uses http **status codes** in responses to inform about the status of the response. Some usual response status codes and meanings are as follows:\n\n"+
                "* _**200**_ - **OK** - the request was successful (some API calls may return 201 or 204 instead).\n\n " +
                "* _**201**_ - **Created** - the request was successful and a resource was created.\n\n" +
                "* _**204**_ - **No Content** - the request was successful but there is nothing to return (i.e. an empty list).\n\n" +
                "* _**400**_ - **Bad Request** - the request wasn't understood, it was missing required parameters or the parameters didn't pass a validation test.\n\n" +
                "* _**401**_ - **Unauthorized** - authentication failed or the user doesn't have permissions for the requested operation.\n\n" +
                "* _**403**_ - **Forbidden** - access denied, mainly for an expired token.\n\n" +
                "* _**404**_ - **Not Found** - the resource wasn't found or doesn't exists.\n\n" +
                "* _**405**_ - **Method not allowed** - the requested method is not supported for the resource. Returned any time you use a http method in a resource that doesn't support it.\n\n" +
                "* _**409**_ - **Conflict** -  there was a conflict with the request, i.e. if there is an attempt to create a resource that already exists.\n\n" +
                "* _**429**_ - **Too Many Requests** - exceeded API calls request rate limit by a single client. May be returned by any method.\n\n" +
                "* _**500**_ - **Internal Server Error** - a generic error message, given when an unexpected condition was encountered and no more specific m May be returned by any method.\n\n" +
                "* _**501**_ - **Not Implemented** - the requested method is not implemented.\n\n" +
                "* _**503**_ - **Service Unavailable** - service is temporary unavailable (i.i. scheduled maintenance).\n\n" +
                "\n\n\n\n" +
                "**AUTHENTICATION** \n\n\n\n" +
                "If a method requires authentication it is stated in its reference.\n\n\n" +
                "The API uses [Basic Access Authentication] in the login method. Login method should return a token used in the next methods that require aing to [Bearer Token Authentication].<br/><br/>\n\n\n" +
                "\n\n\n\n This document is still a work in progress\n" ;

    }
}


