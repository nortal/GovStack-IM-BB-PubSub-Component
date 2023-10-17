package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.SecurityServer;
import java.util.Set;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class SecurityServersApi {
    private ApiClient apiClient;

    public SecurityServersApi() {
        this(new ApiClient());
    }

    @Autowired
    public SecurityServersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get security server information
     * &lt;h3&gt;Administrator views the details of a security server.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the security server
     * @return SecurityServer
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSecurityServerRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getSecurityServer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return apiClient.invokeAPI("/security-servers/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server information
     * &lt;h3&gt;Administrator views the details of a security server.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the security server
     * @return SecurityServer
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<SecurityServer> getSecurityServer(String id) throws WebClientResponseException {
        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return getSecurityServerRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get security server information
     * &lt;h3&gt;Administrator views the details of a security server.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the security server
     * @return ResponseEntity&lt;SecurityServer&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<SecurityServer>> getSecurityServerWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return getSecurityServerRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get security server information
     * &lt;h3&gt;Administrator views the details of a security server.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the security server
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSecurityServerWithResponseSpec(String id) throws WebClientResponseException {
        return getSecurityServerRequestCreation(id);
    }
    /**
     * get all security servers
     * &lt;h3&gt;Administrator views the details of all security servers.&lt;/h3&gt;
     * <p><b>200</b> - list of SecurityServer objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentServer whether to only get the current server&#39;s identifier
     * @return Set&lt;SecurityServer&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSecurityServersRequestCreation(Boolean currentServer) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "current_server", currentServer));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return apiClient.invokeAPI("/security-servers", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get all security servers
     * &lt;h3&gt;Administrator views the details of all security servers.&lt;/h3&gt;
     * <p><b>200</b> - list of SecurityServer objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentServer whether to only get the current server&#39;s identifier
     * @return Set&lt;SecurityServer&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<SecurityServer> getSecurityServers(Boolean currentServer) throws WebClientResponseException {
        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return getSecurityServersRequestCreation(currentServer).bodyToFlux(localVarReturnType);
    }

    /**
     * get all security servers
     * &lt;h3&gt;Administrator views the details of all security servers.&lt;/h3&gt;
     * <p><b>200</b> - list of SecurityServer objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentServer whether to only get the current server&#39;s identifier
     * @return ResponseEntity&lt;Set&lt;SecurityServer&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<SecurityServer>>> getSecurityServersWithHttpInfo(Boolean currentServer) throws WebClientResponseException {
        ParameterizedTypeReference<SecurityServer> localVarReturnType = new ParameterizedTypeReference<SecurityServer>() {};
        return getSecurityServersRequestCreation(currentServer).toEntityList(localVarReturnType);
    }

    /**
     * get all security servers
     * &lt;h3&gt;Administrator views the details of all security servers.&lt;/h3&gt;
     * <p><b>200</b> - list of SecurityServer objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentServer whether to only get the current server&#39;s identifier
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSecurityServersWithResponseSpec(Boolean currentServer) throws WebClientResponseException {
        return getSecurityServersRequestCreation(currentServer);
    }
}
