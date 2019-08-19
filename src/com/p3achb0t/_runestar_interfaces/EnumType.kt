package com.p3achb0t._runestar_interfaces

interface EnumType: DualNode{
	fun getDefaultint(): Int
	fun getDefaultstr(): String
	fun getInputtype(): Char
	fun getIntvals(): IntArray
	fun getKeys(): IntArray
	fun getOutputcount(): Int
	fun getOutputtype(): Char
	fun getStrvals(): Array<String>
}
