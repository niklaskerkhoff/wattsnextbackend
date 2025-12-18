package de.niklaskerkhoff.wattsnextbackend.model.core.cards

data class CardEffectInfo(
    val type: CardEffectType,
    val amount: Int? = null
)

enum class CardEffectType {
    Money,
    Resources,
    ProgressPoints,
    GenerationAndDistributionTargets,
    NuclearCatastrophe
}
