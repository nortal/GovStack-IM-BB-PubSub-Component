import {
    EventApi,
    EventTypesApi,
    EventTypeVersionsApi,
    ManagersApi,
    MembersApi,
    PublishersApi,
    RoomsApi,
    SubscriptionsApi,
} from '@/generated/api';
import {Configuration} from "@/generated/configuration";
import axios from "axios";
import {getUser} from "@/oidc/oidc";
import getEnv from "@/util/env";
import {ConfigParamName} from "@/global";

async function getToken(): Promise<string> {
    const user = await getUser();
    if (!user) {
        return "";
    } else {
        return user.access_token;
    }
}

const axiosInstance = axios.create({
    baseURL: getEnv(ConfigParamName.VUE_APP_MANAGEMENT_API_URI),
});

axiosInstance.interceptors.request.use(async (config) => {
    const token = await getToken();
    config.headers["Authorization"] = `Bearer ${token}`;
    return config;
});

const managersApi = new ManagersApi(
    new Configuration(),
    undefined,
    axiosInstance,
);

const membersApi = new MembersApi(
    new Configuration(),
    undefined,
    axiosInstance,
);

const roomsApi = new RoomsApi(
    new Configuration(),
    undefined,
    axiosInstance,
);

const publishersApi = new PublishersApi(
  new Configuration(),
  undefined,
  axiosInstance,
);
const eventsApi = new EventApi(
    new Configuration(),
    undefined,
    axiosInstance,
);

const eventTypesApi = new EventTypesApi(
    new Configuration(),
    undefined,
    axiosInstance
)

const eventTypesVersionApi = new EventTypeVersionsApi(
  new Configuration(),
  undefined,
  axiosInstance,
);

const subscriptionsApi = new SubscriptionsApi(
    new Configuration(),
    undefined,
    axiosInstance,
);

export {
  managersApi,
  membersApi,
  roomsApi,
  eventsApi,
  publishersApi,
  eventTypesApi,
  eventTypesVersionApi,
  subscriptionsApi,
};
