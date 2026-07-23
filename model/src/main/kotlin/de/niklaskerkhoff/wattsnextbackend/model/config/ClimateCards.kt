package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.config.helper.*
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierCollection
import de.niklaskerkhoff.wattsnextbackend.model.core.cards.modification.ModifierConfig
import de.niklaskerkhoff.wattsnextbackend.model.values.AchievementName
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.EnergyForm.Electricity
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Achievement
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Supply.Energy
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.Distribution
import de.niklaskerkhoff.wattsnextbackend.model.values.energy.Technology.Generation

val climateCards = listOf(
    ClimateCardData(
        id = "522e8924-fe4b-462d-acfd-c20f1d5f057e",
        name = "Gebäudeisolation",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Erhaltet jeweils 2 Einheiten Ressourcen und Geld.",
        explanation = "Gut isolierte Gebäude brauchen weniger Energie zum Heizen/Kühlen.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2) and updateMoneyEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "6ee0626b-c616-48c2-a367-abe012f5ec95",
        name = "Ausbau der Eisenbahn",
        imageSrc = "Klima.png",
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
        requirementsDescription = "Für den Betrieb der Eisenbahn muss die Stromproduktion und -verteilung sichergestellt sein.",
        explanation = "Das Nutzen öffentlicher Verkehrsmittel reduziert Emissionen. Der Ausbau steigert die Attraktivität. ",
        modifierCollection = ModifierCollection(),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "cafb531a-947e-4141-9776-191e7b70dafd",
        name = "Verbrenner-Aus",
        imageSrc = "Klima.png",
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
        requirementsDescription = "Erhaltet 2 Ressourcen. Für den Umstieg auf E-Mobilität muss die Stromproduktion und -verteilung sichergestellt sein.",
        explanation = "Das Verbrenner-Verbot ist eine sozialgerechte und effektive Möglichkeit, die Emissionen im Verkehrssektor zu senken.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "01e40fe0-cdce-4906-b5f5-a1ac93519e94",
        name = "Subventionierung von E-Autos",
        imageSrc = "Klima.png",
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
        requirementsDescription = "Zum Laden der Autos muss die Stromproduktion und -verteilung sichergestellt sein.",
        explanation = "Elektromobilität ist ein wichtiger Pfeiler der Verkehrswende.",
        modifierCollection = ModifierCollection(),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "f0a283b1-8ebb-42ab-a8cd-a1703a65d8e1",
        name = "CO2-Abscheidung und -Speicherung",
        imageSrc = "Klima.png",
        supply = Achievement(AchievementName.CarbonCapture),
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
        requirementsDescription = "Erhaltet 2 Ressourcen. Carbon Capture ist energieintensiv, daher muss ausreichend Strom produziert werden.",
        explanation = "Das von Kraftwerken ausgestoßene CO2 wird herausgefiltert. Dieses abgeschiedene CO2 kann als Rohstoff dienen.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 1
    ),
    ClimateCardData(
        id = "b665a46f-9b62-419f-aa90-5f9c23c5a633",
        name = "H2-betriebene Schiffe",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Achievement(AchievementName.ChemicalEnergy)
        ),
        requirementsDescription = "Wasserstoff muss durch „Power-to-X“ aus grünem Strom hergestellt werden.",
        explanation = "Auf langen Strecken, für die Batterien nicht ausreichen, bietet sich die Nutzung von Wasserstoff als Kraftstoff an.",
        modifierCollection = ModifierCollection(),
        phaseIndex = 2
    ),
    ClimateCardData(
        id = "8731e98f-d136-48bc-a568-e90611540467",
        name = "E-Buslinien in ländlicher Region",
        imageSrc = "Klima.png",
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
        requirementsDescription = "Erhaltet 2 Ressourcen. Zum Laden der Busse muss die Stromproduktion und -verteilung sichergestellt sein.",
        explanation = "Das Nutzen öffentlicher Verkehrsmittel reduziert Emissionen. Auf dem Land gibt es diese jedoch kaum.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 1
    ),
    *Array(3) {
        ClimateCardData(
            id = "6cb50407-fafe-4528-9c7f-f3792cdd39b$it",
            name = "Power-to-X",
            imageSrc = "Klima.png",
            supply = Achievement(AchievementName.ChemicalEnergy),
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
            requirementsDescription = "Es muss genug Strom produziert werden. Für ein gebautes Gaskraftwerk erhaltet ihr 5 extra Fortschrittspunkte.",
            explanation = "Power-to-X Technologien können Stromüberschüsse aus erneuerbaren Energien langfristig, z. B. in Form von Wasserstoff, speichern.",
            modifierCollection = ModifierCollection(),
            effect = ifGasIsExisting(updateProgressPointsEffect(5)),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = "42f4e0d3-1eef-4d03-a255-fbe5f02c8714",
        name = "CO2-neutraler Flugverkehr",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(
            Achievement(AchievementName.ChemicalEnergy)
        ),
        requirementsDescription = "Grünes Kerosin aus „Power-to-X“-Technologie muss als Kraftstoff eingesetzt werden.",
        explanation = "Synthetisches Kerosin bietet eine grünere Alternative zu fossilem Kraftstoff und ist für Flugzeuge geeignet.",
        modifierCollection = ModifierCollection(),
        phaseIndex = 2
    ),
    ClimateCardData(
        id = "03994918-72a6-4823-8096-1b0e4d54adc4",
        name = "Verbot von Inlandsflügen",
        imageSrc = "Klima.png",
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
        requirementsDescription = "Erhaltet 2 Ressourcen.",
        explanation = "Kurzstreckenflüge sind ineffizient, da Start und Landung viel Energie verbrauchen, was zu hohem CO₂-Ausstoß führt.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    *Array(2) {
        ClimateCardData(
            id = "f2660f7f-b8ef-454e-987c-a04bb97236a$it",
            name = "Kunststoff-Recycling",
            imageSrc = "Klima.png",
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
            requirementsDescription = "Erhaltet jeweils 2 Einheiten Ressourcen und Geld. Recycling ist energieintensiv, daher müssen Stromerzeugung und -verteilung sichergestellt sein.",
            explanation = "Recycelter Kunststoff wird sortiert, zerkleinert, gereinigt und geschmolzen, um neue Produkte herzustellen.",
            modifierCollection = ModifierCollection(),
            effect = updateResourcesEffect(2) and updateMoneyEffect(2),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = "b57e711a-4cc5-4ef8-8bde-92d202b70d13",
        name = "Subventionierung erneuerbarer Energien",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 2,
        resourceCosts = 0,
        basePoints = 0,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Alle Wind- und Photovoltaik-Technologien kosten 2 Geldeinheiten weniger, aber mindestens 1 Geldeinheit.",
        explanation = "Das Fördern von Erneuerbaren soll deren Ausbau beschleunigen.",
        modifierCollection = ModifierCollection(
            cardMoneyCostsModifierConfig = ModifierConfig(
                rank = 10,
                modify = CardCostModifier.CostsWithSubventionOfWindAndPhotovoltaic.modify
            )
        ),
        phaseIndex = 0
    ),
    *Array(2) {
        ClimateCardData(
            id = "f6573f3f-1672-42ad-a1da-717fd0fc348$it",
            name = "Batterie-Recycling",
            imageSrc = "Klima.png",
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
            requirementsDescription = "Erhaltet je 2 Ressourcen und Geld. Recycling ist energieintensiv, Stromerzeugung und -verteilung müssen sichergestellt sein.",
            explanation = "Durch Batterie-Recycling werden wertvolle Rohstoffe zurückgewonnen. ",
            modifierCollection = ModifierCollection(),
            effect = updateResourcesEffect(2) and updateMoneyEffect(2),
            phaseIndex = 1
        )
    },
    ClimateCardData(
        id = "9028b732-b58f-4006-a26a-621976365224",
        name = "Pendeln mit dem Fahrrad",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Das Fahrrad ist eine CO2-neutrale Fortbewegungsmöglichkeit.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "d173ceff-e20f-4a01-8f92-b2e6c7828a7f",
        name = "Sharing is Caring",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Statt viel Geld für neue Dinge auszugeben, tauscht und leiht ihr oder kauft second-hand.",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
    ClimateCardData(
        id = "96448365-5047-4994-a736-38135f7895cf",
        name = "Umweltbewusste Ernährung",
        imageSrc = "Klima.png",
        supply = null,
        moneyCosts = 0,
        resourceCosts = 0,
        basePoints = 2,
        systemPoints = 2,
        supplyRequirementsForSystem = listOf(),
        requirementsDescription = "Ihr erhaltet 2 Ressourcen.",
        explanation = "Ihr verzichtet weitestgehend auf tierische Produkte, achtet auf Regionalität und verringert Lebensmittelverschwendung. ",
        modifierCollection = ModifierCollection(),
        effect = updateResourcesEffect(2),
        phaseIndex = 0
    ),
).map { it.toClimateCard() }
