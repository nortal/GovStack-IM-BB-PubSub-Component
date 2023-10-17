<template>
  <section>
    <header class="header-row d-flex mb-6 align-center">
      <div class="xrd-header-title ma-2 pa-2 me-auto">
        <div class="xrd-view-title">
          <slot name="title">{{ titleValue }}</slot>
        </div>
        <slot name="append-title" />
      </div>

      <div class="xrd-header-buttons ma-2 pa-2 me-6">
        <slot name="header-buttons" />
      </div>
    </header>
    <xrd-empty-placeholder
      :loading="loading"
      :data="data"
      :no-items-text="$t('noData.noData')"
      skeleton-type="table-heading"
    />

    <slot />
  </section>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    title: {
      type: String,
      default: "",
    },
    loading: {
      type: Boolean,
      default: false,
    },
    titleKey: {
      type: String,
      default: "",
    },
    data: {
      type: [Object, Array],
      default() {
        return { a: 1 };
      },
    },
  },
  computed: {
    titleValue(): string {
      return this.titleKey ? this.$t(this.titleKey) : this.title;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/colors";
@import "@/assets/tables";
@import "@/assets/global-style";

.xrd-header-title {
  display: flex;
  align-items: center;
}

header.header-row {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-end;
  width: 100%;
  margin-bottom: 0 !important;
  min-height: 80px;

  .xrd-header-title {
    margin-left: 0 !important;
    padding-left: 0 !important;
  }

  .xrd-header-buttons {
    margin-right: 0 !important;
    padding-right: 0 !important;
  }
}
</style>
