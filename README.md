# Overview

This WireMock extension matches incoming requests whose body is JSON with JSON Schema.

# Running

Programmatically in Java, e.g.

```java
new WireMockServer(
    wireMockConfig().extensions("com.github.hogeraccio.wiremock.JsonSchemaRequestMatcher")
)
```

# Usage

This extension accepts JSON Schema as the parameters.

When using the API, make sure to set the `"name"` field of the customMatcher to `"jsonschema-matcher"`.
Here's an example cURL command that creates a stub mapping with the request matcher:
```sh
curl -d@- http://wiremock:8079/__admin/mappings <<-EOD
{
    "request" : {
        "method" : "POST",
        "url" : "/some_url",
        "customMatcher" : {
            "name" : "jsonschema-matcher",
            "parameters" : {
                "\$schema": "http://json-schema.org/draft-05/schema#",
                "type" : "object",
                "properties" : {
                    "id" : {
                        "type": "integer"
                    }
                }
            }
        }
    },
    "response" : {
        "status" : 199,
        "body": "success"
    }
}
EOD
```

Example request that matches the above stub mapping:
```sh
curl -d@- -X POST http://wiremock:8080/some_url <<-EOD
{
    "id" : 1
}
EOD
```

# Building

Run `gradle jar` to build the JAR without WireMock.
These will be placed in `lib/build/libs/`.
