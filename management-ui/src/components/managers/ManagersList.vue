<template>
  <titled-view title-key="managersList.title" />
  <xrd-button class="xrd-button" @click="showCreateManagerDialog = true">
    <v-icon class="xrd-large-button-icon">icon-Add</v-icon>
    {{ $t('managersList.actions.createManager') }}
  </xrd-button>
  <v-data-table-server
    :loading="loading"
    :headers="headers"
    :items="managersStore.managers"
    :items-per-page="10"
    :items-length="managersStore.pagingOptions.total_items"
    :items-per-page-options="itemsPerPageOptions"
    class="xrd-table elevation-0 rounded"
    item-key="client_id.encoded_id"
    :loader-height="2"
    density="compact"
    @update:options="changeOptions"
  >
    <template #[`item.button`]="{ item }">
      <xrd-button
        class="xrd-clickable"
        text
        @click="openTerminateConfirmationDialog(item.raw.id)"
      >
        {{ $t('managersList.actions.terminate') }}
      </xrd-button>
    </template>
  </v-data-table-server>

  <!--   Create Room action -->
  <add-manager-dialog
    v-if="showCreateManagerDialog"
    @cancel="showCreateManagerDialog = false"
    @save="hideCreateManagerDialogAndRefetch"
  />

  <xrd-confirm-dialog
    v-if="showTerminateManagerDialog"
    title="managersList.actions.terminateAction.terminateTitle"
    text="managersList.actions.terminateAction.terminateConfirm"
    :data="{ managerId: selectedManagerId }"
    :loading="terminatingManager"
    @cancel="showTerminateManagerDialog = false"
    @accept="terminateManager()"
  />
</template>

<script lang="ts">
import { DeepReadonly, defineComponent } from 'vue';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import { DataQuery, DataTableHeader } from '@/ui-types';
import { useManagers } from '@/stores/managers';
import XrdButton from '@/components/XrdButton.vue';
import XrdConfirmDialog from '@/components/XrdConfirmDialog.vue';
import AddManagerDialog from '@/components/managers/AddManagerDialog.vue';
import TitledView from '@/components/ui/TitledView.vue';
import { defaultItemsPerPageOptions } from '@/util/defaults';

export default defineComponent({
  components: {
    TitledView,
    AddManagerDialog,
    XrdConfirmDialog,
    XrdButton,
    VDataTableServer,
  },
  data() {
    const options = defaultItemsPerPageOptions();
    return {
      loading: true,
      showCreateManagerDialog: false,
      showTerminateManagerDialog: false,
      selectedManagerId: '',
      terminatingManager: false,
      itemsPerPageOptions: options,
      pagingOptions: { page: 1, itemsPerPage: options[0].value } as DataQuery,
    };
  },
  computed: {
    ...mapStores(useManagers),
    headers(): DeepReadonly<DataTableHeader[]> {
      return [
        {
          title: this.$t('managersList.header.name'),
          align: 'start',
          key: 'identifier',
        },
        {
          title: this.$t('managersList.header.type'),
          align: 'start',
          key: 'identifierType',
        },
        {
          title: this.$t('managersList.actions.actions'),
          align: 'center',
          key: 'button',
          sortable: false,
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    changeOptions: async function ({ itemsPerPage, page, sortBy }) {
      this.pagingOptions.itemsPerPage = itemsPerPage;
      this.pagingOptions.page = page;
      this.pagingOptions.sortBy = sortBy[0]?.key;
      this.pagingOptions.sortOrder = sortBy[0]?.order;
      this.pagingOptions.search = '';
      await this.fetchManagers();
    },
    fetchManagers: async function () {
      this.loading = true;
      try {
        await this.managersStore.loadManagersPage(this.pagingOptions);
      } catch (error: unknown) {
        this.showError(error);
      } finally {
        this.loading = false;
      }
    },

    hideCreateManagerDialogAndRefetch() {
      this.showCreateManagerDialog = false;
      this.fetchManagers();
    },
    openTerminateConfirmationDialog(managerId: string): void {
      this.selectedManagerId = managerId;
      this.showTerminateManagerDialog = true;
    },

    terminateManager(): void {
      if (!this.selectedManagerId) return;

      this.terminatingManager = true;
      this.managersStore
        .terminateManager(this.selectedManagerId)
        .then(() => {
          this.showSuccess(
            this.$t('managersList.actions.terminateAction.success', {
              managerId: this.selectedManagerId,
            }),
          );
          this.showTerminateManagerDialog = false;
          this.terminatingManager = false;
          this.selectedManagerId = '';
          this.fetchManagers();
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
