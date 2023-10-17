<template>
  <v-row>
    <titled-view title-key="eventTypesList.title" />
    <xrd-button class="xrd-button" @click="showAddEventTypeDialog = true">
      <v-icon class="xrd-large-button-icon">icon-Add</v-icon>
      {{ $t('eventTypesList.actions.add') }}
    </xrd-button>
  </v-row>
  <v-data-table-server
    :loading="loading"
    :headers="headers"
    :items="eventTypesStore.eventTypes"
    :items-per-page="10"
    :items-length="eventTypesStore.pagingOptions.total_items"
    :items-per-page-options="itemsPerPageOptions"
    class="xrd-table elevation-0 rounded"
    item-key="identifier"
    density="compact"
    @update:options="changeOptions"
  >
    <template #[`item.createdAt`]="{ item }">
      <div>
        {{ $filters.formatDateTime(item.columns.createdAt) }}
      </div>
    </template>
    <template #[`item.button`]="{ item }">
      <xrd-button
        class="xrd-clickable"
        text
        @click="openUploadEventTypeVersionDialog(item.raw)"
      >
        {{ $t('eventTypesList.actions.upload') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openViewEventTypeDialog(item.raw)"
      >
        {{ $t('eventTypesList.actions.view') }}
      </xrd-button>
      <xrd-button
        class="xrd-clickable"
        text
        @click="openTerminateConfirmationDialog(item.raw)"
      >
        {{ $t('eventTypesList.actions.terminate') }}
      </xrd-button>
    </template>
  </v-data-table-server>
  <router-view />

  <!--  Create event type action-->
  <add-event-type-dialog
    v-if="showAddEventTypeDialog"
    :room-id="roomId"
    :manager-id="managerId"
    @cancel="showAddEventTypeDialog = false"
    @save="hideAddEventTypeDialogAndRefetch"
  />

  <!--   View event type action-->
  <view-event-type-dialog
    v-if="showViewEventTypeDialog"
    :event-type="selectedEventType.identifier"
    :manager-id="managerId"
    :room-id="roomId"
    @cancel="hideViewEventTypeDialogAndRefetch"
  />

  <!--   View event type action-->
  <upload-event-type-version-dialog
    v-if="showUploadEventTypeVersionDialog"
    :event-type="selectedEventType"
    :manager-id="managerId"
    :room-id="roomId"
    @cancel="showUploadEventTypeVersionDialog = false"
    @save="hideUploadEventTypeVersionDialogAndRefetch"
  />

  <!--  Terminate event type action -->
  <xrd-confirm-dialog
    v-if="showTerminateEventTypeDialog"
    title="eventTypesList.actions.terminateAction.terminateTitle"
    text="eventTypesList.actions.terminateAction.terminateConfirm"
    :data="{ eventType: selectedEventType.identifier }"
    :loading="terminatingEventType"
    @cancel="showTerminateEventTypeDialog = false"
    @accept="terminateEventType()"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useRooms } from '@/stores/rooms';
import { useNotifications } from '@/stores/notifications';
import { EventTypeView } from '@/generated';
import { useEventTypes } from '@/stores/event-types';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { defaultItemsPerPageOptions } from '@/util/defaults';
import TitledView from '@/components/ui/TitledView.vue';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import AddEventTypeDialog from '@/components/eventTypes/AddEventTypeDialog.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import XrdButton from '@/components/XrdButton.vue';
import ViewEventTypeDialog from '@/components/eventTypes/ViewEventTypeDialog.vue';
import UploadEventTypeVersionDialog from '@/components/eventTypes/UploadEventTypeVersionDialog.vue';

export default defineComponent({
  name: 'EventTypes',
  components: {
    UploadEventTypeVersionDialog,
    ViewEventTypeDialog,
    XrdButton,
    XrdConfirmDialog,
    AddEventTypeDialog,
    TitledView,
    VDataTableServer,
  },
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
    const options = defaultItemsPerPageOptions();
    return {
      loading: true,
      itemsPerPageOptions: options,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
      selectedEventType: {} as EventTypeView,
      showAddEventTypeDialog: false,
      showViewEventTypeDialog: false,
      showUploadEventTypeVersionDialog: false,
      showTerminateEventTypeDialog: false,
      terminatingEventType: false,
    };
  },
  computed: {
    ...mapStores(useRooms, useEventTypes),

    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('eventTypesList.header.identifier'),
          align: 'start',
          key: 'identifier',
        },
        {
          title: this.$t('eventTypesList.header.versions'),
          align: 'center',
          key: 'versions',
        },
        {
          title: this.$t('eventTypesList.header.createdBy'),
          align: 'center',
          key: 'createdBy',
        },
        {
          title: this.$t('eventTypesList.header.createdAt'),
          align: 'center',
          key: 'createdAt',
        },
        {
          title: this.$t('eventTypesList.header.actions'),
          align: 'center',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  created() {
    this.loading = false;
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    fetchEventTypes: async function () {
      this.loading = true;

      try {
        await this.eventTypesStore.fetchEventTypes(
          this.managerId,
          this.roomId,
          this.pagingOptions,
        );
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
      await this.fetchEventTypes();
    },
    hideAddEventTypeDialogAndRefetch() {
      this.showAddEventTypeDialog = false;
      this.fetchEventTypes();
    },
    hideUploadEventTypeVersionDialogAndRefetch() {
      this.showUploadEventTypeVersionDialog = false;
      this.fetchEventTypes();
    },
    hideViewEventTypeDialogAndRefetch() {
      this.showViewEventTypeDialog = false;
      this.fetchEventTypes();
    },
    openUploadEventTypeVersionDialog(eventType: EventTypeView) {
      this.selectedEventType = eventType;
      this.showUploadEventTypeVersionDialog = true;
    },
    openViewEventTypeDialog(eventType: EventTypeView) {
      this.selectedEventType = eventType;
      this.showViewEventTypeDialog = true;
    },
    openTerminateConfirmationDialog(eventType: EventTypeView) {
      this.selectedEventType = eventType;
      this.showTerminateEventTypeDialog = true;
    },
    terminateEventType() {
      if (!this.selectedEventType) return;

      this.terminatingEventType = true;
      this.eventTypesStore
        .terminateEventType(
          this.managerId,
          this.roomId,
          this.selectedEventType.identifier,
        )
        .then(() => {
          this.showSuccess(
            this.$t('eventTypesList.actions.terminateAction.success', {
              eventType: this.selectedEventType.identifier,
            }),
          );
          this.showTerminateEventTypeDialog = false;
          this.terminatingEventType = false;
          this.selectedEventType = {} as EventTypeView;
          this.fetchEventTypes();
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
</style>
