<template>
  <div>
    <v-tabs>
      <v-tab
        v-for="tab in tabs"
        :key="tab.key"
        :to="tab.to"
        :data-test="tab.key"
        exact
      >{{ $t(tab.name) }}
        <span v-if="tab.showAttention" class="dot mb-3"></span>
      </v-tab>
    </v-tabs>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { Tab } from '@/ui-types';

export interface PageNavigationTab extends Tab {
  showAttention?: boolean;
}

export default defineComponent({
  name: 'PageNavigation',
  props: {
    tabs: {
      type: Array as PropType<PageNavigationTab[]>,
      required: true,
    },
  },
  computed: {},
});
</script>
<style lang="scss" scoped>
:deep(.v-tab) {
  text-transform: none;
  font-weight: 600;
  color: rgb(0 0 0 / 54%);
}
</style>
