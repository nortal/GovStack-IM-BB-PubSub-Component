package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.User;

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
public class UserApi {
    private ApiClient apiClient;

    public UserApi() {
        this(new ApiClient());
    }

    @Autowired
    public UserApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get user data for the logged user
     * &lt;h3&gt;Administrator gets user data from backend.&lt;/h3&gt;
     * <p><b>200</b> - user details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return User
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getUserRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<User> localVarReturnType = new ParameterizedTypeReference<User>() {};
        return apiClient.invokeAPI("/user", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get user data for the logged user
     * &lt;h3&gt;Administrator gets user data from backend.&lt;/h3&gt;
     * <p><b>200</b> - user details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return User
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<User> getUser() throws WebClientResponseException {
        ParameterizedTypeReference<User> localVarReturnType = new ParameterizedTypeReference<User>() {};
        return getUserRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * get user data for the logged user
     * &lt;h3&gt;Administrator gets user data from backend.&lt;/h3&gt;
     * <p><b>200</b> - user details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;User&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<User>> getUserWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<User> localVarReturnType = new ParameterizedTypeReference<User>() {};
        return getUserRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * get user data for the logged user
     * &lt;h3&gt;Administrator gets user data from backend.&lt;/h3&gt;
     * <p><b>200</b> - user details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getUserWithResponseSpec() throws WebClientResponseException {
        return getUserRequestCreation();
    }
}
