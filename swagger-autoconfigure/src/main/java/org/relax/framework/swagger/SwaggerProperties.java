package org.relax.framework.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

import java.util.Collection;
import java.util.List;

@ConfigurationProperties("swagger")
@Data
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private Contact contact;
    private String license;
    private String licenseUrl;
    private Collection<VendorExtension> vendorExtensions;
    private List<ParameterProperties> globalParameters;
}
