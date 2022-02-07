package com.github.hogeraccio.wiremock;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.MatchResult;

class JsonSchemaRequestMatcherTest {
    @Test
    void wrongJson() {
        JsonSchemaRequestMatcher requestMatcher = new JsonSchemaRequestMatcher();

        Request request = new DefaultRequest() {
            public String getBodyAsString() {
                return "{\"id\": \"2\"}";
            };
        };
        Parameters parameters = new Parameters();
        Map<String, Object> id = new HashMap<String, Object>();
        id.put("type", "number");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", id);
        parameters.put("$schema", "http://json-schema.org/draft-06/schema#");
        parameters.put("properties", properties);

        MatchResult expected = MatchResult.noMatch();
        MatchResult actual = requestMatcher.match(request, parameters);
        assertEquals(expected.getDistance(), actual.getDistance());
    }

    @Test
    void correctJson() {
        JsonSchemaRequestMatcher requestMatcher = new JsonSchemaRequestMatcher();

        Request request = new DefaultRequest() {
            public String getBodyAsString() {
                return "{\"id\": 2}";
            };
        };
        Parameters parameters = new Parameters();
        Map<String, Object> id = new HashMap<String, Object>();
        id.put("type", "number");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", id);
        parameters.put("$schema", "http://json-schema.org/draft-06/schema#");
        parameters.put("properties", properties);

        MatchResult expected = MatchResult.exactMatch();
        MatchResult actual = requestMatcher.match(request, parameters);
        assertEquals(expected.getDistance(), actual.getDistance());
    }

    @Test
    void unsupportedSchema() {
        JsonSchemaRequestMatcher requestMatcher = new JsonSchemaRequestMatcher();

        Request request = new DefaultRequest() {
            public String getBodyAsString() {
                return "{\"id\": 2}";
            };
        };
        Parameters parameters = new Parameters();
        Map<String, Object> id = new HashMap<String, Object>();
        id.put("type", "number");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", id);
        parameters.put("$schema", "https://json-schema.org/draft/2020-12/schema#");
        parameters.put("properties", properties);

        MatchResult expected = MatchResult.noMatch();
        MatchResult actual = requestMatcher.match(request, parameters);
        assertEquals(expected.getDistance(), actual.getDistance());
    }
}
