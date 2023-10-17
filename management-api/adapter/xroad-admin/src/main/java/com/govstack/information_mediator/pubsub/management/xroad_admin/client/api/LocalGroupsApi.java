package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.LocalGroup;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.LocalGroupDescription;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Members;

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
public class LocalGroupsApi {
    private ApiClient apiClient;

    public LocalGroupsApi() {
        this(new ApiClient());
    }

    @Autowired
    public LocalGroupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * add new member for the local group
     * &lt;h3&gt;Administrator adds a new member for the local group.&lt;/h3&gt; &lt;p&gt;The new member can be an X-Road member or a subsystem.&lt;/p&gt;
     * <p><b>201</b> - new members added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @return Members
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addLocalGroupMemberRequestCreation(String groupId, Members members) throws WebClientResponseException {
        Object postBody = members;
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new WebClientResponseException("Missing the required parameter 'groupId' when calling addLocalGroupMember", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("group_id", groupId);

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

        ParameterizedTypeReference<Members> localVarReturnType = new ParameterizedTypeReference<Members>() {};
        return apiClient.invokeAPI("/local-groups/{group_id}/members", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new member for the local group
     * &lt;h3&gt;Administrator adds a new member for the local group.&lt;/h3&gt; &lt;p&gt;The new member can be an X-Road member or a subsystem.&lt;/p&gt;
     * <p><b>201</b> - new members added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @return Members
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Members> addLocalGroupMember(String groupId, Members members) throws WebClientResponseException {
        ParameterizedTypeReference<Members> localVarReturnType = new ParameterizedTypeReference<Members>() {};
        return addLocalGroupMemberRequestCreation(groupId, members).bodyToMono(localVarReturnType);
    }

    /**
     * add new member for the local group
     * &lt;h3&gt;Administrator adds a new member for the local group.&lt;/h3&gt; &lt;p&gt;The new member can be an X-Road member or a subsystem.&lt;/p&gt;
     * <p><b>201</b> - new members added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @return ResponseEntity&lt;Members&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Members>> addLocalGroupMemberWithHttpInfo(String groupId, Members members) throws WebClientResponseException {
        ParameterizedTypeReference<Members> localVarReturnType = new ParameterizedTypeReference<Members>() {};
        return addLocalGroupMemberRequestCreation(groupId, members).toEntity(localVarReturnType);
    }

    /**
     * add new member for the local group
     * &lt;h3&gt;Administrator adds a new member for the local group.&lt;/h3&gt; &lt;p&gt;The new member can be an X-Road member or a subsystem.&lt;/p&gt;
     * <p><b>201</b> - new members added
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addLocalGroupMemberWithResponseSpec(String groupId, Members members) throws WebClientResponseException {
        return addLocalGroupMemberRequestCreation(groupId, members);
    }
    /**
     * delete local group
     * &lt;h3&gt;Administrator deletes the local group.&lt;/h3&gt;
     * <p><b>204</b> - local group deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteLocalGroupRequestCreation(String groupId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new WebClientResponseException("Missing the required parameter 'groupId' when calling deleteLocalGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/local-groups/{group_id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete local group
     * &lt;h3&gt;Administrator deletes the local group.&lt;/h3&gt;
     * <p><b>204</b> - local group deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteLocalGroup(String groupId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteLocalGroupRequestCreation(groupId).bodyToMono(localVarReturnType);
    }

    /**
     * delete local group
     * &lt;h3&gt;Administrator deletes the local group.&lt;/h3&gt;
     * <p><b>204</b> - local group deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteLocalGroupWithHttpInfo(String groupId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteLocalGroupRequestCreation(groupId).toEntity(localVarReturnType);
    }

    /**
     * delete local group
     * &lt;h3&gt;Administrator deletes the local group.&lt;/h3&gt;
     * <p><b>204</b> - local group deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteLocalGroupWithResponseSpec(String groupId) throws WebClientResponseException {
        return deleteLocalGroupRequestCreation(groupId);
    }
    /**
     * delete member from local group
     * &lt;h3&gt;Administrator deletes the member from local group.&lt;/h3&gt;
     * <p><b>204</b> - members deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteLocalGroupMemberRequestCreation(String groupId, Members members) throws WebClientResponseException {
        Object postBody = members;
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new WebClientResponseException("Missing the required parameter 'groupId' when calling deleteLocalGroupMember", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/local-groups/{group_id}/members/delete", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete member from local group
     * &lt;h3&gt;Administrator deletes the member from local group.&lt;/h3&gt;
     * <p><b>204</b> - members deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteLocalGroupMember(String groupId, Members members) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteLocalGroupMemberRequestCreation(groupId, members).bodyToMono(localVarReturnType);
    }

    /**
     * delete member from local group
     * &lt;h3&gt;Administrator deletes the member from local group.&lt;/h3&gt;
     * <p><b>204</b> - members deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteLocalGroupMemberWithHttpInfo(String groupId, Members members) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteLocalGroupMemberRequestCreation(groupId, members).toEntity(localVarReturnType);
    }

    /**
     * delete member from local group
     * &lt;h3&gt;Administrator deletes the member from local group.&lt;/h3&gt;
     * <p><b>204</b> - members deleted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param members The members parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteLocalGroupMemberWithResponseSpec(String groupId, Members members) throws WebClientResponseException {
        return deleteLocalGroupMemberRequestCreation(groupId, members);
    }
    /**
     * get local group information
     * &lt;h3&gt;Administrator views local group details.&lt;/h3&gt;
     * <p><b>200</b> - group object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getLocalGroupRequestCreation(String groupId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new WebClientResponseException("Missing the required parameter 'groupId' when calling getLocalGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/local-groups/{group_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get local group information
     * &lt;h3&gt;Administrator views local group details.&lt;/h3&gt;
     * <p><b>200</b> - group object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<LocalGroup> getLocalGroup(String groupId) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return getLocalGroupRequestCreation(groupId).bodyToMono(localVarReturnType);
    }

    /**
     * get local group information
     * &lt;h3&gt;Administrator views local group details.&lt;/h3&gt;
     * <p><b>200</b> - group object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @return ResponseEntity&lt;LocalGroup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<LocalGroup>> getLocalGroupWithHttpInfo(String groupId) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return getLocalGroupRequestCreation(groupId).toEntity(localVarReturnType);
    }

    /**
     * get local group information
     * &lt;h3&gt;Administrator views local group details.&lt;/h3&gt;
     * <p><b>200</b> - group object
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getLocalGroupWithResponseSpec(String groupId) throws WebClientResponseException {
        return getLocalGroupRequestCreation(groupId);
    }
    /**
     * update local group information
     * &lt;h3&gt;Administrator updates the local group information.&lt;/h3&gt;
     * <p><b>200</b> - local group modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param localGroupDescription The localGroupDescription parameter
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateLocalGroupRequestCreation(String groupId, LocalGroupDescription localGroupDescription) throws WebClientResponseException {
        Object postBody = localGroupDescription;
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new WebClientResponseException("Missing the required parameter 'groupId' when calling updateLocalGroup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("group_id", groupId);

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
        return apiClient.invokeAPI("/local-groups/{group_id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * update local group information
     * &lt;h3&gt;Administrator updates the local group information.&lt;/h3&gt;
     * <p><b>200</b> - local group modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param localGroupDescription The localGroupDescription parameter
     * @return LocalGroup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<LocalGroup> updateLocalGroup(String groupId, LocalGroupDescription localGroupDescription) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return updateLocalGroupRequestCreation(groupId, localGroupDescription).bodyToMono(localVarReturnType);
    }

    /**
     * update local group information
     * &lt;h3&gt;Administrator updates the local group information.&lt;/h3&gt;
     * <p><b>200</b> - local group modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param localGroupDescription The localGroupDescription parameter
     * @return ResponseEntity&lt;LocalGroup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<LocalGroup>> updateLocalGroupWithHttpInfo(String groupId, LocalGroupDescription localGroupDescription) throws WebClientResponseException {
        ParameterizedTypeReference<LocalGroup> localVarReturnType = new ParameterizedTypeReference<LocalGroup>() {};
        return updateLocalGroupRequestCreation(groupId, localGroupDescription).toEntity(localVarReturnType);
    }

    /**
     * update local group information
     * &lt;h3&gt;Administrator updates the local group information.&lt;/h3&gt;
     * <p><b>200</b> - local group modified
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param groupId id of the local group
     * @param localGroupDescription The localGroupDescription parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateLocalGroupWithResponseSpec(String groupId, LocalGroupDescription localGroupDescription) throws WebClientResponseException {
        return updateLocalGroupRequestCreation(groupId, localGroupDescription);
    }
}
