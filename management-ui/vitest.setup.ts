import { config } from '@vue/test-utils'
import { createI18n } from 'vue-i18n'

const i18n = createI18n({
    legacy: false,
    allowComposition: true,
    globalInjection: true,
    locale: 'en',
})
config.global.plugins = [i18n]
config.global.mocks = {
    $t: (tKey: any) => tKey // returns the key itself for any translation
};
