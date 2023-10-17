package com.govstack.information_mediator.pubsub.management.xroad_admin.client.api;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.ApiClient;

import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.Backup;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.BackupExt;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.ErrorInfo;
import java.io.File;
import java.util.Set;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.TokensLoggedOut;

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
public class BackupsApi {
    private ApiClient apiClient;

    public BackupsApi() {
        this(new ApiClient());
    }

    @Autowired
    public BackupsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * add new backup for the security server
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return Backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addBackupRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return apiClient.invokeAPI("/backups", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new backup for the security server
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return Backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Backup> addBackup() throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return addBackupRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * add new backup for the security server
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Backup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Backup>> addBackupWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return addBackupRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * add new backup for the security server
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
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
    public ResponseSpec addBackupWithResponseSpec() throws WebClientResponseException {
        return addBackupRequestCreation();
    }
    /**
     * add new backup for the security server and return extra backup state
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return BackupExt
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addBackupExtRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<BackupExt> localVarReturnType = new ParameterizedTypeReference<BackupExt>() {};
        return apiClient.invokeAPI("/backups/ext", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * add new backup for the security server and return extra backup state
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return BackupExt
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BackupExt> addBackupExt() throws WebClientResponseException {
        ParameterizedTypeReference<BackupExt> localVarReturnType = new ParameterizedTypeReference<BackupExt>() {};
        return addBackupExtRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * add new backup for the security server and return extra backup state
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;BackupExt&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<BackupExt>> addBackupExtWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<BackupExt> localVarReturnType = new ParameterizedTypeReference<BackupExt>() {};
        return addBackupExtRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * add new backup for the security server and return extra backup state
     * &lt;h3&gt;Adds security server backup to the system.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup generation script.&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
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
    public ResponseSpec addBackupExtWithResponseSpec() throws WebClientResponseException {
        return addBackupExtRequestCreation();
    }
    /**
     * delete security server backup
     * &lt;h3&gt;Administrator deletes the backup of the security server.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteBackupRequestCreation(String filename) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new WebClientResponseException("Missing the required parameter 'filename' when calling deleteBackup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("filename", filename);

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
        return apiClient.invokeAPI("/backups/{filename}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * delete security server backup
     * &lt;h3&gt;Administrator deletes the backup of the security server.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteBackup(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteBackupRequestCreation(filename).bodyToMono(localVarReturnType);
    }

    /**
     * delete security server backup
     * &lt;h3&gt;Administrator deletes the backup of the security server.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteBackupWithHttpInfo(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteBackupRequestCreation(filename).toEntity(localVarReturnType);
    }

    /**
     * delete security server backup
     * &lt;h3&gt;Administrator deletes the backup of the security server.&lt;/h3&gt;
     * <p><b>204</b> - deletion was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteBackupWithResponseSpec(String filename) throws WebClientResponseException {
        return deleteBackupRequestCreation(filename);
    }
    /**
     * download security server backup
     * &lt;h3&gt;Administrator downloads the backup of the security server.&lt;/h3&gt;
     * <p><b>200</b> - backup file downloaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadBackupRequestCreation(String filename) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new WebClientResponseException("Missing the required parameter 'filename' when calling downloadBackup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("filename", filename);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/backups/{filename}/download", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * download security server backup
     * &lt;h3&gt;Administrator downloads the backup of the security server.&lt;/h3&gt;
     * <p><b>200</b> - backup file downloaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> downloadBackup(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadBackupRequestCreation(filename).bodyToMono(localVarReturnType);
    }

    /**
     * download security server backup
     * &lt;h3&gt;Administrator downloads the backup of the security server.&lt;/h3&gt;
     * <p><b>200</b> - backup file downloaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return ResponseEntity&lt;File&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<File>> downloadBackupWithHttpInfo(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return downloadBackupRequestCreation(filename).toEntity(localVarReturnType);
    }

    /**
     * download security server backup
     * &lt;h3&gt;Administrator downloads the backup of the security server.&lt;/h3&gt;
     * <p><b>200</b> - backup file downloaded
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadBackupWithResponseSpec(String filename) throws WebClientResponseException {
        return downloadBackupRequestCreation(filename);
    }
    /**
     * get security server backups
     * &lt;h3&gt;Administrator views the backups for the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of security server backups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;Backup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getBackupsRequestCreation() throws WebClientResponseException {
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

        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return apiClient.invokeAPI("/backups", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * get security server backups
     * &lt;h3&gt;Administrator views the backups for the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of security server backups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return Set&lt;Backup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<Backup> getBackups() throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return getBackupsRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * get security server backups
     * &lt;h3&gt;Administrator views the backups for the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of security server backups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseEntity&lt;Set&lt;Backup&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<Backup>>> getBackupsWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return getBackupsRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * get security server backups
     * &lt;h3&gt;Administrator views the backups for the security server.&lt;/h3&gt;
     * <p><b>200</b> - list of security server backups
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getBackupsWithResponseSpec() throws WebClientResponseException {
        return getBackupsRequestCreation();
    }
    /**
     * restore security server configuration from backup
     * &lt;h3&gt;Administrator restores the security server configuration from backup.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup restore script.&lt;/p&gt;
     * <p><b>200</b> - restore was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return TokensLoggedOut
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec restoreBackupRequestCreation(String filename) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'filename' is set
        if (filename == null) {
            throw new WebClientResponseException("Missing the required parameter 'filename' when calling restoreBackup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("filename", filename);

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

        ParameterizedTypeReference<TokensLoggedOut> localVarReturnType = new ParameterizedTypeReference<TokensLoggedOut>() {};
        return apiClient.invokeAPI("/backups/{filename}/restore", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * restore security server configuration from backup
     * &lt;h3&gt;Administrator restores the security server configuration from backup.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup restore script.&lt;/p&gt;
     * <p><b>200</b> - restore was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return TokensLoggedOut
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TokensLoggedOut> restoreBackup(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<TokensLoggedOut> localVarReturnType = new ParameterizedTypeReference<TokensLoggedOut>() {};
        return restoreBackupRequestCreation(filename).bodyToMono(localVarReturnType);
    }

    /**
     * restore security server configuration from backup
     * &lt;h3&gt;Administrator restores the security server configuration from backup.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup restore script.&lt;/p&gt;
     * <p><b>200</b> - restore was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return ResponseEntity&lt;TokensLoggedOut&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<TokensLoggedOut>> restoreBackupWithHttpInfo(String filename) throws WebClientResponseException {
        ParameterizedTypeReference<TokensLoggedOut> localVarReturnType = new ParameterizedTypeReference<TokensLoggedOut>() {};
        return restoreBackupRequestCreation(filename).toEntity(localVarReturnType);
    }

    /**
     * restore security server configuration from backup
     * &lt;h3&gt;Administrator restores the security server configuration from backup.&lt;/h3&gt; &lt;p&gt;This endpoint can also return metadata in the error response. The metadata array contains the output of a failed backup restore script.&lt;/p&gt;
     * <p><b>200</b> - restore was successful
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>500</b> - internal server error
     * @param filename filename of the backup
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec restoreBackupWithResponseSpec(String filename) throws WebClientResponseException {
        return restoreBackupRequestCreation(filename);
    }
    /**
     * upload new backup for the security server
     * &lt;h3&gt;Uploads new security server backup to the system.&lt;/h3&gt; &lt;p&gt;Note that it is possible to overwrite an existing backup file with the same name. &lt;ul&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true will overwrite the existing backup.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;The warning response has a warning code &lt;code&gt;warning_file_already_exists&lt;/code&gt; and the metadata field contains the name of the existing backup&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param ignoreWarnings If true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail.
     * @param _file The _file parameter
     * @return Backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec uploadBackupRequestCreation(Boolean ignoreWarnings, File _file) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ignore_warnings", ignoreWarnings));

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return apiClient.invokeAPI("/backups/upload", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * upload new backup for the security server
     * &lt;h3&gt;Uploads new security server backup to the system.&lt;/h3&gt; &lt;p&gt;Note that it is possible to overwrite an existing backup file with the same name. &lt;ul&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true will overwrite the existing backup.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;The warning response has a warning code &lt;code&gt;warning_file_already_exists&lt;/code&gt; and the metadata field contains the name of the existing backup&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param ignoreWarnings If true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail.
     * @param _file The _file parameter
     * @return Backup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Backup> uploadBackup(Boolean ignoreWarnings, File _file) throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return uploadBackupRequestCreation(ignoreWarnings, _file).bodyToMono(localVarReturnType);
    }

    /**
     * upload new backup for the security server
     * &lt;h3&gt;Uploads new security server backup to the system.&lt;/h3&gt; &lt;p&gt;Note that it is possible to overwrite an existing backup file with the same name. &lt;ul&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true will overwrite the existing backup.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;The warning response has a warning code &lt;code&gt;warning_file_already_exists&lt;/code&gt; and the metadata field contains the name of the existing backup&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param ignoreWarnings If true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail.
     * @param _file The _file parameter
     * @return ResponseEntity&lt;Backup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Backup>> uploadBackupWithHttpInfo(Boolean ignoreWarnings, File _file) throws WebClientResponseException {
        ParameterizedTypeReference<Backup> localVarReturnType = new ParameterizedTypeReference<Backup>() {};
        return uploadBackupRequestCreation(ignoreWarnings, _file).toEntity(localVarReturnType);
    }

    /**
     * upload new backup for the security server
     * &lt;h3&gt;Uploads new security server backup to the system.&lt;/h3&gt; &lt;p&gt;Note that it is possible to overwrite an existing backup file with the same name. &lt;ul&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; false causes the operation to fail with a warning in response&#39;s ErrorInfo object.&lt;/li&gt; &lt;li&gt;Attempt to upload a new backup file having the same name with an existing backup and with query parameter &lt;code&gt;ignore_warnings&lt;/code&gt; &#x3D; true will overwrite the existing backup.&lt;/li&gt; &lt;/ul&gt; &lt;/p&gt; &lt;p&gt;The warning response has a warning code &lt;code&gt;warning_file_already_exists&lt;/code&gt; and the metadata field contains the name of the existing backup&lt;/p&gt;
     * <p><b>201</b> - item created
     * <p><b>202</b> - item accepted
     * <p><b>400</b> - request was invalid
     * <p><b>401</b> - authentication credentials are missing
     * <p><b>403</b> - request has been refused
     * <p><b>404</b> - resource requested does not exists
     * <p><b>406</b> - request specified an invalid format
     * <p><b>409</b> - an existing item already exists
     * <p><b>500</b> - internal server error
     * @param ignoreWarnings If true, any ignorable warnings are ignored. if false (or missing), any warnings cause request to fail.
     * @param _file The _file parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec uploadBackupWithResponseSpec(Boolean ignoreWarnings, File _file) throws WebClientResponseException {
        return uploadBackupRequestCreation(ignoreWarnings, _file);
    }
}
