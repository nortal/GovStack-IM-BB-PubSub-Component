<template>
  <div>
    <searchable-titled-view
      v-model="search"
      title-key="membersList.title"
      @update:model-value="fetchMembers"
    >
      <v-data-table-server
        :loading="loading"
        :headers="headers"
        :items="memberStore.members"
        :items-per-page="10"
        :items-per-page-options="itemsPerPageOptions"
        :items-length="memberStore.pagingOptions.total_items"
        class="xrd-table elevation-0 rounded"
        item-key="client_id.encoded_id"
        :loader-height="2"
        density="compact"
        data-test="members-table"
        @update:options="changeOptions"
      >
        <template #top></template>
        <template #[`item.name`]="{ item }">
          <div class="xrd-table">
            {{ item.columns.name }}
          </div>
        </template>
      </v-data-table-server>
    </searchable-titled-view>
  </div>
</template>

<script lang="ts">
import { DeepReadonly, defineComponent } from 'vue';
import { Member, useMember } from '@/stores/members';
import { RouteName } from '@/global';
import { mapActions, mapStores } from 'pinia';
import { useNotifications } from '@/stores/notifications';
import { VDataTableServer } from 'vuetify/labs/VDataTable';
import { DataQuery, DataTableHeader, PagingOptions } from '@/ui-types';
import SearchableTitledView from '@/components/ui/SearchableTitledView.vue';
import { useAuth } from '@/stores/auth';
import { defaultItemsPerPageOptions } from '@/util/defaults';

// To provide the Vue instance to debounce
// eslint-disable-next-line @typescript-eslint/no-explicit-any
let that: any;

export default defineComponent({
  components: {
    SearchableTitledView,
    VDataTableServer,
  },
  data() {
    return {
      search: '',
      dataQuery: { page: 1, itemsPerPage: 10 } as DataQuery,
      itemsPerPageOptions: defaultItemsPerPageOptions(),
      loading: false,
    };
  },
  computed: {
    ...mapStores(useMember),
    ...mapActions(useAuth, ['hasAnyOfRoles']),
    headers(): DeepReadonly<DataTableHeader[]> {
      return [
        {
          title: this.$t('membersList.header.name'),
          align: 'start',
          key: 'name',
        },
        {
          title: this.$t('membersList.header.xRoadInstance'),
          align: 'start',
          key: 'xroadInstance',
        },
        {
          title: this.$t('membersList.header.memberClass'),
          align: 'start',
          key: 'memberClass',
        },
        {
          title: this.$t('membersList.header.memberCode'),
          align: 'start',
          key: 'memberCode',
        },
        {
          title: this.$t('membersList.header.subsystemCode'),
          align: 'start',
          key: 'subsystemCode',
        },
      ];
    },
  },
  methods: {
    ...mapActions(useNotifications, ['showError', 'showSuccess']),
    toDetails(member: Member): void {
      this.$router.push({
        name: RouteName.Rooms,
      });
    },
    fetchMembers: async function () {
      this.dataQuery.search = this.search;
      this.loading = true;
      try {
        await this.memberStore.getMembers(this.dataQuery);
      } catch (error: unknown) {
        this.showError(error);
      } finally {
        this.loading = false;
      }
    },
    changeOptions: async function ({
      itemsPerPage,
      page,
      sortBy,
    }: PagingOptions) {
      this.dataQuery.itemsPerPage = itemsPerPage;
      this.dataQuery.page = page;
      this.dataQuery.sortBy = sortBy[0]?.key;
      if (typeof sortBy[0]?.order === 'boolean') {
        this.dataQuery.sortOrder = sortBy[0].order ? 'asc' : 'desc';
      } else {
        this.dataQuery.sortOrder = sortBy[0]?.order;
      }
      await this.fetchMembers();
    },
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/colors';
@import '@/assets/tables';
@import '@/assets/global-style';
</style>
