package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Anchor;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateDetails;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.DistinguishedName;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import java.io.File;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.NodeTypeResponse;
import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TimestampingService;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.VersionInfo;

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
public class SystemApi {
    private ApiClient apiClient;

    public SystemApi() {
        this(new ApiClient());
    }

    @Autowired
    public SystemApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * add a configured timestamping service
     * &lt;h3&gt;Administrator selects a new timestamping service.&lt;/h3&gt;
     * <p><b>201</b> - timestamping service created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to add
     * @return TimestampingService
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addConfiguredTimestampingServiceRequestCreation(TimestampingService timestampingService) throws WebClientResponseException {
        Object postBody = timestampingService;
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

        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return apiClient.invokeAPI("/system/timestamping-services", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add a configured timestamping service
     * &lt;h3&gt;Administrator selects a new timestamping service.&lt;/h3&gt;
     * <p><b>201</b> - timestamping service created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to add
     * @return TimestampingService
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TimestampingService> addConfiguredTimestampingService(TimestampingService timestampingService) throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return addConfiguredTimestampingServiceRequestCreation(timestampingService).bodyToMono(localVarReturnType);
    }

    /**
     * add a configured timestamping service
     * &lt;h3&gt;Administrator selects a new timestamping service.&lt;/h3&gt;
     * <p><b>201</b> - timestamping service created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to add
     * @return ResponseEntity&lt;TimestampingService&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<TimestampingService>> addConfiguredTimestampingServiceWithHttpInfo(TimestampingService timestampingService) throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return addConfiguredTimestampingServiceRequestCreation(timestampingService).toEntity(localVarReturnType);
    }

    /**
     * add a configured timestamping service
     * &lt;h3&gt;Administrator selects a new timestamping service.&lt;/h3&gt;
     * <p><b>201</b> - timestamping service created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to add
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addConfiguredTimestampingServiceWithResponseSpec(TimestampingService timestampingService) throws WebClientResponseException {
        return addConfiguredTimestampingServiceRequestCreation(timestampingService);
    }
    /**
     * delete configured timestamping service
     * &lt;h3&gt;Administrator removes a configured timestamping service.&lt;/h3&gt;
     * <p><b>204</b> - timestamping service deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to delete
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteConfiguredTimestampingServiceRequestCreation(TimestampingService timestampingService) throws WebClientResponseException {
        Object postBody = timestampingService;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/system/timestamping-services/delete", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete configured timestamping service
     * &lt;h3&gt;Administrator removes a configured timestamping service.&lt;/h3&gt;
     * <p><b>204</b> - timestamping service deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to delete
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteConfiguredTimestampingService(TimestampingService timestampingService) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteConfiguredTimestampingServiceRequestCreation(timestampingService).bodyToMono(localVarReturnType);
    }

    /**
     * delete configured timestamping service
     * &lt;h3&gt;Administrator removes a configured timestamping service.&lt;/h3&gt;
     * <p><b>204</b> - timestamping service deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to delete
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteConfiguredTimestampingServiceWithHttpInfo(TimestampingService timestampingService) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteConfiguredTimestampingServiceRequestCreation(timestampingService).toEntity(localVarReturnType);
    }

    /**
     * delete configured timestamping service
     * &lt;h3&gt;Administrator removes a configured timestamping service.&lt;/h3&gt;
     * <p><b>204</b> - timestamping service deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param timestampingService Timestamping service to delete
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteConfiguredTimestampingServiceWithResponseSpec(TimestampingService timestampingService) throws WebClientResponseException {
        return deleteConfiguredTimestampingServiceRequestCreation(timestampingService);
    }
    /**
     * download configuration anchor information
     * &lt;h3&gt;Administrator downloads the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - configuration anchor
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadAnchorRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/system/anchor/download", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * download configuration anchor information
     * &lt;h3&gt;Administrator downloads the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - configuration anchor
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> downloadAnchor() throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadAnchorRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * download configuration anchor information
     * &lt;h3&gt;Administrator downloads the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - configuration anchor
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> downloadAnchorWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadAnchorRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * download configuration anchor information
     * &lt;h3&gt;Administrator downloads the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - configuration anchor
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadAnchorWithResponseSpec() throws WebClientResponseException {
        return downloadAnchorRequestCreation();
    }
    /**
     * download the security server certificate as gzip compressed tar archive
     * &lt;h3&gt;Administrator downloads the security server TLS certificate.&lt;/h3&gt;
     * <p><b>200</b> - information fetched successfully
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadSystemCertificateRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/gzip"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/system/certificate/export", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * download the security server certificate as gzip compressed tar archive
     * &lt;h3&gt;Administrator downloads the security server TLS certificate.&lt;/h3&gt;
     * <p><b>200</b> - information fetched successfully
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> downloadSystemCertificate() throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadSystemCertificateRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * download the security server certificate as gzip compressed tar archive
     * &lt;h3&gt;Administrator downloads the security server TLS certificate.&lt;/h3&gt;
     * <p><b>200</b> - information fetched successfully
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> downloadSystemCertificateWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadSystemCertificateRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * download the security server certificate as gzip compressed tar archive
     * &lt;h3&gt;Administrator downloads the security server TLS certificate.&lt;/h3&gt;
     * <p><b>200</b> - information fetched successfully
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadSystemCertificateWithResponseSpec() throws WebClientResponseException {
        return downloadSystemCertificateRequestCreation();
    }
    /**
     * generate new certificate request
     * &lt;h3&gt;Administrator generates a new certificate request.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param distinguishedName The distinguishedName parameter
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec generateSystemCertificateRequestRequestCreation(DistinguishedName distinguishedName) throws WebClientResponseException {
        Object postBody = distinguishedName;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/system/certificate/csr", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * generate new certificate request
     * &lt;h3&gt;Administrator generates a new certificate request.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param distinguishedName The distinguishedName parameter
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> generateSystemCertificateRequest(DistinguishedName distinguishedName) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return generateSystemCertificateRequestRequestCreation(distinguishedName).bodyToMono(localVarReturnType);
    }

    /**
     * generate new certificate request
     * &lt;h3&gt;Administrator generates a new certificate request.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param distinguishedName The distinguishedName parameter
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> generateSystemCertificateRequestWithHttpInfo(DistinguishedName distinguishedName) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return generateSystemCertificateRequestRequestCreation(distinguishedName).toEntity(localVarReturnType);
    }

    /**
     * generate new certificate request
     * &lt;h3&gt;Administrator generates a new certificate request.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param distinguishedName The distinguishedName parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec generateSystemCertificateRequestWithResponseSpec(DistinguishedName distinguishedName) throws WebClientResponseException {
        return generateSystemCertificateRequestRequestCreation(distinguishedName);
    }
    /**
     * generate a new internal TLS key and cert
     * &lt;h3&gt;Administrator generates new internal TLS key and certificate.&lt;/h3&gt;
     * <p><b>204</b> - tls key generated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec generateSystemTlsKeyAndCertificateRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/system/certificate", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * generate a new internal TLS key and cert
     * &lt;h3&gt;Administrator generates new internal TLS key and certificate.&lt;/h3&gt;
     * <p><b>204</b> - tls key generated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> generateSystemTlsKeyAndCertificate() throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return generateSystemTlsKeyAndCertificateRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * generate a new internal TLS key and cert
     * &lt;h3&gt;Administrator generates new internal TLS key and certificate.&lt;/h3&gt;
     * <p><b>204</b> - tls key generated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> generateSystemTlsKeyAndCertificateWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return generateSystemTlsKeyAndCertificateRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * generate a new internal TLS key and cert
     * &lt;h3&gt;Administrator generates new internal TLS key and certificate.&lt;/h3&gt;
     * <p><b>204</b> - tls key generated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec generateSystemTlsKeyAndCertificateWithResponseSpec() throws WebClientResponseException {
        return generateSystemTlsKeyAndCertificateRequestCreation();
    }
    /**
     * view the configuration anchor information
     * &lt;h3&gt;Administrator views the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - anchor information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAnchorRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return apiClient.invokeAPI("/system/anchor", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view the configuration anchor information
     * &lt;h3&gt;Administrator views the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - anchor information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Anchor> getAnchor() throws WebClientResponseException {
        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return getAnchorRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view the configuration anchor information
     * &lt;h3&gt;Administrator views the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - anchor information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Anchor&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Anchor>> getAnchorWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return getAnchorRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view the configuration anchor information
     * &lt;h3&gt;Administrator views the configuration anchor information.&lt;/h3&gt;
     * <p><b>200</b> - anchor information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAnchorWithResponseSpec() throws WebClientResponseException {
        return getAnchorRequestCreation();
    }
    /**
     * view the configured timestamping services
     * &lt;h3&gt;Administrator views the configured timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of configured timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingService&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getConfiguredTimestampingServicesRequestCreation() throws WebClientResponseException {
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
        return apiClient.invokeAPI("/system/timestamping-services", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view the configured timestamping services
     * &lt;h3&gt;Administrator views the configured timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of configured timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingService&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<TimestampingService> getConfiguredTimestampingServices() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return getConfiguredTimestampingServicesRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * view the configured timestamping services
     * &lt;h3&gt;Administrator views the configured timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of configured timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;TimestampingService&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<TimestampingService>>> getConfiguredTimestampingServicesWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingService> localVarReturnType = new ParameterizedTypeReference<TimestampingService>() {};
        return getConfiguredTimestampingServicesRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * view the configured timestamping services
     * &lt;h3&gt;Administrator views the configured timestamping services.&lt;/h3&gt;
     * <p><b>200</b> - list of configured timestamping services
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getConfiguredTimestampingServicesWithResponseSpec() throws WebClientResponseException {
        return getConfiguredTimestampingServicesRequestCreation();
    }
    /**
     * get the node type
     * &lt;h3&gt;Administrator views the node type&lt;/h3&gt;
     * <p><b>200</b> - node type information
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>500</b> - internal server error
     * @return NodeTypeResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNodeTypeRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<NodeTypeResponse> localVarReturnType = new ParameterizedTypeReference<NodeTypeResponse>() {};
        return apiClient.invokeAPI("/system/node-type", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get the node type
     * &lt;h3&gt;Administrator views the node type&lt;/h3&gt;
     * <p><b>200</b> - node type information
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>500</b> - internal server error
     * @return NodeTypeResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<NodeTypeResponse> getNodeType() throws WebClientResponseException {
        ParameterizedTypeReference<NodeTypeResponse> localVarReturnType = new ParameterizedTypeReference<NodeTypeResponse>() {};
        return getNodeTypeRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * get the node type
     * &lt;h3&gt;Administrator views the node type&lt;/h3&gt;
     * <p><b>200</b> - node type information
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;NodeTypeResponse&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<NodeTypeResponse>> getNodeTypeWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<NodeTypeResponse> localVarReturnType = new ParameterizedTypeReference<NodeTypeResponse>() {};
        return getNodeTypeRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * get the node type
     * &lt;h3&gt;Administrator views the node type&lt;/h3&gt;
     * <p><b>200</b> - node type information
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNodeTypeWithResponseSpec() throws WebClientResponseException {
        return getNodeTypeRequestCreation();
    }
    /**
     * view the security server certificate information
     * &lt;h3&gt;Administrator views the security server TLS certificate information.&lt;/h3&gt;
     * <p><b>200</b> - certificate information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSystemCertificateRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return apiClient.invokeAPI("/system/certificate", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view the security server certificate information
     * &lt;h3&gt;Administrator views the security server TLS certificate information.&lt;/h3&gt;
     * <p><b>200</b> - certificate information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CertificateDetails> getSystemCertificate() throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getSystemCertificateRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view the security server certificate information
     * &lt;h3&gt;Administrator views the security server TLS certificate information.&lt;/h3&gt;
     * <p><b>200</b> - certificate information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CertificateDetails>> getSystemCertificateWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return getSystemCertificateRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view the security server certificate information
     * &lt;h3&gt;Administrator views the security server TLS certificate information.&lt;/h3&gt;
     * <p><b>200</b> - certificate information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSystemCertificateWithResponseSpec() throws WebClientResponseException {
        return getSystemCertificateRequestCreation();
    }
    /**
     * import new internal TLS certificate.
     * &lt;h3&gt;Administrator imports a new internal TLS certificate&lt;/h3&gt;
     * <p><b>200</b> - tls certificate imported
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to add
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec importSystemCertificateRequestCreation(File body) throws WebClientResponseException {
        Object postBody = body;
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
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return apiClient.invokeAPI("/system/certificate/import", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * import new internal TLS certificate.
     * &lt;h3&gt;Administrator imports a new internal TLS certificate&lt;/h3&gt;
     * <p><b>200</b> - tls certificate imported
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to add
     * @return CertificateDetails
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<CertificateDetails> importSystemCertificate(File body) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return importSystemCertificateRequestCreation(body).bodyToMono(localVarReturnType);
    }

    /**
     * import new internal TLS certificate.
     * &lt;h3&gt;Administrator imports a new internal TLS certificate&lt;/h3&gt;
     * <p><b>200</b> - tls certificate imported
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to add
     * @return ResponseEntity&lt;CertificateDetails&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<CertificateDetails>> importSystemCertificateWithHttpInfo(File body) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateDetails> localVarReturnType = new ParameterizedTypeReference<CertificateDetails>() {};
        return importSystemCertificateRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * import new internal TLS certificate.
     * &lt;h3&gt;Administrator imports a new internal TLS certificate&lt;/h3&gt;
     * <p><b>200</b> - tls certificate imported
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to add
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec importSystemCertificateWithResponseSpec(File body) throws WebClientResponseException {
        return importSystemCertificateRequestCreation(body);
    }
    /**
     * Read and the configuration anchor file and return the hash for a preview.
     * &lt;h3&gt;Administrator wants to preview a configuration anchor file hash.&lt;/h3&gt; &lt;p&gt;The instance of the anchor is also validated unless the &lt;code&gt;validate_instance&lt;/code&gt; query parameter is explicitly set to false. The anchor will not be saved.&lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param validateInstance Whether or not to validate the owner instance of the anchor. Set this to false explicitly when previewing an anchor in the security server initialization phase. Default value is true if the parameter is omitted.
     * @param body configuration anchor
     * @return Anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec previewAnchorRequestCreation(Boolean validateInstance, File body) throws WebClientResponseException {
        Object postBody = body;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validate_instance", validateInstance));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return apiClient.invokeAPI("/system/anchor/previews", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Read and the configuration anchor file and return the hash for a preview.
     * &lt;h3&gt;Administrator wants to preview a configuration anchor file hash.&lt;/h3&gt; &lt;p&gt;The instance of the anchor is also validated unless the &lt;code&gt;validate_instance&lt;/code&gt; query parameter is explicitly set to false. The anchor will not be saved.&lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param validateInstance Whether or not to validate the owner instance of the anchor. Set this to false explicitly when previewing an anchor in the security server initialization phase. Default value is true if the parameter is omitted.
     * @param body configuration anchor
     * @return Anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Anchor> previewAnchor(Boolean validateInstance, File body) throws WebClientResponseException {
        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return previewAnchorRequestCreation(validateInstance, body).bodyToMono(localVarReturnType);
    }

    /**
     * Read and the configuration anchor file and return the hash for a preview.
     * &lt;h3&gt;Administrator wants to preview a configuration anchor file hash.&lt;/h3&gt; &lt;p&gt;The instance of the anchor is also validated unless the &lt;code&gt;validate_instance&lt;/code&gt; query parameter is explicitly set to false. The anchor will not be saved.&lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param validateInstance Whether or not to validate the owner instance of the anchor. Set this to false explicitly when previewing an anchor in the security server initialization phase. Default value is true if the parameter is omitted.
     * @param body configuration anchor
     * @return ResponseEntity&lt;Anchor&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Anchor>> previewAnchorWithHttpInfo(Boolean validateInstance, File body) throws WebClientResponseException {
        ParameterizedTypeReference<Anchor> localVarReturnType = new ParameterizedTypeReference<Anchor>() {};
        return previewAnchorRequestCreation(validateInstance, body).toEntity(localVarReturnType);
    }

    /**
     * Read and the configuration anchor file and return the hash for a preview.
     * &lt;h3&gt;Administrator wants to preview a configuration anchor file hash.&lt;/h3&gt; &lt;p&gt;The instance of the anchor is also validated unless the &lt;code&gt;validate_instance&lt;/code&gt; query parameter is explicitly set to false. The anchor will not be saved.&lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param validateInstance Whether or not to validate the owner instance of the anchor. Set this to false explicitly when previewing an anchor in the security server initialization phase. Default value is true if the parameter is omitted.
     * @param body configuration anchor
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec previewAnchorWithResponseSpec(Boolean validateInstance, File body) throws WebClientResponseException {
        return previewAnchorRequestCreation(validateInstance, body);
    }
    /**
     * Upload a configuration anchor file to replace an existing one.
     * &lt;h3&gt;Administrator uploads a configuration anchor file anytime after the Security Server has been initialized.&lt;/h3&gt; &lt;p&gt; &lt;b&gt;Note that this only works if there already exists an anchor that can be replaced.&lt;/b&gt; When initalizing a new Security Server, use the endpoint &lt;code&gt;POST /system/anchor&lt;/code&gt; instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec replaceAnchorRequestCreation(File body) throws WebClientResponseException {
        Object postBody = body;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/system/anchor", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a configuration anchor file to replace an existing one.
     * &lt;h3&gt;Administrator uploads a configuration anchor file anytime after the Security Server has been initialized.&lt;/h3&gt; &lt;p&gt; &lt;b&gt;Note that this only works if there already exists an anchor that can be replaced.&lt;/b&gt; When initalizing a new Security Server, use the endpoint &lt;code&gt;POST /system/anchor&lt;/code&gt; instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> replaceAnchor(File body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return replaceAnchorRequestCreation(body).bodyToMono(localVarReturnType);
    }

    /**
     * Upload a configuration anchor file to replace an existing one.
     * &lt;h3&gt;Administrator uploads a configuration anchor file anytime after the Security Server has been initialized.&lt;/h3&gt; &lt;p&gt; &lt;b&gt;Note that this only works if there already exists an anchor that can be replaced.&lt;/b&gt; When initalizing a new Security Server, use the endpoint &lt;code&gt;POST /system/anchor&lt;/code&gt; instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> replaceAnchorWithHttpInfo(File body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return replaceAnchorRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * Upload a configuration anchor file to replace an existing one.
     * &lt;h3&gt;Administrator uploads a configuration anchor file anytime after the Security Server has been initialized.&lt;/h3&gt; &lt;p&gt; &lt;b&gt;Note that this only works if there already exists an anchor that can be replaced.&lt;/b&gt; When initalizing a new Security Server, use the endpoint &lt;code&gt;POST /system/anchor&lt;/code&gt; instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec replaceAnchorWithResponseSpec(File body) throws WebClientResponseException {
        return replaceAnchorRequestCreation(body);
    }
    /**
     * get information for the system version
     * &lt;h3&gt;Administrator views the system version details.&lt;/h3&gt;
     * <p><b>200</b> - system version information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return VersionInfo
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec systemVersionRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<VersionInfo> localVarReturnType = new ParameterizedTypeReference<VersionInfo>() {};
        return apiClient.invokeAPI("/system/version", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get information for the system version
     * &lt;h3&gt;Administrator views the system version details.&lt;/h3&gt;
     * <p><b>200</b> - system version information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return VersionInfo
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<VersionInfo> systemVersion() throws WebClientResponseException {
        ParameterizedTypeReference<VersionInfo> localVarReturnType = new ParameterizedTypeReference<VersionInfo>() {};
        return systemVersionRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * get information for the system version
     * &lt;h3&gt;Administrator views the system version details.&lt;/h3&gt;
     * <p><b>200</b> - system version information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;VersionInfo&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<VersionInfo>> systemVersionWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<VersionInfo> localVarReturnType = new ParameterizedTypeReference<VersionInfo>() {};
        return systemVersionRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * get information for the system version
     * &lt;h3&gt;Administrator views the system version details.&lt;/h3&gt;
     * <p><b>200</b> - system version information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec systemVersionWithResponseSpec() throws WebClientResponseException {
        return systemVersionRequestCreation();
    }
    /**
     * Upload a new configuration anchor file when initializing a new security server.
     * &lt;h3&gt;Administrator uploads a new configuration anchor file in the security server&#39;s initialization phase.&lt;/h3&gt; &lt;p&gt; Calls to this endpoint only succeed if a configuration anchor is not already found – meaning that &lt;b&gt;this endpoint can only be used when initializing a new security server&lt;/b&gt;. For updating the anchor for an already initialized security server use the &lt;code&gt;PUT /system/anchor&lt;/code&gt; endpoint instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec uploadInitialAnchorRequestCreation(File body) throws WebClientResponseException {
        Object postBody = body;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/octet-stream"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/system/anchor", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a new configuration anchor file when initializing a new security server.
     * &lt;h3&gt;Administrator uploads a new configuration anchor file in the security server&#39;s initialization phase.&lt;/h3&gt; &lt;p&gt; Calls to this endpoint only succeed if a configuration anchor is not already found – meaning that &lt;b&gt;this endpoint can only be used when initializing a new security server&lt;/b&gt;. For updating the anchor for an already initialized security server use the &lt;code&gt;PUT /system/anchor&lt;/code&gt; endpoint instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> uploadInitialAnchor(File body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return uploadInitialAnchorRequestCreation(body).bodyToMono(localVarReturnType);
    }

    /**
     * Upload a new configuration anchor file when initializing a new security server.
     * &lt;h3&gt;Administrator uploads a new configuration anchor file in the security server&#39;s initialization phase.&lt;/h3&gt; &lt;p&gt; Calls to this endpoint only succeed if a configuration anchor is not already found – meaning that &lt;b&gt;this endpoint can only be used when initializing a new security server&lt;/b&gt;. For updating the anchor for an already initialized security server use the &lt;code&gt;PUT /system/anchor&lt;/code&gt; endpoint instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> uploadInitialAnchorWithHttpInfo(File body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return uploadInitialAnchorRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * Upload a new configuration anchor file when initializing a new security server.
     * &lt;h3&gt;Administrator uploads a new configuration anchor file in the security server&#39;s initialization phase.&lt;/h3&gt; &lt;p&gt; Calls to this endpoint only succeed if a configuration anchor is not already found – meaning that &lt;b&gt;this endpoint can only be used when initializing a new security server&lt;/b&gt;. For updating the anchor for an already initialized security server use the &lt;code&gt;PUT /system/anchor&lt;/code&gt; endpoint instead. &lt;/p&gt;
     * <p><b>201</b> - configuration anchor uploaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body configuration anchor
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec uploadInitialAnchorWithResponseSpec(File body) throws WebClientResponseException {
        return uploadInitialAnchorRequestCreation(body);
    }
}
