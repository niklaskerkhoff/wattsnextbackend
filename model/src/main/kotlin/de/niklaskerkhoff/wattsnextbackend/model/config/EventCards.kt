package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.CardCostModifier
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.SupplyListModifier
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.SupplyModifier
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.and
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.ifSolarIsExisting
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.ifWindIsExisting
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.nuclearCatastropheIfExistingEffect
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.updateCurrentPhaseTarget
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.updateMoneyEffect
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.updateMoneyPerPlayerEffect
import de.niklaskerkhoff.wattsnextbackend.model.config.helper.updateResourcesEffect
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard.EffectDescription
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.EffectType.MoneyAndResources
import de.niklaskerkhoff.wattsnextbackend.model.values.EffectType.Points
import de.niklaskerkhoff.wattsnextbackend.model.values.EffectType.EnergySystem
import java.util.*


val eventCards = listOf(
    EventCard(
        id = UUID.randomUUID(),
        name = "Langer und kalter Winter",
        eventDescription = "Im Winter sind die Temperaturen niedriger als üblich und unzureichend isolierte Häuser benötigen mehr Energie zum Heizen als in normalen Wintern.",
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Nukleare Katastrophe",
        eventDescription = "Eine nukleares Desaster zerstört ein Kernkraftwerk und eine Kernschmelze löst eine weltweite nukleare Katastrophe aus.",
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Blackout durch Cyberangriff",
        eventDescription = "Ein großer Teil des deutschen Stromnetzes bricht zusammen. Das wird teuer...",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Große Erzeugungstechnologien (≥3 Energieeinheiten) zählen nur mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                type = EnergySystem,
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
        id = UUID.randomUUID(),
        name = "Rebound-Effekt",
        eventDescription = "Der Großteil der Wohngebäude wurde neu gedämmt. Mit dem guten Gefühl dadurch Geld zu sparen und etwas Gutes fürs Klima zu machen, heizen die Bewohner*innen jetzt umso wärmer, weshalb keine Energie eingespart wird.",
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Viruspandemie: Lockdown",
        eventDescription = "Bei einem Lockdown stehen viele Bereiche still: Weniger Produktion, weniger Verkehr, mehr Homeoffice. Weniger Mobilität und Produktion bedeuten auch weniger Energieverbrauch.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 1 Einheiten reduziert.",
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
        id = UUID.randomUUID(),
        name = "Neues aus der Photovoltaikforschung",
        eventDescription = "Photovoltaikanlagen sind jetzt langlebiger. Außerdem wurde eine Recycling-Methode entwickelt. Das spart Geld und Ressourcen.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld und Ressourcen werden jeweils um zwei Einheiten erhöht.",
                type = MoneyAndResources,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2) and updateMoneyEffect(2),
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Sturmschäden",
        eventDescription = "Eine Sturmfront, die über Deutschland hinweg zog, zerstörte die Überlandleitungen im ganzen Land. Viele Regionen haben Schwierigkeiten, ihren Bedarf zu decken.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            // TODO: Eigentlich nur "Bereits gebaute oberirdische Stromleitungen ..."
            EffectDescription(
                text = "Oberirdische Stromleitungen zählen nicht zur Erfüllung der Voraussetzung anderer Fortschrittskarten und der Phasenziele.",
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Dürre",
        eventDescription = "Durch die Klimakrise werden Trockenperioden häufiger. Wasserkraftwerke fallen wegen niedrigem Wasserstand aus. Außerdem sind Dürren verantwortlich für Ernteausfälle, was Lebensmittelpreise steigen lässt.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld wird um 4 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Wasser- und Pumpspeicherkraftwerke zählen nur mit den Basispunkten in die Fortschrittspunkte.",
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
        id = UUID.randomUUID(),
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
        id = UUID.randomUUID(),
        name = "Blackout",
        eventDescription = "Das Stromnetz bricht zusammen, weil zu viel Strom verbraucht oder zu wenig erzeugt wird. Die Stromversorgung muss aufwendig neu gestartet werden. Teure Reparaturarbeiten an Kraftwerken sind notwendig.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 6 Einheiten und Ressourcen um 2 Einheiten reduziert.",
                type = MoneyAndResources,
            ),
            EffectDescription(
                text = "Karten mit der Voraussetzung Verteilung zählen in dieser Runde nur mit den Basispunkten in die Fortschrittspunkte.",
                type = EnergySystem,
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
