Dette da blir repoet for koden til Projeskt oppgave 1.


Som i selve oppgaven sagt i laget jeg en app som håndeterer to do lister.
Ved applikasjon start så havner vi på hoved siden som kan enten bli tom for todolister, eller i dette tilfellet så ligger det et par eksempler fra min firebase databse.
Gjennom bruk av dataklasser,adaptere,activitys og managere, klarte jeg og sette opp en to sidig interface, første siden tillater bruker å bla gjennom lagde todolister,slette de,
legge til nye lister og se allerede eksisterende lister. Aplikasjon er i stand til å både lagre, hente og slette data fra firebase databse. Det er to hoved view bind, med en hver egen 
recycler view, hver recycler view håndtere visning av todolister og mål/tasks til hver liste. Det er mulig å gå inn på todolisten på eksiterende todoliste og legge til mål som må gjøres
i følge den todoliste.

Det er mulig å slette individuelle task innen todo liste, med klarte ikke å finne en måte å dynamisk oppdatere relevant todolist, selve funksjonalitet funker men da må man ut og inn
i listen på nytt (onClick funksjon) da blir den oppdater, klarte å ikke finne en ordentlig måte for å definere intent i klass definisjon av adapter.

Progressbar er lagt til inni selve todoliste, men er ikke koblet mot noe funksjonalitet.
Samme problemet gjelder checkboxene, de funker bare visuelt indikasjon at tasken har blitt fullført, men dataene er ikke oppdater i databases, med det er mulig å hente ut status=true verdier,
fra databasen og vise de på relevante lister som fullførte




App Distribution lenke: https://appdistribution.firebase.dev/i/45c2816f038b2fda
Har også sendt en release versjon til din epost, usikker om det ble gjort korrekt, så heller kan invitasjons lenke ovenfor brukes for App distribuering 
