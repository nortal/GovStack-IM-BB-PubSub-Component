package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.IgnoreWarnings;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Service;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceDescription;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceDescriptionDisabledNotice;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceDescriptionUpdate;
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
public class ServiceDescriptionsApi {
    private ApiClient apiClient;

    public ServiceDescriptionsApi() {
        this(new ApiClient());
    }

    @Autowired
    public ServiceDescriptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * delete service description
     * &lt;h3&gt;Administrator deletes the service description.&lt;/h3&gt;
     * <p><b>204</b> - service description deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteServiceDescriptionRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete service description
     * &lt;h3&gt;Administrator deletes the service description.&lt;/h3&gt;
     * <p><b>204</b> - service description deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteServiceDescription(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceDescriptionRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * delete service description
     * &lt;h3&gt;Administrator deletes the service description.&lt;/h3&gt;
     * <p><b>204</b> - service description deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteServiceDescriptionWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceDescriptionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * delete service description
     * &lt;h3&gt;Administrator deletes the service description.&lt;/h3&gt;
     * <p><b>204</b> - service description deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteServiceDescriptionWithResponseSpec(String id) throws WebClientResponseException {
        return deleteServiceDescriptionRequestCreation(id);
    }
    /**
     * disable selected service description
     * &lt;h3&gt;Administrator disables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description disabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionDisabledNotice The serviceDescriptionDisabledNotice parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec disableServiceDescriptionRequestCreation(String id, ServiceDescriptionDisabledNotice serviceDescriptionDisabledNotice) throws WebClientResponseException {
        Object postBody = serviceDescriptionDisabledNotice;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling disableServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}/disable", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * disable selected service description
     * &lt;h3&gt;Administrator disables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description disabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionDisabledNotice The serviceDescriptionDisabledNotice parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> disableServiceDescription(String id, ServiceDescriptionDisabledNotice serviceDescriptionDisabledNotice) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return disableServiceDescriptionRequestCreation(id, serviceDescriptionDisabledNotice).bodyToMono(localVarReturnType);
    }

    /**
     * disable selected service description
     * &lt;h3&gt;Administrator disables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description disabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionDisabledNotice The serviceDescriptionDisabledNotice parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> disableServiceDescriptionWithHttpInfo(String id, ServiceDescriptionDisabledNotice serviceDescriptionDisabledNotice) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return disableServiceDescriptionRequestCreation(id, serviceDescriptionDisabledNotice).toEntity(localVarReturnType);
    }

    /**
     * disable selected service description
     * &lt;h3&gt;Administrator disables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description disabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionDisabledNotice The serviceDescriptionDisabledNotice parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec disableServiceDescriptionWithResponseSpec(String id, ServiceDescriptionDisabledNotice serviceDescriptionDisabledNotice) throws WebClientResponseException {
        return disableServiceDescriptionRequestCreation(id, serviceDescriptionDisabledNotice);
    }
    /**
     * enable selected service description
     * &lt;h3&gt;Administrator enables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description enabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec enableServiceDescriptionRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling enableServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}/enable", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * enable selected service description
     * &lt;h3&gt;Administrator enables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description enabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> enableServiceDescription(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return enableServiceDescriptionRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * enable selected service description
     * &lt;h3&gt;Administrator enables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description enabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> enableServiceDescriptionWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return enableServiceDescriptionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * enable selected service description
     * &lt;h3&gt;Administrator enables service description.&lt;/h3&gt;
     * <p><b>204</b> - service description enabled
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec enableServiceDescriptionWithResponseSpec(String id) throws WebClientResponseException {
        return enableServiceDescriptionRequestCreation(id);
    }
    /**
     * get service description with provided id
     * &lt;h3&gt;Administrator views a service description with a certain id.&lt;/h3&gt;
     * <p><b>200</b> - wanted service description
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceDescriptionRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get service description with provided id
     * &lt;h3&gt;Administrator views a service description with a certain id.&lt;/h3&gt;
     * <p><b>200</b> - wanted service description
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ServiceDescription> getServiceDescription(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return getServiceDescriptionRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get service description with provided id
     * &lt;h3&gt;Administrator views a service description with a certain id.&lt;/h3&gt;
     * <p><b>200</b> - wanted service description
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseEntity&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ServiceDescription>> getServiceDescriptionWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return getServiceDescriptionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get service description with provided id
     * &lt;h3&gt;Administrator views a service description with a certain id.&lt;/h3&gt;
     * <p><b>200</b> - wanted service description
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceDescriptionWithResponseSpec(String id) throws WebClientResponseException {
        return getServiceDescriptionRequestCreation(id);
    }
    /**
     * get services for the selected service description
     * &lt;h3&gt;Administrator views the services for the selected service description.&lt;/h3&gt;
     * <p><b>200</b> - list of services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return Set&lt;Service&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceDescriptionServicesRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getServiceDescriptionServices", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/service-descriptions/{id}/services", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get services for the selected service description
     * &lt;h3&gt;Administrator views the services for the selected service description.&lt;/h3&gt;
     * <p><b>200</b> - list of services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return Set&lt;Service&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<Service> getServiceDescriptionServices(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return getServiceDescriptionServicesRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get services for the selected service description
     * &lt;h3&gt;Administrator views the services for the selected service description.&lt;/h3&gt;
     * <p><b>200</b> - list of services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseEntity&lt;Set&lt;Service&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<Service>>> getServiceDescriptionServicesWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Service> localVarReturnType = new ParameterizedTypeReference<Service>() {};
        return getServiceDescriptionServicesRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get services for the selected service description
     * &lt;h3&gt;Administrator views the services for the selected service description.&lt;/h3&gt;
     * <p><b>200</b> - list of services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceDescriptionServicesWithResponseSpec(String id) throws WebClientResponseException {
        return getServiceDescriptionServicesRequestCreation(id);
    }
    /**
     * refresh selected service description
     * &lt;h3&gt;Administrator refreshes service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if refreshing the service description fails due to invalid URLs, already existing service description or already existing services. If refreshing the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If refreshing the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If refreshing the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description refreshed
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param ignoreWarnings The ignoreWarnings parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec refreshServiceDescriptionRequestCreation(String id, IgnoreWarnings ignoreWarnings) throws WebClientResponseException {
        Object postBody = ignoreWarnings;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling refreshServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}/refresh", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * refresh selected service description
     * &lt;h3&gt;Administrator refreshes service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if refreshing the service description fails due to invalid URLs, already existing service description or already existing services. If refreshing the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If refreshing the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If refreshing the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description refreshed
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param ignoreWarnings The ignoreWarnings parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ServiceDescription> refreshServiceDescription(String id, IgnoreWarnings ignoreWarnings) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return refreshServiceDescriptionRequestCreation(id, ignoreWarnings).bodyToMono(localVarReturnType);
    }

    /**
     * refresh selected service description
     * &lt;h3&gt;Administrator refreshes service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if refreshing the service description fails due to invalid URLs, already existing service description or already existing services. If refreshing the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If refreshing the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If refreshing the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description refreshed
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param ignoreWarnings The ignoreWarnings parameter
     * @return ResponseEntity&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ServiceDescription>> refreshServiceDescriptionWithHttpInfo(String id, IgnoreWarnings ignoreWarnings) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return refreshServiceDescriptionRequestCreation(id, ignoreWarnings).toEntity(localVarReturnType);
    }

    /**
     * refresh selected service description
     * &lt;h3&gt;Administrator refreshes service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if refreshing the service description fails due to invalid URLs, already existing service description or already existing services. If refreshing the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If refreshing the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If refreshing the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;IgnoreWarnings.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description refreshed
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param ignoreWarnings The ignoreWarnings parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec refreshServiceDescriptionWithResponseSpec(String id, IgnoreWarnings ignoreWarnings) throws WebClientResponseException {
        return refreshServiceDescriptionRequestCreation(id, ignoreWarnings);
    }
    /**
     * update url or service code for the selected service description
     * &lt;h3&gt;Administrator updates the selected service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if updating the service description fails due to invalid URLs, already existing service description or already existing services. If updating the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If updating the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If updating the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description modified
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionUpdate The serviceDescriptionUpdate parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateServiceDescriptionRequestCreation(String id, ServiceDescriptionUpdate serviceDescriptionUpdate) throws WebClientResponseException {
        Object postBody = serviceDescriptionUpdate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return apiClient.invokeAPI("/service-descriptions/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update url or service code for the selected service description
     * &lt;h3&gt;Administrator updates the selected service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if updating the service description fails due to invalid URLs, already existing service description or already existing services. If updating the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If updating the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If updating the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description modified
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionUpdate The serviceDescriptionUpdate parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ServiceDescription> updateServiceDescription(String id, ServiceDescriptionUpdate serviceDescriptionUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return updateServiceDescriptionRequestCreation(id, serviceDescriptionUpdate).bodyToMono(localVarReturnType);
    }

    /**
     * update url or service code for the selected service description
     * &lt;h3&gt;Administrator updates the selected service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if updating the service description fails due to invalid URLs, already existing service description or already existing services. If updating the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If updating the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If updating the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description modified
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionUpdate The serviceDescriptionUpdate parameter
     * @return ResponseEntity&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ServiceDescription>> updateServiceDescriptionWithHttpInfo(String id, ServiceDescriptionUpdate serviceDescriptionUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return updateServiceDescriptionRequestCreation(id, serviceDescriptionUpdate).toEntity(localVarReturnType);
    }

    /**
     * update url or service code for the selected service description
     * &lt;h3&gt;Administrator updates the selected service description.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if updating the service description fails due to invalid URLs, already existing service description or already existing services. If updating the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If updating the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If updating the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionUpdate.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;WSDL or OpenAPI3 validation fails and validation warnings are returned (warning code will be either &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;existing services will be removed (warning code &lt;code&gt;deleting_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be removed)&lt;/li&gt; &lt;li&gt;new services will be added (warning code &lt;code&gt;adding_services&lt;/code&gt; and the warning metadata contains a list of service codes that will be added)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>200</b> - service description modified
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the service description
     * @param serviceDescriptionUpdate The serviceDescriptionUpdate parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateServiceDescriptionWithResponseSpec(String id, ServiceDescriptionUpdate serviceDescriptionUpdate) throws WebClientResponseException {
        return updateServiceDescriptionRequestCreation(id, serviceDescriptionUpdate);
    }
}
