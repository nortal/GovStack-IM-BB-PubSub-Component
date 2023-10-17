<template>
  <xrd-simple-dialog
    :disable-save="!formReady"
    :loading="loading"
    cancel-button-text="action.cancel"
    title="managersList.actions.createManager"
    @cancel="cancel"
    @save="add"
  >
    <template #content>
      <v-select
        v-model="createManagerRequest.identifierType"
        :items="managerTypes"
        :label="$t('managersList.actions.createAction.managerType')"
        variant="outlined"
        @update:modelValue="loadMembers"
      />
      <v-select
        v-if="
          createManagerRequest.identifierType ===
          CreateManagerRequestIdentifierTypeEnum.Xroad
        "
        v-model="createManagerRequest.identifier"
        :items="membersToSelect"
        :label="$t('managersList.actions.createAction.identifier')"
        variant="outlined"
      />
      <v-text-field
        v-if="
          createManagerRequest.identifierType !==
          CreateManagerRequestIdentifierTypeEnum.Xroad
        "
        v-model="createManagerRequest.identifier"
        :label="$t('managersList.actions.createAction.identifier')"
        variant="outlined"
        :disabled="!this.createManagerRequest.identifierType"
        autofocus
      />
    </template>
  </xrd-simple-dialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { Event } from '@/ui-types';
import {
  CreateManagerRequest,
  CreateManagerRequestIdentifierTypeEnum,
} from '@/generated';
import { useManagers } from '@/stores/managers';
import { useMember } from '@/stores/members';

export default defineComponent({
  emits: [Event.Cancel, Event.Save],
  data() {
    return {
      loading: false,
      createManagerRequest: {} as CreateManagerRequest,
      currentManagers: [] as string[],
      membersToSelect: [] as string[],
    };
  },
  computed: {
    CreateManagerRequestIdentifierTypeEnum() {
      return CreateManagerRequestIdentifierTypeEnum;
    },
    ...mapStores(useManagers, useMember),
    formReady(): boolean {
      return !!(
        this.createManagerRequest.identifier &&
        this.createManagerRequest.identifierType
      );
    },
    managerTypes(): string[] {
      return [
        CreateManagerRequestIdentifierTypeEnum.Internal,
        CreateManagerRequestIdentifierTypeEnum.Xroad,
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    cancel(): void {
      this.$emit(Event.Cancel);
      this.clearForm();
    },
    clearForm(): void {
      this.createManagerRequest = {} as CreateManagerRequest;
    },
    add(): void {
      this.loading = true;
      this.managersStore
        .createManager(this.createManagerRequest)
        .then(() => {
          this.showSuccess(
            this.$t('managersList.actions.createAction.success', {
              managerId: this.createManagerRequest.identifier,
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
          this.loading = false;
        });
    },
    loadManagers() {
      this.createManagerRequest.identifier = '';
      if (
        this.createManagerRequest.identifierType ===
        CreateManagerRequestIdentifierTypeEnum.Xroad
      ) {
        this.managersStore
          .loadAllManagers()
          .then((resp) => {
            this.currentManagers = resp.data.flatMap(
              (manager) => manager.identifier,
            );
          })
          .catch((error) => {
            throw error;
          });
      }
    },
    loadMembers() {
      this.loadManagers();
      this.memberStore
        .loadAllMembers()
        .then((resp) => {
          this.membersToSelect = resp.data
            .filter(
              (member) => !this.currentManagers.includes(member.identifier),
            )
            .flatMap((member) => member.identifier);
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
</script>
