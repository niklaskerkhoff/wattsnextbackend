package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.CardCostModifier
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.Tag.*
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.TechnologyCardData
import de.niklaskerkhoff.wattsnextbackend.model.core.TechnologyColumn
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.AchievementName
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Electricity
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Heat
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Achievement
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Energy
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.*

val technologyCards = listOf(
    TechnologyCardData(
        id = "5bcb9299-43b8-4ce2-81d3-b507d5d1a52b",
        name = "Kohlekraftwerk",
        imageSrc = "Kohlekraftwerk.png",
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
            Achievement(AchievementName.CarbonCapture)
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden. CO2 muss aus dem Abgas entfernt werden.",
        explanation = "Verbrennung von Kohle erzeugt Strom und die Abwärme ist nutzbar für Fernwärme.",

        phaseIndex = 0,
        tags = tagsOf(Coal)
    ),
    *Array(3) {
        TechnologyCardData(
            id = "12cf4f5f-0cdd-44d6-aff3-649286417c0$it",
            name = "Photovoltaik auf dem Dach",
            imageSrc = "Photovoltaik auf dem Dach.png",
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

            phaseIndex = 0,
            tags = tagsOf(Solar, Photovoltaic)
        )
    },
    *Array(3) {
        TechnologyCardData(
            id = "9e988a4e-0dce-48bf-9a06-4d794c3ee37$it",
            name = "Balkon-Photovoltaik",
            imageSrc = "Balkon-Photovoltaik.png",
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

            phaseIndex = 0,
            tags = tagsOf(Solar, Photovoltaic)
        )
    },
    *Array(3) {
        TechnologyCardData(
            id = "5dbb90d4-68a2-4ea5-9013-e234bf39e55$it",
            name = "Solarthermie-Anlage auf Dach",
            imageSrc = "Solarthermie-Anlage auf Dach.png",
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

            phaseIndex = 0,
            tags = tagsOf(Solar)
        )
    },
    TechnologyCardData(
        id = "303c8221-1646-4126-82bf-c348badb2548",
        name = "Ölheizung",
        imageSrc = "Ölheizung.png",
        supply = Energy(
            technology = Generation,
            form = Heat,
            size = 1,
        ),

        moneyCosts = 2,
        resourceCosts = 2,
        basePoints = 0,
        systemPoints = 1,
        supplyRequirementsForSystem = listOf(
            Achievement(AchievementName.ChemicalEnergy)
        ),

        requirementsDescription = "Ölheizungen können mit grünem Brennstoff aus „Power-to-X“-Technologie betrieben werden.",
        explanation = "Eine Erdölheizung erzeugt durch Ölverbrennung Wärme für deine Heizung.",

        phaseIndex = 0
    ),
    *Array(3) {
        TechnologyCardData(
            id = "a9f174a8-e9fe-460f-aeca-62957be683e$it",
            name = "Erdwärmeheizung",
            imageSrc = "Erdwärmeheizung.png",
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

            phaseIndex = 0
        )
    },
    TechnologyCardData(
        id = "e2ab22fe-9bbc-4e86-aaac-91aa9ce514c4",
        name = "Kleiner Windpark",
        imageSrc = "Kleiner Windpark.png",
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

        phaseIndex = 0,
        tags = tagsOf(Wind)
    ),
    TechnologyCardData(
        id = "f3b60f9e-c83a-489e-be80-33f87753a730",
        name = "Photovoltaik-Park",
        imageSrc = "Photovoltaik-Park.png",
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

        phaseIndex = 1,
        tags = tagsOf(Solar, Photovoltaic)
    ),
    TechnologyCardData(
        id = "41bd396b-ab06-4350-8fa4-5777c32338b2",
        name = "Gaskraftwerk",
        imageSrc = "Gaskraftwerk.png",
        supply = Energy(
            technology = Generation,
            form = Electricity,
            size = 3,
        ),

        moneyCosts = 6,
        resourceCosts = 4,
        basePoints = 6,
        systemPoints = 11,
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
            Achievement(AchievementName.CarbonCapture)
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden. CO2 muss aus dem Abgas entfernt werden.",
        explanation = "Durch das Verbrennen von Gas kann Stromerzeugt werden, die Abwärme kann für Fernwärme genutzt werden.",

        phaseIndex = 0,
        tags = tagsOf(Gas)
    ),
    TechnologyCardData(
        id = "a6bedca0-e564-4223-b86b-65943b83fe9a",
        name = "Großer Windpark",
        imageSrc = "Großer Windpark.png",
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
        requirementsDescription = "Bei Bau auf „Kleiner Windpark“: Zahle nur 1 Geldeinheiten und 1 Ressource. Der Strom muss verteilt und in windreichen Stunden gespeichert werden.",
        explanation = "Viele Windkraftanalgen erzeugen aus Wind im großen Maße Strom.",

        phaseIndex = 1,
        tags = tagsOf(Wind)
    ),
    TechnologyCardData(
        id = "a3bac719-409b-47fd-b855-f40b6c16aa63",
        name = "Geothermieheizwerk",
        imageSrc = "Geothermieheizwerk.png",
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

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "266015d0-0d83-4b85-b627-084770a46a99",
        name = "Atomkraftwerk",
        imageSrc = "Atomkraftwerk.png",
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
            Achievement(AchievementName.NuclearWasteRepository),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Strom muss verteilt und ein Endlager für radioaktiven Abfall gefunden werden.",
        explanation = "Durch Spaltung von Uran wird Strom und Wärme erzeugt.",

        phaseIndex = 0,
        tags = tagsOf(Nuclear)
    ),
    TechnologyCardData(
        id = "da34a02f-9352-4611-aa41-8ffc5d2038c5",
        name = "Laufwasserkraftwerk",
        imageSrc = "Laufwasserkraftwerk.png",
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

        phaseIndex = 0,
        tags = tagsOf(Water)
    ),
    TechnologyCardData(
        id = "59c1765e-f5df-425b-86d4-85c92e073ad2",
        name = "Offshore Windpark",
        imageSrc = "Offshore Windpark.png",
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
        explanation = "Viele Windkraftanlagen vor der Küste erzeugen Strom aus Wind.",

        phaseIndex = 1,
        tags = tagsOf(Wind)
    ),
    // Three Wärmepumpen in phase 0 and one in phase 1
    *Array(3) {
        TechnologyCardData(
            id = "88eb5f2d-72c7-4f95-9fdb-31b0f740c51$it",
            name = "Luftwärmepumpe",
            imageSrc = "Luftwärmepumpe.png",
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
            explanation = "Die Luftwärmepumpe nutzt Strom, um mit der Umgebungswärme zu heizen.",

            phaseIndex = 0
        )
    },
    TechnologyCardData(
        id = "bda7f4ab-cbee-427a-a420-397c6876ecc5",
        name = "Luftwärmepumpe",
        imageSrc = "Luftwärmepumpe.png",
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
        explanation = "Die Luftwärmepumpe nutzt Strom, um mit der Umgebungswärme zu heizen.",

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = "d73b3ca3-416d-4528-8a1c-29968cb8e93e",
        name = "Eisenkraftwerk",
        imageSrc = "Eisenkraftwerk.png",
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
            Achievement(AchievementName.CarbonCapture)
        ),
        requirementsDescription = "Bei Bau auf Kohlekraftwerk: Zahle nur 4 Geldeinheiten und 1 Ressource. Strom und Wärme müssen verteilt werden.",
        explanation = "Eisen wird verbrannt und erzeugt Strom und Wärme, z. B. in umgerüsteten Kohlekraftwerken.",
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
        id = "c074e451-ec2e-4e99-979e-8f4d2c2321a5",
        name = "Biomassekraftwerk",
        imageSrc = "Biomassekraftwerk.png",
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

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "b8299381-19a2-4332-9193-fbdee721a17c",
        name = "Erdkabel für kommunale Verteilung",
        imageSrc = "Erdkabel für kommunale Verteilung.png",
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
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses Kabel den Straßen innerhalb eines Ortes.",

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "2d75760f-493c-4b55-a225-7a6770c08c0c",
        name = "Erdkabel für regionale Verteilung",
        imageSrc = "Erdkabel für regionale Verteilung.png",
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

        requirementsDescription = "Diese Karte allein gibt keine Punkte. Ein stabiles Energieverteilungsnetz ist eine Grundvoraussetzung für das Energiesystem.",
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses unterirdische Erdkabel den Landstraßen. ",

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "4dba5490-7df7-4ed2-8467-00594731bcd3",
        name = "Freileitungen für überregionale Verteilung",
        imageSrc = "Freileitungen für überregionale Verteilung.png",
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
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht diese überirdische Freileitung den Bundesstraßen.",

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "123d010d-b44c-4f7b-907c-d538760f529f",
        name = "Erdkabel für Stromübertragung auf weiten Strecken",
        imageSrc = "Erdkabel für Stromübertragung auf weiten Strecken.png",
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
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht dieses unterirdische Erdkabel den Autobahnen. ",

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = "68b8150b-f6f5-40e7-b294-a0a7fef7c497",
        name = "Oberirdische Höchstspannungsleitung",
        imageSrc = "Oberirdische Höchstspannungsleitung.png",
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
        explanation = "Vergleichen wir das Strom- mit dem Straßennetz, so entspricht diese überirdische Freileitung den Autobahnen.",

        phaseIndex = 1,
        tags = tagsOf(OverheadPowerLine)
    ),
    TechnologyCardData(
        id = "07511e44-b130-4cb0-923f-786e07ebe097",
        name = "Freileitung Südlink",
        imageSrc = "Freileitung Südlink.png",
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
        explanation = "Im Norden erzeugter Strom aus Wind wird über diese \"Stromautobahn\" überirdisch in den Süden transportiert.",

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = "1f731e92-cada-4594-a199-b43ca6d39214",
        name = "Erdkabel Südlink",
        imageSrc = "Erdkabel Südlink.png",
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
        explanation = "Im Norden erzeugter Strom aus Wind wird über diese \"Stromautobahn\" unterirdisch in den Süden transportiert.",

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = "a0240b4f-eeb4-4c58-896a-9f024ca5b91f",
        name = "Nahwärmenetz",
        imageSrc = "Nahwärmenetz.png",
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
        explanation = "(Ab-)wärme aus der Industrie wird in nahen Wohngebieten für die Wärmeversorgung zu Hause verwendet.",

        phaseIndex = 0
    ),
    *Array(2) {
        TechnologyCardData(
            id = "befae368-dc10-436c-b574-4104371f488$it",
            name = "Fernwärmenetz",
            imageSrc = "Fernwärmenetz.png",
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
            explanation = "(Ab-)wärme aus der Industrie wird kilometerweit transportiert und für die Wärmeversorgung zu Hause verwendet.",

            phaseIndex = 0
        )
    },
    *Array(3) {
        TechnologyCardData(
            id = "c0784e6a-ca8f-47d3-9983-10161ab563a$it",
            name = "E-Autos als Speicher",
            imageSrc = "E-Autos als Speicher.png",
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
            explanation = "Angebotsorientiertes Laden und Entladen von E-Autos kann helfen, Netzschwankungen auszugleichen.",

            phaseIndex = 2
        )
    },
    TechnologyCardData(
        id = "401a3542-3efa-49b9-b255-98f12b1afb53",
        name = "Wärmespeicher",
        imageSrc = "Wärmespeicher.png",
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
        explanation = "Wärme, die aus der Sonnenenergie oder Umwelt gewonnen wird, kann für die spätere Nutzung gespeichert werden.",

        phaseIndex = 1
    ),
    *Array(3) {
        TechnologyCardData(
            id = "dd1124ef-3e86-4c0f-93b8-2fa1c7aa5eea",
            name = "Batteriespeicher im Haus",
            imageSrc = "Batteriespeicher im Haus.png",
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

            phaseIndex = 1,
            tags = tagsOf(Battery)
        )
    },
    TechnologyCardData(
        id = "c31cace0-27b9-4ddf-b883-97a65f843b1b",
        name = "Wasserstoffspeicher",
        imageSrc = "Wasserstoffspeicher.png",
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

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = "d44498dc-d5e5-468b-9c53-64a099f43eb9",
        name = "Großer Wasserstoffspeicher",
        imageSrc = "Großer Wasserstoffspeicher.png",
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

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = "0782f1d9-19e9-497a-b5c7-eea3794f7f97",
        name = "Schwungradspeicher",
        imageSrc = "Schwungradspeicher.png",
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

        phaseIndex = 1
    ),
    TechnologyCardData(
        id = "ed02f805-1eaa-4e9b-ae54-57f64b689403",
        name = "Fernwärmespeicher",
        imageSrc = "Fernwärmespeicher.png",
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

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "8460f3d7-fdf8-4359-b578-b81f86e28804",
        name = "Lithium-Ionen-Batterie Park",
        imageSrc = "Lithium-Ionen-Batterie Park.png",
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
        explanation = "Effiziente Lithium-Ionen-Batterien speichern Strom und helfen, das Netz bei Schwankungen stabil zu halten.",

        phaseIndex = 1,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = "459a1b59-7167-4c10-a5b7-5d5dfcce3d9f",
        name = "Natrium-Ionen-Batterie Park",
        imageSrc = "Natrium-Ionen-Batterie Park.png",
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
        explanation = "Natrium-Ionen-Batterien speichern Strom und gleichen Schwankungen im Netz zuverlässig aus.",

        phaseIndex = 1,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = "9b6479fb-460e-47f4-bbb0-204bda139225",
        name = "Bleiakkumulator",
        imageSrc = "Bleiakkumulator.png",
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
        explanation = "Bleiakkumulatoren sind schwere und robuste Batterien zur Notstromversorgung.",

        phaseIndex = 0
    ),
    TechnologyCardData(
        id = "fe2a3bc6-6edc-4651-bd5b-aee6a4d3753e",
        name = "Redox-Flow-Batterie",
        imageSrc = "Redox-Flow-Batterie.png",
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

        phaseIndex = 2,
        tags = tagsOf(Battery)
    ),
    TechnologyCardData(
        id = "c54b2455-f3ed-4d54-a61f-0aa813777600",
        name = "Pumpspeicherkraftwerk",
        imageSrc = "Pumpspeicherkraftwerk.png",
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
        explanation = "Ein Pumpspeicherkraftwerk speichert Energie, indem es Wasser hochpumpt und später zur Stromerzeugung wieder abfließen lässt.",

        phaseIndex = 1,
        tags = tagsOf(PumpStorage)
    ),
    TechnologyCardData(
        id = "6b53a792-7547-47e1-ba1c-f0be8da67a7d",
        name = "Methanspeicher",
        imageSrc = "Methanspeicher.png",
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
        explanation = "Methangas kann sicher aufbewahrt werden, um es später als Energiequelle zu nutzen.",

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = "dc1cafa5-abb6-4ebe-917b-ffd606d3349c",
        name = "Ammoniakspeicher",
        imageSrc = "Ammoniakspeicher.png",
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

        phaseIndex = 2
    ),
    TechnologyCardData(
        id = "e2ec18e3-2a32-4fb3-8570-b790ace86165",
        name = "Druckluftspeicher",
        imageSrc = "Druckluftspeicher.png",
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
        explanation = "Luft wird in unterirdische Kavernen gepresst. Bei Bedarf wird sie wieder ausgedehnt und dadurch Energie freigesetzt.",

        phaseIndex = 1
    ),
).map { it.toTechnologyCard() }

private val startWithOilHeatingCard =
    TechnologyCardData(
        id = "079f1c00-f57b-41c9-8a2d-8c9cd8ea4f85",
        name = "Ölheizung",
        imageSrc = "Ölheizung.png",
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

        phaseIndex = 0
    ).toTechnologyCard()

private val startWithCoalCard =
    TechnologyCardData(
        id = "f8705fb3-a3fd-4301-8a79-6c32d67f8ea8",
        name = "Kohlekraftwerk",
        imageSrc = "Kohlekraftwerk.png",
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
            Achievement(AchievementName.CarbonCapture)
        ),
        requirementsDescription = "Strom und Wärme müssen verteilt werden. CO2 muss aus dem Abgas entfernt werden.",
        explanation = "Verbrennung von Kohle erzeugt Strom und die Abwärme ist nutzbar für Fernwärme.",

        phaseIndex = 0,
        tags = tagsOf(Coal)
    ).toTechnologyCard()

private val startWithNuclearCard =
    TechnologyCardData(
        id = "fa0e41f2-98f7-4d5d-a417-1a534eae7c17",
        name = "Atomkraftwerk",
        imageSrc = "Atomkraftwerk.png",
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
            Achievement(AchievementName.NuclearWasteRepository),
            Energy(
                technology = Distribution,
                form = Electricity,
                size = 4,
            ),
        ),
        requirementsDescription = "Strom muss verteilt und ein Endlager für radioaktiven Abfall gefunden werden.",
        explanation = "Durch Spaltung von Uran wird Strom und Wärme erzeugt.",

        phaseIndex = 0,
        tags = tagsOf(Nuclear)
    ).toTechnologyCard()

private val startDistributionCard =
    TechnologyCardData(
        id = "64a64ef7-f94a-41fc-943f-f2590e8a981d",
        name = "Freileitungen für überregionale Verteilung",
        imageSrc = "Freileitungen für überregionale Verteilung.png",
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

        phaseIndex = 0
    ).toTechnologyCard()

val startCards = listOf(startWithOilHeatingCard, startWithCoalCard, startWithNuclearCard, startDistributionCard)

val startGenerationCardsWithCoal: TechnologyColumn =
    listOf(
        listOf(startWithCoalCard),
        listOf(startWithOilHeatingCard),
        emptyList(),
    )

val startGenerationCardsWithNuclear: TechnologyColumn =
    listOf(
        emptyList(),
        listOf(startWithOilHeatingCard),
        listOf(startWithNuclearCard),
    )

val startDistributionCards: TechnologyColumn = listOf(
    listOf(startDistributionCard),
    emptyList(),
    emptyList()
)

private fun tagsOf(vararg tags: Tag): List<String> = tags.map { it.name }
