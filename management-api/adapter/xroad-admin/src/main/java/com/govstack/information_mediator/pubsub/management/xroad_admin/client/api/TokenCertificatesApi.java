package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import java.io.File;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.PossibleAction;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.SecurityServerAddress;
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
public class TokenCertificatesApi {
    private ApiClient apiClient;

    public TokenCertificatesApi() {
        this(new ApiClient());
    }

    @Autowired
    public TokenCertificatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * activate certificate
     * &lt;h3&gt;Administrator activates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec activateCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling activateCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/token-certificates/{hash}/activate", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * activate certificate
     * &lt;h3&gt;Administrator activates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> activateCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return activateCertificateRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * activate certificate
     * &lt;h3&gt;Administrator activates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> activateCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return activateCertificateRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * activate certificate
     * &lt;h3&gt;Administrator activates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec activateCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return activateCertificateRequestCreation(hash);
    }
    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the certificate is not found. The metadata array contains the id of that certificate.&lt;/p&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling deleteCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the certificate is not found. The metadata array contains the id of that certificate.&lt;/p&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteCertificateRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the certificate is not found. The metadata array contains the id of that certificate.&lt;/p&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteCertificateRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * delete certificate
     * &lt;h3&gt;Administrator deletes the certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the certificate is not found. The metadata array contains the id of that certificate.&lt;/p&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return deleteCertificateRequestCreation(hash);
    }
    /**
     * deactivate certificate
     * &lt;h3&gt;Administrator deactivates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - certificate was deactivated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec disableCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling disableCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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
        return apiClient.invokeAPI("/token-certificates/{hash}/disable", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * deactivate certificate
     * &lt;h3&gt;Administrator deactivates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - certificate was deactivated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> disableCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return disableCertificateRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * deactivate certificate
     * &lt;h3&gt;Administrator deactivates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - certificate was deactivated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> disableCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return disableCertificateRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * deactivate certificate
     * &lt;h3&gt;Administrator deactivates selected certificate.&lt;/h3&gt;
     * <p><b>204</b> - certificate was deactivated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec disableCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return disableCertificateRequestCreation(hash);
    }
    /**
     * get certificate information
     * &lt;h3&gt;Administrator views certificate details.&lt;/h3&gt;
     * <p><b>200</b> - token certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling getCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get certificate information
     * &lt;h3&gt;Administrator views certificate details.&lt;/h3&gt;
     * <p><b>200</b> - token certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TokenCertificate> getCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return getCertificateRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * get certificate information
     * &lt;h3&gt;Administrator views certificate details.&lt;/h3&gt;
     * <p><b>200</b> - token certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseEntity&lt;TokenCertificate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<TokenCertificate>> getCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return getCertificateRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * get certificate information
     * &lt;h3&gt;Administrator views certificate details.&lt;/h3&gt;
     * <p><b>200</b> - token certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return getCertificateRequestCreation(hash);
    }
    /**
     * get possible actions for one certificate
     * &lt;h3&gt;UI needs to know which actions can be done on one certificate.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPossibleActionsForCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling getPossibleActionsForCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}/possible-actions", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get possible actions for one certificate
     * &lt;h3&gt;UI needs to know which actions can be done on one certificate.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PossibleAction> getPossibleActionsForCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForCertificateRequestCreation(hash).bodyToFlux(localVarReturnType);
    }

    /**
     * get possible actions for one certificate
     * &lt;h3&gt;UI needs to know which actions can be done on one certificate.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseEntity&lt;List&lt;PossibleAction&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PossibleAction>>> getPossibleActionsForCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForCertificateRequestCreation(hash).toEntityList(localVarReturnType);
    }

    /**
     * get possible actions for one certificate
     * &lt;h3&gt;UI needs to know which actions can be done on one certificate.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPossibleActionsForCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return getPossibleActionsForCertificateRequestCreation(hash);
    }
    /**
     * import new certificate
     * &lt;h3&gt;Imports certificate to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for the certificate is not found. The metadata array contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - certificate created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to import
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec importCertificateRequestCreation(File body) throws WebClientResponseException {
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

        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return apiClient.invokeAPI("/token-certificates", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * import new certificate
     * &lt;h3&gt;Imports certificate to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for the certificate is not found. The metadata array contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - certificate created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to import
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TokenCertificate> importCertificate(File body) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return importCertificateRequestCreation(body).bodyToMono(localVarReturnType);
    }

    /**
     * import new certificate
     * &lt;h3&gt;Imports certificate to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for the certificate is not found. The metadata array contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - certificate created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to import
     * @return ResponseEntity&lt;TokenCertificate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<TokenCertificate>> importCertificateWithHttpInfo(File body) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return importCertificateRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * import new certificate
     * &lt;h3&gt;Imports certificate to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for the certificate is not found. The metadata array contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - certificate created
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param body certificate to import
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec importCertificateWithResponseSpec(File body) throws WebClientResponseException {
        return importCertificateRequestCreation(body);
    }
    /**
     * import an existing certificate from a token by cert hash
     * &lt;h3&gt;Imports certificate from a token to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for this certificate is not found. The metadata contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - the imported certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec importCertificateFromTokenRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling importCertificateFromToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}/import", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * import an existing certificate from a token by cert hash
     * &lt;h3&gt;Imports certificate from a token to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for this certificate is not found. The metadata contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - the imported certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return TokenCertificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TokenCertificate> importCertificateFromToken(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return importCertificateFromTokenRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * import an existing certificate from a token by cert hash
     * &lt;h3&gt;Imports certificate from a token to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for this certificate is not found. The metadata contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - the imported certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseEntity&lt;TokenCertificate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<TokenCertificate>> importCertificateFromTokenWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<TokenCertificate> localVarReturnType = new ParameterizedTypeReference<TokenCertificate>() {};
        return importCertificateFromTokenRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * import an existing certificate from a token by cert hash
     * &lt;h3&gt;Imports certificate from a token to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if the client for this certificate is not found. The metadata contains the identifier of that client.&lt;/p&gt;
     * <p><b>201</b> - the imported certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec importCertificateFromTokenWithResponseSpec(String hash) throws WebClientResponseException {
        return importCertificateFromTokenRequestCreation(hash);
    }
    /**
     * marks an auth certificate for deletion
     * &lt;h3&gt;Administrator marks an auth certificate for deletion.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec markAuthCertForDeletionRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling markAuthCertForDeletion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}/mark-for-deletion", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * marks an auth certificate for deletion
     * &lt;h3&gt;Administrator marks an auth certificate for deletion.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> markAuthCertForDeletion(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return markAuthCertForDeletionRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * marks an auth certificate for deletion
     * &lt;h3&gt;Administrator marks an auth certificate for deletion.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> markAuthCertForDeletionWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return markAuthCertForDeletionRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * marks an auth certificate for deletion
     * &lt;h3&gt;Administrator marks an auth certificate for deletion.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec markAuthCertForDeletionWithResponseSpec(String hash) throws WebClientResponseException {
        return markAuthCertForDeletionRequestCreation(hash);
    }
    /**
     * register certificate
     * &lt;h3&gt;Administrator registers selected certificate.&lt;/h3&gt;
     * <p><b>200</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @param securityServerAddress The securityServerAddress parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec registerCertificateRequestCreation(String hash, SecurityServerAddress securityServerAddress) throws WebClientResponseException {
        Object postBody = securityServerAddress;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling registerCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("hash", hash);

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
        return apiClient.invokeAPI("/token-certificates/{hash}/register", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * register certificate
     * &lt;h3&gt;Administrator registers selected certificate.&lt;/h3&gt;
     * <p><b>200</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @param securityServerAddress The securityServerAddress parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> registerCertificate(String hash, SecurityServerAddress securityServerAddress) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return registerCertificateRequestCreation(hash, securityServerAddress).bodyToMono(localVarReturnType);
    }

    /**
     * register certificate
     * &lt;h3&gt;Administrator registers selected certificate.&lt;/h3&gt;
     * <p><b>200</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @param securityServerAddress The securityServerAddress parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> registerCertificateWithHttpInfo(String hash, SecurityServerAddress securityServerAddress) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return registerCertificateRequestCreation(hash, securityServerAddress).toEntity(localVarReturnType);
    }

    /**
     * register certificate
     * &lt;h3&gt;Administrator registers selected certificate.&lt;/h3&gt;
     * <p><b>200</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @param securityServerAddress The securityServerAddress parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec registerCertificateWithResponseSpec(String hash, SecurityServerAddress securityServerAddress) throws WebClientResponseException {
        return registerCertificateRequestCreation(hash, securityServerAddress);
    }
    /**
     * unregister authentication certificate
     * &lt;h3&gt;Administrator unregisters selected authentication certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unregisterAuthCertificateRequestCreation(String hash) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new WebClientResponseException("Missing the required parameter 'hash' when calling unregisterAuthCertificate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/token-certificates/{hash}/unregister", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * unregister authentication certificate
     * &lt;h3&gt;Administrator unregisters selected authentication certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> unregisterAuthCertificate(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return unregisterAuthCertificateRequestCreation(hash).bodyToMono(localVarReturnType);
    }

    /**
     * unregister authentication certificate
     * &lt;h3&gt;Administrator unregisters selected authentication certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> unregisterAuthCertificateWithHttpInfo(String hash) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return unregisterAuthCertificateRequestCreation(hash).toEntity(localVarReturnType);
    }

    /**
     * unregister authentication certificate
     * &lt;h3&gt;Administrator unregisters selected authentication certificate.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending the management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt;
     * <p><b>204</b> - request was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param hash SHA-1 hash of the certificate
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unregisterAuthCertificateWithResponseSpec(String hash) throws WebClientResponseException {
        return unregisterAuthCertificateRequestCreation(hash);
    }
}
