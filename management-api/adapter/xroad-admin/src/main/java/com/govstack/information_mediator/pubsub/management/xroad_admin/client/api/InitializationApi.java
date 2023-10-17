package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.InitialServerConf;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.InitializationStatus;

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
public class InitializationApi {
    private ApiClient apiClient;

    public InitializationApi() {
        this(new ApiClient());
    }

    @Autowired
    public InitializationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Check the initialization status of the Security Server
     * &lt;h3&gt;Administrator checks the initialization status of the Security Server.&lt;/h3&gt;
     * <p><b>200</b> - initialization status of the Security Server
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return InitializationStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getInitializationStatusRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<InitializationStatus> localVarReturnType = new ParameterizedTypeReference<InitializationStatus>() {};
        return apiClient.invokeAPI("/initialization/status", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Check the initialization status of the Security Server
     * &lt;h3&gt;Administrator checks the initialization status of the Security Server.&lt;/h3&gt;
     * <p><b>200</b> - initialization status of the Security Server
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return InitializationStatus
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<InitializationStatus> getInitializationStatus() throws WebClientResponseException {
        ParameterizedTypeReference<InitializationStatus> localVarReturnType = new ParameterizedTypeReference<InitializationStatus>() {};
        return getInitializationStatusRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * Check the initialization status of the Security Server
     * &lt;h3&gt;Administrator checks the initialization status of the Security Server.&lt;/h3&gt;
     * <p><b>200</b> - initialization status of the Security Server
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;InitializationStatus&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<InitializationStatus>> getInitializationStatusWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<InitializationStatus> localVarReturnType = new ParameterizedTypeReference<InitializationStatus>() {};
        return getInitializationStatusRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Check the initialization status of the Security Server
     * &lt;h3&gt;Administrator checks the initialization status of the Security Server.&lt;/h3&gt;
     * <p><b>200</b> - initialization status of the Security Server
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getInitializationStatusWithResponseSpec() throws WebClientResponseException {
        return getInitializationStatusRequestCreation();
    }
    /**
     * Initialize a new security server with the provided initial configuration
     * &lt;h3&gt;Administrator initializes a new Security Server with the provided initial configuration.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; Other possible error code is &lt;code&gt;invalid_init_params&lt;/code&gt; which can have any one or more of the following strings in the metadata field &lt;ul&gt; &lt;li&gt;server_code_not_provided&lt;/li&gt; &lt;li&gt;member_class_not_provided&lt;/li&gt; &lt;li&gt;member_code_not_provided&lt;/li&gt; &lt;li&gt;pin_code_not_provided&lt;/li&gt; &lt;li&gt;server_code_exists&lt;/li&gt; &lt;li&gt;member_class_exists&lt;/li&gt; &lt;li&gt;member_code_exists&lt;/li&gt; &lt;li&gt;pin_code_exists&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;server code has already been set for this Security Server (warning code &lt;code&gt;init_servercode_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;server owner has already been set for this Security Server(warning code &lt;code&gt;init_server_owner_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;software token has already been initialized in this Security Server(warning code &lt;code&gt;init_software_token_initialized&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided owner member is unregistered(warning code &lt;code&gt;init_unregistered_member&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided server id is already in use by another Security Server(warning code &lt;code&gt;init_server_id_exists&lt;/code&gt;)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the &#39;400&#39; response examples&lt;/strong&gt;
     * <p><b>201</b> - security server initialized
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param initialServerConf initial security server configuration
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec initSecurityServerRequestCreation(InitialServerConf initialServerConf) throws WebClientResponseException {
        Object postBody = initialServerConf;
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/initialization", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Initialize a new security server with the provided initial configuration
     * &lt;h3&gt;Administrator initializes a new Security Server with the provided initial configuration.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; Other possible error code is &lt;code&gt;invalid_init_params&lt;/code&gt; which can have any one or more of the following strings in the metadata field &lt;ul&gt; &lt;li&gt;server_code_not_provided&lt;/li&gt; &lt;li&gt;member_class_not_provided&lt;/li&gt; &lt;li&gt;member_code_not_provided&lt;/li&gt; &lt;li&gt;pin_code_not_provided&lt;/li&gt; &lt;li&gt;server_code_exists&lt;/li&gt; &lt;li&gt;member_class_exists&lt;/li&gt; &lt;li&gt;member_code_exists&lt;/li&gt; &lt;li&gt;pin_code_exists&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;server code has already been set for this Security Server (warning code &lt;code&gt;init_servercode_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;server owner has already been set for this Security Server(warning code &lt;code&gt;init_server_owner_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;software token has already been initialized in this Security Server(warning code &lt;code&gt;init_software_token_initialized&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided owner member is unregistered(warning code &lt;code&gt;init_unregistered_member&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided server id is already in use by another Security Server(warning code &lt;code&gt;init_server_id_exists&lt;/code&gt;)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the &#39;400&#39; response examples&lt;/strong&gt;
     * <p><b>201</b> - security server initialized
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param initialServerConf initial security server configuration
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> initSecurityServer(InitialServerConf initialServerConf) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return initSecurityServerRequestCreation(initialServerConf).bodyToMono(localVarReturnType);
    }

    /**
     * Initialize a new security server with the provided initial configuration
     * &lt;h3&gt;Administrator initializes a new Security Server with the provided initial configuration.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; Other possible error code is &lt;code&gt;invalid_init_params&lt;/code&gt; which can have any one or more of the following strings in the metadata field &lt;ul&gt; &lt;li&gt;server_code_not_provided&lt;/li&gt; &lt;li&gt;member_class_not_provided&lt;/li&gt; &lt;li&gt;member_code_not_provided&lt;/li&gt; &lt;li&gt;pin_code_not_provided&lt;/li&gt; &lt;li&gt;server_code_exists&lt;/li&gt; &lt;li&gt;member_class_exists&lt;/li&gt; &lt;li&gt;member_code_exists&lt;/li&gt; &lt;li&gt;pin_code_exists&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;server code has already been set for this Security Server (warning code &lt;code&gt;init_servercode_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;server owner has already been set for this Security Server(warning code &lt;code&gt;init_server_owner_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;software token has already been initialized in this Security Server(warning code &lt;code&gt;init_software_token_initialized&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided owner member is unregistered(warning code &lt;code&gt;init_unregistered_member&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided server id is already in use by another Security Server(warning code &lt;code&gt;init_server_id_exists&lt;/code&gt;)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the &#39;400&#39; response examples&lt;/strong&gt;
     * <p><b>201</b> - security server initialized
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param initialServerConf initial security server configuration
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> initSecurityServerWithHttpInfo(InitialServerConf initialServerConf) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return initSecurityServerRequestCreation(initialServerConf).toEntity(localVarReturnType);
    }

    /**
     * Initialize a new security server with the provided initial configuration
     * &lt;h3&gt;Administrator initializes a new Security Server with the provided initial configuration.&lt;/h3&gt; &lt;p&gt; This endpoint can also return metadata in the error response. The metadata array can contain error messages about why the init did not succeed. If the pin code is too weak, the error code &lt;code&gt;weak_pin&lt;/code&gt; is used and the entries in the metadata array are always ordered in following way &lt;ul&gt; &lt;li&gt;metadata has a list of strings [\&quot;pin_min_length\&quot;, x, \&quot;pin_min_char_classes_count\&quot;, y] where&lt;/li&gt; &lt;li&gt;x &#x3D; the minimum length of the pin code&lt;/li&gt; &lt;li&gt;y &#x3D; the minimum amount of character classes (e.g. uppercase, number, special characters) to be used in the pin code&lt;/li&gt; &lt;/ul&gt; Other possible error code is &lt;code&gt;invalid_init_params&lt;/code&gt; which can have any one or more of the following strings in the metadata field &lt;ul&gt; &lt;li&gt;server_code_not_provided&lt;/li&gt; &lt;li&gt;member_class_not_provided&lt;/li&gt; &lt;li&gt;member_code_not_provided&lt;/li&gt; &lt;li&gt;pin_code_not_provided&lt;/li&gt; &lt;li&gt;server_code_exists&lt;/li&gt; &lt;li&gt;member_class_exists&lt;/li&gt; &lt;li&gt;member_code_exists&lt;/li&gt; &lt;li&gt;pin_code_exists&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt; This endpoint can return a warnings response which can be ignored by setting &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; true. If &lt;code&gt;InitialServerConf.ignore_warnings&lt;/code&gt; &#x3D; false, a warnings response will be returned if any one of the following conditions is true &lt;ul&gt; &lt;li&gt;server code has already been set for this Security Server (warning code &lt;code&gt;init_servercode_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;server owner has already been set for this Security Server(warning code &lt;code&gt;init_server_owner_exists&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;software token has already been initialized in this Security Server(warning code &lt;code&gt;init_software_token_initialized&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided owner member is unregistered(warning code &lt;code&gt;init_unregistered_member&lt;/code&gt;)&lt;/li&gt; &lt;li&gt;the provided server id is already in use by another Security Server(warning code &lt;code&gt;init_server_id_exists&lt;/code&gt;)&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;strong&gt;See the &#39;400&#39; response examples&lt;/strong&gt;
     * <p><b>201</b> - security server initialized
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param initialServerConf initial security server configuration
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec initSecurityServerWithResponseSpec(InitialServerConf initialServerConf) throws WebClientResponseException {
        return initSecurityServerRequestCreation(initialServerConf);
    }
}
