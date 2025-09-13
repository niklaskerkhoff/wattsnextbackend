package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.EventCard.EffectDescription
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.Modifier
import de.niklaskerkhoff.wattsnextbackend.model.modifiers.ModifierCollection
import java.util.*

val eventCards = listOf(
    EventCard(
        id = UUID.randomUUID(),
        name = "Langer und kalter Winter",
        eventDescription = "Im Winter sind die Temperaturen niedriger als üblich und unzureichend isolierte Häuser benötigen mehr Energie zum Heizen als in normalen Wintern.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Die Nachfrage nach  Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Energieeinheiten dieser Technologien werden um 1 reduziert.",// Rm. Effect
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 5,
                modify = SupplyListModifier.BasePointsForSolar.modify
            )
        ),
        effect = null,
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Energieeinheiten dieser Technologien werden um 1 reduziert.",// Rm. Effect
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 5,
                modify = SupplyListModifier.BasePointsForWind.modify
            )
        ),
        effect = null,
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
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Speicher zählen mit ihrer Systempunktzahl in die Fortschrittspunkte, da sie vollständig geladen wurden.",
                imageSrc = null,
            ),
        ),
        effectConditionDescription = "Falls Solartechnologien im Energiesystem vorhanden:",
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 4,
                modify = SupplyListModifier.SystemPointsForSolar.modify
            )
        ),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
        ),
        effectConditionDescription = "Falls Atomkraftwerk im Energie-system vorhanden:",
        footnote = "Kein Atomkraftwerk? Glück gehabt",
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Speicher zählen mit ihrer Systempunktzahl in die Fortschrittspunkte, da sie vollständig geladen wurden.",
                imageSrc = null,
            ),
        ),
        effectConditionDescription = "Falls Windtechnologien im Energiesystem vorhanden:",
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 5,
                modify = SupplyListModifier.SystemPointsForStorage.modify
            )
        ),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Große Erzeugungstechnologien (≥3 Energieeinheiten) zählen nur mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Energieeinheiten dieser Technologien werden um 1 reduziert.",// Rm. Effect
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 5,
                modify = SupplyListModifier.BasePointsForLargeGeneration.modify
            )
        ),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Die Nachfrage nach  Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Batterie-Preis sinkt",
        eventDescription = "Die Herstellung von Batteriespeichern wurde effizienter und damit günstiger.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Batterie-Speichertechnologien kosten in dieser Phase 2 Geldeinheiten weniger.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            cardMoneyCostsModifier = Modifier(
                rank = 5,
                modify = CardCostModifier.CostsWithBatteryImproved.modify
            )
        ),
        effect = null,
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Virus-Pandemie: Lockdown",
        eventDescription = "Bei einem Lockdown stehen viele Bereiche still: Weniger Produktion, weniger Verkehr, mehr Homeoffice. Weniger Mobilität und Produktion bedeuten auch weniger Energieverbrauch.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 1 Einheiten reduziert.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Die Nachfrage nach Energie sinkt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit reduziert.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Die Nachfrage nach  Energie sinkt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit reduziert.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
        phaseIndex = 0,
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Sturmschaden",
        eventDescription = "Eine Sturmfront, die über Deutschland hinweg zog, zerstörte die Überlandleitungen im ganzen Land. Viele Regionen haben Schwierigkeiten, ihren Bedarf zu decken.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Oberirdische Stromleitungen zählen nicht zur Erfüllung der Voraussetzung anderer Fortschrittskarten und der Phasenziele.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyModifier = Modifier(
                rank = 100,
                modify = SupplyModifier.NoSupplyForOverheadPowerLine.modify
            )
        ),
        effect = null,// Impl. Effect
        phaseIndex = 0,
        isCatastrophe = false
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Hitzewelle",
        eventDescription = "Eine extreme Hitzewelle lässt die Nachfrage an Strom steigen und die Leistung von thermischen Kraftwerken abnehmen. Die Klimakrise erhöht die Wahrscheinlichkeiten für Hitzewellen.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 2 Einheiten reduziert.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Die Nachfrage nach Energie steigt. Das Phasenziel für Erzeugung und Verteilung wird um 1 Energieeinheit erhöht.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Kohle-, Gas-, und Nukleare Kraftwerke zählen in dieser Runde nur mit den Basispunkten in die Fortschrittspunkte.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 10,
                modify = SupplyListModifier.BasePointsForCoalAndGasAndNuclear.modify
            )
        ),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Photovoltaik- und Solarthermie- Technologien zählen in dieser Runde mit ihrer Basispunktzahl in die Fortschrittspunkte.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 10,
                modify = SupplyListModifier.BasePointsForSolar.modify
            )
        ),
        effect = null,// Impl. Effect
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
                imageSrc = null,
            ),
            EffectDescription(
                text = "Wasser- und Pumpspeicherkraftwerke zählen nur mit den Basispunkten in die Fortschrittspunkte.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 10,
                modify = SupplyListModifier.BasePointsForWaterAndPumpStorage.modify
            )
        ),
        effect = null,
        phaseIndex = 0,// Impl. Effect
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Mückenplage",
        eventDescription = "Die Klimakrise schafft günstigere Bedingungen für Mücken, sodass diese sich immer mehr ausbreiten. Mücken können Krankheiten übertragen. Um dies zu verhindern, muss mehr Arbeit investiert werden, die Mücken zu bekämpfen.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Das Geld wird um 4 Einheiten reduziert.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(),
        effect = null,
        phaseIndex = 0,// Impl. Effect
        isCatastrophe = true
    ),
    EventCard(
        id = UUID.randomUUID(),
        name = "Blackout",
        eventDescription = "Das Stromnetz bricht zusammen, weil zu viel Strom verbraucht oder zu wenig erzeugt wird. Die Stromversorgung muss aufwendig neu gestartet werden. Teure Reparaturarbeiten an Kraftwerken sind notwendig.",
        effectDescriptions = listOf(
            EffectDescription(
                text = "Geld wird um 6 Einheiten und Ressourcen um 2 Einheiten reduziert.",
                imageSrc = null,
            ),
            EffectDescription(
                text = "Karten mit der Voraussetzung Verteilung zählen in dieser Runde nur mit den Basispunkten in die Fortschrittspunkte.",
                imageSrc = null,
            ),
        ),
        modifierCollection = ModifierCollection(
            supplyRequirementsForSystemModifier = Modifier(
                rank = 10,
                modify = SupplyListModifier.BasePointsForDistribution.modify
            )
        ),
        effect = null,// Impl. Effect
        phaseIndex = 0,
        isCatastrophe = true
    ),
)
