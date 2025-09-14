package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.*
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag.*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.ProgressCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Electricity
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Heat
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Achievement
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Energy
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.*
import java.util.*

private val progressCards: List<ProgressCard> = listOf(
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Kohlekraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 8,
        resourceCosts = 5,
        basePoints = 0,
        systemPoints = 1,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
            Energy(
                technology = Distribution,
                form = Heat,
                size = 2,
            ),
            Achievement("CCS")
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden. CO2 muss aus dem Abgas entfernt werden.",
        explanation = "Verbrennung von Kohle erzeugt Strom und die Abwärme ist nutzbar für Fernwärme.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Coal)
    ),
    *Array(4) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Photovoltaik auf dem Dach",
            imageSrc = "",
            supply = Energy(
                technology = Generation,
                form = Electricity,
                size = 1,
            ),

            moneyCosts = 1,
            resourceCosts = 1,
            basePoints = 3,
            systemPoints = 6,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Storage,
                    form = Electricity,
                    size = 1,
                ),
            ),
            requirementsDescription = "Um das volle Potenzial nutzen zu können, muss in sonnenreichen Stunden Strom gespeichert werden.",
            explanation = "Photovoltaik generiert aus Sonnenlicht Strom.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0,
            tags = tagsOf(Solar, Photovoltaic)
        )
    },
    *Array(4) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Balkon-Photovoltaik",
            imageSrc = "",
            supply = Energy(
                technology = Generation,
                form = Electricity,
                size = 1,
            ),

            moneyCosts = 1,
            resourceCosts = 1,
            basePoints = 3,
            systemPoints = 6,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Storage,
                    form = Electricity,
                    size = 1,
                ),
            ),
            requirementsDescription = "Um das volle Potenzial nutzen zu können, muss in sonnenreichen Stunden Strom gespeichert werden.",
            explanation = "Eine kleine Photovoltaikanalage auf deinem Balkon generiert aus Sonnenlicht Strom.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0,
            tags = tagsOf(Solar, Photovoltaic)
        )
    },
    *Array(4) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Solarthermie-Anlage auf Dach",
            imageSrc = "",
            supply = Energy(
                technology = Generation,
                form = Heat,
                size = 1,
            ),

            moneyCosts = 1,
            resourceCosts = 1,
            basePoints = 2,
            systemPoints = 5,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Storage,
                    form = Heat,
                    size = 1,
                ),
            ),
            requirementsDescription = "Um das volle Potenzial nutzen zu können, muss in sonnenreichen Stunden Wärme gespeichert werden.",
            explanation = "Solarthermieanlagen erzeugen aus Sonnenenergie warmes Wasser für deine Dusche und Heizung.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0,
            tags = tagsOf(Solar)
        )
    },
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Ölheizung",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Heat,
            size = 1,
        ),

        moneyCosts = 2,
        resourceCosts = 2,
        basePoints = 0,
        systemPoints = 1,
        supplyRequirementsForSystem = listOf(),

        requirementsDescription = "",
        explanation = "Eine Erdölheizung erzeugt durch Ölverbrennung Wärme für deine Heizung.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    *Array(4) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Erdwärmeheizung",
            imageSrc = "",
            supply = Energy(
                technology = Generation,
                form = Heat,
                size = 1,
            ),

            moneyCosts = 2,
            resourceCosts = 1,
            basePoints = 2,
            systemPoints = 6,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Storage,
                    form = Heat,
                    size = 1,
                ),
                Energy(
                    technology = Generation,
                    form = Electricity,
                    size = 1,
                ),
            ),
            requirementsDescription = "Um das volle Potenzial nutzen zu können, muss Wärme gespeichert werden.",
            explanation = "Die Wärme aus dem Erdboden wird für deine Heizung genutzt.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0
        )
    },
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Kleiner Windpark",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 2,
        ),

        moneyCosts = 2,
        resourceCosts = 2,
        basePoints = 6,
        systemPoints = 11,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Storage,
                form = Electricity,
                size = 2,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Der Strom muss verteilt werden. In windreichen Stunden muss Strom gespeichert werden.",
        explanation = "Windkraftanlagen nutzen den Wind um daraus Strom zu erzeugen.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Wind)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Großer Photovoltaik-Park",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 2,
        ),

        moneyCosts = 2,
        resourceCosts = 2,
        basePoints = 6,
        systemPoints = 11,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Storage,
                form = Electricity,
                size = 2,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Der Strom muss verteilt werden. In sonnenreichen Stunden muss Strom gespeichert werden.",
        explanation = "Mit Sonnenlicht wird im großen Maße Strom erzeugt.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Solar, Photovoltaic)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Gaskraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 6,
        resourceCosts = 4,
        basePoints = 0,
        systemPoints = 1,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
            Energy(
                technology = Distribution,
                form = Heat,
                size = 2,
            ),
            Achievement("CCS")
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden. CO2 muss aus dem Abgas entfernt werden.",
        explanation = "Durch das Verbrennen von Gas kann Stromerzeugt werden, die Abwärme kann für Fernwärme genutzt werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Gas)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Großer Windpark",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 3,
        resourceCosts = 3,
        basePoints = 9,
        systemPoints = 16,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Storage,
                form = Electricity,
                size = 3,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Der Strom muss verteilt werden. In windreichen Stunden muss Strom gespeichert werden.",
        explanation = "Viele Windkraftanalgen erzeugen aus Wind im großen Maße Strom.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Wind)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Geothermieheizwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Heat,
            size = 2,
        ),

        moneyCosts = 4,
        resourceCosts = 2,
        basePoints = 5,
        systemPoints = 14,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Heat,
                size = 2,
            ),
        ),
        requirementsDescription = "Die Wärme muss  verteilt werden.",
        explanation = "Durch Geothermie kann Erdwärme genutzt und ins Fernwärmenetz eingespeist werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Atomkraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 10,
        resourceCosts = 5,
        basePoints = 10,
        systemPoints = 18,
        supplyRequirementsForSystem = listOf(
            Achievement("Endlager"),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Strom muss verteilt und ein Endlager für radioaktiven Abfall gefunden werden.",
        explanation = "Durch Spaltung von Uran wird Strom und Wärme erzeugt.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Nuclear)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Laufwasserkraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 4,
        resourceCosts = 3,
        basePoints = 9,
        systemPoints = 16,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Der Strom muss verteilt werden.",
        explanation = "Durch die Flussströmung kann Strom erzeugt werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Water)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Offshore Windpark",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 7,
        resourceCosts = 4,
        basePoints = 12,
        systemPoints = 21,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
            Energy(
                technology = Storage,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Der Strom muss verteilt werden. In windreichen Stunden muss Strom gespeichert werden.",
        explanation = "Viele Windkraftanalgen erzeugen aus Wind vor der Küste Strom.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(Wind)
    ),
    *Array(4) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Luft-Wärmepumpe",
            imageSrc = "",
            supply = Energy(
                technology = Generation,
                form = Heat,
                size = 1,
            ),

            moneyCosts = 2,
            resourceCosts = 1,
            basePoints = 2,
            systemPoints = 6,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Generation,
                    form = Electricity,
                    size = 1,
                ),
            ),
            requirementsDescription = "Für den Betrieb der Wärmepumpe wird Strom benötigt.",
            explanation = "Luft-Wärmepumpe: Nutzt Strom um mit der Umgebungstemperatur zu heizen.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0
        )
    },
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Eisenkraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 9,
        resourceCosts = 4,
        basePoints = 10,
        systemPoints = 18,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
            Energy(
                technology = Distribution,
                form = Heat,
                size = 2,
            ),
            Achievement("CCS")
        ),
        requirementsDescription = "Bau auf Kohlekraftwerk: Zahle nur 4 Geldeinheiten und 1 Ressource. Strom und Wärme müssen verteilt werden.",
        explanation = "Strom und Abwärme werden durch Verbrennung von Eisen erzeugt. Dafür kann ein Kohlekraftwerk umgerüstet werden.",
        modifierCollection = ModifierCollection(
            cardMoneyCostsModifierConfig = ModifierConfig(
                rank = 10,
                modify = CardCostModifier.MoneyCostsBuildingIronOnCoal.modify
            ),
            cardResourceCostsModifierConfig = ModifierConfig(
                rank = 10,
                modify = CardCostModifier.ResourceCostsBuildingIronOnCoal.modify
            ),
        ),

        phaseIndex = 2,
        tags = tagsOf(Iron)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Biomassekraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 7,
        resourceCosts = 3,
        basePoints = 9,
        systemPoints = 16,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
            Energy(
                technology = Distribution,
                form = Heat,
                size = 1,
            ),
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden.",
        explanation = "Strom wird durch die Verbrennung von Biomasse, z.B. Altholz, erzeugt.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Erdkabel für kommunale Verteilung",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 1,
        ),

        moneyCosts = 1,
        resourceCosts = 3,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),

        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses Erdkabel den Straßen innerhalb eines Ortes. Es transportiert den Strom unterirdisch zu den Haushalten.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Erdkabel für regionale Verteilung",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 2,
        ),

        moneyCosts = 1,
        resourceCosts = 3,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),

        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses Erdkabel den Landstraßen.  Es transportiert den Strom unterirdisch innerhalb einer Region.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Freileitungen für überregionale Verteilung",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 2,
        resourceCosts = 1,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht diese Freileitung den Bundesstraßen. Sie transportiert den  Strom überirdisch zwischen Regionen.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Erdkabel für Stromübertragung auf weiten Strecken",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 10,
        resourceCosts = 3,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses Erdkabel den Autobahnen.  Es transportiert den Strom unterirdisch deutschlandweit über weite Strecken hin zu  Orten, an denen es viele Verbraucher gibt.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Oberirdische Höchstspannungsleitung",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 5,
        resourceCosts = 1,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht diese Freileitung den Autobahnen. Sie transportiert den Strom unterirdisch deutschlandweit über weite Strecken hin zu  Orten, an denen es viele Verbraucher gibt.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(OverheadPowerLine)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Freileitung Südlink",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 4,
        resourceCosts = 1,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Im Norden erzeugter Windstrom wird mittels  Hochspannungs-Gleichstrom-Übertragung überirdisch in den Süden  transportiert. Vergleichen wir das Strom- mit dem Straßennetz, so entspricht diese Freileitung den Autobahnen.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Erdkabel Südlink",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 10,
        resourceCosts = 3,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Im Norden erzeugter Windstrom wird mittels  Hochspannungs-Gleichstrom-Übertragung unterirdisch in den Süden  transportiert. Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses Erdkabel den Autobahnen.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Nahwärmenetz",
        imageSrc = "",
        supply = Energy(
            technology = Distribution,
            form = Heat,
            size = 1,
        ),

        moneyCosts = 4,
        resourceCosts = 2,
        basePoints = 0,
        systemPoints = 0,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Das Nahwärmenetz transportiert zentral erzeugte Wärme oder Abwärme aus Fabriken oder Kraftwerken innerhalb eines Wohngebiets für die eigene Wärmeversorgung zu Hause.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    *Array(2) {
        TechnologyCardData(
            id = UUID.randomUUID(),
            name = "Fernwärmenetz",
            imageSrc = "",
            supply = Energy(
                technology = Distribution,
                form = Heat,
                size = 2,
            ),

            moneyCosts = 7,
            resourceCosts = 2,
            basePoints = 0,
            systemPoints = 0,
            supplyRequirementsForSystem = listOf(),
            requirementsDescription = "Diese Karte allein  gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
            explanation = "Das Fernwärmenetz transportiert zentral erzeugte Wärme oder Abwärme aus Fabriken oder Kraftwerken über mehrere Kilometer für die eigene Wärmeversorgung zu Hause.",
            modifierCollection = ModifierCollection(),

            phaseIndex = 0
        )
    },
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "E-Autos als Speicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 1,
        ),

        moneyCosts = 1,
        resourceCosts = 1,
        basePoints = 4,
        systemPoints = 6,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 1,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Laden und Entladen von E-Autos kann helfen Netzschwankungen auszugleichen.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Wärmespeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Heat,
            size = 3,
        ),

        moneyCosts = 5,
        resourceCosts = 4,
        basePoints = 12,
        systemPoints = 20,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Heat,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Wärme die aus der Sonnenenergie oder Umwelttemperatur gewonnen wird, muss für die spätere Nutzung gespeichert werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Batteriespeicher im Haus",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 1,
        ),

        moneyCosts = 2,
        resourceCosts = 1,
        basePoints = 4,
        systemPoints = 6,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 1,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Strom aus der Sonnenenergie muss bis zum späteren Verbrauch in einer Batterie gespeichert werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 1,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Wasserstoffspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 8,
        resourceCosts = 3,
        basePoints = 6,
        systemPoints = 10,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird im Sommer für den Winter gespeichert.",
        explanation = "Mit Hilfe von Wasserstoff kann Energie gespeichert werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Großer Wasserstoffspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 10,
        resourceCosts = 4,
        basePoints = 8,
        systemPoints = 13,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird im Sommer für den Winter gespeichert.",
        explanation = "Mit Hilfe von Wasserstoff kann Energie gespeichert werden.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Schwungradspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 2,
        ),

        moneyCosts = 3,
        resourceCosts = 3,
        basePoints = 8,
        systemPoints = 13,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Elektrische Energie wird in der Drehung einer großen Masse gespeichert.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Fernwärmespeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Heat,
            size = 2,
        ),

        moneyCosts = 3,
        resourceCosts = 4,
        basePoints = 8,
        systemPoints = 13,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Heat,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Wärme wird in einem großen Speicher gleich für mehrere Haushalte gespeichert.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Lithium-Ionen-Batterie Park",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 7,
        resourceCosts = 3,
        basePoints = 12,
        systemPoints = 20,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Große Speicher werden genutzt um Schwankungen auszugleichen. Lithium-Ionen sind dabei besonders gut geeignet.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 1,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Natrium-Ionen-Batterie Park",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 7,
        resourceCosts = 3,
        basePoints = 12,
        systemPoints = 20,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Große Speicher werden genutzt um Schwankungen auszugleichen. Lithium-Ionen sind umweltfreundlich und günstig.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 1,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Bleiakkumulator",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 1,
        ),

        moneyCosts = 2,
        resourceCosts = 2,
        basePoints = 4,
        systemPoints = 6,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Sind schwere und robuste Batterien zur Notstromversorgung.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Redox-Flow-Batterie",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 8,
        resourceCosts = 3,
        basePoints = 11,
        systemPoints = 18,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Redox-Flow-Batterien eignen sich für Kurz- und Langzeitspeicherung, sind aber noch in Entwicklung.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Pumpspeicherkraftwerk",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 4,
        resourceCosts = 4,
        basePoints = 11,
        systemPoints = 18,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird gespeichert.",
        explanation = "Strom, welcher durch das Abfließen von Wasser aus einem Stausee ins Tal generiert wird.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0,
        tags = tagsOf(PumpStorage)
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Methanspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 9,
        resourceCosts = 4,
        basePoints = 8,
        systemPoints = 13,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird im Sommer für den Winter gespeichert.",
        explanation = "Speicher welche Gas oder grün erzeugtes Methan speichern können.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Ammoniakspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 10,
        resourceCosts = 3,
        basePoints = 6,
        systemPoints = 10,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Überschüssige Energie wird im Sommer für den Winter gespeichert.",
        explanation = "Ammoniak speichert Wasserstoff, der bei Bedarf freigesetzt und zur Energiegewinnung genutzt werden kann.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = UUID.randomUUID(),
        name = "Druckluftspeicher",
        imageSrc = "",
        supply = Energy(
            technology = Storage,
            form = Electricity,
            size = 4,
        ),

        moneyCosts = 6,
        resourceCosts = 4,
        basePoints = 12,
        systemPoints = 20,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Speicher entlasten das Netz, weil sie überschüssige Energie speichern und später abgeben, wenn zu wenig produziert wird.",
        explanation = "Luft wird in unterirdische Kavernen gepresst und kann bei Bedarf durch eine Turbine expandiert werden zur Energiefreisetzung.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 1
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Gebäudeisolation",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet jeweils 2 Einheiten Ressourcen und Geld.",
        explanation = "Gut isolierte Gebäude brauchen weniger Energie zum Heizen/Kühlen.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2) and updateMoneyEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Ausbau der Eisenbahn",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "",
        explanation = "Das Nutzen von öffentlichen Verkehrsmitteln reduziert Emissionen. Der Ausbau steigert die Attraktivität. Am Besten mit grünem Strom antreiben.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Verbrenner-Aus",
        imageSrc = "",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 2,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Das Verbieten von Neuzulassungen von Verbrennern ist eine sozialgerechte und effektive Möglichkeit die Emissionen im Verkehrssektor zu senken.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Subventionierung von E-Autos",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 2,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "",
        explanation = "Elektromobilität ist ein wichtiger Pfeiler der Verkehrswende.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "CO2-Abscheidung und -Speicherung",
        imageSrc = "",
        supply = Achievement("CCS"),
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Das von Kraftwerken ausgestoßene CO2 wird herausgefiltert. Dieses abgeschiedene CO2 kann als Rohstoff z.B. in der Chemie-Industrie dienen.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 1
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "H2-betriebene Schiffe",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Achievement("Chemie")
        ),
        requirementsDescription = "",
        explanation = "Wasserstoff (H2) gilt als alternativer grüner Kraftstoff. Die Nutzung bietet sich vor allem auf langen Strecken an, da Batterien nicht die notwendige Reichweite bieten können.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "E-Buslinien in ländlicher Region",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 3,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 3,
            ),
        ),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Das Nutzen von öffentlichen Verkehrsmitteln reduziert Emissionen. Auf dem Land ist die Verfügbarkeit jedoch noch sehr dünn.\u200B",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 1
    ),
    *Array(3) {
        ClimateCardData(
            id = UUID.randomUUID(),
            name = "Power-to-X",
            imageSrc = "",
            supply = Achievement("Chemie"),
            moneyCosts = 2,
            resourceCosts = 0,
            basePoints = 0,
            systemPoints = 2,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Generation,
                    form = Electricity,
                    size = 3,
                ),
                Energy(
                    technology = Distribution,
                    form = Electricity,
                    size = 3,
                ),
            ),
            requirementsDescription = "Für ein gebautes Gaskraftwerk erhaltet ihr 5 zusätzliche Fortschrittspunkte.",
            explanation = "Power-to-X Technologien speichern Stromüberschüsse aus erneuerbaren Energien und wandeln sie in chemische Energieträger (z.B. H2) für Langfristspeicherung und Verkehr um.",
            modifierCollection = ModifierCollection(),
            effect = updateProgressPointsEffect(5),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "CO2-neutraler Flugverkehr",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Achievement("Chemie")
        ),
        requirementsDescription = "",
        explanation = "Die Nutzung von synthetischem Kerosin bietet eine grünere Alternative zu fossilem Kraftstoff, die für Flugzeuge geeignet ist.",
        modifierCollection = ModifierCollection(),

        phaseIndex = 2
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Verbot von Inlandsflügen",
        imageSrc = "",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Generation,
                form = Electricity,
                size = 2,
            ),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Kurzstreckenflüge sind ineffizient, da Start und Landung viel Energie verbrauchen. Sie verursachen pro Person deutlich mehr CO₂ als Züge oder Busse.\u200B",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    *Array(2) {
        ClimateCardData(
            id = UUID.randomUUID(),
            name = "Kunsstoff-Recycling",
            imageSrc = "",
            supply = null,
            moneyCosts = 2,
            resourceCosts = 0,
            basePoints = 0,
            systemPoints = 2,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Generation,
                    form = Electricity,
                    size = 2,
                ),
                Energy(
                    technology = Distribution,
                    form = Electricity,
                    size = 2,
                ),
            ),
            requirementsDescription = "Ihr erhaltet jeweils 2 Einheiten Ressourcen und Geld.",
            explanation = "Recycelter Kunststoff wird gereinigt, zerkleinert und geschmolzen, um neue Produkte wie Verpackungen oder Bauteile herzustellen.",
            modifierCollection = ModifierCollection(),
            effect = updateResourcesEffect(2) and updateMoneyEffect(2),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Subventionierung von Erneuerbaren",
        imageSrc = "",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 2,
            ),
        ),
        requirementsDescription = "Alle Wind- und Photovoltaik-Technologien kosten 2 Geldeinheiten weniger, aber mindestens 1 Geldeinheit.",
        explanation = "Das Fördern von Erneuerbaren soll deren Ausbau Beschleunigen.",
        modifierCollection = ModifierCollection(
            cardMoneyCostsModifierConfig = ModifierConfig(
                rank = 10,
                modify = CardCostModifier.CostsWithSubventionOfWindAndPhotovoltaic.modify
            ),
            cardResourceCostsModifierConfig = ModifierConfig(
                rank = 10,
                modify = CardCostModifier.CostsWithSubventionOfWindAndPhotovoltaic.modify
            )
        ),

        phaseIndex = 0
    ),
    *Array(2) {
        ClimateCardData(
            id = UUID.randomUUID(),
            name = "Batterie-Recycling",
            imageSrc = "",
            supply = null,
            moneyCosts = 2,
            resourceCosts = 0,
            basePoints = 0,
            systemPoints = 2,
            supplyRequirementsForSystem = listOf(
                Energy(
                    technology = Generation,
                    form = Electricity,
                    size = 2,
                ),
                Energy(
                    technology = Distribution,
                    form = Electricity,
                    size = 2,
                ),
            ),
            requirementsDescription = "Ihr erhaltet jeweils 2 Einheiten Ressourcen und Geld.",
            explanation = "Recycling von Batterien ist wichtig, um wertvolle Rohstoffe zurückzugewinnen und sorgt für eine nachhaltigere Nutzung von Ressourcen.",
            modifierCollection = ModifierCollection(),
            effect = updateResourcesEffect(2) and updateMoneyEffect(2),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Pendeln mit dem Fahrrad",
        imageSrc = "",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Ab jetzt pendelt ihr mit dem Fahrrad zur Schule/Uni/Arbeit.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Sharing is Caring",
        imageSrc = "",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Statt viel Geld für immer neue Dinge auszugeben tauscht oder leiht ihr. Wenn dies nicht möglich ist, kauft ihr second-hand.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = UUID.randomUUID(),
        name = "Umweltbewusste Ernährung",
        imageSrc = "",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Ihr verzichtet weitestgehend auf tierische Produkte, achtest auf Regionalität und Saisonalität und verringert Lebensmittelverschwendung.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
).mapNotNull {
    when (it) {
        is TechnologyCardData -> it.toTechnologyCard()
        is ClimateCardData -> it.toClimateCard()
        else -> null
    }
}

private fun tagsOf(vararg tags: Tag): List<String> = tags.map { it.name }
