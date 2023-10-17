package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

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
public class MemberClassesApi {
    private ApiClient apiClient;

    public MemberClassesApi() {
        this(new ApiClient());
    }

    @Autowired
    public MemberClassesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get list of known member classes
     * &lt;h3&gt;Administrator lists member classes.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentInstance if true, return member classes for this instance. if false (default), return member classes for all instances
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMemberClassesRequestCreation(Boolean currentInstance) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "current_instance", currentInstance));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return apiClient.invokeAPI("/member-classes", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get list of known member classes
     * &lt;h3&gt;Administrator lists member classes.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentInstance if true, return member classes for this instance. if false (default), return member classes for all instances
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Set<String>> getMemberClasses(Boolean currentInstance) throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getMemberClassesRequestCreation(currentInstance).bodyToMono(localVarReturnType);
    }

    /**
     * get list of known member classes
     * &lt;h3&gt;Administrator lists member classes.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentInstance if true, return member classes for this instance. if false (default), return member classes for all instances
     * @return ResponseEntity&lt;Set&lt;String&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Set<String>>> getMemberClassesWithHttpInfo(Boolean currentInstance) throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getMemberClassesRequestCreation(currentInstance).toEntity(localVarReturnType);
    }

    /**
     * get list of known member classes
     * &lt;h3&gt;Administrator lists member classes.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param currentInstance if true, return member classes for this instance. if false (default), return member classes for all instances
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMemberClassesWithResponseSpec(Boolean currentInstance) throws WebClientResponseException {
        return getMemberClassesRequestCreation(currentInstance);
    }
    /**
     * get list of known member classes for a given instance
     * &lt;h3&gt;Administrator lists member classes for a given instance.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id instance id
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMemberClassesForInstanceRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getMemberClassesForInstance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return apiClient.invokeAPI("/member-classes/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get list of known member classes for a given instance
     * &lt;h3&gt;Administrator lists member classes for a given instance.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id instance id
     * @return Set&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Set<String>> getMemberClassesForInstance(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getMemberClassesForInstanceRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get list of known member classes for a given instance
     * &lt;h3&gt;Administrator lists member classes for a given instance.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id instance id
     * @return ResponseEntity&lt;Set&lt;String&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Set<String>>> getMemberClassesForInstanceWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Set<String>> localVarReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return getMemberClassesForInstanceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get list of known member classes for a given instance
     * &lt;h3&gt;Administrator lists member classes for a given instance.&lt;/h3&gt;
     * <p><b>200</b> - array of member classes
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id instance id
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMemberClassesForInstanceWithResponseSpec(String id) throws WebClientResponseException {
        return getMemberClassesForInstanceRequestCreation(id);
    }
}
