import type {UserManagerSettings} from "oidc-client-ts";
import {User, UserManager, WebStorageStateStore} from "oidc-client-ts";
import {useAuth} from "@/stores/auth";
import getEnv from "@/util/env";
import {ConfigParamName} from "@/global";

const oidcSettings: UserManagerSettings = {
    authority: getEnv(ConfigParamName.VUE_APP_OIDC_PROVIDER_URL),
    scope: "openid",
    client_id: getEnv(ConfigParamName.VUE_APP_OIDC_CLIENT_ID),
    client_secret: getEnv(ConfigParamName.VUE_APP_OIDC_CLIENT_SECRET),
    redirect_uri: origin + "/oidc-callback",
    response_type: "code",
    loadUserInfo: true,
    post_logout_redirect_uri: origin,
    automaticSilentRenew: true,
    userStore: new WebStorageStateStore({store: window.localStorage}),
};

const userManager = new UserManager(oidcSettings);

export async function getUser(): Promise<User | null> {
    return await userManager.getUser();
}

export async function signInRedirect(): Promise<void> {
    return await userManager.signinRedirect();
}

export async function signInRedirectCallback(): Promise<User> {
    return await userManager.signinRedirectCallback();
}

export async function signOutRedirect(): Promise<void> {
    return await userManager.signoutRedirect();
}

export async function isSessionActive(): Promise<boolean | null> {
    let user = await userManager.getUser();
    return user && !user.expired;
}

userManager.events.addUserLoaded((user) => {
    if (user?.profile) {
        const authStore = useAuth();
        authStore.setUserRoles(user).then((r) => {
        });
    }
});
