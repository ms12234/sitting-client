package com.ms.sittingclient.settings_window

class Settings(var visualReaction: Boolean, var soundReaction: Boolean,
               var soundReactionType: SoundNotifier, var reactionDelayInSeconds: Int,
               var baselineMonitoringTimeInMinutes: Int,
               var learningTimeInMinutes: Int, var reviewTimeInMinutes: Int) {

    constructor() : this(visualReaction = true, soundReaction = false,
            soundReactionType = SoundNotifier.LAGODNY_DZWIEK, reactionDelayInSeconds = 1,
            baselineMonitoringTimeInMinutes = 20, learningTimeInMinutes = 20,
            reviewTimeInMinutes = 20)
}