<template>
  <titled-view>
    <template #title>
      <slot name="title">{{ titleValue }}</slot>
    </template>
    <template #append-title>
      <xrd-search
        v-model="query"
        class="search-box"
        data-test="search-query-field"
      />
      <slot name="append-search" />
    </template>
    <template #header-buttons>
      <slot name="header-buttons" />
    </template>
    <slot />
  </titled-view>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TitledView from "@/components/ui/TitledView.vue";

export default defineComponent({
  components: { TitledView },
  props: {
    title: {
      type: String,
      default: "",
    },
    titleKey: {
      type: String,
      default: "",
    },
    modelValue: {
      type: String,
      default: "",
    },
  },
  emits: ["update:model-value"],
  computed: {
    query: {
      get() {
        return this.modelValue;
      },
      set(newValue: string) {
        this.$emit("update:model-value", newValue);
      },
    },
    titleValue() {
      return this.titleKey ? this.$t(this.titleKey) : this.title;
    },
  },
});
</script>

<style lang="scss" scoped>
.search-box {
  margin: 0 0 5px 20px;
}
</style>
