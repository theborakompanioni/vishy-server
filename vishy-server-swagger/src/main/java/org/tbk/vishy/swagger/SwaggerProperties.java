package org.tbk.vishy.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@ConfigurationProperties("vishy.swagger")
public class SwaggerProperties {

    private static final Contact DEFAULT_CONTACT = new Contact("", "", "");

    private static final ApiInfo DEFAULT = new ApiInfo(
            "OpenMRC REST API",
            "The OpenMRC REST API interface definition",
            "0.0.1",
            "API Terms Of Service",
            DEFAULT_CONTACT,
            "MIT License",
            "https://opensource.org/licenses/MIT"
    );

    private String version = DEFAULT.getVersion();
    private String title = DEFAULT.getTitle();
    private String description = DEFAULT.getDescription();
    private String termsOfServiceUrl = DEFAULT.getTermsOfServiceUrl();
    private String license = DEFAULT.getLicense();
    private String licenseUrl = DEFAULT.getLicenseUrl();
    private String contact = DEFAULT.getContact().getName();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
