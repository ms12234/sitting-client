package com.ms.sittingclient.repository

data class Sensor(var x: Float, var y: Float, var position: Position,
                  var value: Float) {

    constructor() : this(0F, 0F, Position.Back, 0F)
}
