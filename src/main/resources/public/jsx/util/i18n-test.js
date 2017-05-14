import i18n from 'i18next'
import Backend from 'i18next-sync-fs-backend'
import LanguageDetector from 'i18next-browser-languagedetector'

i18n
    .use(Backend)
    .use(LanguageDetector)
    .init({
        whitelist: ['en'],
        fallbackLng: 'en',

        // have a common namespace used around the full app
        ns: ['cureme'],
        defaultNS: 'cureme',

        debug: false,

        interpolation: {
            escapeValue: false // not needed for react!!
        },

        backend: {
            // path where resources get loaded from, or a function
            // returning a path:
            // function(lngs, namespaces) { return customPath; }
            // the returned path will interpolate lng, ns if provided like giving a static path
            loadPath: process.cwd() + '/src/main/resources/public/locales/{{lng}}/{{ns}}.json',

            // path to post missing resources
            addPath: process.cwd() + '/src/main/resources/public/locales/add/{{lng}}/{{ns}}.missing.json',
        }
    })

export default i18n
