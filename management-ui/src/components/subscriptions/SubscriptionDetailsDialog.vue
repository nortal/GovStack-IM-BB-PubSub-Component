<template>
  <xrd-simple-dialog
    show-close
    hide-save-button
    title="subscriptionsList.actions.detailsAction.title"
    cancel-button-text="action.close"
    @cancel="cancel"
    width="1000px"
    scrollable
  >
    <template #content>
      <v-row class="px-0">
        <v-col>
          <v-text-field
            v-model="subscription.id"
            :label="$t('subscriptionDetails.subscription.id')"
            variant="underlined"
            :readonly="true"
          />
          <v-text-field
            v-model="subscription.identifier"
            :label="$t('subscriptionDetails.subscription.identifier')"
            variant="underlined"
            :readonly="true"
          />
          <v-text-field
            v-model="subscription.status"
            :label="$t('subscriptionDetails.subscription.status')"
            variant="underlined"
            :readonly="true"
          />
          <v-text-field
            v-model="subscription.method"
            :label="$t('subscriptionDetails.subscription.method')"
            variant="underlined"
            :readonly="true"
          />
          <v-text-field
            v-if="subscription.method === 'PUSH'"
            v-model="subscription.pushUrl"
            :label="$t('subscriptionDetails.subscription.pushUrl')"
            variant="underlined"
            :readonly="true"
          />
          <v-text-field
            v-model="subscription.createdAt"
            :label="$t('subscriptionDetails.subscription.createdAt')"
            variant="underlined"
            :readonly="true"
          />
        </v-col>
      </v-row>
      <subscription-events-list
        :subscription-id="subscription.id"
        :room-id="roomId"
        :manager-id="managerId"
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { Event } from '@/ui-types';
import { Subscription } from '@/generated';
import { PropType } from 'vue/dist/vue';
import SubscriptionEventsList from '@/components/subscriptions/SubscriptionEventsList.vue';

export default defineComponent({
  components: { SubscriptionEventsList },
  emits: [Event.Cancel],
  props: {
    managerId: {
      type: String,
      required: true,
    },
    roomId: {
      type: String,
      required: true,
    },
    subscription: {
      type: Object as PropType<Subscription>,
      required: true,
    },
  },
  methods: {
    cancel(): void {
      this.$emit(Event.Cancel);
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
