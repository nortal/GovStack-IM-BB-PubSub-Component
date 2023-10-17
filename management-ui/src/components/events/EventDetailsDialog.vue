<template>
  <xrd-simple-dialog
      :loading="loading"
      hide-save-button
      @cancel="cancel"
      :cancel-button-text="$t('action.close')"
      title="eventDetails.title"
  >
    <template #content>
      <v-card flat class="xrd-card">
        <v-card-text class="card-text">
          <v-row class="px-0">
            <v-col><h3> {{ $t('eventDetails.event.title') }} </h3></v-col>
          </v-row>
          <v-row class="px-0">
            <v-col>
              <v-text-field
                  v-if="eventDetails.event"
                  v-model="eventDetails.event.id"
                  :label="$t('eventDetails.event.identifier')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.event"
                  v-model="eventDetails.event.correlationId"
                  :label="$t('eventDetails.event.correlationId')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.event"
                  v-model="eventDetails.event.createdAt"
                  :label="$t('eventDetails.event.createdAt')"
                  variant="underlined"
                  :readonly="readOnly"
              />
            </v-col>
          </v-row>
          <v-row class="px-0">
            <v-col><h3> {{ $t('eventDetails.eventType.title') }} </h3></v-col>
          </v-row>
          <v-row class="px-0">
            <v-col>
              <v-text-field
                  v-if="eventDetails.eventType"
                  v-model="eventDetails.eventType.identifier"
                  :label="$t('eventDetails.eventType.identifier')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.eventType && eventDetails.eventType.deletedAt !== undefined"
                  v-model="eventDetails.eventType.deletedAt"
                  :label="$t('eventDetails.eventType.deletedAt')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.eventTypeVersion"
                  v-model="eventDetails.eventTypeVersion.versionNo"
                  :label="$t('eventDetails.eventTypeVersion.versionNo')"
                  type="number"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-textarea
                  v-if="eventDetails.eventTypeVersion"
                  v-model="eventDetails.eventTypeVersion.schema"
                  :label="$t('eventDetails.eventTypeVersion.schema')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.eventTypeVersion && eventDetails.eventTypeVersion.deletedAt !== undefined"
                  v-model="eventDetails.eventTypeVersion.deletedAt"
                  :label="$t('eventDetails.eventTypeVersion.deletedAt')"
                  variant="underlined"
                  :readonly="readOnly"
              />
            </v-col>
          </v-row>
          <v-row class="px-0">
            <v-col><h3> {{ $t('eventDetails.publisher.title') }} </h3></v-col>
          </v-row>
          <v-row class="px-0">
            <v-col>
              <v-text-field
                  v-if="eventDetails.publisher"
                  v-model="eventDetails.publisher.identifier"
                  :label="$t('eventDetails.publisher.identifier')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.publisher"
                  v-model="eventDetails.publisher.identifierType"
                  :label="$t('eventDetails.publisher.identifierType')"
                  variant="underlined"
                  :readonly="readOnly"
              />
              <v-text-field
                  v-if="eventDetails.publisher && eventDetails.publisher.deletedAt !== undefined"
                  v-model="eventDetails.publisher.deletedAt"
                  :label="$t('eventDetails.publisher.deletedAt')"
                  variant="underlined"
                  :readonly="readOnly"
              />
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {EventDetails} from "@/generated/api";
import {mapActions, mapStores} from "pinia";
import {useEvent} from "@/stores/events";
import {useNotifications} from "@/stores/notifications";
import {Event} from "@/ui-types";

export default defineComponent({
  props: {
    eventId: {
      type: String,
      required: true,
    },
    managerId: {
      type: String,
      required: true,
    },
    roomId: {
      type: String,
      required: true,
    },
  },
  emits: [Event.Cancel],
  data() {
    return {
      loading: false,
      readOnly: true,
      eventDetails: {} as EventDetails,
    };
  },
  created() {
    this.loading = true;
    this.eventStore.getEventDetails(this.managerId, this.roomId, this.eventId).then((resp) => {
      this.eventDetails = resp;
    }).catch((err) => {
      this.showError(err);
      this.cancel();
    }).finally(() => {
      this.loading = false;
    });
  },
  computed: {
    ...mapStores(useEvent, ['eventStore']),
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
    },
  },
});
</script>
