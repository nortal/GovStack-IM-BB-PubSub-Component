package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Endpoint;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Service;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClient;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClients;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceUpdate;
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
public class ServicesApi {
    private ApiClient apiClient;

    public ServicesApi() {
        this(new ApiClient());
    }

    @Autowired
    public ServicesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * create endpoint
     * &lt;h3&gt;Administrator creates a new endpoint.&lt;/h3&gt;
     * <p><b>201</b> - endpoint added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param endpoint The endpoint parameter
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addEndpointRequestCreation(String id, Endpoint endpoint) throws WebClientResponseException {
        Object postBody = endpoint;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return apiClient.invokeAPI("/services/{id}/endpoints", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * create endpoint
     * &lt;h3&gt;Administrator creates a new endpoint.&lt;/h3&gt;
     * <p><b>201</b> - endpoint added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param endpoint The endpoint parameter
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Endpoint> addEndpoint(String id, Endpoint endpoint) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return addEndpointRequestCreation(id, endpoint).bodyToMono(localVarReturnType);
    }

    /**
     * create endpoint
     * &lt;h3&gt;Administrator creates a new endpoint.&lt;/h3&gt;
     * <p><b>201</b> - endpoint added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param endpoint The endpoint parameter
     * @return ResponseEntity&lt;Endpoint&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Endpoint>> addEndpointWithHttpInfo(String id, Endpoint endpoint) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return addEndpointRequestCreation(id, endpoint).toEntity(localVarReturnType);
    }

    /**
     * create endpoint
     * &lt;h3&gt;Administrator creates a new endpoint.&lt;/h3&gt;
     * <p><b>201</b> - endpoint added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param endpoint The endpoint parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addEndpointWithResponseSpec(String id, Endpoint endpoint) throws WebClientResponseException {
        return addEndpointRequestCreation(id, endpoint);
    }
    /**
     * add access rights to selected service for new ServiceClients
     * &lt;h3&gt;Adds access rights to selected service for new ServiceClients.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addServiceServiceClientsRequestCreation(String id, ServiceClients serviceClients) throws WebClientResponseException {
        Object postBody = serviceClients;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addServiceServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return apiClient.invokeAPI("/services/{id}/service-clients", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add access rights to selected service for new ServiceClients
     * &lt;h3&gt;Adds access rights to selected service for new ServiceClients.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> addServiceServiceClients(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return addServiceServiceClientsRequestCreation(id, serviceClients).bodyToFlux(localVarReturnType);
    }

    /**
     * add access rights to selected service for new ServiceClients
     * &lt;h3&gt;Adds access rights to selected service for new ServiceClients.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> addServiceServiceClientsWithHttpInfo(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return addServiceServiceClientsRequestCreation(id, serviceClients).toEntityList(localVarReturnType);
    }

    /**
     * add access rights to selected service for new ServiceClients
     * &lt;h3&gt;Adds access rights to selected service for new ServiceClients.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addServiceServiceClientsWithResponseSpec(String id, ServiceClients serviceClients) throws WebClientResponseException {
        return addServiceServiceClientsRequestCreation(id, serviceClients);
    }
    /**
     * remove access to selected service from given ServiceClients
     * &lt;h3&gt;Administrator removes access to selected service from given ServiceClients.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteServiceServiceClientsRequestCreation(String id, ServiceClients serviceClients) throws WebClientResponseException {
        Object postBody = serviceClients;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteServiceServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/services/{id}/service-clients/delete", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * remove access to selected service from given ServiceClients
     * &lt;h3&gt;Administrator removes access to selected service from given ServiceClients.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteServiceServiceClients(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceServiceClientsRequestCreation(id, serviceClients).bodyToMono(localVarReturnType);
    }

    /**
     * remove access to selected service from given ServiceClients
     * &lt;h3&gt;Administrator removes access to selected service from given ServiceClients.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteServiceServiceClientsWithHttpInfo(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceServiceClientsRequestCreation(id, serviceClients).toEntity(localVarReturnType);
    }

    /**
     * remove access to selected service from given ServiceClients
     * &lt;h3&gt;Administrator removes access to selected service from given ServiceClients.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceClients The serviceClients parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteServiceServiceClientsWithResponseSpec(String id, ServiceClients serviceClients) throws WebClientResponseException {
        return deleteServiceServiceClientsRequestCreation(id, serviceClients);
    }
    /**
     * get service
     * &lt;h3&gt;Administrator views selected service.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return Service
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getService", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return apiClient.invokeAPI("/services/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get service
     * &lt;h3&gt;Administrator views selected service.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return Service
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Service> getService(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return getServiceRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get service
     * &lt;h3&gt;Administrator views selected service.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return ResponseEntity&lt;Service&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Service>> getServiceWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return getServiceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get service
     * &lt;h3&gt;Administrator views selected service.&lt;/h3&gt;
     * <p><b>200</b> - ok
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceWithResponseSpec(String id) throws WebClientResponseException {
        return getServiceRequestCreation(id);
    }
    /**
     * get service clients who have access rights for the selected service
     * &lt;h3&gt;Administrator views service clients who have access to the given service.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceServiceClientsRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getServiceServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return apiClient.invokeAPI("/services/{id}/service-clients", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected service
     * &lt;h3&gt;Administrator views service clients who have access to the given service.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> getServiceServiceClients(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getServiceServiceClientsRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected service
     * &lt;h3&gt;Administrator views service clients who have access to the given service.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> getServiceServiceClientsWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getServiceServiceClientsRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected service
     * &lt;h3&gt;Administrator views service clients who have access to the given service.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceServiceClientsWithResponseSpec(String id) throws WebClientResponseException {
        return getServiceServiceClientsRequestCreation(id);
    }
    /**
     * update service
     * &lt;h3&gt;Administrator updates the service.&lt;/h3&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, it is possible to receive a warnings response from this endpoint if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because SSL handshake fails&lt;/li&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because of other errors (e.g. host unreachable)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceUpdate The serviceUpdate parameter
     * @return Service
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateServiceRequestCreation(String id, ServiceUpdate serviceUpdate) throws WebClientResponseException {
        Object postBody = serviceUpdate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateService", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return apiClient.invokeAPI("/services/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update service
     * &lt;h3&gt;Administrator updates the service.&lt;/h3&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, it is possible to receive a warnings response from this endpoint if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because SSL handshake fails&lt;/li&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because of other errors (e.g. host unreachable)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceUpdate The serviceUpdate parameter
     * @return Service
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Service> updateService(String id, ServiceUpdate serviceUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return updateServiceRequestCreation(id, serviceUpdate).bodyToMono(localVarReturnType);
    }

    /**
     * update service
     * &lt;h3&gt;Administrator updates the service.&lt;/h3&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, it is possible to receive a warnings response from this endpoint if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because SSL handshake fails&lt;/li&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because of other errors (e.g. host unreachable)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceUpdate The serviceUpdate parameter
     * @return ResponseEntity&lt;Service&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Service>> updateServiceWithHttpInfo(String id, ServiceUpdate serviceUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return updateServiceRequestCreation(id, serviceUpdate).toEntity(localVarReturnType);
    }

    /**
     * update service
     * &lt;h3&gt;Administrator updates the service.&lt;/h3&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, it is possible to receive a warnings response from this endpoint if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because SSL handshake fails&lt;/li&gt; &lt;li&gt;SSL authentication is set to true, but https connection to the service URL fails because of other errors (e.g. host unreachable)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service
     * @param serviceUpdate The serviceUpdate parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateServiceWithResponseSpec(String id, ServiceUpdate serviceUpdate) throws WebClientResponseException {
        return updateServiceRequestCreation(id, serviceUpdate);
    }
}
