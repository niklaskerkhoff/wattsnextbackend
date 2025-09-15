package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Electricity
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Achievement
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Energy
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.Distribution
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.Generation
import java.util.*

val climateCards = listOf(
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
).map { it.toClimateCard() }
