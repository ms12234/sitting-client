package com.ms.sittingclient.settings_window

import org.apache.commons.collections4.bidimap.DualHashBidiMap
import org.springframework.stereotype.Component

@Component
class SoundNotifiers {
    final val values = DualHashBidiMap<String, SoundNotifier>()

    init {
        values.put("Łagodny dzwięk", SoundNotifier.LAGODNY_DZWIEK)
        values.put("Pisk", SoundNotifier.PISK)
        values.put("Melodia - klasyczna", SoundNotifier.MELODIA_KLASYCZNA)
        values.put("Melodia - rock", SoundNotifier.MELODIA_ROCK)
    }
}