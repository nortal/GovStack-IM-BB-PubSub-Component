<template>
  <titled-view title-key="roomsList.title" />
  <v-row>
    <v-select
      v-if="hasAdministratorPermission()"
      class="manager-filter"
      v-model="selectedManager"
      :items="managersToSelect"
      :label="$t('roomsList.filterByManager')"
      variant="plain"
      @update:modelValue="filterByManager"
      clearable
    />

    <xrd-button
      v-if="hasAdministratorPermission()"
      class="xrd-button"
      @click="showCreateRoomDialog = true"
    >
      <v-icon class="xrd-large-button-icon">icon-Add</v-icon>
      {{ $t('roomsList.actions.createRoom') }}
    </xrd-button>
  </v-row>
  <v-data-table-server
    :loading="loading"
    :headers="headers"
    :items="roomsStore.rooms"
    :items-per-page="10"
    :items-length="roomsStore.pagingOptions.total_items"
    :items-per-page-options="itemsPerPageOptions"
    class="xrd-table elevation-0 rounded"
    item-key="identifier"
    density="compact"
    @update:options="changeOptions"
  >
    <template #[`item.identifier`]="{ item }">
      <div class="xrd-clickable" @click="navigateToRoomDetails(item.raw)">
        {{ item.columns.identifier }}
      </div>
    </template>

    <template #[`item.button`]="{ item }">
      <xrd-button
        class="xrd-clickable"
        text
        @click="navigateToRoomDetails(item.raw)"
      >
        {{ $t('roomsList.actions.viewDetails') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openEditRoomDialog(item.raw)"
      >
        {{ $t('roomsList.actions.edit') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openTerminateConfirmationDialog(item.raw)"
      >
        {{ $t('roomsList.actions.terminate') }}
      </xrd-button>
    </template>
  </v-data-table-server>

  <!--   Create Room action -->
  <add-room-dialog
    v-if="showCreateRoomDialog"
    @cancel="showCreateRoomDialog = false"
    @save="hideCreateRoomDialogAndRefetch"
  />

  <!--   Edit Room action -->
  <edit-room-dialog
    v-if="showEditRoomDialog"
    :room-id="selectedRoom.identifier"
    :manager-id="selectedRoom.managerIdentifier"
    @cancel="showEditRoomDialog = false"
    @save="hideEditRoomDialogAndRefetch"
  />

  <!--  Terminate room action -->
  <xrd-confirm-dialog
    v-if="showTerminateRoomDialog"
    title="roomsList.actions.terminateAction.terminateTitle"
    text="roomsList.actions.terminateAction.terminateConfirm"
    :data="{ name: selectedRoom.identifier }"
    :loading="terminatingRoom"
    @cancel="showTerminateRoomDialog = false"
    @accept="terminateRoom()"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { mapActions, mapState, mapStores } from 'pinia';
import { useRooms } from '@/stores/rooms';
import { useNotifications } from '@/stores/notifications';
import { RouteName } from '@/global';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import XrdButton from '@/components/XrdButton.vue';
import AddRoomDialog from '@/components/rooms/AddRoomDialog.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import EditRoomDialog from '@/components/rooms/EditRoomDialog.vue';
import TitledView from '@/components/ui/TitledView.vue';
import { defaultItemsPerPageOptions } from '@/util/defaults';
import { useManagers } from '@/stores/managers';
import { useAuth } from '@/stores/auth';
import { RoomResponse } from '@/generated';

export default defineComponent({
  name: 'RoomsList',
  components: {
    EditRoomDialog,
    XrdConfirmDialog,
    AddRoomDialog,
    XrdButton,
    VDataTableServer,
    TitledView,
  },
  data() {
    const options = defaultItemsPerPageOptions();
    return {
      loading: true,
      showCreateRoomDialog: false,
      showEditRoomDialog: false,
      showTerminateRoomDialog: false,
      selectedRoom: {} as RoomResponse,
      terminatingRoom: false,
      managersToSelect: [] as string[],
      selectedManager: undefined as undefined | string,
      itemsPerPageOptions: options,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
    };
  },
  computed: {
    ...mapStores(useRooms, useManagers),
    ...mapState(useAuth, ['username']),
    rooms() {
      return this.roomsStore.rooms;
    },
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('roomsList.header.name'),
          align: 'start',
          key: 'identifier',
        },
        {
          title: this.$t('roomsList.header.managerName'),
          align: 'start',
          key: 'managerIdentifier',
        },
        {
          title: this.$t('roomsList.actions.actions'),
          align: 'center',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  created() {
    this.roomsStore.managerIdToFilter = null;
    if (this.hasAdministratorPermission()) {
      this.managersStore
        .loadAllManagers()
        .then((resp) => {
          this.managersToSelect = resp.data.flatMap(
            (manager) => manager.identifier,
          );
        })
        .catch((error) => {
          throw error;
        });
    } else {
      this.roomsStore.managerIdToFilter = this.username as string;
    }
    this.loading = false;
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    ...mapActions(useAuth, ['isAdministrator']),
    fetchRooms: async function () {
      this.loading = true;

      try {
        await this.roomsStore.fetchRooms(this.pagingOptions);
      } catch (error: unknown) {
        this.showError(error);
      } finally {
        this.loading = false;
      }
    },
    changeOptions: async function ({ itemsPerPage, page, sortBy }) {
      this.pagingOptions.itemsPerPage = itemsPerPage;
      this.pagingOptions.page = page;
      this.pagingOptions.sortBy = sortBy[0]?.key;
      this.pagingOptions.sortOrder = sortBy[0]?.order;
      this.pagingOptions.search = '';
      await this.fetchRooms();
    },
    filterByManager: async function () {
      if (!this.selectedManager) {
        this.roomsStore.managerIdToFilter = null;
      } else {
        this.roomsStore.managerIdToFilter = this.selectedManager;
      }
      await this.fetchRooms();
    },
    hasAdministratorPermission() {
      return this.isAdministrator();
    },
    hideCreateRoomDialogAndRefetch() {
      this.showCreateRoomDialog = false;
      this.fetchRooms();
    },
    hideEditRoomDialogAndRefetch() {
      this.showEditRoomDialog = false;
      this.selectedRoom = {} as RoomResponse;
      this.fetchRooms();
    },
    openTerminateConfirmationDialog(room: RoomResponse): void {
      this.selectedRoom = room;
      this.showTerminateRoomDialog = true;
    },
    openEditRoomDialog(room: RoomResponse): void {
      this.selectedRoom = room;
      this.showEditRoomDialog = true;
    },
    navigateToRoomDetails(room: RoomResponse) {
      this.$router.push({
        name: RouteName.RoomDetails,
        params: {
          managerId: room.managerIdentifier,
          roomId: room.identifier,
        },
      });
    },
    terminateRoom(): void {
      if (!this.selectedRoom) return;

      this.terminatingRoom = true;
      this.roomsStore
        .terminateRoom(
          this.selectedRoom.managerIdentifier,
          this.selectedRoom.identifier,
        )
        .then(() => {
          this.showSuccess(
            this.$t('roomsList.actions.terminateAction.success', {
              room: this.selectedRoom.identifier,
            }),
          );
          this.showTerminateRoomDialog = false;
          this.terminatingRoom = false;
          this.selectedRoom = {} as RoomResponse;
          this.fetchRooms();
        })
        .catch((error) => {
          this.showError(error);
        });
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';

.manager-filter {
  max-width: 300px;
}
</style>
