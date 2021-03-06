package it.amongsus.view.actor

import akka.actor.ActorRef
import it.amongsus.core.entities.map.{Collectionable, DeadBody}
import it.amongsus.core.entities.player.{Crewmate, Impostor, Player}
import it.amongsus.core.entities.util.GameEnd.{Lost, Win}
import it.amongsus.core.entities.util.{ButtonType, GameEnd}
import it.amongsus.view.frame.{GameFrame, WinFrame}

/**
 * Trait that
 */
trait UiGameActorInfo {
  /**
   * The reference of the server actor
   *
   * @return
   */
  def clientRef: Option[ActorRef]
  /**
   * The frame of the game
   *
   * @return
   */
  def gameFrame: Option[GameFrame]
  /**
   *  Method to manage enable of buttons
   *
   * @param button to enable or disable
   * @param boolean that tell is the button is to turn on or off
   */
  def enableButton(button : ButtonType, boolean: Boolean): Unit
  /**
   * Method that update a character
   *
   * @param myChar to update
   * @param players of the game
   * @param collectionables of the game
   * @param deadBodies of the game
   */
  def updatePlayer(myChar: Player, players: Seq[Player], collectionables : Seq[Collectionable],
                   deadBodies : Seq[DeadBody]): Unit

  def updateKillButton(seconds : Long) : Unit

  def updateSabotageButton(seconds : Long) : Unit

  def endGame(myChar: Player, gameEnd: GameEnd): Unit
}

object UiGameActorInfo {
  def apply() : UiGameActorData = UiGameActorData(None, None)
  def apply(clientRef: Option[ActorRef], gameFrame: Option[GameFrame]) : UiGameActorData =
    UiGameActorData(clientRef, gameFrame)
}

case class UiGameActorData(override val clientRef: Option[ActorRef],
                           override val gameFrame: Option[GameFrame]) extends UiGameActorInfo {

  override def updatePlayer(myChar: Player,players: Seq[Player], collectionables : Seq[Collectionable],
                            deadBodies : Seq[DeadBody]): Unit =
    gameFrame.get.updatePlayers(myChar, players, collectionables, deadBodies)

  override def enableButton(button: ButtonType, boolean: Boolean): Unit =
    gameFrame.get.enableButton(button, boolean).unsafeRunSync()

  override def updateKillButton(seconds: Long): Unit = gameFrame.get.updateKillButton(seconds).unsafeRunSync()

  override def updateSabotageButton(seconds: Long): Unit = gameFrame.get.updateSabotageButton(seconds).unsafeRunSync()

  override def endGame(myChar: Player, gameEnd: GameEnd): Unit = {
    myChar match {
      case _: Crewmate => WinFrame(gameEnd).start().unsafeRunSync()
      case _: Impostor => WinFrame(gameEnd).start().unsafeRunSync()
    }
  }
}