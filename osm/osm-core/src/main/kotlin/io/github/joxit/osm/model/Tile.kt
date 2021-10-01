package io.github.joxit.osm.model

data class Tile(val z: Int, val x: Int, val y: Int, val scale: Int = 1, val ext: String = "png") {
  val id: String = "$z/$x/$y@$scale.$ext"

  companion object {
    @JvmStatic
    fun idOf(z: Int, x: Int, y: Int, scale: Int = 1, ext: String = "png") = "$z/$x/$y@$scale.$ext"
  }
}