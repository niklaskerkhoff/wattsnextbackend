package de.niklaskerkhoff.wattsnextbackend.model.modifiers

class ModifierCollection(
    val costModifier: Modifier<WithTargetPositionModifierFunction<Int>>?,
    val pointModifier: Modifier<SimpleModifierExecutorFunction<Int>>?,
)