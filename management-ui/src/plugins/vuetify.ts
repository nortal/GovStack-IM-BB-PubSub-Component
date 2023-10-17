import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi";
import { Colors } from "@/global";

export default createVuetify({
  defaults: {
    VProgressLinear: {
      color: "primary",
    },
    VCheckbox: {
      color: "primary",
    },
    VTextField: {
      color: "primary",
    },
    VTabs: {
      color: "primary",
    },
  },
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
  theme: {
    //TODO check customProperties in https://vuetifyjs.com/en/getting-started/upgrade-guide/
    themes: {
      light: {
        dark: false,
        colors: {
          primary: Colors.Purple100,
          secondary: Colors.Purple70,
          "on-error": Colors.Black100,
        },
      },
    },
  },
});
