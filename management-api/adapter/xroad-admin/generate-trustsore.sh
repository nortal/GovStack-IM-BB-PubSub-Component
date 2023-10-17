#!/bin/bash

declare -a urls=(
  "ss1.im.sandbox-playground.com:4000"
  "ss2.im.sandbox-playground.com:4000"
  "ss3.im.sandbox-playground.com:4000"
  "niis-ss.ss04.im.sandbox-playground.com:4000"
)

# Truststore parameters
truststore="src/main/resources/truststore.jks"
truststore_password="changeit"

# Loop through each URL to download the certificate and import it into the truststore
for url in "${urls[@]}"; do
  # Extract domain and port
  domain=$(echo $url | cut -d ':' -f1)
  port=$(echo $url | cut -d ':' -f2)

  echo "Downloading certificate for $domain:$port..."
  openssl s_client -connect $domain:$port </dev/null 2>/dev/null | openssl x509 >"$domain.crt"
  echo "Importing certificate into truststore..."
  keytool -import -trustcacerts -keystore $truststore -storepass $truststore_password -noprompt -alias $domain -file "$domain.crt"
  rm "$domain.crt"

  echo "Certificate for $domain imported into $truststore."
done

echo "All certificates imported into $truststore."
