package de.niklaskerkhoff.wattsnextbackend.model.config

import de.niklaskerkhoff.wattsnextbackend.model.core.cards.QuizCard
import java.util.UUID

// Quiz questions for the online version, taken from the printed quiz cards (24 in total).
// Left out: card 3 (year 2023), card 9 (open answer, no options) and card 12 (year 2024 and a
// mismatch between marked answer and explanation) — see the comments where they would sit.
// correctIndex is 0-based. The original printed card number is noted for traceability.
val quizCards = listOf(
    // Card 1
    QuizCard(
        id = UUID.fromString("b13b2371-9b61-45dc-b88d-5d9df3d1b056"),
        question = "Welchen durchschnittlichen Wirkungsgrad der Leistungsumwandlung hat ein Auto mit Benzinmotor?",
        options = listOf("ca. 20–35 %", "ca. 55–70 %"),
        correctIndex = 0,
        explanation = "Der Benzinmotor hat einen Wirkungsgrad von etwa 20 %. Auch E-Fuels sind aufgrund ihres hohen Energieaufwands in der Herstellung ineffizient und umstritten. Deutlich besser schneidet der Elektromotor ab: Mit 70 % hat er die höchste Effizienz aller herkömmlichen Pkw-Antriebe.",
        info = "Der Wirkungsgrad der Leistungsumwandlung meint die Effizienz, mit der ein Auto mit Benzinmotor die Energie des Kraftstoffs in Bewegungsenergie umwandelt. Ein hoher Wirkungsgrad ermöglicht also mehr Kilometer pro Liter Kraftstoff.",
    ),
    // Card 2
    QuizCard(
        id = UUID.fromString("e665a53d-cc99-44bf-9c13-5e35afacb809"),
        question = "Welcher von diesen Faktoren hat den größten Einfluss auf das heimische Vogelsterben in Deutschland?",
        options = listOf("Straßen- und Bahnverkehr", "Windkraftanlagen", "Glasscheiben", "Hauskatzen"),
        correctIndex = 2,
        explanation = "Glasscheiben fordern die meisten Opfer: Jährlich sterben daran schätzungsweise bis zu 115 Millionen Vögel. Durch den Straßen- und Bahnverkehr kommen bis zu 70 Millionen Vögel pro Jahr ums Leben. Hauskatzen töten jährlich bis zu 60 Millionen Vögel. Im Vergleich dazu verursachen Windkraftanlagen mit etwa 100.000 toten Vögeln pro Jahr deutlich weniger Verluste.",
    ),
    // Card 3 left out: "im Jahr 2023" — year-dependent.
    // Card 4
    QuizCard(
        id = UUID.fromString("a1244d40-2551-407c-81ee-f1b866c42f6b"),
        question = "Wie funktioniert die Elektrolyse zur nachhaltigen Wasserstofferzeugung?",
        options = listOf(
            "Durch den Einsatz geothermaler Energie zur direkten Erzeugung von Wasserstoff durch Verdampfung und anschließender Extraktion aus geothermischen Quellen.",
            "Durch die Spaltung von Wasser in Wasserstoff und Sauerstoff mithilfe regenerativer elektrischer Energie.",
        ),
        correctIndex = 1,
        explanation = "Die Wasserelektrolyse ist ein Verfahren, bei dem Wasser mithilfe elektrischer Energie in Wasserstoff und Sauerstoff zerlegt wird. Dazu wird Strom durch Wasser geleitet, sodass sich Wasserstoff an der negativen Elektrode (Kathode) und Sauerstoff an der positiven Elektrode (Anode) bildet. Wird der Strom aus erneuerbaren Energien wie Wind- oder Solarenergie gewonnen, spricht man von grünem Wasserstoff, da bei der Herstellung kein CO₂ entsteht.",
    ),
    // Card 5
    QuizCard(
        id = UUID.fromString("5bf50445-9bab-4692-a6de-939f6a9c8191"),
        question = "Welche der beiden Technologien setzt die meiste Energie pro Brennstoff frei?",
        options = listOf("Kernspaltung", "Kernfusion"),
        correctIndex = 1,
        explanation = "Bei der Kernfusion wird eine enorme Menge an Energie freigesetzt. Die Energie, die bei der Fusion von Wasserstoff zu Helium freigesetzt wird, ist viel größer als die Energie, die bei der Spaltung schwerer Atomkerne wie Uran freigesetzt wird. Obwohl die Kernfusion theoretisch viel mehr Energie pro Brennstoff liefert, ist sie technologisch noch nicht ausgereift und wird derzeit nicht kommerziell genutzt.",
        info = "Kernspaltung ist der Prozess, der sich derzeit in Atomkraftwerken abspielt. Hier werden schwere Atome gespalten, während bei der Kernfusion leichte Atome verschmelzen. Beide Reaktionen setzen Energie frei, die in Kraftwerken zur Erhitzung von Wasser genutzt wird. Dies treibt eine Dampfturbine an und produziert so über einen Generator Strom.",
    ),
    // Card 6
    QuizCard(
        id = UUID.fromString("e3adcf5e-5964-43ca-8501-34f8bb38b18b"),
        question = "Was sind Power-to-X-Technologien?",
        options = listOf(
            "Technologien zur Umwandlung von Biomasse in Biokraftstoffe.",
            "Technologien zur Umwandlung von überschüssiger Energie in chemische Brennstoffe oder andere Energieträger.",
        ),
        correctIndex = 1,
        explanation = "Power-to-X bezeichnet Technologien, die überschüssige elektrische Energie, vor allem aus erneuerbaren Quellen, in verschiedene andere Formen von Energie oder Stoffen umwandeln. Dabei können zum Beispiel Wärme (Power-to-Heat), chemische Produkte (Power-to-Chemicals) oder klimafreundliche Kraftstoffe (Power-to-Fuels) erzeugt werden. Diese Technologien helfen dabei, die fluktuierende Energieerzeugung aus erneuerbaren Quellen zu speichern oder in nutzbare Formen zu überführen.",
    ),
    // Card 7
    QuizCard(
        id = UUID.fromString("7b156ebc-4a31-4bd5-b74a-a70333801df7"),
        question = "Was ist eine Brennstoffzelle?",
        options = listOf(
            "Eine elektrochemische Vorrichtung, die Wasserstoff und Sauerstoff in elektrische Energie umwandelt.",
            "Ein spezieller Wasserstoff-Tank.",
        ),
        correctIndex = 0,
        explanation = "In der Brennstoffzelle reagiert Wasserstoff kontrolliert mit Sauerstoff – wie bei der Knallgasreaktion, aber ohne Explosion. Dabei entstehen Strom, Wärme und Wasser – ganz ohne schädliche Abgase.",
    ),
    // Card 8
    QuizCard(
        id = UUID.fromString("68c607a0-df9c-48f3-aaca-721eb7ec2174"),
        question = "Welcher der folgenden Energieträger enthält die meiste Energie pro Gewicht?",
        options = listOf("Biomasse", "Erdgas", "Wasserstoff"),
        correctIndex = 2,
        explanation = "Ein Kilogramm Wasserstoff enthält 141,8 MJ Energie. Das ist besonders viel und macht Wasserstoff zu einem vielversprechenden Energieträger. Allerdings hat Wasserstoff eine sehr geringe Dichte, was bedeutet, dass er in großen Volumina gespeichert werden muss, um eine nennenswerte Energiemenge zu liefern.",
    ),
    // Card 9 left out: open answer ("Nennt mindestens fünf Energieformen") — no answer options.
    // Card 10
    QuizCard(
        id = UUID.fromString("3d380802-463f-4f3d-97f6-db38adea452b"),
        question = "Was ist der Unterschied zwischen Photovoltaikgeräten (PV) und Solarthermiegeräten für den Hausgebrauch?",
        options = listOf(
            "PV wandelt Sonnenlicht in elektrische Energie um, während Solarthermiegeräte Sonnenwärme zur Warmwassererzeugung und Raumheizung nutzen.",
            "Beide Geräte erzeugen elektrische Energie, die jedoch unterschiedlich im Haus genutzt wird. PV wird für den allgemeinen Haushaltsstrom und Solarthermiegeräte speziell für Heizsysteme verwendet.",
        ),
        correctIndex = 0,
        explanation = "Solarthermie nutzt Sonnenlicht, um Wasser oder andere Flüssigkeiten zu erhitzen, etwa für Warmwasser oder Heizungen. Photovoltaiksysteme wandeln Sonnenlicht direkt in Strom um, z. B. für elektrische Geräte. Solarthermie ist meist effizienter, da mehr Sonnenenergie genutzt wird. Photovoltaik ist dafür praktischer für kleinere Anwendungen und einfacher zu installieren.",
    ),
    // Card 11
    QuizCard(
        id = UUID.fromString("33d997e9-281c-4ee0-8c8a-c5bb9c24c06c"),
        question = "Welcher fossile Energieträger hat den höchsten Kohlenstoffgehalt pro Energieeinheit?",
        options = listOf("Erdöl", "Braunkohle"),
        correctIndex = 1,
        explanation = "Braunkohle besteht fast nur aus Kohlenstoff (C), während Erdöl zu etwa 90 % aus Kohlenstoff besteht, aber auch rund 10 % Wasserstoff enthält. Dadurch entsteht bei der Verbrennung von Kohle mehr CO₂ pro Energieeinheit.",
        info = "Der Kohlenstoffgehalt bezieht sich auf den Anteil des Elements Kohlenstoff in einem Material. Bei fossilen Energieträgern ist dies der Anteil des Kohlenstoffs, der bei der Verbrennung als Kohlendioxid (CO₂) freigesetzt wird. Ein hoher Kohlenstoffgehalt bedeutet mehr CO₂-Emission und damit einen stärkeren Einfluss auf den Klimawandel.",
    ),
    // Card 12 left out: "im Jahr 2024" — year-dependent, and marked answer conflicts with the explanation.
    // Card 13
    QuizCard(
        id = UUID.fromString("33a4caaf-b3ff-41a7-b955-8961dd36880f"),
        question = "Die Nutzung welches Energieträgers verursacht derzeit weltweit die größten Treibhausgasemissionen?",
        options = listOf("Erdgas", "Öl", "Kohle"),
        correctIndex = 2,
        explanation = "Weltweit stammen die meisten Treibhausgasemissionen aus der Verbrennung von Kohle (45 %), gefolgt von Erdöl (32 %) und Erdgas (22 %).",
    ),
    // Card 14
    QuizCard(
        id = UUID.fromString("0b67693f-eb48-4bf4-b021-5d10a7c911a3"),
        question = "Wahr oder falsch? Photovoltaikanlagen erzeugen auch bei bewölktem Himmel Strom.",
        options = listOf("Wahr", "Falsch"),
        correctIndex = 0,
        explanation = "Wahr. Photovoltaikanlagen erzeugen auch bei bewölktem Himmel Strom – allerdings weniger als bei direkter Sonneneinstrahlung.",
    ),
    // Card 15
    QuizCard(
        id = UUID.fromString("19be9adf-2efb-4056-97d7-97b419e249fc"),
        question = "Was versteht man unter dem Begriff „Sektorenkopplung“?",
        options = listOf(
            "Die Verknüpfung von verschiedenen Energieunternehmen zur Effizienzsteigerung.",
            "Die Verbindung der Bereiche Strom, Wärme und Verkehr zur gemeinsamen Nutzung erneuerbarer Energien.",
        ),
        correctIndex = 1,
        explanation = "Sektorenkopplung bedeutet, die Bereiche Strom, Wärme und Verkehr zu verknüpfen, um erneuerbare Energien effizienter zu nutzen und die Energieversorgung nachhaltiger zu gestalten. Ein Beispiel dafür ist die E-Mobilität: Elektroautos werden mit Strom aus erneuerbaren Quellen geladen. Wenn das Auto nicht genutzt wird, kann es als „mobiler Speicher“ dienen und überschüssigen Strom ins Netz zurückspeisen.",
    ),
    // Card 16
    QuizCard(
        id = UUID.fromString("641d7e07-e0a3-49f2-b5c0-e88b6610aea7"),
        question = "Um wie viel Grad darf die Klimaerwärmung laut Pariser Klimaschutzabkommen maximal ansteigen?",
        options = listOf("1,0 °C", "1,5 °C", "2,5 °C"),
        correctIndex = 1,
        explanation = "Laut dem Pariser Klimaschutzabkommen soll die Erwärmung maximal 1,5 °C betragen. Ohne drastische Maßnahmen steuert die Welt jedoch auf eine Erwärmung von über 1,5 °C zu.",
    ),
    // Card 17
    QuizCard(
        id = UUID.fromString("38a8e1c5-2942-4493-b454-11b991b502bc"),
        question = "Welches Treibhausgas ist kurzfristig (über 20 Jahre) am klimaschädlichsten?",
        options = listOf("CO₂", "Methan (CH₄)", "Lachgas (N₂O)"),
        correctIndex = 1,
        explanation = "Methan absorbiert Infrarotstrahlung sehr effektiv und hat daher eine größere Fähigkeit, Wärme zu speichern. In einem Zeitraum von 20 Jahren hat Methan etwa 81-mal mehr Einfluss auf die Erwärmung der Erde als die gleiche Menge CO₂.",
    ),
    // Card 18
    QuizCard(
        id = UUID.fromString("153706e7-6a43-466f-a1e1-4f73371c55be"),
        question = "Wahr oder falsch? Der größte Teil des menschengemachten CO₂ stammt aus der Landwirtschaft.",
        options = listOf("Wahr", "Falsch"),
        correctIndex = 1,
        explanation = "Falsch. Der größte Teil des menschengemachten CO₂ stammt nicht aus der Landwirtschaft, sondern aus der Energieerzeugung, insbesondere durch die Verbrennung von fossilen Brennstoffen wie Kohle, Öl und Gas. Die Landwirtschaft trägt zwar ebenfalls zum Klimawandel bei, insbesondere durch Methan- und Lachgasemissionen, aber der CO₂-Ausstoß aus der Landwirtschaft ist im Vergleich zur Energieerzeugung geringer.",
    ),
    // Card 19
    QuizCard(
        id = UUID.fromString("bb81af7a-f7a6-4150-a947-65e219a32511"),
        question = "Welche dieser Entscheidungen spart am meisten CO₂ pro Kopf?",
        options = listOf(
            "Auf Kurzstreckenflüge verzichten.",
            "LED statt Glühbirnen verwenden.",
            "Lebensmittel plastikfrei einkaufen.",
        ),
        correctIndex = 0,
        explanation = "Kurzstreckenflüge verursachen hohe CO₂-Emissionen pro Kilometer, besonders beim Starten und Landen. Umweltfreundlichere Alternativen wie Bahnfahrten können diese Emissionen deutlich reduzieren.",
    ),
    // Card 20
    QuizCard(
        id = UUID.fromString("1fbdd5f3-082e-4f65-a075-1b75d19c3bab"),
        question = "Warum werden in Deutschland Windräder manchmal abgeschaltet, obwohl Wind weht und theoretisch Strom produziert werden könnte?",
        options = listOf(
            "Um die Lebensdauer der Windräder zu verlängern.",
            "Weil das Stromnetz überlastet ist und der Strom nicht gespeichert oder verteilt werden kann.",
        ),
        correctIndex = 1,
        explanation = "Wenn zu viel Strom im Netz ist und keine Speicher oder Verteilungswege verfügbar sind, werden Windräder und andere Stromerzeuger abgeschaltet, um das Netz vor Überlastung zu schützen. Ein Ausbau von Energiespeichern (wie Batterien oder Pumpspeicherkraftwerken) könnte helfen, überschüssigen Strom zu speichern und die Notwendigkeit für Abschaltungen zu minimieren. So könnte das Netz stabiler bleiben, auch wenn viel erneuerbare Energie erzeugt wird.",
    ),
    // Card 21
    QuizCard(
        id = UUID.fromString("5c1a2f4c-63f6-4537-a23b-537f3ba79ca3"),
        question = "Warum reicht es nicht aus, einfach viele Solaranlagen und Windräder zu bauen?",
        options = listOf(
            "Weil diese Technologien bald verboten werden könnten.",
            "Weil deren Stromproduktion wetterabhängig ist und nicht immer zur Verbrauchsspitze passt.",
            "Weil sie im Winter keinen Strom liefern.",
        ),
        correctIndex = 1,
        explanation = "Solaranlagen und Windräder liefern nicht immer dann Strom, wenn er gebraucht wird, da ihre Produktion wetterabhängig ist. Eine Lösung für die Energiewende ist der Ausbau von Energiespeichern und flexiblen Stromnetzen, um überschüssige Energie zu speichern und bei Bedarf zu nutzen.",
    ),
    // Card 22
    QuizCard(
        id = UUID.fromString("0c01aaa1-7ba5-4f9f-a0a0-f20eb949547d"),
        question = "Welcher dieser Stromspeicher kann auch saisonal Energie speichern (also über Monate)?",
        options = listOf("Schwungradspeicher", "Chemische Speicher wie Wasserstoff", "Batterien"),
        correctIndex = 1,
        explanation = "Chemische Speicher wie Wasserstoff können Energie über Monate speichern, da sie langfristig gespeichert und bei Bedarf genutzt werden können. Im Gegensatz dazu speichern Pumpspeicherkraftwerke nur für Stunden bis wenige Tage. Batterien sind für die kurzfristige Speicherung von Energie (in der Regel nur für Stunden bis Tage) geeignet.",
    ),
    // Card 23
    QuizCard(
        id = UUID.fromString("9b28dd8d-a401-46f3-9fed-9d1a59ed3225"),
        question = "Wahr oder falsch? Ohne den Ausbau von Stromnetzen und Speichern wird Deutschland nicht in der Lage sein, 100 % erneuerbare Energie zu nutzen.",
        options = listOf("Wahr", "Falsch"),
        correctIndex = 0,
        explanation = "Wahr. Erneuerbare Energiequellen wie Wind und Sonne sind wetterabhängig, deshalb sind ein effektives Stromnetz sowie Energiespeicher notwendig. Zum Beispiel muss Windenergie aus dem Norden in den bevölkerungsreichen Süden transportiert werden, wo die Nachfrage höher, aber die Windenergieproduktion geringer ist. Ein gut ausgebautes Stromnetz ermöglicht diesen Transport und sorgt für eine stabile Energieversorgung.",
    ),
    // Card 24
    QuizCard(
        id = UUID.fromString("8fbab592-6240-4f39-983c-e66705bcbf4b"),
        question = "Wie funktioniert eine Wärmepumpe im Haus?",
        options = listOf(
            "Sie verbrennt Erdgas, um warme Luft zu erzeugen.",
            "Sie nutzt elektrische Energie, um Wärme aus der Umgebung (z. B. Luft, Erde oder Wasser) nutzbar zu machen und damit Räume zu heizen.",
        ),
        correctIndex = 1,
        explanation = "Eine Wärmepumpe funktioniert wie ein Kühlschrank, nur umgekehrt: Sie entzieht der Umgebung Wärme (z. B. aus der Luft, dem Boden oder Wasser) und bringt diese Wärme ins Haus, um es zu heizen. Dafür wird elektrische Energie genutzt.",
    ),
)
