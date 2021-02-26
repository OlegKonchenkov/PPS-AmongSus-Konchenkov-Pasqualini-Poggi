package it.amongsus.controller.actor

import it.amongsus.core.entities.map.{Collectionable, DeadBody, Tile}
import it.amongsus.core.entities.player.Player
import it.amongsus.core.entities.util.{ButtonType, Movement}

object ControllerActorMessages {
  /**
   * Tells to the controller that the model is ready
   */
  case class ModelReadyCotroller(map: Array[Array[Tile]], myChar: Player, players: Seq[Player],
                                 collectionables: Seq[Collectionable])
  /**
   * Tells to the controller that his character moved
   */
  case class MyCharMovedCotroller(direction: Movement)
  /**
   * Tells to the controller that the model has updated his character status
   */
  case class UpdatedMyCharController(player: Player, gamePLayers: Seq[Player], deadBodys: Seq[DeadBody])
  /**
   * Tells to the controller that the model has updated a player status
   */
  case class UpdatedPlayersController(myChar: Player, player: Seq[Player], collectionables: Seq[Collectionable],
                                      deadBodies: Seq[DeadBody])
  /**
   * Tells to the controller that the model has updated a player status
   */
  case class UpdatedPlayerController(player: Player)
  /**
   * Tells to the controller that the Ui Button is pressed
   * @param button that is pressed
   */
  case class UiButtonPressedController(button: ButtonType)
}
