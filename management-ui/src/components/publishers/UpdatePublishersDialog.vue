<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    title="publishersList.actions.updateAction.updateTitle"
    @cancel="cancel"
    @save="update"
  >
    <template #content>
      <v-text-field
        v-model="publisher.identifier"
        :label="$t('publishersList.actions.addAction.selectMember')"
        variant="outlined"
        disabled
      />
      <v-select
        :loading="loading"
        :disabled="loading"
        v-model="publisherUpdateRequest.eventTypes"
        :items="eventsToSelect"
        :label="$t('publishersList.actions.addAction.selectEventType')"
        persistent-hint
        :hint="$t('publishersList.actions.updateAction.atleastOne')"
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
import { PublisherViewResponse, UpdatePublisherRequest } from '@/generated';
import { PropType } from 'vue/dist/vue';

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
    publisher: {
      type: Object as PropType<PublisherViewResponse>,
      required: true,
    },
  },
  data() {
    return {
      updating: false,
      loading: false,
      eventsToSelect: [] as string[],
      originalEvents: [] as string[],
      publisherUpdateRequest: {} as UpdatePublisherRequest,
    };
  },
  computed: {
    ...mapStores(usePublishers, useEventTypes),
    formReady(): boolean {
      return this.publisherUpdateRequest.eventTypes.length > 0;
    },
  },
  created() {
    this.loadEventTypes();
    this.originalEvents = this.publisher.constraints?.flatMap(
      (x) => x.eventIdentifier,
    ) as string[];
    // deep copy
    this.publisherUpdateRequest = {
      eventTypes: JSON.parse(JSON.stringify(this.originalEvents)),
    } as UpdatePublisherRequest;
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },
    update(): void {
      this.updating = true;
      this.publishersStore
        .updatePublisher(
          this.managerId,
          this.roomId,
          this.publisher.id,
          this.publisherUpdateRequest,
        )
        .then(() => {
          this.showSuccess(
            this.$t('publishersList.actions.updateAction.success', {
              publisher: this.publisher.identifier,
            }),
          );
          this.$emit(Event.Save);
        })
        .catch((error) => {
          this.showError(error);
          this.$emit(Event.Cancel);
        })
        .finally(() => {
          this.loading = false;
          this.$emit(Event.Cancel);
        });
    },
    loadEventTypes() {
      this.loading = true;
      this.eventTypesStore
        .loadAllEventTypes(this.managerId, this.roomId)
        .then((resp) => {
          this.eventsToSelect = resp.data;
        })
        .catch((error) => {
          throw error;
        })
        .finally(() => {
          this.loading = false;
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
