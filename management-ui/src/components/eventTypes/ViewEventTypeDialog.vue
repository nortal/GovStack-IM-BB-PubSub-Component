<template>
  <xrd-simple-dialog
    class="xdr-dialog-simple"
    cancel-button-text="Close"
    title="eventTypesList.actions.viewVersions.eventTypeVersionTitle"
    :data="{ eventType: eventType }"
    width="1000"
    @cancel="cancel"
    hide-save-button
  >
    <template #content>
      <v-data-table-virtual
        :headers="headers"
        :items="eventTypeVersions"
        class="xrd-table elevation-0 rounded"
        item-key="eventTypeId"
        density="compact"
      >
        <template #[`item.createdAt`]="{ item }">
          <div>
            {{ $filters.formatDateTime(item.columns.createdAt) }}
          </div>
        </template>
        <template #[`item.downloadButton`]="{ item }">
          <xrd-icon-base class="download-icon">
            <xrd-icon-download />
          </xrd-icon-base>
          <xrd-button
            class="xrd-clickable"
            text
            @click="downloadSchema(item.raw.jsonSchema)"
          >
            {{ $t('eventTypesList.actions.download') }}
          </xrd-button>
        </template>
        <template #[`item.button`]="{ item }">
          <xrd-button
            class="xrd-clickable"
            text
            @click="openEditEventTypeVersionDialog(item.raw)"
          >
            {{ $t('eventTypesList.actions.edit') }}
          </xrd-button>
          <xrd-button
            class="xrd-clickable"
            text
            @click="openTerminateEventTypeVersionDialog(item.raw)"
          >
            {{ $t('eventTypesList.actions.terminate') }}
          </xrd-button>
        </template>
      </v-data-table-virtual>
    </template>
  </xrd-simple-dialog>

  <!--   Edit event type version action-->
  <edit-event-type-version-dialog
    v-if="showEditEventTypeVersionDialog"
    :event-type-version="selectedEventTypeVersion"
    :manager-id="managerId"
    :room-id="roomId"
    :event-type-id="eventType"
    @cancel="showEditEventTypeVersionDialog = false"
    @save="hideEditEventTypeVersionDialogAndRefetch"
  />

  <!--  Terminate event type action -->
  <xrd-confirm-dialog
    v-if="showTerminateEventTypeVersionDialog"
    title="eventTypesList.actions.terminateVersion.terminateTitle"
    text="eventTypesList.actions.terminateVersion.terminateConfirm"
    :data="{
      eventType: eventType,
      versionNo: selectedEventTypeVersion.versionNo,
    }"
    :loading="terminatingEventTypeVersion"
    @cancel="showTerminateEventTypeVersionDialog = false"
    @accept="terminateEventTypeVersion()"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { DataTableHeader, Event } from '@/ui-types';
import XrdButton from '@/components/XrdButton.vue';
import { EventTypeVersion } from '@/generated';
import { useEventTypeVersion } from '@/stores/event-type-version';
import { VDataTableVirtual } from 'vuetify/labs/VDataTable';
import XrdIconDownload from '@/components/icons/XrdIconDownload.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import EditEventTypeVersionDialog from '@/components/eventTypes/EditEventTypeVersionDialog.vue';

export default defineComponent({
  components: {
    EditEventTypeVersionDialog,
    XrdConfirmDialog,
    XrdIconDownload,
    XrdButton,
    VDataTableVirtual,
  },
  emits: [Event.Cancel, Event.Save],
  props: {
    roomId: {
      type: String,
      required: true,
    },
    managerId: {
      type: String,
      required: true,
    },
    eventType: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      showTerminateEventTypeVersionDialog: false,
      showEditEventTypeVersionDialog: false,
      terminatingEventTypeVersion: false,
      selectedEventTypeVersion: {} as EventTypeVersion,
      eventTypeVersions: [] as EventTypeVersion,
    };
  },
  computed: {
    ...mapStores(useEventTypeVersion),
    headers(): DataTableHeader[] {
      return [
        {
          title: this.$t('eventTypesList.actions.viewVersions.versionNo'),
          align: 'start',
          key: 'versionNo',
          sortable: false,
        },
        {
          title: this.$t('eventTypesList.actions.viewVersions.createdAt'),
          align: 'start',
          key: 'createdAt',
          sortable: false,
        },
        {
          title: this.$t('eventTypesList.header.download'),
          align: 'center',
          key: 'downloadButton',
          sortable: false,
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
    this.loadEventTypeVersions();
  },

  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },

    loadEventTypeVersions() {
      this.eventTypeVersionStore
        .loadAllEventTypesVersion(this.managerId, this.roomId, this.eventType)
        .then((resp) => {
          this.eventTypeVersions = resp.data;
        })
        .catch((error) => {
          throw error;
        });
    },

    openEditEventTypeVersionDialog(selectedVersion: EventTypeVersion) {
      this.selectedEventTypeVersion = selectedVersion;
      this.showEditEventTypeVersionDialog = true;
    },

    hideEditEventTypeVersionDialogAndRefetch() {
      this.showEditEventTypeVersionDialog = false;
      this.loadEventTypeVersions();
    },

    openTerminateEventTypeVersionDialog(selectedVersion: EventTypeVersion) {
      this.selectedEventTypeVersion = selectedVersion;
      this.showTerminateEventTypeVersionDialog = true;
    },

    downloadSchema(schema: string) {
      this.eventTypeVersionStore.downloadSchema(schema);
    },

    terminateEventTypeVersion() {
      if (!this.selectedEventTypeVersion) return;

      this.terminatingEventTypeVersion = true;
      this.eventTypeVersionStore
        .terminateEventTypeVersion(
          this.managerId,
          this.roomId,
          this.eventType,
          this.selectedEventTypeVersion.versionNo,
        )
        .then(() => {
          this.showSuccess(
            this.$t('eventTypesList.actions.terminateAction.success', {
              eventType: this.eventType,
              versionNo: this.selectedEventTypeVersion.versionNo,
            }),
          );
          this.showTerminateEventTypeVersionDialog = false;
          this.terminatingEventTypeVersion = false;
          this.selectedEventTypeVersion = {} as EventTypeVersion;
          this.loadEventTypeVersions();
        })
        .catch((error) => {
          this.showError(error);
          this.showTerminateEventTypeVersionDialog = false;
          this.cancel();
        });
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';

.download-icon {
  color: $XRoad-Purple100;
}
</style>
