package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.AddOnStatus;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.BackupEncryptionStatus;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.GlobalConfDiagnostics;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.MessageLogEncryptionStatus;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.OcspResponderDiagnostics;
import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TimestampingServiceDiagnostics;

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
public class DiagnosticsApi {
    private ApiClient apiClient;

    public DiagnosticsApi() {
        this(new ApiClient());
    }

    @Autowired
    public DiagnosticsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * view addon services diagnostics information
     * &lt;h3&gt;Administrator views the proxy addon status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - addon services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return AddOnStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAddOnDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<AddOnStatus> localVarReturnType = new ParameterizedTypeReference<AddOnStatus>() {};
        return apiClient.invokeAPI("/diagnostics/addon-status", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view addon services diagnostics information
     * &lt;h3&gt;Administrator views the proxy addon status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - addon services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return AddOnStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<AddOnStatus> getAddOnDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<AddOnStatus> localVarReturnType = new ParameterizedTypeReference<AddOnStatus>() {};
        return getAddOnDiagnosticsRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view addon services diagnostics information
     * &lt;h3&gt;Administrator views the proxy addon status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - addon services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;AddOnStatus&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<AddOnStatus>> getAddOnDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<AddOnStatus> localVarReturnType = new ParameterizedTypeReference<AddOnStatus>() {};
        return getAddOnDiagnosticsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view addon services diagnostics information
     * &lt;h3&gt;Administrator views the proxy addon status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - addon services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAddOnDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getAddOnDiagnosticsRequestCreation();
    }
    /**
     * view backup encryption services diagnostics information
     * &lt;h3&gt;Administrator views the proxy backup encryption status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - backup encryption services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return BackupEncryptionStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getBackupEncryptionDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<BackupEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<BackupEncryptionStatus>() {};
        return apiClient.invokeAPI("/diagnostics/backup-encryption-status", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view backup encryption services diagnostics information
     * &lt;h3&gt;Administrator views the proxy backup encryption status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - backup encryption services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return BackupEncryptionStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BackupEncryptionStatus> getBackupEncryptionDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<BackupEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<BackupEncryptionStatus>() {};
        return getBackupEncryptionDiagnosticsRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view backup encryption services diagnostics information
     * &lt;h3&gt;Administrator views the proxy backup encryption status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - backup encryption services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;BackupEncryptionStatus&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<BackupEncryptionStatus>> getBackupEncryptionDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<BackupEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<BackupEncryptionStatus>() {};
        return getBackupEncryptionDiagnosticsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view backup encryption services diagnostics information
     * &lt;h3&gt;Administrator views the proxy backup encryption status diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - backup encryption services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getBackupEncryptionDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getBackupEncryptionDiagnosticsRequestCreation();
    }
    /**
     * view global configuration diagnostics information
     * &lt;h3&gt;Administrator views the global configuration diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - global configuration diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return GlobalConfDiagnostics
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getGlobalConfDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<GlobalConfDiagnostics> localVarReturnType = new ParameterizedTypeReference<GlobalConfDiagnostics>() {};
        return apiClient.invokeAPI("/diagnostics/globalconf", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view global configuration diagnostics information
     * &lt;h3&gt;Administrator views the global configuration diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - global configuration diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return GlobalConfDiagnostics
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GlobalConfDiagnostics> getGlobalConfDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<GlobalConfDiagnostics> localVarReturnType = new ParameterizedTypeReference<GlobalConfDiagnostics>() {};
        return getGlobalConfDiagnosticsRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view global configuration diagnostics information
     * &lt;h3&gt;Administrator views the global configuration diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - global configuration diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;GlobalConfDiagnostics&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<GlobalConfDiagnostics>> getGlobalConfDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<GlobalConfDiagnostics> localVarReturnType = new ParameterizedTypeReference<GlobalConfDiagnostics>() {};
        return getGlobalConfDiagnosticsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view global configuration diagnostics information
     * &lt;h3&gt;Administrator views the global configuration diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - global configuration diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getGlobalConfDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getGlobalConfDiagnosticsRequestCreation();
    }
    /**
     * view message log encryption and grouping services diagnostics information
     * &lt;h3&gt;Administrator views the proxy message log encryption and grouping diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - message log encryption and grouping diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return MessageLogEncryptionStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMessageLogEncryptionDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<MessageLogEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<MessageLogEncryptionStatus>() {};
        return apiClient.invokeAPI("/diagnostics/message-log-encryption-status", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view message log encryption and grouping services diagnostics information
     * &lt;h3&gt;Administrator views the proxy message log encryption and grouping diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - message log encryption and grouping diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return MessageLogEncryptionStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MessageLogEncryptionStatus> getMessageLogEncryptionDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<MessageLogEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<MessageLogEncryptionStatus>() {};
        return getMessageLogEncryptionDiagnosticsRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * view message log encryption and grouping services diagnostics information
     * &lt;h3&gt;Administrator views the proxy message log encryption and grouping diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - message log encryption and grouping diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;MessageLogEncryptionStatus&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MessageLogEncryptionStatus>> getMessageLogEncryptionDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<MessageLogEncryptionStatus> localVarReturnType = new ParameterizedTypeReference<MessageLogEncryptionStatus>() {};
        return getMessageLogEncryptionDiagnosticsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * view message log encryption and grouping services diagnostics information
     * &lt;h3&gt;Administrator views the proxy message log encryption and grouping diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - message log encryption and grouping diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMessageLogEncryptionDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getMessageLogEncryptionDiagnosticsRequestCreation();
    }
    /**
     * view ocsp responders diagnostics information
     * &lt;h3&gt;Administrator views the ocsp responders diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - ocsp responders diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;OcspResponderDiagnostics&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getOcspRespondersDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<OcspResponderDiagnostics> localVarReturnType = new ParameterizedTypeReference<OcspResponderDiagnostics>() {};
        return apiClient.invokeAPI("/diagnostics/ocsp-responders", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view ocsp responders diagnostics information
     * &lt;h3&gt;Administrator views the ocsp responders diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - ocsp responders diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;OcspResponderDiagnostics&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<OcspResponderDiagnostics> getOcspRespondersDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<OcspResponderDiagnostics> localVarReturnType = new ParameterizedTypeReference<OcspResponderDiagnostics>() {};
        return getOcspRespondersDiagnosticsRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * view ocsp responders diagnostics information
     * &lt;h3&gt;Administrator views the ocsp responders diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - ocsp responders diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;OcspResponderDiagnostics&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<OcspResponderDiagnostics>>> getOcspRespondersDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<OcspResponderDiagnostics> localVarReturnType = new ParameterizedTypeReference<OcspResponderDiagnostics>() {};
        return getOcspRespondersDiagnosticsRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * view ocsp responders diagnostics information
     * &lt;h3&gt;Administrator views the ocsp responders diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - ocsp responders diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getOcspRespondersDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getOcspRespondersDiagnosticsRequestCreation();
    }
    /**
     * view timestamping services diagnostics information
     * &lt;h3&gt;Administrator views the timestamping services diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - timestamping services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingServiceDiagnostics&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTimestampingServicesDiagnosticsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<TimestampingServiceDiagnostics> localVarReturnType = new ParameterizedTypeReference<TimestampingServiceDiagnostics>() {};
        return apiClient.invokeAPI("/diagnostics/timestamping-services", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view timestamping services diagnostics information
     * &lt;h3&gt;Administrator views the timestamping services diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - timestamping services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;TimestampingServiceDiagnostics&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<TimestampingServiceDiagnostics> getTimestampingServicesDiagnostics() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingServiceDiagnostics> localVarReturnType = new ParameterizedTypeReference<TimestampingServiceDiagnostics>() {};
        return getTimestampingServicesDiagnosticsRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * view timestamping services diagnostics information
     * &lt;h3&gt;Administrator views the timestamping services diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - timestamping services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;TimestampingServiceDiagnostics&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<TimestampingServiceDiagnostics>>> getTimestampingServicesDiagnosticsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<TimestampingServiceDiagnostics> localVarReturnType = new ParameterizedTypeReference<TimestampingServiceDiagnostics>() {};
        return getTimestampingServicesDiagnosticsRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * view timestamping services diagnostics information
     * &lt;h3&gt;Administrator views the timestamping services diagnostics information.&lt;/h3&gt;
     * <p><b>200</b> - timestamping services diagnostics information
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTimestampingServicesDiagnosticsWithResponseSpec() throws WebClientResponseException {
        return getTimestampingServicesDiagnosticsRequestCreation();
    }
}
