package it.amongsus.view.draw

import it.amongsus.core.map.{Boundary, Emergency, Floor, Other, Tile, Vent, Wall}
import it.amongsus.core.player.Player
import java.awt.Graphics
import java.awt.image.BufferedImage

/**
 *
 * @tparam T
 */
trait DrawableTile[T] {
  def drawTile(tile : T, g : Graphics, player : Player)
}

object DrawableTile {
  def drawTile[T: DrawableTile](tile: T, g: Graphics, player : Player): Unit =
    implicitly[DrawableTile[T]].drawTile(tile, g, player)

  implicit object drawWall extends DrawableTile[Wall] {
    override def drawTile(tile: Wall, g: Graphics, player : Player): Unit = {
      if (fieldOfViewDistance(tile, player)) {
        paintTile(g, tile, WALL)
      } else {
        paintTile(g, tile, WALL_OFF)
      }
    }
  }

  implicit object drawVent extends DrawableTile[Vent] {
    override def drawTile(tile: Vent, g: Graphics, player : Player): Unit = {
      if (fieldOfViewDistance(tile, player)) {
        paintTile(g, tile, VENT)
      } else {
        paintTile(g, tile, VENT_OFF)
      }
    }
  }

  implicit object drawEmergency extends DrawableTile[Emergency] {
    override def drawTile(tile: Emergency, g: Graphics, player : Player): Unit = {
      if (fieldOfViewDistance(tile, player)) {
        paintTile(g, tile, EMERGENCY)
      } else {
        paintTile(g, tile, EMERGENCY_OFF)
      }
    }
  }

  implicit object drawFloor extends DrawableTile[Floor] {
    override def drawTile(tile: Floor, g: Graphics, player : Player): Unit = {
      if (fieldOfViewDistance(tile, player)) {
        paintTile(g, tile, FLOOR)
      } else {
        paintTile(g, tile, FLOOR_OFF)
      }
    }
  }

  implicit object drawBoundary extends DrawableTile[Boundary] {
    override def drawTile(tile: Boundary, g: Graphics, player : Player): Unit = {
      paintTile(g, tile, SPACE)
    }
  }

  implicit object drawOther extends DrawableTile[Other] {
    override def drawTile(tile: Other, g: Graphics, player : Player): Unit = {
      paintTile(g, tile, SPACE)
    }
  }
}