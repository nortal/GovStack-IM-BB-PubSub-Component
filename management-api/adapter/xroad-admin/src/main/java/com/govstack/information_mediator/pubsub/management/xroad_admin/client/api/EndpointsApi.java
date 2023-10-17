package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Endpoint;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.EndpointUpdate;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClient;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClients;
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
public class EndpointsApi {
    private ApiClient apiClient;

    public EndpointsApi() {
        this(new ApiClient());
    }

    @Autowired
    public EndpointsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * add access rights for given service clients to the selected endpoint
     * &lt;h3&gt;Administrator add access rights for a service clients to the selected endpoint.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients The serviceClients parameter
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addEndpointServiceClientsRequestCreation(String id, ServiceClients serviceClients) throws WebClientResponseException {
        Object postBody = serviceClients;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addEndpointServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/endpoints/{id}/service-clients", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add access rights for given service clients to the selected endpoint
     * &lt;h3&gt;Administrator add access rights for a service clients to the selected endpoint.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients The serviceClients parameter
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> addEndpointServiceClients(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return addEndpointServiceClientsRequestCreation(id, serviceClients).bodyToFlux(localVarReturnType);
    }

    /**
     * add access rights for given service clients to the selected endpoint
     * &lt;h3&gt;Administrator add access rights for a service clients to the selected endpoint.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients The serviceClients parameter
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> addEndpointServiceClientsWithHttpInfo(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return addEndpointServiceClientsRequestCreation(id, serviceClients).toEntityList(localVarReturnType);
    }

    /**
     * add access rights for given service clients to the selected endpoint
     * &lt;h3&gt;Administrator add access rights for a service clients to the selected endpoint.&lt;/h3&gt;
     * <p><b>201</b> - access rights added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients The serviceClients parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addEndpointServiceClientsWithResponseSpec(String id, ServiceClients serviceClients) throws WebClientResponseException {
        return addEndpointServiceClientsRequestCreation(id, serviceClients);
    }
    /**
     * delete endpoint
     * &lt;h3&gt;Administrator removes an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - endpoint deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteEndpointRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/endpoints/{id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete endpoint
     * &lt;h3&gt;Administrator removes an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - endpoint deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteEndpoint(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEndpointRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * delete endpoint
     * &lt;h3&gt;Administrator removes an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - endpoint deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteEndpointWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEndpointRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * delete endpoint
     * &lt;h3&gt;Administrator removes an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - endpoint deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteEndpointWithResponseSpec(String id) throws WebClientResponseException {
        return deleteEndpointRequestCreation(id);
    }
    /**
     * remove access rights from specified service clients to the selected endpoint
     * &lt;h3&gt;Administrator removes access rights from a service clients to an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients Service client to be removed
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteEndpointServiceClientsRequestCreation(String id, ServiceClients serviceClients) throws WebClientResponseException {
        Object postBody = serviceClients;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteEndpointServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/endpoints/{id}/service-clients/delete", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * remove access rights from specified service clients to the selected endpoint
     * &lt;h3&gt;Administrator removes access rights from a service clients to an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients Service client to be removed
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteEndpointServiceClients(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEndpointServiceClientsRequestCreation(id, serviceClients).bodyToMono(localVarReturnType);
    }

    /**
     * remove access rights from specified service clients to the selected endpoint
     * &lt;h3&gt;Administrator removes access rights from a service clients to an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients Service client to be removed
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteEndpointServiceClientsWithHttpInfo(String id, ServiceClients serviceClients) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteEndpointServiceClientsRequestCreation(id, serviceClients).toEntity(localVarReturnType);
    }

    /**
     * remove access rights from specified service clients to the selected endpoint
     * &lt;h3&gt;Administrator removes access rights from a service clients to an endpoint.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param serviceClients Service client to be removed
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteEndpointServiceClientsWithResponseSpec(String id, ServiceClients serviceClients) throws WebClientResponseException {
        return deleteEndpointServiceClientsRequestCreation(id, serviceClients);
    }
    /**
     * Get an endpoint by its id
     * &lt;h3&gt;Administrator fetches an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getEndpointRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return apiClient.invokeAPI("/endpoints/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get an endpoint by its id
     * &lt;h3&gt;Administrator fetches an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Endpoint> getEndpoint(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return getEndpointRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * Get an endpoint by its id
     * &lt;h3&gt;Administrator fetches an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return ResponseEntity&lt;Endpoint&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Endpoint>> getEndpointWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return getEndpointRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get an endpoint by its id
     * &lt;h3&gt;Administrator fetches an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getEndpointWithResponseSpec(String id) throws WebClientResponseException {
        return getEndpointRequestCreation(id);
    }
    /**
     * get service clients who have access rights for the selected endpoint
     * &lt;h3&gt;Administrator views endpoints access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getEndpointServiceClientsRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getEndpointServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/endpoints/{id}/service-clients", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected endpoint
     * &lt;h3&gt;Administrator views endpoints access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> getEndpointServiceClients(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getEndpointServiceClientsRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected endpoint
     * &lt;h3&gt;Administrator views endpoints access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> getEndpointServiceClientsWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getEndpointServiceClientsRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get service clients who have access rights for the selected endpoint
     * &lt;h3&gt;Administrator views endpoints access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getEndpointServiceClientsWithResponseSpec(String id) throws WebClientResponseException {
        return getEndpointServiceClientsRequestCreation(id);
    }
    /**
     * Update an endpoint
     * &lt;h3&gt;Administrator updates an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param endpointUpdate The endpointUpdate parameter
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateEndpointRequestCreation(String id, EndpointUpdate endpointUpdate) throws WebClientResponseException {
        Object postBody = endpointUpdate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/endpoints/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an endpoint
     * &lt;h3&gt;Administrator updates an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param endpointUpdate The endpointUpdate parameter
     * @return Endpoint
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Endpoint> updateEndpoint(String id, EndpointUpdate endpointUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return updateEndpointRequestCreation(id, endpointUpdate).bodyToMono(localVarReturnType);
    }

    /**
     * Update an endpoint
     * &lt;h3&gt;Administrator updates an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param endpointUpdate The endpointUpdate parameter
     * @return ResponseEntity&lt;Endpoint&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Endpoint>> updateEndpointWithHttpInfo(String id, EndpointUpdate endpointUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Endpoint> localVarReturnType = new ParameterizedTypeReference<Endpoint>() {};
        return updateEndpointRequestCreation(id, endpointUpdate).toEntity(localVarReturnType);
    }

    /**
     * Update an endpoint
     * &lt;h3&gt;Administrator updates an endpoint.&lt;/h3&gt;
     * <p><b>200</b> - endpoint updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the endpoint
     * @param endpointUpdate The endpointUpdate parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateEndpointWithResponseSpec(String id, EndpointUpdate endpointUpdate) throws WebClientResponseException {
        return updateEndpointRequestCreation(id, endpointUpdate);
    }
}
