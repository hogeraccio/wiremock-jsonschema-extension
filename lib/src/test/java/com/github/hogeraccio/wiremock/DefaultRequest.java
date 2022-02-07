package com.github.hogeraccio.wiremock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import com.github.tomakehurst.wiremock.http.Cookie;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.google.common.base.Optional;

class DefaultRequest implements Request {
    public String getUrl() {
        return "";
    }

    public String getAbsoluteUrl() {
        return "";
    }

    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    public String getScheme() {
        return "";
    }

    public String getHost() {
        return "";
    }

    public int getPort() {
        return 80;
    }

    public String getClientIp() {
        return "";
    }

    public String getHeader(String key) {
        return "";
    }

    public HttpHeader header(String key) {
        return HttpHeader.empty(key);
    }

    public ContentTypeHeader contentTypeHeader() {
        return ContentTypeHeader.absent();
    }

    public HttpHeaders getHeaders() {
        return HttpHeaders.noHeaders();
    }

    public boolean containsHeader(String key) {
        return false;
    }

    public Set<String> getAllHeaderKeys() {
        return new HashSet<String>();
    }

    public Map<String, Cookie> getCookies() {
        return new HashMap<String, Cookie>();
    }

    public QueryParameter queryParameter(String key) {
        return QueryParameter.absent(key);
    }

    public byte[] getBody() {
        return new byte[] {};
    }

    public String getBodyAsString() {
        return "";
    }

    public String getBodyAsBase64() {
        return "";
    }

    public boolean isMultipart() {
        return false;
    }

    public Collection<Part> getParts() {
        return new ArrayList<Request.Part>();
    }

    public Part getPart(String name) {
        return null;
    }

    public boolean isBrowserProxyRequest() {
        return false;
    }

    public Optional<Request> getOriginalRequest() {
        return Optional.absent();
    }
}
