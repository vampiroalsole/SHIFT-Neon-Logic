# SHIFT — Neon Logic (Android 1.7)

Puzzle game Android offline, griglia 7×7, generazione procedurale e verifica algoritmica della risolvibilità. Interfaccia HTML/CSS/JavaScript ospitata in una WebView Android.

## Gioco
- Scorri per far scivolare la sfera fino a un ostacolo e raggiungere l'anello dorato.
- Blocchi mobili dal livello 10, portali dal 21, interruttori/porte dal 41.
- Progressione numerata fino al livello 999, livelli sbloccabili e rigiocabili, record, stelle e crediti.
- Missione giornaliera: completa tre livelli distinti per ottenere 30 crediti (riscatto manuale).
- Traguardi, suggerimenti a crediti, annulla/riprova, sfida opzionale di 90 secondi, pausa e audio.
- Tutorial iniziale con opzione di disattivazione e finestre esplicative delle nuove meccaniche.
- Salvataggi locali e backup/ripristino JSON; il gioco funziona offline.

## Compilazione
Apri **Actions → Build SHIFT Android APK**. Dopo un'esecuzione riuscita scarica l'artefatto **SHIFT-Neon-Logic-debug-APK**. Il workflow verifica la sintassi JavaScript e gli identificatori HTML prima di compilare l'APK.

## Stato e limitazioni
- Questa è una **build debug per test**, non una release Google Play.
- La **firma permanente è rimandata**: gli APK potrebbero non aggiornare le installazioni esistenti. Effettuare un backup prima di disinstallare.
- La chiave debug è memorizzata in una cache temporanea di GitHub Actions, non è una garanzia di continuità della firma.
- I 999 livelli sono il limite numerico previsto; non sono stati ancora collaudati singolarmente su dispositivi Android.
- Non sono integrati SDK pubblicitari, acquisti in-app o analytics. Prima del lancio commerciale servono ulteriori test su dispositivi, conformità Play Store, privacy policy e configurazione della firma di rilascio.
