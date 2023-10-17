<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    save-button-text="action.save"
    title="eventTypesList.actions.edit"
    @cancel="cancel"
    @save="update"
  >
    <template #content>
      <v-text-field
        v-model="eventTypeId"
        :label="$t('eventTypesList.actions.addAction.identifier')"
        variant="outlined"
        disabled
      />
      <v-text-field
        v-model="eventTypeVersionToEdit.versionNo"
        :label="$t('eventTypesList.actions.addAction.versionNo')"
        type="number"
        variant="outlined"
        disabled
      />
      <v-textarea
        v-model="eventTypeVersionToEdit.schema"
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
import { EventTypeVersion, UpdateEventTypeVersion } from '@/generated';
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
    eventTypeId: {
      type: String,
      required: true,
    },
    eventTypeVersion: {
      type: Object as PropType<EventTypeVersion>,
      required: true,
    },
  },
  data() {
    return {
      loading: false,
      eventTypeVersionToEdit: {} as UpdateEventTypeVersion,
    };
  },
  computed: {
    ...mapStores(useEventTypeVersion),
    formReady(): boolean {
      return !!(
        this.eventTypeVersionToEdit.schema && this.eventTypeVersion.jsonSchema
      );
    },
  },
  created() {
    this.eventTypeVersionToEdit = {
      versionNo: this.eventTypeVersion.versionNo,
      schema: this.eventTypeVersion.jsonSchema,
    } as UpdateEventTypeVersion;
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },
    update(): void {
      this.loading = true;
      this.eventTypeVersionStore
        .updateEventTypeVersion(
          this.managerId,
          this.roomId,
          this.eventTypeId,
          this.eventTypeVersionToEdit,
        )
        .then(() => {
          this.showSuccess(
            this.$t('eventTypesList.actions.updateVersionAction.success', {
              eventType: this.eventTypeId,
              versionNo: this.eventTypeVersion.versionNo,
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
