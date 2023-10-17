<template>
  <div>
    <v-container class="pa-0">
      <ContextualAlerts />
    </v-container>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ContextualAlerts from "./ContextualAlerts.vue";

export default defineComponent({
  components: {
    ContextualAlerts,
  },
});
</script>

<style lang="scss" scoped>
.alerts-wrapper {
  width: 1000px;
  padding: 0;
}
</style>
