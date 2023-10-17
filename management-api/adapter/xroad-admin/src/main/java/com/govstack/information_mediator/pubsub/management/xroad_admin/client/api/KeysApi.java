package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CsrFormat;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CsrGenerate;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import java.io.File;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Key;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyName;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.PossibleAction;

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
public class KeysApi {
    private ApiClient apiClient;

    public KeysApi() {
        this(new ApiClient());
    }

    @Autowired
    public KeysApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * delete csr from the selected key
     * &lt;h3&gt;Administrator deletes csr from the key.&lt;/h3&gt;
     * <p><b>204</b> - csr deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteCsrRequestCreation(String id, String csrId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'csrId' is set
        if (csrId == null) {
            throw new WebClientResponseException("Missing the required parameter 'csrId' when calling deleteCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("csr_id", csrId);

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
        return apiClient.invokeAPI("/keys/{id}/csrs/{csr_id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete csr from the selected key
     * &lt;h3&gt;Administrator deletes csr from the key.&lt;/h3&gt;
     * <p><b>204</b> - csr deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteCsr(String id, String csrId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteCsrRequestCreation(id, csrId).bodyToMono(localVarReturnType);
    }

    /**
     * delete csr from the selected key
     * &lt;h3&gt;Administrator deletes csr from the key.&lt;/h3&gt;
     * <p><b>204</b> - csr deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteCsrWithHttpInfo(String id, String csrId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteCsrRequestCreation(id, csrId).toEntity(localVarReturnType);
    }

    /**
     * delete csr from the selected key
     * &lt;h3&gt;Administrator deletes csr from the key.&lt;/h3&gt;
     * <p><b>204</b> - csr deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteCsrWithResponseSpec(String id, String csrId) throws WebClientResponseException {
        return deleteCsrRequestCreation(id, csrId);
    }
    /**
     * delete key
     * &lt;h3&gt;Administrator deletes the key.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending an auth cert deletion management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt; &lt;p&gt; Note that with this endpoint it&#39;s possible to delete an authentication key with a registered authentication certificate. &lt;ul&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true succeeds. The authentication certificate is first unregistered, and the key and certificate are deleted after that.&lt;/li&gt; &lt;/ul&gt; &lt;p&gt;When trying to delete an authentication key with a registered authentication certificate, the warning response has a warning code &lt;code&gt;auth_key_with_registered_cert_warning&lt;/code&gt; and the metadata field contains the key id of the key&lt;/p&gt; &lt;/p&gt;
     * <p><b>204</b> - key deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param ignoreWarnings if true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteKeyRequestCreation(String id, Boolean ignoreWarnings) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling deleteKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ignore_warnings", ignoreWarnings));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/keys/{id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete key
     * &lt;h3&gt;Administrator deletes the key.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending an auth cert deletion management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt; &lt;p&gt; Note that with this endpoint it&#39;s possible to delete an authentication key with a registered authentication certificate. &lt;ul&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true succeeds. The authentication certificate is first unregistered, and the key and certificate are deleted after that.&lt;/li&gt; &lt;/ul&gt; &lt;p&gt;When trying to delete an authentication key with a registered authentication certificate, the warning response has a warning code &lt;code&gt;auth_key_with_registered_cert_warning&lt;/code&gt; and the metadata field contains the key id of the key&lt;/p&gt; &lt;/p&gt;
     * <p><b>204</b> - key deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param ignoreWarnings if true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteKey(String id, Boolean ignoreWarnings) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteKeyRequestCreation(id, ignoreWarnings).bodyToMono(localVarReturnType);
    }

    /**
     * delete key
     * &lt;h3&gt;Administrator deletes the key.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending an auth cert deletion management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt; &lt;p&gt; Note that with this endpoint it&#39;s possible to delete an authentication key with a registered authentication certificate. &lt;ul&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true succeeds. The authentication certificate is first unregistered, and the key and certificate are deleted after that.&lt;/li&gt; &lt;/ul&gt; &lt;p&gt;When trying to delete an authentication key with a registered authentication certificate, the warning response has a warning code &lt;code&gt;auth_key_with_registered_cert_warning&lt;/code&gt; and the metadata field contains the key id of the key&lt;/p&gt; &lt;/p&gt;
     * <p><b>204</b> - key deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param ignoreWarnings if true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteKeyWithHttpInfo(String id, Boolean ignoreWarnings) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteKeyRequestCreation(id, ignoreWarnings).toEntity(localVarReturnType);
    }

    /**
     * delete key
     * &lt;h3&gt;Administrator deletes the key.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response if sending an auth cert deletion management request fails. The metadata array contains the error details that were generated in core. The message is in plain English.&lt;/p&gt; &lt;p&gt; Note that with this endpoint it&#39;s possible to delete an authentication key with a registered authentication certificate. &lt;ul&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to delete an authentication key with a registered authentication certificate and with &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true succeeds. The authentication certificate is first unregistered, and the key and certificate are deleted after that.&lt;/li&gt; &lt;/ul&gt; &lt;p&gt;When trying to delete an authentication key with a registered authentication certificate, the warning response has a warning code &lt;code&gt;auth_key_with_registered_cert_warning&lt;/code&gt; and the metadata field contains the key id of the key&lt;/p&gt; &lt;/p&gt;
     * <p><b>204</b> - key deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param ignoreWarnings if true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteKeyWithResponseSpec(String id, Boolean ignoreWarnings) throws WebClientResponseException {
        return deleteKeyRequestCreation(id, ignoreWarnings);
    }
    /**
     * download a CSR binary
     * &lt;h3&gt;Administrator downloads a csr that has been created earlier.&lt;/h3&gt;
     * <p><b>201</b> - CSR binary
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @param csrFormat format of the certificate signing request (PEM or DER)
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadCsrRequestCreation(String id, String csrId, CsrFormat csrFormat) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling downloadCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'csrId' is set
        if (csrId == null) {
            throw new WebClientResponseException("Missing the required parameter 'csrId' when calling downloadCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("csr_id", csrId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "csr_format", csrFormat));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/keys/{id}/csrs/{csr_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * download a CSR binary
     * &lt;h3&gt;Administrator downloads a csr that has been created earlier.&lt;/h3&gt;
     * <p><b>201</b> - CSR binary
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @param csrFormat format of the certificate signing request (PEM or DER)
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> downloadCsr(String id, String csrId, CsrFormat csrFormat) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadCsrRequestCreation(id, csrId, csrFormat).bodyToMono(localVarReturnType);
    }

    /**
     * download a CSR binary
     * &lt;h3&gt;Administrator downloads a csr that has been created earlier.&lt;/h3&gt;
     * <p><b>201</b> - CSR binary
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @param csrFormat format of the certificate signing request (PEM or DER)
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> downloadCsrWithHttpInfo(String id, String csrId, CsrFormat csrFormat) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadCsrRequestCreation(id, csrId, csrFormat).toEntity(localVarReturnType);
    }

    /**
     * download a CSR binary
     * &lt;h3&gt;Administrator downloads a csr that has been created earlier.&lt;/h3&gt;
     * <p><b>201</b> - CSR binary
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @param csrFormat format of the certificate signing request (PEM or DER)
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadCsrWithResponseSpec(String id, String csrId, CsrFormat csrFormat) throws WebClientResponseException {
        return downloadCsrRequestCreation(id, csrId, csrFormat);
    }
    /**
     * generate csr for the selected key
     * &lt;h3&gt;Administrator generates csr for the key.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrGenerate request to generate csr
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec generateCsrRequestCreation(String id, CsrGenerate csrGenerate) throws WebClientResponseException {
        Object postBody = csrGenerate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling generateCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/keys/{id}/csrs", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * generate csr for the selected key
     * &lt;h3&gt;Administrator generates csr for the key.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrGenerate request to generate csr
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> generateCsr(String id, CsrGenerate csrGenerate) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return generateCsrRequestCreation(id, csrGenerate).bodyToMono(localVarReturnType);
    }

    /**
     * generate csr for the selected key
     * &lt;h3&gt;Administrator generates csr for the key.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrGenerate request to generate csr
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> generateCsrWithHttpInfo(String id, CsrGenerate csrGenerate) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return generateCsrRequestCreation(id, csrGenerate).toEntity(localVarReturnType);
    }

    /**
     * generate csr for the selected key
     * &lt;h3&gt;Administrator generates csr for the key.&lt;/h3&gt;
     * <p><b>201</b> - created CSR
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrGenerate request to generate csr
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec generateCsrWithResponseSpec(String id, CsrGenerate csrGenerate) throws WebClientResponseException {
        return generateCsrRequestCreation(id, csrGenerate);
    }
    /**
     * get information for the selected key in selected token
     * &lt;h3&gt;Administrator views key details.&lt;/h3&gt;
     * <p><b>200</b> - key object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKeyRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return apiClient.invokeAPI("/keys/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get information for the selected key in selected token
     * &lt;h3&gt;Administrator views key details.&lt;/h3&gt;
     * <p><b>200</b> - key object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Key> getKey(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return getKeyRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get information for the selected key in selected token
     * &lt;h3&gt;Administrator views key details.&lt;/h3&gt;
     * <p><b>200</b> - key object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return ResponseEntity&lt;Key&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Key>> getKeyWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return getKeyRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get information for the selected key in selected token
     * &lt;h3&gt;Administrator views key details.&lt;/h3&gt;
     * <p><b>200</b> - key object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKeyWithResponseSpec(String id) throws WebClientResponseException {
        return getKeyRequestCreation(id);
    }
    /**
     * get possible actions for one csr
     * &lt;h3&gt;UI needs to know which actions can be done on one csr.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPossibleActionsForCsrRequestCreation(String id, String csrId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getPossibleActionsForCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'csrId' is set
        if (csrId == null) {
            throw new WebClientResponseException("Missing the required parameter 'csrId' when calling getPossibleActionsForCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);
        pathParams.put("csr_id", csrId);

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
        return apiClient.invokeAPI("/keys/{id}/csrs/{csr_id}/possible-actions", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get possible actions for one csr
     * &lt;h3&gt;UI needs to know which actions can be done on one csr.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PossibleAction> getPossibleActionsForCsr(String id, String csrId) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForCsrRequestCreation(id, csrId).bodyToFlux(localVarReturnType);
    }

    /**
     * get possible actions for one csr
     * &lt;h3&gt;UI needs to know which actions can be done on one csr.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @return ResponseEntity&lt;List&lt;PossibleAction&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PossibleAction>>> getPossibleActionsForCsrWithHttpInfo(String id, String csrId) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForCsrRequestCreation(id, csrId).toEntityList(localVarReturnType);
    }

    /**
     * get possible actions for one csr
     * &lt;h3&gt;UI needs to know which actions can be done on one csr.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param csrId id of the csr
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPossibleActionsForCsrWithResponseSpec(String id, String csrId) throws WebClientResponseException {
        return getPossibleActionsForCsrRequestCreation(id, csrId);
    }
    /**
     * get possible actions for one key
     * &lt;h3&gt;UI needs to know which actions can be done on one key.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPossibleActionsForKeyRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getPossibleActionsForKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return apiClient.invokeAPI("/keys/{id}/possible-actions", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get possible actions for one key
     * &lt;h3&gt;UI needs to know which actions can be done on one key.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return List&lt;PossibleAction&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<PossibleAction> getPossibleActionsForKey(String id) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForKeyRequestCreation(id).bodyToFlux(localVarReturnType);
    }

    /**
     * get possible actions for one key
     * &lt;h3&gt;UI needs to know which actions can be done on one key.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return ResponseEntity&lt;List&lt;PossibleAction&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<PossibleAction>>> getPossibleActionsForKeyWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<PossibleAction> localVarReturnType = new ParameterizedTypeReference<PossibleAction>() {};
        return getPossibleActionsForKeyRequestCreation(id).toEntityList(localVarReturnType);
    }

    /**
     * get possible actions for one key
     * &lt;h3&gt;UI needs to know which actions can be done on one key.&lt;/h3&gt;
     * <p><b>200</b> - possible actions that can be done on the certificate
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPossibleActionsForKeyWithResponseSpec(String id) throws WebClientResponseException {
        return getPossibleActionsForKeyRequestCreation(id);
    }
    /**
     * update key information
     * &lt;h3&gt;Administrator updates the key information.&lt;/h3&gt;
     * <p><b>200</b> - key modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param keyName The keyName parameter
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKeyRequestCreation(String id, KeyName keyName) throws WebClientResponseException {
        Object postBody = keyName;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return apiClient.invokeAPI("/keys/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update key information
     * &lt;h3&gt;Administrator updates the key information.&lt;/h3&gt;
     * <p><b>200</b> - key modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param keyName The keyName parameter
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Key> updateKey(String id, KeyName keyName) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return updateKeyRequestCreation(id, keyName).bodyToMono(localVarReturnType);
    }

    /**
     * update key information
     * &lt;h3&gt;Administrator updates the key information.&lt;/h3&gt;
     * <p><b>200</b> - key modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param keyName The keyName parameter
     * @return ResponseEntity&lt;Key&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Key>> updateKeyWithHttpInfo(String id, KeyName keyName) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return updateKeyRequestCreation(id, keyName).toEntity(localVarReturnType);
    }

    /**
     * update key information
     * &lt;h3&gt;Administrator updates the key information.&lt;/h3&gt;
     * <p><b>200</b> - key modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the key
     * @param keyName The keyName parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKeyWithResponseSpec(String id, KeyName keyName) throws WebClientResponseException {
        return updateKeyRequestCreation(id, keyName);
    }
}
