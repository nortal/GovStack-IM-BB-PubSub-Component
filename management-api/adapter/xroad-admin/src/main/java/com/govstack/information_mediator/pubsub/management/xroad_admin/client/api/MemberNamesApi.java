package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.MemberName;

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
public class MemberNamesApi {
    private ApiClient apiClient;

    public MemberNamesApi() {
        this(new ApiClient());
    }

    @Autowired
    public MemberNamesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * find member name by member class and member code
     * &lt;h3&gt;Administrator looks up member&#39;s name.&lt;/h3&gt;
     * <p><b>200</b> - name of the member
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param memberClass class of the member
     * @param memberCode code of the member
     * @return MemberName
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec findMemberNameRequestCreation(String memberClass, String memberCode) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'memberClass' is set
        if (memberClass == null) {
            throw new WebClientResponseException("Missing the required parameter 'memberClass' when calling findMemberName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'memberCode' is set
        if (memberCode == null) {
            throw new WebClientResponseException("Missing the required parameter 'memberCode' when calling findMemberName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_class", memberClass));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_code", memberCode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<MemberName> localVarReturnType = new ParameterizedTypeReference<MemberName>() {};
        return apiClient.invokeAPI("/member-names", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * find member name by member class and member code
     * &lt;h3&gt;Administrator looks up member&#39;s name.&lt;/h3&gt;
     * <p><b>200</b> - name of the member
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param memberClass class of the member
     * @param memberCode code of the member
     * @return MemberName
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<MemberName> findMemberName(String memberClass, String memberCode) throws WebClientResponseException {
        ParameterizedTypeReference<MemberName> localVarReturnType = new ParameterizedTypeReference<MemberName>() {};
        return findMemberNameRequestCreation(memberClass, memberCode).bodyToMono(localVarReturnType);
    }

    /**
     * find member name by member class and member code
     * &lt;h3&gt;Administrator looks up member&#39;s name.&lt;/h3&gt;
     * <p><b>200</b> - name of the member
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param memberClass class of the member
     * @param memberCode code of the member
     * @return ResponseEntity&lt;MemberName&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<MemberName>> findMemberNameWithHttpInfo(String memberClass, String memberCode) throws WebClientResponseException {
        ParameterizedTypeReference<MemberName> localVarReturnType = new ParameterizedTypeReference<MemberName>() {};
        return findMemberNameRequestCreation(memberClass, memberCode).toEntity(localVarReturnType);
    }

    /**
     * find member name by member class and member code
     * &lt;h3&gt;Administrator looks up member&#39;s name.&lt;/h3&gt;
     * <p><b>200</b> - name of the member
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param memberClass class of the member
     * @param memberCode code of the member
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec findMemberNameWithResponseSpec(String memberClass, String memberCode) throws WebClientResponseException {
        return findMemberNameRequestCreation(memberClass, memberCode);
    }
}
