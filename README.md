# FloodMonitor REST – GK71

Spring Boot 3.2 / Java 17 / Gradle. Hochwasser-Messstationen mit simulierten Messwerten, JSON/XML, Filtern, Statistik, Alerts und Webdashboard.

## Start

Unter Windows: `gradlew.bat bootRun` (im Projektordner); Linux/macOS: `./gradlew bootRun`. Voraussetzung: Java 17 und Internet beim ersten Gradle-Start. Danach `http://localhost:8080/` öffnen. Swagger UI: `http://localhost:8080/swagger-ui/index.html`. Tests: `gradlew.bat test` bzw. `./gradlew test`.

## API

Alle Routen beginnen mit `/api/v1`:

| Methode | Pfad | Funktion |
|---|---|---|
| GET | `/stations` | Alle Stationen; Filter `river`, `isActive`, `warningLevel` |
| GET | `/stations/{stationId}` | Einzelne Station |
| GET | `/stations/{stationId}/measurements/latest` | Letzter Messwert |
| GET | `/stations/{stationId}/measurements` | Historie; `from`, `to` (ISO-8601 UTC), `limit` (1–1000), `warningLevel` |
| GET | `/stations/{stationId}/statistics` | Statistik; `from`, `to` |
| GET | `/alerts` | Aktuelle Warnungen; `warningLevel`, `river` |
| POST | `/stations` | Neue Station (JSON/XML) |
| PATCH | `/stations/{stationId}` | Station ändern (JSON) |
| DELETE | `/stations/{stationId}` | Station löschen |

JSON (Standard): `curl -H "Accept: application/json" http://localhost:8080/api/v1/stations`.
XML: `curl -H "Accept: application/xml" http://localhost:8080/api/v1/stations/ST-001/measurements/latest`.
Filter: `curl "http://localhost:8080/api/v1/stations/ST-001/measurements?limit=10&warningLevel=WARNING"`.
Fehler: 400 ungültiger Parameter, 404 unbekannte Station, 406 Format nicht unterstützt, 409 Station existiert bereits, 415 falscher Request-Medientyp. Kein Login implementiert; administrative Endpunkte sind nur für eine lokale Demo vorgesehen.

## Architektur

- `model`: `Station`, `Location`, `Measurement`, `WarningLevel`, `StationStatus`, `Alert`, `Statistics`.
- `repository`: In-Memory-Datenhaltung mit threadsicheren Maps/Listen.
- `service`: Grenzwertlogik, Historie, Simulation, Filter, Statistik und Stationsverwaltung.
- `controller`: REST-Routen und zentrale Fehlerbehandlung.
- `static/index.html`: HTML/CSS/jQuery-Webclient mit JSON/XML-Umschaltung.

## Simulationsregeln

Vier Stationen werden initialisiert, jede mit 21 zeitlich gestaffelten Messungen. Alle 10 Sekunden (einstellbar über `simulation.interval` in Millisekunden) entsteht pro aktiver und funktionsfähiger Station eine Messung. Der neue Wasserstand basiert auf dem vorherigen Wert, Niederschlag und kleiner Zufallsvariation. Wasserstand >= 0, Akku 0–100, Durchfluss steigt mit dem Wasserstand. Akku unter 5% löst Wartungsstatus aus; dann entstehen keine weiteren Messungen. Warnstufe: unter Warnschwelle NORMAL, ab Warnschwelle WARNING, ab kritischer Schwelle CRITICAL, ungültige Werte UNKNOWN. Die Tests prüfen die Schwellen deterministisch; das Simulationsintervall ist für Tests sehr groß.

## Dokumentation und Hinweise

Daten sind nur im Arbeitsspeicher und gehen beim Neustart verloren. Für den Produktiveinsatz wären Persistenz, Authentifizierung/Autorisierung, TLS und weitere Tests erforderlich. Der Webclient lädt jQuery über CDN (Internetverbindung notwendig). Screenshots bitte nach dem lokalen Start selbst aufnehmen. Der ZIP-Starter enthielt eine unvollständige `Dokumentation.md`; sie wird hier durch diese README ergänzt.
