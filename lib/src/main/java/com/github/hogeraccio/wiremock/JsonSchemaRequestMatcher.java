package com.github.hogeraccio.wiremock;

import com.github.tomakehurst.wiremock.common.LocalNotifier;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import com.github.tomakehurst.wiremock.matching.RequestMatcherExtension;

import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.SpecVersionDetector;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.SpecVersion.VersionFlag;

public class JsonSchemaRequestMatcher extends RequestMatcherExtension {
    protected static final VersionFlag DEFAULT_VERSION_FLAG = SpecVersion.VersionFlag.V7;

    @Override
    public String getName() {
        return "jsonschema-matcher";
    }

    @Override
    public MatchResult match(Request request, Parameters parameters) {
        boolean isMatch;
        try {
            Set<ValidationMessage> errors = this.validate(request, parameters);
            isMatch = errors.isEmpty();
        } catch (Exception e) {
            LocalNotifier.notifier().error("", e);
            isMatch = false;
        }

        return MatchResult.of(isMatch);
    }

    protected Set<ValidationMessage> validate(Request request, Parameters parameters) {
        JsonSchema schema = this.getJsonSchemaFromParameters(parameters);
        JsonNode node = Json.node(request.getBodyAsString());

        return schema.validate(node);
    }

    protected JsonSchema getJsonSchemaFromParameters(Parameters parameters) {
        JsonNode node = Json.getObjectMapper().valueToTree(parameters);
        VersionFlag versionFlag = SpecVersionDetector.detect(node);
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(versionFlag);
        JsonSchema schema = factory.getSchema(node);

        schema.initializeValidators();

        return schema;
    }
}
