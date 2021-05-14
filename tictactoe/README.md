Dette blir et kort avsnitt om prosjekt oppgave 2.
Oppgaven ble satt opp for oss slik at vi skal jobbe mot eksiterende service grensesnitt som ble lagt for oss av læreren vår. 

Prosjekt oppgaven innbrakte at vi skal lage et tre på radd spill med flere enheter koblet med hverandre under en felles gameservice. Dvs. lage en UI for en bruker for å lage/bli med eksisterende spil, skape tilkobling mellom enheter, definere spille regler.

I oppgaven blir det forutsatt at vi bygge opp på eksiterende GameService objekt med å legge til funksjoner for: “createGame”, “joinGame”, “updateGame” and “pollGame” 
som vi skal utføre HTTPS kommunikasjon mellom eksisterende game service med nøkkel «1WlPGAGTi4», kommunikasjon skal foregå gjennom utveksling JSON objekter.
For å sette opp base kommunikasjon med game service var det ikke så vanskelig, en stor hjelp var en lenke «https://editor.swagger.io/» der vi kunne lett teste kommunikasjon mellom vm enheter og game service
ved å bruke et oppsett .yaml fil gitt læreren.
Konfigurasjon av UI var det minste delen av oppgaven, bare å sette riktig tilkobling mellom dialogfragmenter, interface og tilsvarende xml dokumenter slik at mottar data fra UI og overfører den videre til HTTPS funksjoner.

Men, dessverre så hadde jeg kommet til flere problemer med å sette opp en polling funksjon slik at det blir mulig å spille med andre mennesker, man kan fortsatt spille og bli med på tre på
rad spill, men det er bare mulig på enn enhet, siden jeg har ikke fått det til men synkronisering av data på begge enheter, samt hadde jeg problem med dynamisk oppdatering av bruke
i spill for bandasje problemet så laget jeg en «Update Players» knapp bare for å bevise at de brukerne kan faktisk koble til felles spill, men dessverre spiller hver av de for seg selv.
 Kan si at generelt lite erfaring med aktiviteter og fragmenter, og mangel av erfaring med kotlin/java hadde en stor innvirkning på slutt kvalitet av prosjekt oppgaven.
