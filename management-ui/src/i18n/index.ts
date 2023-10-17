import { createI18n } from "vue-i18n";
import merge from "deepmerge";
import vee_en from "@vee-validate/i18n/dist/locale/en.json";
import locals from "@/i18n/locales/en.json";

let en = { validation: vee_en };
en = merge(en, locals);

export default createI18n({
  locale: import.meta.env.VITE_DEFAULT_LOCALE || "en",
  fallbackLocale: import.meta.env.VITE_FALLBACK_LOCALE || "en",
  legacy: false,
  globalInjection: true,
  messages: {
    en,
  },
});
