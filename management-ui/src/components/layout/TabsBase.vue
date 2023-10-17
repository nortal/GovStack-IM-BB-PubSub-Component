<template>
  <v-layout class="main-content" align-left>
    <app-icon />
    <div class="tabs-wrap">
      <v-tabs
        v-if="isAdministrator()"
        v-model="currentTab"
        class="main-tabs"
        color="black"
        height="56px"
        slider-size="2"
        slider-color="primary"
        :show-arrows="true"
      >
        <v-tab
          v-for="tab in tabs"
          :key="tab.key"
          :to="tab.to"
          :data-test="tab.key"
        >{{ $t(tab.name) }}</v-tab
        >
      </v-tabs>
    </div>
    <app-drop-menu />
  </v-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import AppDropMenu from './UserDropMenu.vue';
import { Tab } from '@/ui-types';
import { mapActions } from 'pinia';
import { mainTabs } from '@/global';
import { useAuth } from '@/stores/auth';
import AppIcon from './AppIcon.vue';

export default defineComponent({
  components: {
    AppIcon,
    AppDropMenu,
  },
  data() {
    return {
      currentTab: undefined as undefined | Tab,
    };
  },
  computed: {
    tabs(): Tab[] {
      return mainTabs;
    },
  },
  methods: {
    ...mapActions(useAuth, ['isAdministrator']),
  },
});
</script>

<style lang="scss" scoped>
.main-content {
  background-color: #ffffff;
  height: 56px;
  padding-left: 92px;
  @media only screen and (max-width: 920px) {
    padding-left: 0px;
  }
}
.tabs-wrap {
  margin-left: 20px;
}

.main-tabs {
  max-width: 1000px;
}
</style>
