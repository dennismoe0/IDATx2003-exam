2.1 Nivå 1: Stigespill med noe ekstra (MVP)
Det tradisjonelle stigespillet består av et spillebrett med 9 rader med 10 felt i hver rad (10 kolonner).
Feltene er nummerert fortløpende fra 1 til 90, i «slangemønster» som vist i figur 2.
Spillet starter ved at ingen spillbrikker er på brettet. Yngste spiller begynner ved å kaste to ter-
ninger og flytte spillbrikken sin det antall øyne terningene viser fra rute nr. 1 og videre. Den første
feltrekken følges helt ut til høyre, så flytter man opp i neste rekke og følger feltene helt ut til venstre.
Slik fortsetter man til toppen er nådd.
2.1.1 Stiger
Noen av feltene er koblet sammen med stiger; en stige kan enten flytte en spiller opp eller flytte
en spiller ned på brettet. Felt med stige som flytter spiller opp er som regel farget mørk grønn.
Feltet som stigen leder til er da gjerne lys grønn på farge. Felt med stige som tar spilleren nedover
på brettet er gjerne farget rødt og feltet stigen ender på er gjerne farget lyserødt/oransje (se figur
1b).
6 IDATx2003-2025 : Mappeoppgave
10987654321
20 19 18 17 16 15 14 13 12 11
30292827262524232221
40 39 38 37 36 35 34 33 32 31
50494847464544434241
60 59 58 57 56 55 54 53 52 51
70696867666564636261
80 79 78 77 76 75 74 73 72 71
90898887868584838281
Figur 2: Spillebrett for Stigespillet
2.1.2 Noe ekstra
For å gjøre spillet litt mer spennende, skal det i tillegg inneholde enkelt felt med effekten
•«rykk tilbake til start»
•«stå over ett kast før spilleren kan gå videre»
•eller lignende..
2.1.3 Kravspesifikasjon – funksjonelle krav
Følgende funksjonelle krav gjelder:
•En bruker skal kunne velge et spill blant et på forhånd definerte varianter av stigespill (f.eks.
ved å lese fra fil). Med «varianter» menes f.eks. layout (antall felt) og hvor stigene er plassert).
•Bruker velger så hvor mange spillere som skal delta i spillet (maks fem).
•Hver spiller velger deretter en «brikke» for sin spiller (fra et utvalg av ulike forhåndsdefinerte
brikker/figurer/ikoner) og gir sin brikke/spiller et navn.
•Spillet starter ved at det trilles to terninger for hver spiller, og spilleren sin brikke flyttes tilsva-
rende.
•Avhengig av hvilken type felt spilleren lander på, utføres respektiv handling (stige opp, stige
ned, osv.).
•Når første spiller når siste rute (rute nr. 90) er spillet over, og vinneren kåres.
2.2 Nivå 2: Flere ulike brettspill
Med utgangspunkt i felt som er koblet sammen i en struktur, kan man også lage andre brettspill
enn stigespillet. Slike brettspill kan deles i to kategorier:

1. Spill der spilleren ikke kan velge hvilken retning hen skal bevege seg (stigespill, monopol osv),
   og det finnes kun en vei å bevege seg fra ett felt til det neste.
   2 Problembeskrivelse - Brettspill/Boardgame 7
2. Spill der spilleren har flere alternative veier ut fra feltet hen står på (f.eks. «Ludo», «Den For-
   svunne diamant»), der spiller også kan gå tilbake samme vei hen kom («Den Forsvunne dia-
   mant»)
   For dette nivået skal dere bygge videre på nivå 1, og utvide mulighetene til brettspillet. Dere
   velger selv om dere vil f.eks. gå for en variant av «Monopol», «Den forsvunne diamant», eller om
   dere vil lage et helt nytt spill.
   2.3 Avgrensninger
   For begge nivåer gjelder følgende avgrensninger av oppgaven:
   •Spillet skal spilles av alle spillere på samme datamaskin (ingen nettverksbasert spill).
   •Spillet skal ha et grafisk brukergrensesnitt utviklet med biblioteket JavaFX uten bruk av
   FXML.
