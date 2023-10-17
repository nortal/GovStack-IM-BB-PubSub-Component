// Template of management-ui/src/config.js file.
// File is required when running management-ui development server in local machine
// Otherwise OAuth2 login will not function in localhost.

// File should never be committed to git repository - is in .gitignore.
// In production build/environment, this file is overwritten by
// management-ui/deployment/entrypoint.sh

window.configs = {
    "VUE_APP_OIDC_PROVIDER_URL": "http://iam.im.sandbox-playground.com:8089/realms/pubsub-realm",
    "VUE_APP_OIDC_CLIENT_ID": "pubsub-management-ui-dev",
    "VUE_APP_OIDC_CLIENT_SECRET": "pubsub-management-ui-dev-secret",
    "VUE_APP_MANAGEMENT_API_URI": "http://localhost:8080",
}
