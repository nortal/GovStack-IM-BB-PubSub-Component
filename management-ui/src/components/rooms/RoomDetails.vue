<template>
  <router-link to="" @click="goBack">
    <v-icon :color="colors.Purple100" icon="mdi-chevron-left" />
    {{ $t('navigation.backToRooms') }}
  </router-link>
  <titled-view title-key="roomsList.details.title" />

  <table class="xrd-table">
    <tr>
      <th>{{ $t('roomsList.details.roomName') }}</th>
      <th>{{ $t('roomsList.details.roomManager') }}</th>
      <th>{{ $t('roomsList.details.dateCreated') }}</th>
      <th>{{ $t('roomsList.details.createdBy') }}</th>
    </tr>
    <tr>
      <td>{{ selectedRoom.identifier }}</td>
      <td>{{ selectedRoom.managerIdentifier }}</td>
      <td>{{ $filters.formatDateTime(selectedRoom.createdAt) }}</td>
      <td>{{ selectedRoom.createdBy }}</td>
    </tr>
  </table>

  <PageNavigation
    class="align-center mt-9"
    :tabs="navigationTabs"
  ></PageNavigation>
  <router-view />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useRooms } from '@/stores/rooms';
import { useNotifications } from '@/stores/notifications';
import TitledView from '@/components/ui/TitledView.vue';
import PageNavigation, {
  PageNavigationTab,
} from '@/components/layout/PageNavigation.vue';
import { Colors, RouteName } from '@/global';
import { RoomFullResponse } from '@/generated';

export default defineComponent({
  name: 'RoomDetails',
  components: { PageNavigation, TitledView },
  props: {
    roomId: {
      type: String,
      required: true,
    },
    managerId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      colors: Colors,
      selectedRoom: {} as RoomFullResponse,
    };
  },
  computed: {
    ...mapStores(useRooms),
    navigationTabs(): PageNavigationTab[] {
      return [
        {
          key: 'subscription-view-tab-button',
          name: 'subscriptionsList.title',
          to: {
            name: RouteName.Subscriptions,
            params: { roomId: this.roomId, managerId: this.managerId },
            replace: true,
          },
        },

        {
          key: 'publishers-view-tab-button',
          name: 'publishersList.title',
          to: {
            name: RouteName.Publishers,
            params: { roomId: this.roomId, managerId: this.managerId },
            replace: true,
          },
        },

        {
          key: 'event-types-tab-button',
          name: 'eventTypes.title',
          to: {
            name: RouteName.EventTypes,
            params: { roomId: this.roomId, managerId: this.managerId },
            replace: true,
          },
        },

        {
          key: 'event-tab-button',
          name: 'events.title',
          to: {
            name: RouteName.Events,
            params: {
              roomId: this.roomId,
              managerId: this.managerId,
            },
            replace: true,
          },
        },
      ];
    },
  },
  created() {
    this.roomsStore
      .loadRoom(this.managerId, this.roomId)
      .then((resp) => {
        this.selectedRoom = resp.data;
      })
      .catch((error) => {
        this.showError(error);
      });
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    goBack() {
      this.$router.push({ name: RouteName.Rooms });
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
