<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="adding"
    cancel-button-text="action.cancel"
    title="publishersList.actions.add"
    @cancel="cancel"
    @save="add"
  >
    <template #content>
      <v-select
        :label="$t('publishersList.actions.addAction.selectMember')"
        :loading="loadingMembers"
        :disabled="loadingMembers"
        v-model="selectedManager"
        :items="managerToSelect"
        item-title="identifier"
        variant="outlined"
        return-object
      />
      <v-select
        :loading="loadingEventTypes"
        :disabled="loadingEventTypes"
        v-model="createPublisherRequest.eventTypes"
        :items="eventsToSelect"
        :label="$t('publishersList.actions.addAction.selectEventType')"
        variant="outlined"
        chips
        multiple
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { Event } from '@/ui-types';
import { usePublishers } from '@/stores/publishers';
import { useEventTypes } from '@/stores/event-types';
import { CreatePublisherRequest, ManagerResponse } from '@/generated';
import { useManagers } from '@/stores/managers';

export default defineComponent({
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
  },
  data() {
    return {
      adding: false,
      loadingMembers: false,
      loadingEventTypes: false,
      managerToSelect: [] as ManagerResponse[],
      eventsToSelect: [] as string[],
      selectedManager: {
        identifier: '',
      } as ManagerResponse,
      createPublisherRequest: {} as CreatePublisherRequest,
    };
  },
  computed: {
    ...mapStores(usePublishers, useManagers, useEventTypes),
    formReady(): boolean {
      return !!(this.selectedManager && this.createPublisherRequest.eventTypes);
    },
  },
  created() {
    this.loadManagers();
    this.loadEventTypes();
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
      this.clearForm();
    },
    clearForm(): void {
      this.createPublisherRequest = {} as CreatePublisherRequest;
    },
    add(): void {
      this.adding = true;
      this.createPublisherRequest.identifier = this.selectedManager.identifier;
      this.createPublisherRequest.identifierType =
        this.selectedManager.identifierType;
      this.publishersStore
        .addPublisher(this.managerId, this.roomId, this.createPublisherRequest)
        .then(() => {
          this.showSuccess(
            this.$t('publishersList.actions.addAction.success', {
              publisher: this.createPublisherRequest.identifier,
            }),
          );
          this.$emit(Event.Save);
          this.clearForm();
        })
        .catch((error) => {
          this.showError(error);
          this.$emit(Event.Cancel);
        })
        .finally(() => {
          this.adding = false;
        });
    },
    loadManagers() {
      this.loadingMembers = true;
      this.managersStore
        .loadAllManagers()
        .then((resp) => {
          this.managerToSelect = resp.data as ManagerResponse[];
        })
        .catch((error) => {
          throw error;
        })
        .finally(() => {
          this.loadingMembers = false;
        });
    },
    loadEventTypes() {
      this.loadingEventTypes = true;
      this.eventTypesStore
        .loadAllEventTypes(this.managerId, this.roomId)
        .then((resp) => {
          this.eventsToSelect = resp.data;
        })
        .catch((error) => {
          throw error;
        })
        .finally(() => {
          this.loadingEventTypes = false;
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
