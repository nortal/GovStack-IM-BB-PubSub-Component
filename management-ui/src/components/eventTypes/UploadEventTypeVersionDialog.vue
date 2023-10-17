<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    title="eventTypesList.actions.add"
    @cancel="cancel"
    @save="add"
  >
    <template #content>
      <v-text-field
        v-model="eventType.identifier"
        :label="$t('eventTypesList.actions.addAction.identifier')"
        variant="outlined"
        disabled
      />
      <v-text-field
        v-model="createEventTypeVersion.versionNo"
        :label="$t('eventTypesList.actions.addAction.versionNo')"
        type="number"
        variant="outlined"
        disabled
      />
      <v-textarea
        v-model="createEventTypeVersion.schema"
        :label="$t('eventTypesList.actions.addAction.schema')"
        variant="outlined"
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { Event } from '@/ui-types';
import { EventTypeView, UpdateEventTypeVersion } from '@/generated';
import { useEventTypeVersion } from '@/stores/event-type-version';

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
    eventType: {
      type: Object as PropType<EventTypeView>,
      required: true,
    },
  },
  data() {
    return {
      loading: false,
      createEventTypeVersion: {
        versionNo: this.eventType.versions + 1,
      } as UpdateEventTypeVersion,
    };
  },
  computed: {
    ...mapStores(useEventTypeVersion),
    formReady(): boolean {
      return !!(
        this.createEventTypeVersion.versionNo &&
        this.createEventTypeVersion.schema
      );
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },
    add(): void {
      this.loading = true;
      this.eventTypeVersionStore
        .createEventTypeVersion(
          this.managerId,
          this.roomId,
          this.eventType.identifier,
          this.createEventTypeVersion,
        )
        .then(() => {
          this.showSuccess(
            this.$t('eventTypesList.actions.addVersionAction.success', {
              eventType: this.createEventTypeVersion.identifier,
              versionNo: this.createEventTypeVersion.versionNo,
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
