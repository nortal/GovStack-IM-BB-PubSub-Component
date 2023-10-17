package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CertificateAuthority;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.CsrSubjectFieldDescription;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyUsageType;
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
public class CertificateAuthoritiesApi {
    private ApiClient apiClient;

    public CertificateAuthoritiesApi() {
        this(new ApiClient());
    }

    @Autowired
    public CertificateAuthoritiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * view the approved certificate authorities
     * &lt;h3&gt;Administrator views the approved certificate authorities.&lt;/h3&gt;
     * <p><b>200</b> - list of approved certificate authorities
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param keyUsageType return only CAs suitable for this type of key usage
     * @param includeIntermediateCas if true, include also intermediate CAs. Otherwise only top CAs are included. Default value is \&quot;false\&quot;.
     * @return Set&lt;CertificateAuthority&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApprovedCertificateAuthoritiesRequestCreation(KeyUsageType keyUsageType, Boolean includeIntermediateCas) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key_usage_type", keyUsageType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_intermediate_cas", includeIntermediateCas));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<CertificateAuthority> localVarReturnType = new ParameterizedTypeReference<CertificateAuthority>() {};
        return apiClient.invokeAPI("/certificate-authorities", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view the approved certificate authorities
     * &lt;h3&gt;Administrator views the approved certificate authorities.&lt;/h3&gt;
     * <p><b>200</b> - list of approved certificate authorities
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param keyUsageType return only CAs suitable for this type of key usage
     * @param includeIntermediateCas if true, include also intermediate CAs. Otherwise only top CAs are included. Default value is \&quot;false\&quot;.
     * @return Set&lt;CertificateAuthority&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<CertificateAuthority> getApprovedCertificateAuthorities(KeyUsageType keyUsageType, Boolean includeIntermediateCas) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateAuthority> localVarReturnType = new ParameterizedTypeReference<CertificateAuthority>() {};
        return getApprovedCertificateAuthoritiesRequestCreation(keyUsageType, includeIntermediateCas).bodyToFlux(localVarReturnType);
    }

    /**
     * view the approved certificate authorities
     * &lt;h3&gt;Administrator views the approved certificate authorities.&lt;/h3&gt;
     * <p><b>200</b> - list of approved certificate authorities
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param keyUsageType return only CAs suitable for this type of key usage
     * @param includeIntermediateCas if true, include also intermediate CAs. Otherwise only top CAs are included. Default value is \&quot;false\&quot;.
     * @return ResponseEntity&lt;Set&lt;CertificateAuthority&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<CertificateAuthority>>> getApprovedCertificateAuthoritiesWithHttpInfo(KeyUsageType keyUsageType, Boolean includeIntermediateCas) throws WebClientResponseException {
        ParameterizedTypeReference<CertificateAuthority> localVarReturnType = new ParameterizedTypeReference<CertificateAuthority>() {};
        return getApprovedCertificateAuthoritiesRequestCreation(keyUsageType, includeIntermediateCas).toEntityList(localVarReturnType);
    }

    /**
     * view the approved certificate authorities
     * &lt;h3&gt;Administrator views the approved certificate authorities.&lt;/h3&gt;
     * <p><b>200</b> - list of approved certificate authorities
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param keyUsageType return only CAs suitable for this type of key usage
     * @param includeIntermediateCas if true, include also intermediate CAs. Otherwise only top CAs are included. Default value is \&quot;false\&quot;.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApprovedCertificateAuthoritiesWithResponseSpec(KeyUsageType keyUsageType, Boolean includeIntermediateCas) throws WebClientResponseException {
        return getApprovedCertificateAuthoritiesRequestCreation(keyUsageType, includeIntermediateCas);
    }
    /**
     * get description of subject DN fields for CSR
     * &lt;h3&gt;List DN field descriptions to collect CSR parameters&lt;/h3&gt;
     * <p><b>200</b> - csr subject field objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param caName common name of the CA
     * @param keyUsageType which usage type this CSR is for
     * @param keyId id of the key. If provided, used only for validating correct key usage
     * @param memberId member client id for signing CSRs. &lt;instance_id&gt;:&lt;member_class&gt;:&lt;member_code&gt;
     * @param isNewMember whether or not the member in the member_id parameter is a new member
     * @return Set&lt;CsrSubjectFieldDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSubjectFieldDescriptionsRequestCreation(String caName, KeyUsageType keyUsageType, String keyId, String memberId, Boolean isNewMember) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'caName' is set
        if (caName == null) {
            throw new WebClientResponseException("Missing the required parameter 'caName' when calling getSubjectFieldDescriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyUsageType' is set
        if (keyUsageType == null) {
            throw new WebClientResponseException("Missing the required parameter 'keyUsageType' when calling getSubjectFieldDescriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("ca_name", caName);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key_id", keyId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "key_usage_type", keyUsageType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "member_id", memberId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "is_new_member", isNewMember));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<CsrSubjectFieldDescription> localVarReturnType = new ParameterizedTypeReference<CsrSubjectFieldDescription>() {};
        return apiClient.invokeAPI("/certificate-authorities/{ca_name}/csr-subject-fields", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get description of subject DN fields for CSR
     * &lt;h3&gt;List DN field descriptions to collect CSR parameters&lt;/h3&gt;
     * <p><b>200</b> - csr subject field objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param caName common name of the CA
     * @param keyUsageType which usage type this CSR is for
     * @param keyId id of the key. If provided, used only for validating correct key usage
     * @param memberId member client id for signing CSRs. &lt;instance_id&gt;:&lt;member_class&gt;:&lt;member_code&gt;
     * @param isNewMember whether or not the member in the member_id parameter is a new member
     * @return Set&lt;CsrSubjectFieldDescription&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<CsrSubjectFieldDescription> getSubjectFieldDescriptions(String caName, KeyUsageType keyUsageType, String keyId, String memberId, Boolean isNewMember) throws WebClientResponseException {
        ParameterizedTypeReference<CsrSubjectFieldDescription> localVarReturnType = new ParameterizedTypeReference<CsrSubjectFieldDescription>() {};
        return getSubjectFieldDescriptionsRequestCreation(caName, keyUsageType, keyId, memberId, isNewMember).bodyToFlux(localVarReturnType);
    }

    /**
     * get description of subject DN fields for CSR
     * &lt;h3&gt;List DN field descriptions to collect CSR parameters&lt;/h3&gt;
     * <p><b>200</b> - csr subject field objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param caName common name of the CA
     * @param keyUsageType which usage type this CSR is for
     * @param keyId id of the key. If provided, used only for validating correct key usage
     * @param memberId member client id for signing CSRs. &lt;instance_id&gt;:&lt;member_class&gt;:&lt;member_code&gt;
     * @param isNewMember whether or not the member in the member_id parameter is a new member
     * @return ResponseEntity&lt;Set&lt;CsrSubjectFieldDescription&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<CsrSubjectFieldDescription>>> getSubjectFieldDescriptionsWithHttpInfo(String caName, KeyUsageType keyUsageType, String keyId, String memberId, Boolean isNewMember) throws WebClientResponseException {
        ParameterizedTypeReference<CsrSubjectFieldDescription> localVarReturnType = new ParameterizedTypeReference<CsrSubjectFieldDescription>() {};
        return getSubjectFieldDescriptionsRequestCreation(caName, keyUsageType, keyId, memberId, isNewMember).toEntityList(localVarReturnType);
    }

    /**
     * get description of subject DN fields for CSR
     * &lt;h3&gt;List DN field descriptions to collect CSR parameters&lt;/h3&gt;
     * <p><b>200</b> - csr subject field objects
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param caName common name of the CA
     * @param keyUsageType which usage type this CSR is for
     * @param keyId id of the key. If provided, used only for validating correct key usage
     * @param memberId member client id for signing CSRs. &lt;instance_id&gt;:&lt;member_class&gt;:&lt;member_code&gt;
     * @param isNewMember whether or not the member in the member_id parameter is a new member
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSubjectFieldDescriptionsWithResponseSpec(String caName, KeyUsageType keyUsageType, String keyId, String memberId, Boolean isNewMember) throws WebClientResponseException {
        return getSubjectFieldDescriptionsRequestCreation(caName, keyUsageType, keyId, memberId, isNewMember);
    }
}
