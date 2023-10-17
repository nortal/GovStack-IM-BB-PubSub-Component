<template>
  <v-img
    :src="logo"
    height="35"
    width="132"
    max-height="35"
    max-width="132"
    class="xrd-logo"
    @click="home()"
  ></v-img>
</template>

<script lang="ts">
import logo from "@/assets/bullhorn_black.svg";
import { defineComponent } from "vue";
import { RouteName } from "@/global";

export default defineComponent({
  data() {
    return {
      logo,
    };
  },
  computed: {},
  methods: {
    home(): void {
      this.$router
        .replace({
          name: RouteName.BaseRoute,
        })
        .catch((err) => {
          // Ignore the error regarding navigating to the same path
          if (err.name === "NavigationDuplicated") {
            // eslint-disable-next-line no-console
            console.info("Duplicate navigation");
          } else {
            // Throw for any other errors
            throw err;
          }
        });
    },
  },
});
</script>

<style lang="scss" scoped>
.xrd-logo {
  margin-top: auto;
  margin-bottom: auto;
  cursor: pointer;
  @media only screen and (max-width: 920px) {
    display: none;
  }
}
</style>
