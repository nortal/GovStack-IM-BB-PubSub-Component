package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Language;

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
public class LanguageApi {
    private ApiClient apiClient;

    public LanguageApi() {
        this(new ApiClient());
    }

    @Autowired
    public LanguageApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * change language
     * &lt;h3&gt;Administrator changes the language for the UI.&lt;/h3&gt;
     * <p><b>200</b> - language changed
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param code code of the language (language code)
     * @return Language
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec languageRequestCreation(String code) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new WebClientResponseException("Missing the required parameter 'code' when calling language", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("code", code);

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

        ParameterizedTypeReference<Language> localVarReturnType = new ParameterizedTypeReference<Language>() {};
        return apiClient.invokeAPI("/language/{code}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * change language
     * &lt;h3&gt;Administrator changes the language for the UI.&lt;/h3&gt;
     * <p><b>200</b> - language changed
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param code code of the language (language code)
     * @return Language
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Language> language(String code) throws WebClientResponseException {
        ParameterizedTypeReference<Language> localVarReturnType = new ParameterizedTypeReference<Language>() {};
        return languageRequestCreation(code).bodyToMono(localVarReturnType);
    }

    /**
     * change language
     * &lt;h3&gt;Administrator changes the language for the UI.&lt;/h3&gt;
     * <p><b>200</b> - language changed
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param code code of the language (language code)
     * @return ResponseEntity&lt;Language&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Language>> languageWithHttpInfo(String code) throws WebClientResponseException {
        ParameterizedTypeReference<Language> localVarReturnType = new ParameterizedTypeReference<Language>() {};
        return languageRequestCreation(code).toEntity(localVarReturnType);
    }

    /**
     * change language
     * &lt;h3&gt;Administrator changes the language for the UI.&lt;/h3&gt;
     * <p><b>200</b> - language changed
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param code code of the language (language code)
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec languageWithResponseSpec(String code) throws WebClientResponseException {
        return languageRequestCreation(code);
    }
}
