package com.b2s.sso.common.saml;

import com.b2s.sso.common.saml.xml.IssuerGenerator;
import com.b2s.sso.common.util.IDService;
import com.b2s.sso.common.util.TimeService;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;


public class AuthnRequestGenerator {

    private XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

    private final String issuingEntityName;
    private final TimeService timeService;
    private final IDService idService;
    private IssuerGenerator issuerGenerator;

    public AuthnRequestGenerator(String issuingEntityName, TimeService timeService, IDService idService) {
        super();
        this.issuingEntityName = issuingEntityName;
        this.timeService = timeService;
        this.idService = idService;

        issuerGenerator = new IssuerGenerator(issuingEntityName);
    }

    public AuthnRequest generateAuthnRequest(String destination, String responseLocation) {

        AuthnRequestBuilder authnRequestBuilder = (AuthnRequestBuilder) builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);

        AuthnRequest authnRequest = authnRequestBuilder.buildObject();

        authnRequest.setAssertionConsumerServiceURL(responseLocation);
        authnRequest.setID(idService.generateID());
        authnRequest.setIssueInstant(timeService.getCurrentDateTime());
        authnRequest.setDestination(destination);

        authnRequest.setIssuer(issuerGenerator.generateIssuer());

        return authnRequest;
    }


}
