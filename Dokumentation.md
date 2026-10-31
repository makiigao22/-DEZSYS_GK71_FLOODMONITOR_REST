# FloodMonitor – Projektdokumentation

## Umsetzung
Spring Boot mit Gradle und Java 17. Vier Messstationen werden in einem In-Memory-Repository gespeichert. Die Serviceschicht erzeugt Messwerte und berechnet Warnstufen; der Controller stellt REST-Endpunkte bereit. JSON und XML werden über den Accept-Header ausgewählt. Fehler werden zentral verarbeitet. Der jQuery-Webclient stellt Messwerte und Filter dar.

## Herausforderungen und Reflexion
Die ursprüngliche Vorlage generierte bei jedem Abruf zufällige Stationen, sodass keine konsistente Historie existierte. Daher wurde die Datenhaltung von der Simulation getrennt und eine zeitlich fortlaufende Messhistorie ergänzt. Ebenso wurden Datenmodell, Validierung und Fehlerbehandlung erweitert. Eine Datenbank und Zugriffsschutz wären sinnvolle nächste Schritte.

## Vorführung
1. `gradlew.bat bootRun` starten.
2. `http://localhost:8080/` öffnen und die Station wechseln.
3. XML auswählen und Tabelle prüfen.
4. `http://localhost:8080/api/v1/stations` als JSON anzeigen.
5. Mit `curl -H "Accept: application/xml" http://localhost:8080/api/v1/stations/ST-001` XML zeigen.
6. `http://localhost:8080/api/v1/stations/ST-999` für 404 testen.
7. `gradlew.bat test` für die automatisierten Tests ausführen.

## Screenshots
Screenshots müssen am eigenen Rechner nach erfolgreichem Start aufgenommen werden. Vorhandene Screenshots aus der Vorlage dokumentieren nur den ursprünglichen Starterstand.
