/*
 * X-Road Security Server Admin API
 * X-Road Security Server Admin API. Note that the error metadata responses described in some endpoints are subjects to change and may be updated in upcoming versions.
 *
 * The version of the OpenAPI document: 2.1.0
 * Contact: info@niis.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.govstack.information_mediator.pubsub.management.xroad_admin.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.govstack.information_mediator.pubsub.management.xroad_admin.client.model.KeyUsage;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * certificate details for any kind of certificate (TLS, auth, sign)
 */
@JsonPropertyOrder({
  CertificateDetails.JSON_PROPERTY_ISSUER_DISTINGUISHED_NAME,
  CertificateDetails.JSON_PROPERTY_ISSUER_COMMON_NAME,
  CertificateDetails.JSON_PROPERTY_SUBJECT_DISTINGUISHED_NAME,
  CertificateDetails.JSON_PROPERTY_SUBJECT_COMMON_NAME,
  CertificateDetails.JSON_PROPERTY_NOT_BEFORE,
  CertificateDetails.JSON_PROPERTY_NOT_AFTER,
  CertificateDetails.JSON_PROPERTY_SERIAL,
  CertificateDetails.JSON_PROPERTY_VERSION,
  CertificateDetails.JSON_PROPERTY_SIGNATURE_ALGORITHM,
  CertificateDetails.JSON_PROPERTY_SIGNATURE,
  CertificateDetails.JSON_PROPERTY_PUBLIC_KEY_ALGORITHM,
  CertificateDetails.JSON_PROPERTY_RSA_PUBLIC_KEY_MODULUS,
  CertificateDetails.JSON_PROPERTY_RSA_PUBLIC_KEY_EXPONENT,
  CertificateDetails.JSON_PROPERTY_HASH,
  CertificateDetails.JSON_PROPERTY_KEY_USAGES,
  CertificateDetails.JSON_PROPERTY_SUBJECT_ALTERNATIVE_NAMES
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class CertificateDetails {
  public static final String JSON_PROPERTY_ISSUER_DISTINGUISHED_NAME = "issuer_distinguished_name";
  private String issuerDistinguishedName;

  public static final String JSON_PROPERTY_ISSUER_COMMON_NAME = "issuer_common_name";
  private String issuerCommonName;

  public static final String JSON_PROPERTY_SUBJECT_DISTINGUISHED_NAME = "subject_distinguished_name";
  private String subjectDistinguishedName;

  public static final String JSON_PROPERTY_SUBJECT_COMMON_NAME = "subject_common_name";
  private String subjectCommonName;

  public static final String JSON_PROPERTY_NOT_BEFORE = "not_before";
  private OffsetDateTime notBefore;

  public static final String JSON_PROPERTY_NOT_AFTER = "not_after";
  private OffsetDateTime notAfter;

  public static final String JSON_PROPERTY_SERIAL = "serial";
  private String serial;

  public static final String JSON_PROPERTY_VERSION = "version";
  private Integer version;

  public static final String JSON_PROPERTY_SIGNATURE_ALGORITHM = "signature_algorithm";
  private String signatureAlgorithm;

  public static final String JSON_PROPERTY_SIGNATURE = "signature";
  private String signature;

  public static final String JSON_PROPERTY_PUBLIC_KEY_ALGORITHM = "public_key_algorithm";
  private String publicKeyAlgorithm;

  public static final String JSON_PROPERTY_RSA_PUBLIC_KEY_MODULUS = "rsa_public_key_modulus";
  private String rsaPublicKeyModulus;

  public static final String JSON_PROPERTY_RSA_PUBLIC_KEY_EXPONENT = "rsa_public_key_exponent";
  private Integer rsaPublicKeyExponent;

  public static final String JSON_PROPERTY_HASH = "hash";
  private String hash;

  public static final String JSON_PROPERTY_KEY_USAGES = "key_usages";
  private Set<KeyUsage> keyUsages = new LinkedHashSet<>();

  public static final String JSON_PROPERTY_SUBJECT_ALTERNATIVE_NAMES = "subject_alternative_names";
  private String subjectAlternativeNames;

  public CertificateDetails() {
  }

  public CertificateDetails issuerDistinguishedName(String issuerDistinguishedName) {
    
    this.issuerDistinguishedName = issuerDistinguishedName;
    return this;
  }

   /**
   * certificate issuer distinguished name
   * @return issuerDistinguishedName
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ISSUER_DISTINGUISHED_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getIssuerDistinguishedName() {
    return issuerDistinguishedName;
  }


  @JsonProperty(JSON_PROPERTY_ISSUER_DISTINGUISHED_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIssuerDistinguishedName(String issuerDistinguishedName) {
    this.issuerDistinguishedName = issuerDistinguishedName;
  }


  public CertificateDetails issuerCommonName(String issuerCommonName) {
    
    this.issuerCommonName = issuerCommonName;
    return this;
  }

   /**
   * certificate issuer common name
   * @return issuerCommonName
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ISSUER_COMMON_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getIssuerCommonName() {
    return issuerCommonName;
  }


  @JsonProperty(JSON_PROPERTY_ISSUER_COMMON_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIssuerCommonName(String issuerCommonName) {
    this.issuerCommonName = issuerCommonName;
  }


  public CertificateDetails subjectDistinguishedName(String subjectDistinguishedName) {
    
    this.subjectDistinguishedName = subjectDistinguishedName;
    return this;
  }

   /**
   * certificate subject distinguished name
   * @return subjectDistinguishedName
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SUBJECT_DISTINGUISHED_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSubjectDistinguishedName() {
    return subjectDistinguishedName;
  }


  @JsonProperty(JSON_PROPERTY_SUBJECT_DISTINGUISHED_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSubjectDistinguishedName(String subjectDistinguishedName) {
    this.subjectDistinguishedName = subjectDistinguishedName;
  }


  public CertificateDetails subjectCommonName(String subjectCommonName) {
    
    this.subjectCommonName = subjectCommonName;
    return this;
  }

   /**
   * certificate subject common name
   * @return subjectCommonName
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SUBJECT_COMMON_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSubjectCommonName() {
    return subjectCommonName;
  }


  @JsonProperty(JSON_PROPERTY_SUBJECT_COMMON_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSubjectCommonName(String subjectCommonName) {
    this.subjectCommonName = subjectCommonName;
  }


  public CertificateDetails notBefore(OffsetDateTime notBefore) {
    
    this.notBefore = notBefore;
    return this;
  }

   /**
   * certificate validity not before
   * @return notBefore
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NOT_BEFORE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getNotBefore() {
    return notBefore;
  }


  @JsonProperty(JSON_PROPERTY_NOT_BEFORE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNotBefore(OffsetDateTime notBefore) {
    this.notBefore = notBefore;
  }


  public CertificateDetails notAfter(OffsetDateTime notAfter) {
    
    this.notAfter = notAfter;
    return this;
  }

   /**
   * certificate validity not after
   * @return notAfter
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NOT_AFTER)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getNotAfter() {
    return notAfter;
  }


  @JsonProperty(JSON_PROPERTY_NOT_AFTER)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNotAfter(OffsetDateTime notAfter) {
    this.notAfter = notAfter;
  }


  public CertificateDetails serial(String serial) {
    
    this.serial = serial;
    return this;
  }

   /**
   * serial number
   * @return serial
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SERIAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSerial() {
    return serial;
  }


  @JsonProperty(JSON_PROPERTY_SERIAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSerial(String serial) {
    this.serial = serial;
  }


  public CertificateDetails version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * version
   * @return version
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getVersion() {
    return version;
  }


  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(Integer version) {
    this.version = version;
  }


  public CertificateDetails signatureAlgorithm(String signatureAlgorithm) {
    
    this.signatureAlgorithm = signatureAlgorithm;
    return this;
  }

   /**
   * certificate signature algorithm
   * @return signatureAlgorithm
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SIGNATURE_ALGORITHM)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSignatureAlgorithm() {
    return signatureAlgorithm;
  }


  @JsonProperty(JSON_PROPERTY_SIGNATURE_ALGORITHM)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSignatureAlgorithm(String signatureAlgorithm) {
    this.signatureAlgorithm = signatureAlgorithm;
  }


  public CertificateDetails signature(String signature) {
    
    this.signature = signature;
    return this;
  }

   /**
   * hex encoded certificate signature
   * @return signature
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SIGNATURE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSignature() {
    return signature;
  }


  @JsonProperty(JSON_PROPERTY_SIGNATURE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSignature(String signature) {
    this.signature = signature;
  }


  public CertificateDetails publicKeyAlgorithm(String publicKeyAlgorithm) {
    
    this.publicKeyAlgorithm = publicKeyAlgorithm;
    return this;
  }

   /**
   * certificate public key algorithm
   * @return publicKeyAlgorithm
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PUBLIC_KEY_ALGORITHM)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getPublicKeyAlgorithm() {
    return publicKeyAlgorithm;
  }


  @JsonProperty(JSON_PROPERTY_PUBLIC_KEY_ALGORITHM)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
    this.publicKeyAlgorithm = publicKeyAlgorithm;
  }


  public CertificateDetails rsaPublicKeyModulus(String rsaPublicKeyModulus) {
    
    this.rsaPublicKeyModulus = rsaPublicKeyModulus;
    return this;
  }

   /**
   * hex encoded RSA public key modulus (if RSA key)
   * @return rsaPublicKeyModulus
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RSA_PUBLIC_KEY_MODULUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getRsaPublicKeyModulus() {
    return rsaPublicKeyModulus;
  }


  @JsonProperty(JSON_PROPERTY_RSA_PUBLIC_KEY_MODULUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRsaPublicKeyModulus(String rsaPublicKeyModulus) {
    this.rsaPublicKeyModulus = rsaPublicKeyModulus;
  }


  public CertificateDetails rsaPublicKeyExponent(Integer rsaPublicKeyExponent) {
    
    this.rsaPublicKeyExponent = rsaPublicKeyExponent;
    return this;
  }

   /**
   * RSA public key exponent (if RSA key) as an integer
   * @return rsaPublicKeyExponent
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RSA_PUBLIC_KEY_EXPONENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getRsaPublicKeyExponent() {
    return rsaPublicKeyExponent;
  }


  @JsonProperty(JSON_PROPERTY_RSA_PUBLIC_KEY_EXPONENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRsaPublicKeyExponent(Integer rsaPublicKeyExponent) {
    this.rsaPublicKeyExponent = rsaPublicKeyExponent;
  }


  public CertificateDetails hash(String hash) {
    
    this.hash = hash;
    return this;
  }

   /**
   * certificate SHA-1 hash
   * @return hash
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_HASH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getHash() {
    return hash;
  }


  @JsonProperty(JSON_PROPERTY_HASH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setHash(String hash) {
    this.hash = hash;
  }


  public CertificateDetails keyUsages(Set<KeyUsage> keyUsages) {
    
    this.keyUsages = keyUsages;
    return this;
  }

  public CertificateDetails addKeyUsagesItem(KeyUsage keyUsagesItem) {
    if (this.keyUsages == null) {
      this.keyUsages = new LinkedHashSet<>();
    }
    this.keyUsages.add(keyUsagesItem);
    return this;
  }

   /**
   * certificate key usage array
   * @return keyUsages
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_KEY_USAGES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Set<KeyUsage> getKeyUsages() {
    return keyUsages;
  }


  @JsonDeserialize(as = LinkedHashSet.class)
  @JsonProperty(JSON_PROPERTY_KEY_USAGES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKeyUsages(Set<KeyUsage> keyUsages) {
    this.keyUsages = keyUsages;
  }


  public CertificateDetails subjectAlternativeNames(String subjectAlternativeNames) {
    
    this.subjectAlternativeNames = subjectAlternativeNames;
    return this;
  }

   /**
   * certificate subject alternative names
   * @return subjectAlternativeNames
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SUBJECT_ALTERNATIVE_NAMES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSubjectAlternativeNames() {
    return subjectAlternativeNames;
  }


  @JsonProperty(JSON_PROPERTY_SUBJECT_ALTERNATIVE_NAMES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSubjectAlternativeNames(String subjectAlternativeNames) {
    this.subjectAlternativeNames = subjectAlternativeNames;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CertificateDetails certificateDetails = (CertificateDetails) o;
    return Objects.equals(this.issuerDistinguishedName, certificateDetails.issuerDistinguishedName) &&
        Objects.equals(this.issuerCommonName, certificateDetails.issuerCommonName) &&
        Objects.equals(this.subjectDistinguishedName, certificateDetails.subjectDistinguishedName) &&
        Objects.equals(this.subjectCommonName, certificateDetails.subjectCommonName) &&
        Objects.equals(this.notBefore, certificateDetails.notBefore) &&
        Objects.equals(this.notAfter, certificateDetails.notAfter) &&
        Objects.equals(this.serial, certificateDetails.serial) &&
        Objects.equals(this.version, certificateDetails.version) &&
        Objects.equals(this.signatureAlgorithm, certificateDetails.signatureAlgorithm) &&
        Objects.equals(this.signature, certificateDetails.signature) &&
        Objects.equals(this.publicKeyAlgorithm, certificateDetails.publicKeyAlgorithm) &&
        Objects.equals(this.rsaPublicKeyModulus, certificateDetails.rsaPublicKeyModulus) &&
        Objects.equals(this.rsaPublicKeyExponent, certificateDetails.rsaPublicKeyExponent) &&
        Objects.equals(this.hash, certificateDetails.hash) &&
        Objects.equals(this.keyUsages, certificateDetails.keyUsages) &&
        Objects.equals(this.subjectAlternativeNames, certificateDetails.subjectAlternativeNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issuerDistinguishedName, issuerCommonName, subjectDistinguishedName, subjectCommonName, notBefore, notAfter, serial, version, signatureAlgorithm, signature, publicKeyAlgorithm, rsaPublicKeyModulus, rsaPublicKeyExponent, hash, keyUsages, subjectAlternativeNames);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CertificateDetails {\n");
    sb.append("    issuerDistinguishedName: ").append(toIndentedString(issuerDistinguishedName)).append("\n");
    sb.append("    issuerCommonName: ").append(toIndentedString(issuerCommonName)).append("\n");
    sb.append("    subjectDistinguishedName: ").append(toIndentedString(subjectDistinguishedName)).append("\n");
    sb.append("    subjectCommonName: ").append(toIndentedString(subjectCommonName)).append("\n");
    sb.append("    notBefore: ").append(toIndentedString(notBefore)).append("\n");
    sb.append("    notAfter: ").append(toIndentedString(notAfter)).append("\n");
    sb.append("    serial: ").append(toIndentedString(serial)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    signatureAlgorithm: ").append(toIndentedString(signatureAlgorithm)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    publicKeyAlgorithm: ").append(toIndentedString(publicKeyAlgorithm)).append("\n");
    sb.append("    rsaPublicKeyModulus: ").append(toIndentedString(rsaPublicKeyModulus)).append("\n");
    sb.append("    rsaPublicKeyExponent: ").append(toIndentedString(rsaPublicKeyExponent)).append("\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    keyUsages: ").append(toIndentedString(keyUsages)).append("\n");
    sb.append("    subjectAlternativeNames: ").append(toIndentedString(subjectAlternativeNames)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

