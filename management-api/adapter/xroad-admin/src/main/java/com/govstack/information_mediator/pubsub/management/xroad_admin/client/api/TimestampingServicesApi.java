package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TimestampingService;

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
public class TimestampingServicesApi {
    private ApiClient apiClient;

    public TimestampingServicesApi() {
        this(new ApiClient());
    }

    @Autowired
    public TimestampingServicesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * view the approved timestamping services
     * &lt;h3&gt;Administrator views the approved timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of approved timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingService&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApprovedTimestampingServicesRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return apiClient.invokeAPI("/timestamping-services", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view the approved timestamping services
     * &lt;h3&gt;Administrator views the approved timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of approved timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingService&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<TimestampingService> getApprovedTimestampingServices() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return getApprovedTimestampingServicesRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * view the approved timestamping services
     * &lt;h3&gt;Administrator views the approved timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of approved timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;TimestampingService&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<TimestampingService>>> getApprovedTimestampingServicesWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return getApprovedTimestampingServicesRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * view the approved timestamping services
     * &lt;h3&gt;Administrator views the approved timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of approved timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApprovedTimestampingServicesWithResponseSpec() throws WebClientResponseException {
        return getApprovedTimestampingServicesRequestCreation();
    }
}
