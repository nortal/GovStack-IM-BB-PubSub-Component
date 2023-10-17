import { Tab } from '@/ui-types';

export enum RouteName {
  BaseRoute = "base",
  Login = "login",
  Logout = "logout",
  Forbidden = "forbidden",
  Error = "error",
  OidcCallback = "oidc-callback",
  Members = "members",
  Managers = "managers",
  Rooms = "rooms",
  RoomDetails = "rooms-details",
  Subscriptions = "subscriptions",
  Publishers = "publishers",
  EventTypes = "event-types",
  Events = "events",
}

export enum UserRole {
  SecurityOfficer = "xroad-security-officer",
  RegistrationOfficer = "xroad-registration-officer",
  SecurityServerObserver = "xroad-securityserver-observer",
  ServiceAdministrator = "xroad-service-administrator",
  SystemAdministrator = "xroad-system-administrator",
  PubSubManager = "pubsub-manager"
}

export enum ConfigParamName {
    VUE_APP_OIDC_PROVIDER_URL = "VUE_APP_OIDC_PROVIDER_URL",
    VUE_APP_OIDC_CLIENT_SECRET = "VUE_APP_OIDC_CLIENT_SECRET",
    VUE_APP_OIDC_CLIENT_ID = "VUE_APP_OIDC_CLIENT_ID",
    VUE_APP_MANAGEMENT_API_URI = "VUE_APP_MANAGEMENT_API_URI"
}

export const mainTabs: Tab[] = [
  {
    to: { name: RouteName.Rooms },
    key: 'rooms',
    name: 'tabs.rooms',
  },
  {
    to: { name: RouteName.Managers },
    key: 'managers',
    name: 'tabs.managers',
  },
  {
    to: { name: RouteName.Members },
    key: 'members',
    name: 'tabs.members',
  },
];
export enum Colors {
  Blue10 = "#0a065e",
  Purple10 = "#efebfb",
  Purple20 = "#e0d8f8",
  Purple30 = "#d1c4f4",
  Purple70 = "#9376e6",
  Purple100 = "#663cdc",
  Black10 = "#e8e8e8",
  Black30 = "#bcbbbb",
  Black50 = "#908e8e",
  Black70 = "#636161",
  Black100 = "#211e1e",
  White100 = "#ffffff",
  Yellow = "#f2994A",
  WarmGrey10 = "#f4f3f6",
  WarmGrey20 = "#eae8ee",
  WarmGrey30 = "#dedce4",
  WarmGrey50 = "#c9c6d3",
  WarmGrey70 = "#b4afc2",
  WarmGrey100 = "#575169",
  Error = "#ec4040",
  Success100 = "#0cc177",
  Success10 = "#e6f8f1",
  Background = "#e5e5e5",
}
