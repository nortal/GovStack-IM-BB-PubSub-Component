import { RouteName, UserRole } from '@/global';
import LoginView from '@/views/LoginView.vue';
import ForbiddenView from '@/views/ForbiddenView.vue';
import ErrorView from '@/views/ErrorView.vue';
import type { RouteRecordRaw } from 'vue-router';
import OidcCallbackView from '@/views/OidcCallbackView.vue';
import LogoutView from '@/views/LogoutView.vue';
import MembersListView from '@/components/members/MembersList.vue';
import ManagersListView from '@/components/managers/ManagersList.vue';
import RoomsListView from '@/components/rooms/RoomsList.vue';
import RoomDetailsListView from '@/components/rooms/RoomDetails.vue';
import MembersView from '@/views/Members/MembersView.vue';
import ManagersView from '@/views/Managers/ManagersView.vue';
import RoomsView from '@/views/Rooms/RoomsView.vue';
import RoomDetailsView from '@/views/RoomDetails/RoomDetailsView.vue';
import TabsBase from '@/components/layout/TabsBase.vue';
import AppBase from '@/views/AppBase.vue';
import AlertsContainer from '@/components/ui/AlertsContainer.vue';
import PublishersList from '@/components/publishers/PublishersList.vue';
import SubscriptionsList from '@/components/subscriptions/SubscriptionsList.vue';
import EventTypesList from '@/components/eventTypes/EventTypesList.vue';
import EventsList from '@/components/events/EventsList.vue';
import { useRoute } from 'vue-router';
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: AppBase,
    name: RouteName.BaseRoute,
    redirect: '/rooms',
    children: [
      {
        path: '',
        components: {
          default: MembersView,
          top: TabsBase,
          alerts: AlertsContainer,
        },
        children: [
          {
            path: '/members',
            name: RouteName.Members,
            component: MembersListView,
            meta: {
              roles: Object.values(UserRole),
            },
          },
        ],
      },
      {
        path: '',
        components: {
          default: RoomsView,
          top: TabsBase,
          alerts: AlertsContainer,
        },
        props: true,
        meta: {
          roles: Object.values(UserRole),
        },
        children: [
          {
            path: '/rooms',
            name: RouteName.Rooms,
            component: RoomsListView,
            props: true,
            meta: {
              roles: Object.values(UserRole),
            },
          },
          {
            path: '',
            components: {
              default: RoomDetailsView,
              top: TabsBase,
              alerts: AlertsContainer,
            },
            props: true,
            meta: {
              roles: Object.values(UserRole),
            },
            children: [
              {
                path: '/rooms/:managerId/:roomId/details',
                name: RouteName.RoomDetails,
                component: RoomDetailsListView,
                props(): { managerId: string; roomId: string } {
                  const route = useRoute();
                  return {
                    managerId: route.params.managerId as string,
                    roomId: route.params.roomId as string,
                  };
                },
                meta: {
                  roles: Object.values(UserRole),
                },
                children: [
                  {
                    name: RouteName.Subscriptions,
                    path: 'subscriptions',
                    component: SubscriptionsList,
                    props: true,
                    meta: {
                      roles: Object.values(UserRole),
                    },
                  },
                  {
                    name: RouteName.Publishers,
                    path: 'publishers',
                    component: PublishersList,
                    props: true,
                    meta: {
                      roles: Object.values(UserRole),
                    },
                  },
                  {
                    name: RouteName.EventTypes,
                    path: 'event-types',
                    component: EventTypesList,
                    props: true,
                    meta: {
                      roles: Object.values(UserRole),
                    },
                  },
                  {
                    name: RouteName.Events,
                    path: 'events',
                    component: EventsList,
                    props: true,
                    meta: {
                      roles: Object.values(UserRole),
                    },
                  },
                ],
              },
            ],
          },
        ],
      },
      {
        path: '',
        components: {
          default: ManagersView,
          top: TabsBase,
          alerts: AlertsContainer,
        },
        children: [
          {
            path: '/managers',
            name: RouteName.Managers,
            component: ManagersListView,
            meta: {
              roles: Object.values(UserRole),
            },
          },
        ],
      },
    ],
  },
  {
    path: '/login',
    name: RouteName.Login,
    components: {
      default: LoginView,
    },
  },
  {
    path: '/logout',
    name: RouteName.Logout,
    components: {
      default: LogoutView,
    },
    meta: {
      roles: Object.values(UserRole),
    },
  },
  {
    path: '/oidc-callback',
    name: RouteName.OidcCallback,
    components: {
      default: OidcCallbackView,
    },
  },
  {
    path: '/forbidden',
    name: RouteName.Forbidden,
    components: {
      default: ForbiddenView,
    },
  },
  {
    path: '/:pathMatch(.*)*',
    name: RouteName.Error,
    components: {
      default: ErrorView,
    },
  },
];
export default routes;
