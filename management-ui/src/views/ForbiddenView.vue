<template>
  <div class="xrd-view-common" data-test="forbidden-view">
    <TabsBase />
    <v-container>
      <div class="xrd-view-title pt-6">{{ $t("403.topTitle") }}</div>
      <v-card flat class="xrd-card custom-card">
        <v-card-text>
          <div class="content-wrap">
            <div class="main-title">{{ $t("403.mainTitle") }}</div>
            <div class="permission-text">
              {{ $t("403.text") }}
            </div>
          </div>
        </v-card-text>
      </v-card>
    </v-container>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TabsBase from "@/components/layout/TabsBase.vue";

export default defineComponent({
  components: {
    TabsBase: TabsBase,
  },
  computed: {},
  methods: {},
});
</script>

<style lang="scss" scoped>
@import "@/assets/colors";

.xrd-view-common {
  width: 100%;
}

.permission-text {
  width: 620px;
  text-align: center;
}

.main-title {
  font-family: Open Sans;
  font-style: normal;
  font-weight: bold;
  font-size: 40px;
  line-height: 56px;
  color: $XRoad-Black100;
  margin-top: 50px;
  margin-bottom: 60px;
}

.custom-card {
  width: 100%;
  margin-top: 40px;
  margin-bottom: 30px;
}

.content-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.buttons-wrap {
  display: row;
  flex-direction: column;
  align-items: center;
}
</style>
