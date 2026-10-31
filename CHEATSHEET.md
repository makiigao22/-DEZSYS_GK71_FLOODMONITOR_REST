# Abgabegespräch – FloodMonitor

- **REST:** HTTP-Endpunkte liefern Ressourcen wie Stationen und Messwerte.
- **GET:** Daten lesen; **POST:** Station erstellen (201); **PATCH:** teilweise ändern; **DELETE:** löschen (204).
- **JSON/XML:** Der `Accept`-Header steuert das Antwortformat; ohne spezifischen Header ist JSON vorgesehen.
- **Controller:** Nimmt HTTP-Anfragen entgegen und ruft Service auf; keine umfangreiche Geschäftslogik.
- **Service:** Simulation, Validierung, Warnstufen und Filter.
- **Repository:** Speichert Stationen und Messhistorie im RAM.
- **Model:** Java-Records und Enums für die Daten.
- **Simulation:** `@Scheduled` führt regelmäßig `tick()` aus; Intervall in `application.properties`.
- **Warnstufen:** `NORMAL` unter Warnschwelle, `WARNING` ab Warnschwelle, `CRITICAL` ab kritischer Schwelle.
- **Fehler:** Zentrale `@RestControllerAdvice`; 400 ungültige Eingabe, 404 unbekannte ID, 406 falscher Accept-Header.
- **Webclient:** jQuery AJAX lädt API-Daten; XML wird geparst; Tabelle aktualisiert sich alle 10 Sekunden.
- **Tests:** `gradlew.bat test`; Spring MockMvc prüft Endpunkte und HTTP-Codes.
- **Einschränkung:** Keine persistente Datenbank und keine produktive Authentifizierung; Daten verschwinden beim Neustart.
