package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.AccessRight;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.AccessRights;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateDetails;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Client;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ClientAdd;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ConnectionTypeWrapper;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import java.io.File;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.LocalGroup;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.LocalGroupAdd;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.OrphanInformation;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClient;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceClientType;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceDescription;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ServiceDescriptionAdd;
import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenCertificate;

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
public class ClientsApi {
    private ApiClient apiClient;

    public ClientsApi() {
        this(new ApiClient());
    }

    @Autowired
    public ClientsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add new client for the security server.
     * &lt;h3&gt;Adds new client to the system.&lt;/h3&gt; &lt;p&gt;The client id will be validated and if the validation fails a validation error is returned. Note that with this endpoint it is possible to add an unregistered member as a client. &lt;ul&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; true succeeds.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;When trying to add an unregister client, the error response has an error code &lt;code&gt;unregistered_member&lt;/code&gt; and the metadata field contains the identifier of the unregistered client&lt;/p&gt;
     * <p><b>201</b> - new client created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param clientAdd client to add
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addClientRequestCreation(ClientAdd clientAdd) throws WebClientResponseException {
        Object postBody = clientAdd;
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return apiClient.invokeAPI("/clients", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add new client for the security server.
     * &lt;h3&gt;Adds new client to the system.&lt;/h3&gt; &lt;p&gt;The client id will be validated and if the validation fails a validation error is returned. Note that with this endpoint it is possible to add an unregistered member as a client. &lt;ul&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; true succeeds.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;When trying to add an unregister client, the error response has an error code &lt;code&gt;unregistered_member&lt;/code&gt; and the metadata field contains the identifier of the unregistered client&lt;/p&gt;
     * <p><b>201</b> - new client created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param clientAdd client to add
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Client> addClient(ClientAdd clientAdd) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return addClientRequestCreation(clientAdd).bodyToMono(localVarReturnType);
    }

    /**
     * Add new client for the security server.
     * &lt;h3&gt;Adds new client to the system.&lt;/h3&gt; &lt;p&gt;The client id will be validated and if the validation fails a validation error is returned. Note that with this endpoint it is possible to add an unregistered member as a client. &lt;ul&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; true succeeds.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;When trying to add an unregister client, the error response has an error code &lt;code&gt;unregistered_member&lt;/code&gt; and the metadata field contains the identifier of the unregistered client&lt;/p&gt;
     * <p><b>201</b> - new client created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param clientAdd client to add
     * @return ResponseEntity&lt;Client&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Client>> addClientWithHttpInfo(ClientAdd clientAdd) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return addClientRequestCreation(clientAdd).toEntity(localVarReturnType);
    }

    /**
     * Add new client for the security server.
     * &lt;h3&gt;Adds new client to the system.&lt;/h3&gt; &lt;p&gt;The client id will be validated and if the validation fails a validation error is returned. Note that with this endpoint it is possible to add an unregistered member as a client. &lt;ul&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to add an unregistered member with &lt;code&gt;ClientAdd.ignore_warnings&lt;/code&gt; &#x3D; true succeeds.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;When trying to add an unregister client, the error response has an error code &lt;code&gt;unregistered_member&lt;/code&gt; and the metadata field contains the identifier of the unregistered client&lt;/p&gt;
     * <p><b>201</b> - new client created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param clientAdd client to add
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addClientWithResponseSpec(ClientAdd clientAdd) throws WebClientResponseException {
        return addClientRequestCreation(clientAdd);
    }
    /**
     * add new local group for the security server client
     * &lt;h3&gt;Administrator adds a new local group for the client.&lt;/h3&gt;
     * <p><b>201</b> - local group created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param localGroupAdd group to add
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addClientLocalGroupRequestCreation(String id, LocalGroupAdd localGroupAdd) throws WebClientResponseException {
        Object postBody = localGroupAdd;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addClientLocalGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return apiClient.invokeAPI("/clients/{id}/local-groups", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new local group for the security server client
     * &lt;h3&gt;Administrator adds a new local group for the client.&lt;/h3&gt;
     * <p><b>201</b> - local group created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param localGroupAdd group to add
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<LocalGroup> addClientLocalGroup(String id, LocalGroupAdd localGroupAdd) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return addClientLocalGroupRequestCreation(id, localGroupAdd).bodyToMono(localVarReturnType);
    }

    /**
     * add new local group for the security server client
     * &lt;h3&gt;Administrator adds a new local group for the client.&lt;/h3&gt;
     * <p><b>201</b> - local group created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param localGroupAdd group to add
     * @return ResponseEntity&lt;LocalGroup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<LocalGroup>> addClientLocalGroupWithHttpInfo(String id, LocalGroupAdd localGroupAdd) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return addClientLocalGroupRequestCreation(id, localGroupAdd).toEntity(localVarReturnType);
    }

    /**
     * add new local group for the security server client
     * &lt;h3&gt;Administrator adds a new local group for the client.&lt;/h3&gt;
     * <p><b>201</b> - local group created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param localGroupAdd group to add
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addClientLocalGroupWithResponseSpec(String id, LocalGroupAdd localGroupAdd) throws WebClientResponseException {
        return addClientLocalGroupRequestCreation(id, localGroupAdd);
    }
    /**
     * add new service description for the security server client
     * &lt;h3&gt;Administrator adds a new service description for the client.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if adding the service description fails due to invalid URLs, already existing service description or already existing services. If adding the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If adding the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If adding the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if WSDL or OpenAPI3 validation fails and validation warnings are returned. The warnings will have the warning code &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt; depending on which type the service description is. The metadata of the warning contains the output of the validator itself. &lt;/p&gt;
     * <p><b>201</b> - service description created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param serviceDescriptionAdd The serviceDescriptionAdd parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addClientServiceDescriptionRequestCreation(String id, ServiceDescriptionAdd serviceDescriptionAdd) throws WebClientResponseException {
        Object postBody = serviceDescriptionAdd;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addClientServiceDescription", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/service-descriptions", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new service description for the security server client
     * &lt;h3&gt;Administrator adds a new service description for the client.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if adding the service description fails due to invalid URLs, already existing service description or already existing services. If adding the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If adding the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If adding the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if WSDL or OpenAPI3 validation fails and validation warnings are returned. The warnings will have the warning code &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt; depending on which type the service description is. The metadata of the warning contains the output of the validator itself. &lt;/p&gt;
     * <p><b>201</b> - service description created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param serviceDescriptionAdd The serviceDescriptionAdd parameter
     * @return ServiceDescription
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ServiceDescription> addClientServiceDescription(String id, ServiceDescriptionAdd serviceDescriptionAdd) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return addClientServiceDescriptionRequestCreation(id, serviceDescriptionAdd).bodyToMono(localVarReturnType);
    }

    /**
     * add new service description for the security server client
     * &lt;h3&gt;Administrator adds a new service description for the client.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if adding the service description fails due to invalid URLs, already existing service description or already existing services. If adding the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If adding the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If adding the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if WSDL or OpenAPI3 validation fails and validation warnings are returned. The warnings will have the warning code &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt; depending on which type the service description is. The metadata of the warning contains the output of the validator itself. &lt;/p&gt;
     * <p><b>201</b> - service description created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param serviceDescriptionAdd The serviceDescriptionAdd parameter
     * @return ResponseEntity&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ServiceDescription>> addClientServiceDescriptionWithHttpInfo(String id, ServiceDescriptionAdd serviceDescriptionAdd) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return addClientServiceDescriptionRequestCreation(id, serviceDescriptionAdd).toEntity(localVarReturnType);
    }

    /**
     * add new service description for the security server client
     * &lt;h3&gt;Administrator adds a new service description for the client.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response if adding the service description fails due to invalid URLs, already existing service description or already existing services. If adding the service description fails due to an invalid service URL, the error response will have the error code &lt;code&gt;invalid_service_url&lt;/code&gt; and the metadata will contain a list of invalid URLs. If adding the service description fails due to an already existing service description, the error response will have the error code &lt;code&gt;url_already_exists&lt;/code&gt; and the metadata will contain the existing URL. If adding the service description fails due to already existing services, the metadata array will have a more complex format. See the description of the &#39;409&#39; response in this endpoint. &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;ServiceDescriptionAdd.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if WSDL or OpenAPI3 validation fails and validation warnings are returned. The warnings will have the warning code &lt;code&gt;wsdl_validation_warnings&lt;/code&gt; or &lt;code&gt;openapi_validation_warnings&lt;/code&gt; depending on which type the service description is. The metadata of the warning contains the output of the validator itself. &lt;/p&gt;
     * <p><b>201</b> - service description created
     * <p><b>400</b> - there are warnings or errors related to the service description
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - &lt;h3&gt;An existing item already exists&lt;/h3&gt; &lt;p&gt; If there are existing WSDL services with the same name, the error response will include the existing services&#39; names and the containing WSDL&#39;s URL in the metadata array. &lt;/p&gt; &lt;p&gt; In this case the error code &lt;code&gt;service_already_exists&lt;/code&gt; (WSDL) or &lt;code&gt;service_code_already_exists&lt;/code&gt; (OPENAPI3) is used and the entries in the metadata array are ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [C1,U1,C2,U2....Cn,Un] where&lt;/li&gt; &lt;li&gt;C1 &#x3D; duplicate service full code&lt;/li&gt; &lt;li&gt;U1 &#x3D; URL of the existing WSDL that has the duplicate&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the example&lt;/strong&gt;
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param serviceDescriptionAdd The serviceDescriptionAdd parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addClientServiceDescriptionWithResponseSpec(String id, ServiceDescriptionAdd serviceDescriptionAdd) throws WebClientResponseException {
        return addClientServiceDescriptionRequestCreation(id, serviceDescriptionAdd);
    }
    /**
     * add new certificate for the security server client
     * &lt;h3&gt;Administrator adds a new certificate for the client.&lt;/h3&gt;
     * <p><b>201</b> - certificate added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param body certificate to add
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addClientTlsCertificateRequestCreation(String id, File body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addClientTlsCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return apiClient.invokeAPI("/clients/{id}/tls-certificates", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new certificate for the security server client
     * &lt;h3&gt;Administrator adds a new certificate for the client.&lt;/h3&gt;
     * <p><b>201</b> - certificate added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param body certificate to add
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CertificateDetails> addClientTlsCertificate(String id, File body) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return addClientTlsCertificateRequestCreation(id, body).bodyToMono(localVarReturnType);
    }

    /**
     * add new certificate for the security server client
     * &lt;h3&gt;Administrator adds a new certificate for the client.&lt;/h3&gt;
     * <p><b>201</b> - certificate added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param body certificate to add
     * @return ResponseEntity&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CertificateDetails>> addClientTlsCertificateWithHttpInfo(String id, File body) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return addClientTlsCertificateRequestCreation(id, body).toEntity(localVarReturnType);
    }

    /**
     * add new certificate for the security server client
     * &lt;h3&gt;Administrator adds a new certificate for the client.&lt;/h3&gt;
     * <p><b>201</b> - certificate added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param body certificate to add
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addClientTlsCertificateWithResponseSpec(String id, File body) throws WebClientResponseException {
        return addClientTlsCertificateRequestCreation(id, body);
    }
    /**
     * Add new access rights for selected service client. If service client did not exist yet, one is created.
     * &lt;h3&gt;Adds access rights to the service client.&lt;/h3&gt;
     * <p><b>201</b> - access right that was added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights The accessRights parameter
     * @return Set&lt;AccessRight&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addServiceClientAccessRightsRequestCreation(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        Object postBody = accessRights;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scId' is set
        if (scId == null) {
            throw new WebClientResponseException("Missing the required parameter 'scId' when calling addServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("sc_id", scId);

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

        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return apiClient.invokeAPI("/clients/{id}/service-clients/{sc_id}/access-rights", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add new access rights for selected service client. If service client did not exist yet, one is created.
     * &lt;h3&gt;Adds access rights to the service client.&lt;/h3&gt;
     * <p><b>201</b> - access right that was added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights The accessRights parameter
     * @return Set&lt;AccessRight&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<AccessRight> addServiceClientAccessRights(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return addServiceClientAccessRightsRequestCreation(id, scId, accessRights).bodyToFlux(localVarReturnType);
    }

    /**
     * Add new access rights for selected service client. If service client did not exist yet, one is created.
     * &lt;h3&gt;Adds access rights to the service client.&lt;/h3&gt;
     * <p><b>201</b> - access right that was added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights The accessRights parameter
     * @return ResponseEntity&lt;Set&lt;AccessRight&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<AccessRight>>> addServiceClientAccessRightsWithHttpInfo(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return addServiceClientAccessRightsRequestCreation(id, scId, accessRights).toEntityList(localVarReturnType);
    }

    /**
     * Add new access rights for selected service client. If service client did not exist yet, one is created.
     * &lt;h3&gt;Adds access rights to the service client.&lt;/h3&gt;
     * <p><b>201</b> - access right that was added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights The accessRights parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addServiceClientAccessRightsWithResponseSpec(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        return addServiceClientAccessRightsRequestCreation(id, scId, accessRights);
    }
    /**
     * make client Security Server&#39;s owner. Client must be a member and already registered on the Security Server
     * &lt;h3&gt;Administrator changes Security Server&#39;s owner.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was set as owner
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client to be set as owner
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec changeOwnerRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling changeOwner", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/make-owner", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * make client Security Server&#39;s owner. Client must be a member and already registered on the Security Server
     * &lt;h3&gt;Administrator changes Security Server&#39;s owner.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was set as owner
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client to be set as owner
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> changeOwner(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return changeOwnerRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * make client Security Server&#39;s owner. Client must be a member and already registered on the Security Server
     * &lt;h3&gt;Administrator changes Security Server&#39;s owner.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was set as owner
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client to be set as owner
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> changeOwnerWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return changeOwnerRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * make client Security Server&#39;s owner. Client must be a member and already registered on the Security Server
     * &lt;h3&gt;Administrator changes Security Server&#39;s owner.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was set as owner
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client to be set as owner
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec changeOwnerWithResponseSpec(String id) throws WebClientResponseException {
        return changeOwnerRequestCreation(id);
    }
    /**
     * delete security server client
     * &lt;h3&gt;Administrator deletes the client of the security server.&lt;/h3&gt;
     * <p><b>204</b> - client deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteClientRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete security server client
     * &lt;h3&gt;Administrator deletes the client of the security server.&lt;/h3&gt;
     * <p><b>204</b> - client deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteClient(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteClientRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * delete security server client
     * &lt;h3&gt;Administrator deletes the client of the security server.&lt;/h3&gt;
     * <p><b>204</b> - client deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteClientWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteClientRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * delete security server client
     * &lt;h3&gt;Administrator deletes the client of the security server.&lt;/h3&gt;
     * <p><b>204</b> - client deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteClientWithResponseSpec(String id) throws WebClientResponseException {
        return deleteClientRequestCreation(id);
    }
    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate from selected client.&lt;/h3&gt;
     * <p><b>204</b> - certificate deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteClientTlsCertificateRequestCreation(String id, String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteClientTlsCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling deleteClientTlsCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("hash", hash);

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
        return apiClient.invokeAPI("/clients/{id}/tls-certificates/{hash}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate from selected client.&lt;/h3&gt;
     * <p><b>204</b> - certificate deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteClientTlsCertificate(String id, String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteClientTlsCertificateRequestCreation(id, hash).bodyToMono(localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate from selected client.&lt;/h3&gt;
     * <p><b>204</b> - certificate deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteClientTlsCertificateWithHttpInfo(String id, String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteClientTlsCertificateRequestCreation(id, hash).toEntity(localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate from selected client.&lt;/h3&gt;
     * <p><b>204</b> - certificate deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteClientTlsCertificateWithResponseSpec(String id, String hash) throws WebClientResponseException {
        return deleteClientTlsCertificateRequestCreation(id, hash);
    }
    /**
     * delete orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator deletes the orphaned sign keys, certificates and csrs left behind a delete client.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteOrphansRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteOrphans", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/orphans", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator deletes the orphaned sign keys, certificates and csrs left behind a delete client.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteOrphans(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteOrphansRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * delete orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator deletes the orphaned sign keys, certificates and csrs left behind a delete client.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteOrphansWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteOrphansRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * delete orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator deletes the orphaned sign keys, certificates and csrs left behind a delete client.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteOrphansWithResponseSpec(String id) throws WebClientResponseException {
        return deleteOrphansRequestCreation(id);
    }
    /**
     * remove access rights
     * &lt;h3&gt;Administrator removes access rights from selected service client.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights list of access rights to be deleted
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteServiceClientAccessRightsRequestCreation(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        Object postBody = accessRights;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scId' is set
        if (scId == null) {
            throw new WebClientResponseException("Missing the required parameter 'scId' when calling deleteServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("sc_id", scId);

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
        return apiClient.invokeAPI("/clients/{id}/service-clients/{sc_id}/access-rights/delete", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * remove access rights
     * &lt;h3&gt;Administrator removes access rights from selected service client.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights list of access rights to be deleted
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteServiceClientAccessRights(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceClientAccessRightsRequestCreation(id, scId, accessRights).bodyToMono(localVarReturnType);
    }

    /**
     * remove access rights
     * &lt;h3&gt;Administrator removes access rights from selected service client.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights list of access rights to be deleted
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteServiceClientAccessRightsWithHttpInfo(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteServiceClientAccessRightsRequestCreation(id, scId, accessRights).toEntity(localVarReturnType);
    }

    /**
     * remove access rights
     * &lt;h3&gt;Administrator removes access rights from selected service client.&lt;/h3&gt;
     * <p><b>204</b> - access right(s) deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @param accessRights list of access rights to be deleted
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteServiceClientAccessRightsWithResponseSpec(String id, String scId, AccessRights accessRights) throws WebClientResponseException {
        return deleteServiceClientAccessRightsRequestCreation(id, scId, accessRights);
    }
    /**
     * find security server clients
     * &lt;h3&gt;Administrator views the clients of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param name pass an optional search string (name) for looking up clients
     * @param instance pass an optional search string (instance) for looking up clients
     * @param memberClass pass an optional search string (member_class) for looking up clients
     * @param memberCode pass an optional search string (member_code) for looking up clients
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up clients
     * @param showMembers to include members for search results
     * @param internalSearch to search only clients inside security server
     * @param localValidSignCert To search only clients that have (or don&#39;t have) a valid (registered, OCSP response GOOD) sign cert stored on this security server. Can be used to search both local and global clients, and can be combined with &lt;code&gt;internal_search&lt;/code&gt; and &lt;code&gt;exclude_local&lt;/code&gt; parameters. True &#x3D; limit to clients that have a valid local sign cert, false &#x3D; limit to clients that don&#39;t have a valid local sign cert.
     * @param excludeLocal to search only clients that are not added to this security server
     * @return Set&lt;Client&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec findClientsRequestCreation(String name, String instance, String memberClass, String memberCode, String subsystemCode, Boolean showMembers, Boolean internalSearch, Boolean localValidSignCert, Boolean excludeLocal) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "instance", instance));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_class", memberClass));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_code", memberCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "subsystem_code", subsystemCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "show_members", showMembers));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "internal_search", internalSearch));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "local_valid_sign_cert", localValidSignCert));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "exclude_local", excludeLocal));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return apiClient.invokeAPI("/clients", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * find security server clients
     * &lt;h3&gt;Administrator views the clients of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param name pass an optional search string (name) for looking up clients
     * @param instance pass an optional search string (instance) for looking up clients
     * @param memberClass pass an optional search string (member_class) for looking up clients
     * @param memberCode pass an optional search string (member_code) for looking up clients
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up clients
     * @param showMembers to include members for search results
     * @param internalSearch to search only clients inside security server
     * @param localValidSignCert To search only clients that have (or don&#39;t have) a valid (registered, OCSP response GOOD) sign cert stored on this security server. Can be used to search both local and global clients, and can be combined with &lt;code&gt;internal_search&lt;/code&gt; and &lt;code&gt;exclude_local&lt;/code&gt; parameters. True &#x3D; limit to clients that have a valid local sign cert, false &#x3D; limit to clients that don&#39;t have a valid local sign cert.
     * @param excludeLocal to search only clients that are not added to this security server
     * @return Set&lt;Client&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<Client> findClients(String name, String instance, String memberClass, String memberCode, String subsystemCode, Boolean showMembers, Boolean internalSearch, Boolean localValidSignCert, Boolean excludeLocal) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return findClientsRequestCreation(name, instance, memberClass, memberCode, subsystemCode, showMembers, internalSearch, localValidSignCert, excludeLocal).bodyToFlux(localVarReturnType);
    }

    /**
     * find security server clients
     * &lt;h3&gt;Administrator views the clients of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param name pass an optional search string (name) for looking up clients
     * @param instance pass an optional search string (instance) for looking up clients
     * @param memberClass pass an optional search string (member_class) for looking up clients
     * @param memberCode pass an optional search string (member_code) for looking up clients
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up clients
     * @param showMembers to include members for search results
     * @param internalSearch to search only clients inside security server
     * @param localValidSignCert To search only clients that have (or don&#39;t have) a valid (registered, OCSP response GOOD) sign cert stored on this security server. Can be used to search both local and global clients, and can be combined with &lt;code&gt;internal_search&lt;/code&gt; and &lt;code&gt;exclude_local&lt;/code&gt; parameters. True &#x3D; limit to clients that have a valid local sign cert, false &#x3D; limit to clients that don&#39;t have a valid local sign cert.
     * @param excludeLocal to search only clients that are not added to this security server
     * @return ResponseEntity&lt;Set&lt;Client&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<Client>>> findClientsWithHttpInfo(String name, String instance, String memberClass, String memberCode, String subsystemCode, Boolean showMembers, Boolean internalSearch, Boolean localValidSignCert, Boolean excludeLocal) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return findClientsRequestCreation(name, instance, memberClass, memberCode, subsystemCode, showMembers, internalSearch, localValidSignCert, excludeLocal).toEntityList(localVarReturnType);
    }

    /**
     * find security server clients
     * &lt;h3&gt;Administrator views the clients of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param name pass an optional search string (name) for looking up clients
     * @param instance pass an optional search string (instance) for looking up clients
     * @param memberClass pass an optional search string (member_class) for looking up clients
     * @param memberCode pass an optional search string (member_code) for looking up clients
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up clients
     * @param showMembers to include members for search results
     * @param internalSearch to search only clients inside security server
     * @param localValidSignCert To search only clients that have (or don&#39;t have) a valid (registered, OCSP response GOOD) sign cert stored on this security server. Can be used to search both local and global clients, and can be combined with &lt;code&gt;internal_search&lt;/code&gt; and &lt;code&gt;exclude_local&lt;/code&gt; parameters. True &#x3D; limit to clients that have a valid local sign cert, false &#x3D; limit to clients that don&#39;t have a valid local sign cert.
     * @param excludeLocal to search only clients that are not added to this security server
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec findClientsWithResponseSpec(String name, String instance, String memberClass, String memberCode, String subsystemCode, Boolean showMembers, Boolean internalSearch, Boolean localValidSignCert, Boolean excludeLocal) throws WebClientResponseException {
        return findClientsRequestCreation(name, instance, memberClass, memberCode, subsystemCode, showMembers, internalSearch, localValidSignCert, excludeLocal);
    }
    /**
     * find ServiceClient candidates for a specific client
     * &lt;h3&gt;Administrator views the clients, globalgroups and localgroups, that could be added as ServiceClients for given Client&#39;s services.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param memberNameGroupDescription pass an optional search string (name) for looking up subjects - name of a member or description of a group
     * @param serviceClientType pass an optional search string (service_client_type) for looking up service clients
     * @param instance pass an optional search string (instance) for looking up service clients - full instance id should be used
     * @param memberClass pass an optional search string (member_class) for looking up service clients
     * @param memberGroupCode pass an optional search string (member_group_code) for looking up service clients - member_code of a member or group_code of a group
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up service clients
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec findServiceClientCandidatesRequestCreation(String id, String memberNameGroupDescription, ServiceClientType serviceClientType, String instance, String memberClass, String memberGroupCode, String subsystemCode) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling findServiceClientCandidates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_name_group_description", memberNameGroupDescription));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "service_client_type", serviceClientType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "instance", instance));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_class", memberClass));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_group_code", memberGroupCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "subsystem_code", subsystemCode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return apiClient.invokeAPI("/clients/{id}/service-client-candidates", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * find ServiceClient candidates for a specific client
     * &lt;h3&gt;Administrator views the clients, globalgroups and localgroups, that could be added as ServiceClients for given Client&#39;s services.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param memberNameGroupDescription pass an optional search string (name) for looking up subjects - name of a member or description of a group
     * @param serviceClientType pass an optional search string (service_client_type) for looking up service clients
     * @param instance pass an optional search string (instance) for looking up service clients - full instance id should be used
     * @param memberClass pass an optional search string (member_class) for looking up service clients
     * @param memberGroupCode pass an optional search string (member_group_code) for looking up service clients - member_code of a member or group_code of a group
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up service clients
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> findServiceClientCandidates(String id, String memberNameGroupDescription, ServiceClientType serviceClientType, String instance, String memberClass, String memberGroupCode, String subsystemCode) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return findServiceClientCandidatesRequestCreation(id, memberNameGroupDescription, serviceClientType, instance, memberClass, memberGroupCode, subsystemCode).bodyToFlux(localVarReturnType);
    }

    /**
     * find ServiceClient candidates for a specific client
     * &lt;h3&gt;Administrator views the clients, globalgroups and localgroups, that could be added as ServiceClients for given Client&#39;s services.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param memberNameGroupDescription pass an optional search string (name) for looking up subjects - name of a member or description of a group
     * @param serviceClientType pass an optional search string (service_client_type) for looking up service clients
     * @param instance pass an optional search string (instance) for looking up service clients - full instance id should be used
     * @param memberClass pass an optional search string (member_class) for looking up service clients
     * @param memberGroupCode pass an optional search string (member_group_code) for looking up service clients - member_code of a member or group_code of a group
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up service clients
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> findServiceClientCandidatesWithHttpInfo(String id, String memberNameGroupDescription, ServiceClientType serviceClientType, String instance, String memberClass, String memberGroupCode, String subsystemCode) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return findServiceClientCandidatesRequestCreation(id, memberNameGroupDescription, serviceClientType, instance, memberClass, memberGroupCode, subsystemCode).toEntityList(localVarReturnType);
    }

    /**
     * find ServiceClient candidates for a specific client
     * &lt;h3&gt;Administrator views the clients, globalgroups and localgroups, that could be added as ServiceClients for given Client&#39;s services.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param memberNameGroupDescription pass an optional search string (name) for looking up subjects - name of a member or description of a group
     * @param serviceClientType pass an optional search string (service_client_type) for looking up service clients
     * @param instance pass an optional search string (instance) for looking up service clients - full instance id should be used
     * @param memberClass pass an optional search string (member_class) for looking up service clients
     * @param memberGroupCode pass an optional search string (member_group_code) for looking up service clients - member_code of a member or group_code of a group
     * @param subsystemCode pass an optional search string (subsystem_code) for looking up service clients
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec findServiceClientCandidatesWithResponseSpec(String id, String memberNameGroupDescription, ServiceClientType serviceClientType, String instance, String memberClass, String memberGroupCode, String subsystemCode) throws WebClientResponseException {
        return findServiceClientCandidatesRequestCreation(id, memberNameGroupDescription, serviceClientType, instance, memberClass, memberGroupCode, subsystemCode);
    }
    /**
     * get security server client information
     * &lt;h3&gt;Administrator views the client details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - client object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return apiClient.invokeAPI("/clients/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server client information
     * &lt;h3&gt;Administrator views the client details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - client object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Client> getClient(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return getClientRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get security server client information
     * &lt;h3&gt;Administrator views the client details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - client object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Client&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Client>> getClientWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return getClientRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get security server client information
     * &lt;h3&gt;Administrator views the client details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - client object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientWithResponseSpec(String id) throws WebClientResponseException {
        return getClientRequestCreation(id);
    }
    /**
     * get local groups for the selected client
     * &lt;h3&gt;Administrator views the local groups for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of local groups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;LocalGroup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientLocalGroupsRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientLocalGroups", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return apiClient.invokeAPI("/clients/{id}/local-groups", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get local groups for the selected client
     * &lt;h3&gt;Administrator views the local groups for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of local groups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;LocalGroup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<LocalGroup> getClientLocalGroups(String id) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return getClientLocalGroupsRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get local groups for the selected client
     * &lt;h3&gt;Administrator views the local groups for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of local groups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Set&lt;LocalGroup&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<LocalGroup>>> getClientLocalGroupsWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return getClientLocalGroupsRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get local groups for the selected client
     * &lt;h3&gt;Administrator views the local groups for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of local groups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientLocalGroupsWithResponseSpec(String id) throws WebClientResponseException {
        return getClientLocalGroupsRequestCreation(id);
    }
    /**
     * get information about orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator has deleted a client and wants to know if some orphaned sign keys, certificates or csrs exist.&lt;/h3&gt;
     * <p><b>200</b> - Information telling that orphans exist. If they don&#39;t exist, 404 is returned instead.
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return OrphanInformation
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientOrphansRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientOrphans", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<OrphanInformation> localVarReturnType = new ParameterizedTypeReference<OrphanInformation>() {};
        return apiClient.invokeAPI("/clients/{id}/orphans", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get information about orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator has deleted a client and wants to know if some orphaned sign keys, certificates or csrs exist.&lt;/h3&gt;
     * <p><b>200</b> - Information telling that orphans exist. If they don&#39;t exist, 404 is returned instead.
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return OrphanInformation
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<OrphanInformation> getClientOrphans(String id) throws WebClientResponseException {
        ParameterizedTypeReference<OrphanInformation> localVarReturnType = new ParameterizedTypeReference<OrphanInformation>() {};
        return getClientOrphansRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get information about orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator has deleted a client and wants to know if some orphaned sign keys, certificates or csrs exist.&lt;/h3&gt;
     * <p><b>200</b> - Information telling that orphans exist. If they don&#39;t exist, 404 is returned instead.
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;OrphanInformation&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<OrphanInformation>> getClientOrphansWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<OrphanInformation> localVarReturnType = new ParameterizedTypeReference<OrphanInformation>() {};
        return getClientOrphansRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get information about orphaned sign keys, certificates and csrs left behind a delete client
     * &lt;h3&gt;Administrator has deleted a client and wants to know if some orphaned sign keys, certificates or csrs exist.&lt;/h3&gt;
     * <p><b>200</b> - Information telling that orphans exist. If they don&#39;t exist, 404 is returned instead.
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientOrphansWithResponseSpec(String id) throws WebClientResponseException {
        return getClientOrphansRequestCreation(id);
    }
    /**
     * get service clients for the selected client&#39;s services
     * &lt;h3&gt;Administrator views the service clients for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientServiceClientsRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientServiceClients", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/service-clients", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get service clients for the selected client&#39;s services
     * &lt;h3&gt;Administrator views the service clients for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceClient> getClientServiceClients(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getClientServiceClientsRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get service clients for the selected client&#39;s services
     * &lt;h3&gt;Administrator views the service clients for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Set&lt;ServiceClient&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceClient>>> getClientServiceClientsWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getClientServiceClientsRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get service clients for the selected client&#39;s services
     * &lt;h3&gt;Administrator views the service clients for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientServiceClientsWithResponseSpec(String id) throws WebClientResponseException {
        return getClientServiceClientsRequestCreation(id);
    }
    /**
     * get security server client service descriptions
     * &lt;h3&gt;Administrator views the service descriptions for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service descriptions
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientServiceDescriptionsRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientServiceDescriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/service-descriptions", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server client service descriptions
     * &lt;h3&gt;Administrator views the service descriptions for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service descriptions
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;ServiceDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ServiceDescription> getClientServiceDescriptions(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return getClientServiceDescriptionsRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get security server client service descriptions
     * &lt;h3&gt;Administrator views the service descriptions for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service descriptions
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Set&lt;ServiceDescription&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<ServiceDescription>>> getClientServiceDescriptionsWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceDescription> localVarReturnType = new ParameterizedTypeReference<ServiceDescription>() {};
        return getClientServiceDescriptionsRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get security server client service descriptions
     * &lt;h3&gt;Administrator views the service descriptions for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of service descriptions
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientServiceDescriptionsWithResponseSpec(String id) throws WebClientResponseException {
        return getClientServiceDescriptionsRequestCreation(id);
    }
    /**
     * get security server client certificates information
     * &lt;h3&gt;Administrator views the certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;TokenCertificate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientSignCertificatesRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientSignCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return apiClient.invokeAPI("/clients/{id}/sign-certificates", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server client certificates information
     * &lt;h3&gt;Administrator views the certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;TokenCertificate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<TokenCertificate> getClientSignCertificates(String id) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return getClientSignCertificatesRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get security server client certificates information
     * &lt;h3&gt;Administrator views the certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Set&lt;TokenCertificate&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<TokenCertificate>>> getClientSignCertificatesWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return getClientSignCertificatesRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get security server client certificates information
     * &lt;h3&gt;Administrator views the certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientSignCertificatesWithResponseSpec(String id) throws WebClientResponseException {
        return getClientSignCertificatesRequestCreation(id);
    }
    /**
     * get TLS certificate
     * &lt;h3&gt;Administrator gets the TLS certificate for the selected client.&lt;/h3&gt;
     * <p><b>200</b> - certificate details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientTlsCertificateRequestCreation(String id, String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientTlsCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling getClientTlsCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("hash", hash);

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

        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return apiClient.invokeAPI("/clients/{id}/tls-certificates/{hash}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get TLS certificate
     * &lt;h3&gt;Administrator gets the TLS certificate for the selected client.&lt;/h3&gt;
     * <p><b>200</b> - certificate details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CertificateDetails> getClientTlsCertificate(String id, String hash) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getClientTlsCertificateRequestCreation(id, hash).bodyToMono(localVarReturnType);
    }

    /**
     * get TLS certificate
     * &lt;h3&gt;Administrator gets the TLS certificate for the selected client.&lt;/h3&gt;
     * <p><b>200</b> - certificate details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @return ResponseEntity&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CertificateDetails>> getClientTlsCertificateWithHttpInfo(String id, String hash) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getClientTlsCertificateRequestCreation(id, hash).toEntity(localVarReturnType);
    }

    /**
     * get TLS certificate
     * &lt;h3&gt;Administrator gets the TLS certificate for the selected client.&lt;/h3&gt;
     * <p><b>200</b> - certificate details
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientTlsCertificateWithResponseSpec(String id, String hash) throws WebClientResponseException {
        return getClientTlsCertificateRequestCreation(id, hash);
    }
    /**
     * get security server client TLS certificates information
     * &lt;h3&gt;Administrator views the TLS certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of tls certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getClientTlsCertificatesRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getClientTlsCertificates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return apiClient.invokeAPI("/clients/{id}/tls-certificates", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server client TLS certificates information
     * &lt;h3&gt;Administrator views the TLS certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of tls certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return Set&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<CertificateDetails> getClientTlsCertificates(String id) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getClientTlsCertificatesRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get security server client TLS certificates information
     * &lt;h3&gt;Administrator views the TLS certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of tls certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseEntity&lt;Set&lt;CertificateDetails&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<CertificateDetails>>> getClientTlsCertificatesWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getClientTlsCertificatesRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get security server client TLS certificates information
     * &lt;h3&gt;Administrator views the TLS certificates for the client.&lt;/h3&gt;
     * <p><b>200</b> - list of tls certificates
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getClientTlsCertificatesWithResponseSpec(String id) throws WebClientResponseException {
        return getClientTlsCertificatesRequestCreation(id);
    }
    /**
     * get single service client by client id and service client id
     * &lt;h3&gt;Administrator views the information for a single service client.&lt;h3&gt;
     * <p><b>200</b> - single service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param scId id of the service client
     * @return ServiceClient
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceClientRequestCreation(String id, String scId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getServiceClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scId' is set
        if (scId == null) {
            throw new WebClientResponseException("Missing the required parameter 'scId' when calling getServiceClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("sc_id", scId);

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
        return apiClient.invokeAPI("/clients/{id}/service-clients/{sc_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get single service client by client id and service client id
     * &lt;h3&gt;Administrator views the information for a single service client.&lt;h3&gt;
     * <p><b>200</b> - single service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param scId id of the service client
     * @return ServiceClient
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ServiceClient> getServiceClient(String id, String scId) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getServiceClientRequestCreation(id, scId).bodyToMono(localVarReturnType);
    }

    /**
     * get single service client by client id and service client id
     * &lt;h3&gt;Administrator views the information for a single service client.&lt;h3&gt;
     * <p><b>200</b> - single service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param scId id of the service client
     * @return ResponseEntity&lt;ServiceClient&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ServiceClient>> getServiceClientWithHttpInfo(String id, String scId) throws WebClientResponseException {
        ParameterizedTypeReference<ServiceClient> localVarReturnType = new ParameterizedTypeReference<ServiceClient>() {};
        return getServiceClientRequestCreation(id, scId).toEntity(localVarReturnType);
    }

    /**
     * get single service client by client id and service client id
     * &lt;h3&gt;Administrator views the information for a single service client.&lt;h3&gt;
     * <p><b>200</b> - single service clients
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param scId id of the service client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceClientWithResponseSpec(String id, String scId) throws WebClientResponseException {
        return getServiceClientRequestCreation(id, scId);
    }
    /**
     * get access rights for the selected service client.
     * &lt;h3&gt;Administrator views service client&#39;s access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @return Set&lt;AccessRight&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getServiceClientAccessRightsRequestCreation(String id, String scId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'scId' is set
        if (scId == null) {
            throw new WebClientResponseException("Missing the required parameter 'scId' when calling getServiceClientAccessRights", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("sc_id", scId);

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

        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return apiClient.invokeAPI("/clients/{id}/service-clients/{sc_id}/access-rights", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get access rights for the selected service client.
     * &lt;h3&gt;Administrator views service client&#39;s access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @return Set&lt;AccessRight&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<AccessRight> getServiceClientAccessRights(String id, String scId) throws WebClientResponseException {
        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return getServiceClientAccessRightsRequestCreation(id, scId).bodyToFlux(localVarReturnType);
    }

    /**
     * get access rights for the selected service client.
     * &lt;h3&gt;Administrator views service client&#39;s access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @return ResponseEntity&lt;Set&lt;AccessRight&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<AccessRight>>> getServiceClientAccessRightsWithHttpInfo(String id, String scId) throws WebClientResponseException {
        ParameterizedTypeReference<AccessRight> localVarReturnType = new ParameterizedTypeReference<AccessRight>() {};
        return getServiceClientAccessRightsRequestCreation(id, scId).toEntityList(localVarReturnType);
    }

    /**
     * get access rights for the selected service client.
     * &lt;h3&gt;Administrator views service client&#39;s access rights.&lt;/h3&gt;
     * <p><b>200</b> - list of access rights
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client who owns the services
     * @param scId id of the service client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getServiceClientAccessRightsWithResponseSpec(String id, String scId) throws WebClientResponseException {
        return getServiceClientAccessRightsRequestCreation(id, scId);
    }
    /**
     * register security server client
     * &lt;h3&gt;Administrator registers client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was registered
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec registerClientRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling registerClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/register", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * register security server client
     * &lt;h3&gt;Administrator registers client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was registered
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> registerClient(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return registerClientRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * register security server client
     * &lt;h3&gt;Administrator registers client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was registered
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> registerClientWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return registerClientRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * register security server client
     * &lt;h3&gt;Administrator registers client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - client was registered
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec registerClientWithResponseSpec(String id) throws WebClientResponseException {
        return registerClientRequestCreation(id);
    }
    /**
     * unregister security server client
     * &lt;h3&gt;Administrator unregisters client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - unregister was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unregisterClientRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling unregisterClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/clients/{id}/unregister", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * unregister security server client
     * &lt;h3&gt;Administrator unregisters client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - unregister was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> unregisterClient(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return unregisterClientRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * unregister security server client
     * &lt;h3&gt;Administrator unregisters client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - unregister was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> unregisterClientWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return unregisterClientRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * unregister security server client
     * &lt;h3&gt;Administrator unregisters client.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - unregister was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unregisterClientWithResponseSpec(String id) throws WebClientResponseException {
        return unregisterClientRequestCreation(id);
    }
    /**
     * update security server client information
     * &lt;h3&gt;Administrator updates the client information.&lt;/h3&gt;
     * <p><b>200</b> - client modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param connectionTypeWrapper The connectionTypeWrapper parameter
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateClientRequestCreation(String id, ConnectionTypeWrapper connectionTypeWrapper) throws WebClientResponseException {
        Object postBody = connectionTypeWrapper;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateClient", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return apiClient.invokeAPI("/clients/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update security server client information
     * &lt;h3&gt;Administrator updates the client information.&lt;/h3&gt;
     * <p><b>200</b> - client modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param connectionTypeWrapper The connectionTypeWrapper parameter
     * @return Client
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Client> updateClient(String id, ConnectionTypeWrapper connectionTypeWrapper) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return updateClientRequestCreation(id, connectionTypeWrapper).bodyToMono(localVarReturnType);
    }

    /**
     * update security server client information
     * &lt;h3&gt;Administrator updates the client information.&lt;/h3&gt;
     * <p><b>200</b> - client modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param connectionTypeWrapper The connectionTypeWrapper parameter
     * @return ResponseEntity&lt;Client&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Client>> updateClientWithHttpInfo(String id, ConnectionTypeWrapper connectionTypeWrapper) throws WebClientResponseException {
        ParameterizedTypeReference<Client> localVarReturnType = new ParameterizedTypeReference<Client>() {};
        return updateClientRequestCreation(id, connectionTypeWrapper).toEntity(localVarReturnType);
    }

    /**
     * update security server client information
     * &lt;h3&gt;Administrator updates the client information.&lt;/h3&gt;
     * <p><b>200</b> - client modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the client
     * @param connectionTypeWrapper The connectionTypeWrapper parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateClientWithResponseSpec(String id, ConnectionTypeWrapper connectionTypeWrapper) throws WebClientResponseException {
        return updateClientRequestCreation(id, connectionTypeWrapper);
    }
}
