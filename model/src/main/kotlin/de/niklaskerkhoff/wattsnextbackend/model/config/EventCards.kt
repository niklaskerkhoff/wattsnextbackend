package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard.EffectDescription
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.EffectType.*
import java.util.*


val eventCards = listOf(
    EventCard(
        id = UUID.fromString("c1ea1fd9-6882-4247-a5b3-6db5c311fc86"),
        name = "Langer und kalter Winter",
        eventDescription = "Diesen Winter sind die Temperaturen niedriger als üblich und unzureichend isolierte Häuser benötigen mehr Energie zum Heizen als in normalen Wintern.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Die Nachfrage nach  Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                type = EnergySystem,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateCurrentPhaseTarget(1),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("a4636f5f-742b-4299-a49f-efeb8ce054d5"),
        name = "Bewölktes Wetter",
        eventDescription = "Das Wetter ist bewölkt, es gibt seit Wochen keinen Sonnenschein. Solarkraftwerke produzieren kaum Strom.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Photovoltaik- und Solarthermie- Technologien zählen mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 5,
                modify = SupplyListModifier.BasePointsForSolar.modify
            )
        ),
        effect = { Pair(it, emptyList()) },
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("e76b0f7f-5029-420e-aa34-ab96363990c3"),
        name = "Windflaute",
        eventDescription = "Es herrscht wochenlang Windstille. Windkraftanlagen produzieren keinen Strom.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Windtechnologien zählen mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 5,
                modify = SupplyListModifier.BasePointsForWind.modify
            )
        ),
        effect = { Pair(it, emptyList()) },
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("f73c682c-7b1a-4a4c-bc57-ec12db07e6c1"),
        name = "Alles Gute zum neuen Jahr",
        eventDescription = "Ein neues Jahr beginnt mit einigen teuren Partys und Feuerwerken. Auch die Reinigung der Städte und die Versorgung der Verletzten sind teuer.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Ihr verliert pro Spieler 1 Geldeinheit.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyPerPlayerEffect(-1),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("23698ead-e0fc-4394-a47d-51d5120a7912"),
        name = "Sonniges Wetter",
        eventDescription = "Produzierter Solarstrom, der den aktuellen Bedarf in Deutschland übersteigt, wird ins Ausland verkauft.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 3 Einheiten erhöht.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Speicher zählen mit ihrer Systempunktzahl in die Fortschrittspunkte, da sie vollständig geladen wurden.",
                type = Points,
            ),
        ),
        effectConditionDescription = "Falls Solartechnologien im Energiesystem vorhanden:",
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 4,
                modify = SupplyListModifier.SystemPointsForSolar.modify
            )
        ),
        effect = ifSolarIsExisting(updateMoneyEffect(3)),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("235dfaae-ee5d-4ecf-bb0d-9868660fc8a4"),
        name = "Nukleare Katastrophe",
        eventDescription = "Eine Kernschmelze löst eine weltweite nukleare Katastrophe aus.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Energiesystem wird auf den Startzustand zurückgesetzt.  War ein Atomkraftwerk auf dem Startfeld, wird auch dieses entfernt.",
                type = EnergySystem,
            ),
        ),
        effectConditionDescription = "Falls Atomkraftwerk im Energiesystem vorhanden:",
        modifierCollection = ModifierCollection(),
        effect = nuclearCatastropheIfExistingEffect(),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("8a7edb80-cc91-48c2-af3f-5fd0483f4e9d"),
        name = "Windiges Wetter",
        eventDescription = "Produzierter Windstrom, der den aktuellen Bedarf in Deutschland übersteigt, wird ins Ausland verkauft.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten erhöht.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Speicher zählen mit ihrer Systempunktzahl in die Fortschrittspunkte, da sie vollständig geladen wurden.",
                type = Points,
            ),
        ),
        effectConditionDescription = "Falls Windtechnologien im Energiesystem vorhanden:",
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 5,
                modify = SupplyListModifier.SystemPointsForStorage.modify
            )
        ),
        effect = ifWindIsExisting(updateMoneyEffect(2)),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("abd495fa-5f77-426a-b44d-6984ed19a196"),
        name = "Blackout durch Cyberangriff",
        eventDescription = "Ein großer Teil des deutschen Stromnetzes bricht zusammen. Das wird teuer...",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Große Erzeugungstechnologien (≥3 Energieeinheiten) zählen nur mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 5,
                modify = SupplyListModifier.BasePointsForLargeGeneration.modify
            )
        ),
        effect = updateMoneyEffect(-2),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("c6e271d1-aa0d-4fce-ab4b-0a3c174705c3"),
        name = "Rebound-Effekt",
        eventDescription = "Der Großteil der Wohngebäude wurde neu gedämmt. Mit dem guten Gefühl, dadurch Geld zu sparen und etwas Gutes fürs Klima zu machen, heizen die Bewohner*innen jetzt umso mehr, weshalb keine Energie eingespart wird.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 1 Einheit reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Die Nachfrage nach  Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                type = EnergySystem,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(-1) and updateCurrentPhaseTarget(1),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("17917efd-d4ac-42d5-992b-78e1886713fa"),
        name = "Batteriepreis sinkt",
        eventDescription = "Die Herstellung von Batteriespeichern wurde effizienter und damit günstiger.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Batterie-Speichertechnologien kosten in dieser Phase 2 Geldeinheiten weniger.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(
            // TODO: Isn't there a parameter 2 missing
            cardMoneyCostsModifierConfig = ModifierConfig(
                rank = 5,
                modify = CardCostModifier.CostsWithBatteryImproved.modify
            )
        ),
        effect = { Pair(it, emptyList()) },
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("c433dc78-f691-461a-aacb-88695b8d6ad2"),
        name = "Lockdown wegen Viruspandemie",
        eventDescription = "Bei einem Lockdown stehen viele Bereiche still: Weniger Produktion, weniger Verkehr, mehr Homeoffice. Weniger Mobilität und Produktion bedeuten auch weniger Energieverbrauch.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 1 Einheit reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Die Nachfrage nach Energie sinkt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit reduziert.",
                type = EnergySystem,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(-1) and updateCurrentPhaseTarget(-1),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("ede9c64a-f8c0-43d1-9d1e-9185707915fb"),
        name = "Neues aus der Photovoltaikforschung",
        eventDescription = "Photovoltaikanlagen sind jetzt langlebiger. Außerdem wurde eine Recycling-Methode entwickelt. Das spart Geld und Ressourcen.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld und Ressourcen werden jeweils um 2 Einheiten erhöht, falls Photovoltaik-Technologien im Energiesystem vorhanden sind.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2) and updateMoneyEffect(2),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("a44b062f-4795-4486-9e68-87277a77105e"),
        name = "Milder Winter",
        eventDescription = "Es muss weniger geheizt werden, als sonst.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld erhöht sich um 2 Einheiten.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Die Nachfrage nach  Energie sinkt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit reduziert.",
                type = EnergySystem,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(2) and updateCurrentPhaseTarget(-1),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("53b46e11-a4b3-4944-9fc9-16f10d3f20fd"),
        name = "Klimaziele verpasst",
        eventDescription = "Deutschland hat seine Klimaziele verpasst. Dafür muss jetzt Strafe gezahlt werden.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld wird um 6 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(-6),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("9a949a40-6f18-4add-969e-18910b13033a"),
        name = "Sturmschäden",
        eventDescription = "Eine Sturmfront, die über Deutschland hinweg zog, zerstörte die Überlandleitungen im ganzen Land. Viele Regionen haben Schwierigkeiten, ihren Bedarf zu decken.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            // TODO: Eigentlich nur "Bereits gebaute oberirdische Stromleitungen ..."
            EffectDescription(
                text = "Bereits gebaute oberirdische Stromleitungen zählen nicht zur Erfüllung der Voraussetzung anderer Fortschrittskarten und der Phasenziele. ",
                type = EnergySystem,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyModifierConfig = ModifierConfig(
                rank = 100,
                modify = SupplyModifier.NoSupplyFromOverheadPowerLine.modify
            )
        ),
        effect = updateMoneyEffect(-2),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.fromString("1298409a-9359-4104-bd01-c39b7b4048fa"),
        name = "Hitzewelle",
        eventDescription = "Während Hitzewellen steigt die Nachfrage nach Strom und die Leistung von thermischen Kraftwerken nimmt wegen sinkender Kühlleistung ab. Die Klimakrise erhöht die Wahrscheinlichkeit für Hitzewellen.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Die Nachfrage nach Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                type = EnergySystem,
            ),
            EffectDescription(
                text = "Kohle-, Gas-, und Nukleare Kraftwerke zählen in dieser Runde nur mit den Basispunkten in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 10,
                modify = SupplyListModifier.BasePointsForCoalAndGasAndNuclear.modify
            )
        ),
        effect = updateMoneyEffect(-2) and updateCurrentPhaseTarget(1),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("b71335de-a8cf-4e7f-9e02-3f436b53832d"),
        name = "Überschwemmung",
        eventDescription = "Eine Folge des Klimawandels sind häufigere Überschwemmungen. Das hat negative Auswirkungen auf jegliche Infrastruktur, auch im Energiesystem. Es müssen teure Reparaturen durchgeführt werden.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 5 Einheiten verringert.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(-5),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("8a5ac688-e7b3-4613-bb4e-f5f50cb9829e"),
        name = "Großflächiger Waldbrand",
        eventDescription = "Waldbrände infolge des Klimawandels schaden Umwelt und Energieinfrastruktur. Rauch und Feinstaub beeinträchtigen Photovoltaikanlagen - sie produzieren weniger Strom.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld und Ressourcen werden um je 2 Einheiten verringert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Photovoltaik- und Solarthermie- Technologien zählen in dieser Runde mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 10,
                modify = SupplyListModifier.BasePointsForSolar.modify
            )
        ),
        effect = updateMoneyEffect(-2) and updateResourcesEffect(-2),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("6c66a4ad-86ae-4274-a526-e5e8a9009b32"),
        name = "Dürre",
        eventDescription = "Durch die Klimakrise werden Trockenperioden häufiger. Wasserkraftwerke fallen wegen niedrigem Wasserstand aus. Außerdem sind Dürren verantwortlich für Ernteausfälle, was Lebensmittelpreise steigen lässt.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld wird um 4 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Pumpspeicher- und Wasserkraftwerke zählen nur mit den Basispunkten in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 10,
                modify = SupplyListModifier.BasePointsForWaterAndPumpStorage.modify
            )
        ),
        effect = updateMoneyEffect(-4),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("19a19155-e22a-4f7a-bb61-642df63adc08"),
        name = "Mückenplage",
        eventDescription = "Die Klimakrise schafft günstigere Bedingungen für Mücken, sodass diese sich immer mehr ausbreiten. Mücken können Krankheiten übertragen. Das führt zu Mehrkosten bei der Mückenbekämpfung und im Gesundheitssystem.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld wird um 4 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateMoneyEffect(-4),
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.fromString("e15be36e-f257-475d-b2c8-53f972f619cf"),
        name = "Blackout",
        eventDescription = "Nach einem großflächigen Stromausfall ist ein sogenannter Schwarzstart erforderlich. Unter anderem sind viele erneuerbare Energietechnologien nicht dafür ausgelegt. Das Energiesystem wird durch schwarzstartfähige Anlagen schrittweise wieder hochgefahren.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 6 Einheiten und Ressourcen um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Karten mit der Voraussetzung Verteilung zählen in dieser Runde nur mit den Basispunkten in die Fortschrittspunkte.",
                type = Points,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifierConfig = ModifierConfig(
                rank = 10,
                modify = SupplyListModifier.BasePointsForDistribution.modify
            )
        ),
        effect = updateMoneyEffect(-6) and updateResourcesEffect(-2),
        phaseIndex = 0,
        isCatastrophe = true
    ),
)
