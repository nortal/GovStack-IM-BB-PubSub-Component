package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Key;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyLabel;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyLabelWithCsrGenerate;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyWithCertificateSigningRequestId;
import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Token;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenName;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenPassword;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokenPinUpdate;

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
public class TokensApi {
    private ApiClient apiClient;

    public TokensApi() {
        this(new ApiClient());
    }

    @Autowired
    public TokensApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * add new key
     * &lt;h3&gt;Adds key for selected token.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabel The keyLabel parameter
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addKeyRequestCreation(String id, KeyLabel keyLabel) throws WebClientResponseException {
        Object postBody = keyLabel;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/tokens/{id}/keys", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new key
     * &lt;h3&gt;Adds key for selected token.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabel The keyLabel parameter
     * @return Key
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Key> addKey(String id, KeyLabel keyLabel) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return addKeyRequestCreation(id, keyLabel).bodyToMono(localVarReturnType);
    }

    /**
     * add new key
     * &lt;h3&gt;Adds key for selected token.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabel The keyLabel parameter
     * @return ResponseEntity&lt;Key&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Key>> addKeyWithHttpInfo(String id, KeyLabel keyLabel) throws WebClientResponseException {
        ParameterizedTypeReference<Key> localVarReturnType = new ParameterizedTypeReference<Key>() {};
        return addKeyRequestCreation(id, keyLabel).toEntity(localVarReturnType);
    }

    /**
     * add new key
     * &lt;h3&gt;Adds key for selected token.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabel The keyLabel parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addKeyWithResponseSpec(String id, KeyLabel keyLabel) throws WebClientResponseException {
        return addKeyRequestCreation(id, keyLabel);
    }
    /**
     * add a new key and generate a csr for it
     * &lt;h3&gt;Administrator adds a new key and generates a csr for it.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabelWithCsrGenerate The keyLabelWithCsrGenerate parameter
     * @return KeyWithCertificateSigningRequestId
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addKeyAndCsrRequestCreation(String id, KeyLabelWithCsrGenerate keyLabelWithCsrGenerate) throws WebClientResponseException {
        Object postBody = keyLabelWithCsrGenerate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling addKeyAndCsr", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<KeyWithCertificateSigningRequestId> localVarReturnType = new ParameterizedTypeReference<KeyWithCertificateSigningRequestId>() {};
        return apiClient.invokeAPI("/tokens/{id}/keys-with-csrs", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add a new key and generate a csr for it
     * &lt;h3&gt;Administrator adds a new key and generates a csr for it.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabelWithCsrGenerate The keyLabelWithCsrGenerate parameter
     * @return KeyWithCertificateSigningRequestId
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<KeyWithCertificateSigningRequestId> addKeyAndCsr(String id, KeyLabelWithCsrGenerate keyLabelWithCsrGenerate) throws WebClientResponseException {
        ParameterizedTypeReference<KeyWithCertificateSigningRequestId> localVarReturnType = new ParameterizedTypeReference<KeyWithCertificateSigningRequestId>() {};
        return addKeyAndCsrRequestCreation(id, keyLabelWithCsrGenerate).bodyToMono(localVarReturnType);
    }

    /**
     * add a new key and generate a csr for it
     * &lt;h3&gt;Administrator adds a new key and generates a csr for it.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabelWithCsrGenerate The keyLabelWithCsrGenerate parameter
     * @return ResponseEntity&lt;KeyWithCertificateSigningRequestId&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<KeyWithCertificateSigningRequestId>> addKeyAndCsrWithHttpInfo(String id, KeyLabelWithCsrGenerate keyLabelWithCsrGenerate) throws WebClientResponseException {
        ParameterizedTypeReference<KeyWithCertificateSigningRequestId> localVarReturnType = new ParameterizedTypeReference<KeyWithCertificateSigningRequestId>() {};
        return addKeyAndCsrRequestCreation(id, keyLabelWithCsrGenerate).toEntity(localVarReturnType);
    }

    /**
     * add a new key and generate a csr for it
     * &lt;h3&gt;Administrator adds a new key and generates a csr for it.&lt;/h3&gt;
     * <p><b>201</b> - key created for the token
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists or token not logged in
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param keyLabelWithCsrGenerate The keyLabelWithCsrGenerate parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addKeyAndCsrWithResponseSpec(String id, KeyLabelWithCsrGenerate keyLabelWithCsrGenerate) throws WebClientResponseException {
        return addKeyAndCsrRequestCreation(id, keyLabelWithCsrGenerate);
    }
    /**
     * get security server token information
     * &lt;h3&gt;Administrator views the token details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - token object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTokenRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return apiClient.invokeAPI("/tokens/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server token information
     * &lt;h3&gt;Administrator views the token details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - token object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Token> getToken(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return getTokenRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * get security server token information
     * &lt;h3&gt;Administrator views the token details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - token object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return ResponseEntity&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Token>> getTokenWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return getTokenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * get security server token information
     * &lt;h3&gt;Administrator views the token details of the security server.&lt;/h3&gt;
     * <p><b>200</b> - token object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTokenWithResponseSpec(String id) throws WebClientResponseException {
        return getTokenRequestCreation(id);
    }
    /**
     * get security server tokens
     * &lt;h3&gt;Administrator views tokens of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of tokens
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getTokensRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return apiClient.invokeAPI("/tokens", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server tokens
     * &lt;h3&gt;Administrator views tokens of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of tokens
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<Token> getTokens() throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return getTokensRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * get security server tokens
     * &lt;h3&gt;Administrator views tokens of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of tokens
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;Token&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<Token>>> getTokensWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return getTokensRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * get security server tokens
     * &lt;h3&gt;Administrator views tokens of the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of tokens
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getTokensWithResponseSpec() throws WebClientResponseException {
        return getTokensRequestCreation();
    }
    /**
     * login to token
     * &lt;h3&gt;Administrator logs in to a token&lt;/h3&gt;
     * <p><b>200</b> - logged in
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenPassword The tokenPassword parameter
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec loginTokenRequestCreation(String id, TokenPassword tokenPassword) throws WebClientResponseException {
        Object postBody = tokenPassword;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling loginToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return apiClient.invokeAPI("/tokens/{id}/login", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * login to token
     * &lt;h3&gt;Administrator logs in to a token&lt;/h3&gt;
     * <p><b>200</b> - logged in
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenPassword The tokenPassword parameter
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Token> loginToken(String id, TokenPassword tokenPassword) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return loginTokenRequestCreation(id, tokenPassword).bodyToMono(localVarReturnType);
    }

    /**
     * login to token
     * &lt;h3&gt;Administrator logs in to a token&lt;/h3&gt;
     * <p><b>200</b> - logged in
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenPassword The tokenPassword parameter
     * @return ResponseEntity&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Token>> loginTokenWithHttpInfo(String id, TokenPassword tokenPassword) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return loginTokenRequestCreation(id, tokenPassword).toEntity(localVarReturnType);
    }

    /**
     * login to token
     * &lt;h3&gt;Administrator logs in to a token&lt;/h3&gt;
     * <p><b>200</b> - logged in
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenPassword The tokenPassword parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec loginTokenWithResponseSpec(String id, TokenPassword tokenPassword) throws WebClientResponseException {
        return loginTokenRequestCreation(id, tokenPassword);
    }
    /**
     * logout from token
     * &lt;h3&gt;Administrator logs out from token.&lt;/h3&gt;
     * <p><b>200</b> - logged out
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec logoutTokenRequestCreation(String id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling logoutToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return apiClient.invokeAPI("/tokens/{id}/logout", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * logout from token
     * &lt;h3&gt;Administrator logs out from token.&lt;/h3&gt;
     * <p><b>200</b> - logged out
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Token> logoutToken(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return logoutTokenRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * logout from token
     * &lt;h3&gt;Administrator logs out from token.&lt;/h3&gt;
     * <p><b>200</b> - logged out
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return ResponseEntity&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Token>> logoutTokenWithHttpInfo(String id) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return logoutTokenRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * logout from token
     * &lt;h3&gt;Administrator logs out from token.&lt;/h3&gt;
     * <p><b>200</b> - logged out
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec logoutTokenWithResponseSpec(String id) throws WebClientResponseException {
        return logoutTokenRequestCreation(id);
    }
    /**
     * update security server token information
     * &lt;h3&gt;Administrator updates the token information.&lt;/h3&gt;
     * <p><b>200</b> - token modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenName The tokenName parameter
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTokenRequestCreation(String id, TokenName tokenName) throws WebClientResponseException {
        Object postBody = tokenName;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return apiClient.invokeAPI("/tokens/{id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update security server token information
     * &lt;h3&gt;Administrator updates the token information.&lt;/h3&gt;
     * <p><b>200</b> - token modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenName The tokenName parameter
     * @return Token
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Token> updateToken(String id, TokenName tokenName) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return updateTokenRequestCreation(id, tokenName).bodyToMono(localVarReturnType);
    }

    /**
     * update security server token information
     * &lt;h3&gt;Administrator updates the token information.&lt;/h3&gt;
     * <p><b>200</b> - token modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenName The tokenName parameter
     * @return ResponseEntity&lt;Token&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Token>> updateTokenWithHttpInfo(String id, TokenName tokenName) throws WebClientResponseException {
        ParameterizedTypeReference<Token> localVarReturnType = new ParameterizedTypeReference<Token>() {};
        return updateTokenRequestCreation(id, tokenName).toEntity(localVarReturnType);
    }

    /**
     * update security server token information
     * &lt;h3&gt;Administrator updates the token information.&lt;/h3&gt;
     * <p><b>200</b> - token modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the token
     * @param tokenName The tokenName parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTokenWithResponseSpec(String id, TokenName tokenName) throws WebClientResponseException {
        return updateTokenRequestCreation(id, tokenName);
    }
    /**
     * update security server software token pin code
     * &lt;h3&gt;Administrator updates the software token pin code.&lt;/h3&gt; &lt;p&gt;This operation supports updating the pin code of a software token only.&lt;/p&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>204</b> - software token pin updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the software token
     * @param tokenPinUpdate The tokenPinUpdate parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateTokenPinRequestCreation(String id, TokenPinUpdate tokenPinUpdate) throws WebClientResponseException {
        Object postBody = tokenPinUpdate;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling updateTokenPin", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/tokens/{id}/pin", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update security server software token pin code
     * &lt;h3&gt;Administrator updates the software token pin code.&lt;/h3&gt; &lt;p&gt;This operation supports updating the pin code of a software token only.&lt;/p&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>204</b> - software token pin updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the software token
     * @param tokenPinUpdate The tokenPinUpdate parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateTokenPin(String id, TokenPinUpdate tokenPinUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateTokenPinRequestCreation(id, tokenPinUpdate).bodyToMono(localVarReturnType);
    }

    /**
     * update security server software token pin code
     * &lt;h3&gt;Administrator updates the software token pin code.&lt;/h3&gt; &lt;p&gt;This operation supports updating the pin code of a software token only.&lt;/p&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>204</b> - software token pin updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the software token
     * @param tokenPinUpdate The tokenPinUpdate parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateTokenPinWithHttpInfo(String id, TokenPinUpdate tokenPinUpdate) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateTokenPinRequestCreation(id, tokenPinUpdate).toEntity(localVarReturnType);
    }

    /**
     * update security server software token pin code
     * &lt;h3&gt;Administrator updates the software token pin code.&lt;/h3&gt; &lt;p&gt;This operation supports updating the pin code of a software token only.&lt;/p&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt;
     * <p><b>204</b> - software token pin updated
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param id id of the software token
     * @param tokenPinUpdate The tokenPinUpdate parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateTokenPinWithResponseSpec(String id, TokenPinUpdate tokenPinUpdate) throws WebClientResponseException {
        return updateTokenPinRequestCreation(id, tokenPinUpdate);
    }
}
