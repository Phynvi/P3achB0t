package com.p3achb0t._runestar_interfaces

interface PcmStreamMixer: PcmStream{
	fun getSubStreams(): NodeDeque
    fun get__i(): Int
	fun get__k(): Int
    fun get__j(): NodeDeque
}
