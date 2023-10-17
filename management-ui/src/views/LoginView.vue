<template>
  <v-container fluid class="login-view-wrap fill-height">
    <alerts-container class="alerts" />
    <v-row no-gutters class="fill-height">
      <v-col cols="3">
        <div class="graphics">
          <v-img
            :src="logo"
            height="195"
            width="144"
            max-height="195"
            max-width="144"
            class="xrd-logo"
          ></v-img>
        </div>
      </v-col>
      <v-col cols="9" align-self="center">
        <v-container class="set-width">
          <v-card variant="flat">
            <v-card-item class="title-wrap">
              <v-card-title class="login-form-title">
                {{ $t("login.title") }}
              </v-card-title>
              <v-card-subtitle class="sub-title">
                {{ $t("global.appTitle") }}
              </v-card-subtitle>
            </v-card-item>

            <v-card-actions>
              <xrd-button
                id="oauth-login-button"
                color="primary"
                gradient
                block
                large
                :min_width="120"
                rounded
                :loading="loading"
                @click="logIn"
                >{{ $t("login.loginButton") }}
              </xrd-button>
            </v-card-actions>
          </v-card>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { signInRedirect } from "@/oidc/oidc";
import logo from "@/assets/bullhorn_white.svg";
import { defineComponent } from "vue";
import { mapActions, mapState } from "pinia";
import { useNotifications } from "@/stores/notifications";
import AlertsContainer from "@/components/ui/AlertsContainer.vue";

export default defineComponent({
  components: {AlertsContainer},
  setup() {},
  data() {
    return {
      logo,
      loading: false as boolean,
    };
  },
  computed: {},
  methods: {
    async logIn() {
      await signInRedirect().catch((error) => {
        console.log("Unable to sign in: ", error)
        this.showErrorMessage(this.$t("login.loginError"));
      });
    },
    ...mapActions(useNotifications, [
      "showError",
      "showErrorMessage",
      "resetNotifications",
    ]),
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/colors";

.v-text-field {
  margin-bottom: 6px;
}

.alerts {
  top: 40px;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  z-index: 100;
  position: absolute;
}

.login-view-wrap {
  background-color: white;
  padding: 0;

  .graphics {
    height: 100%;
    max-width: 576px; // width of the backround image
    background-color: $XRoad-Blue10;
    background-size: cover;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .set-width {
    max-width: 420px;

    .title-wrap {
      margin-bottom: 30px;

      .login-form-title {
        margin-left: 0;
        color: #252121;
        font-style: normal;
        font-weight: bold;
        font-size: 40px;
        line-height: 54px;
      }

      .sub-title {
        font-style: normal;
        font-weight: normal;
        font-size: $XRoad-DefaultFontSize;
        line-height: 19px;
      }
    }
  }
}
</style>
