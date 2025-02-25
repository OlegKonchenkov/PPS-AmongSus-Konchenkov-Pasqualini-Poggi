package it.amongsus.core.player

import it.amongsus.core.map.Coin

/**
 * Trait that manages Crewmate.
 */
trait Crewmate {
  self: Player =>
  /**
   * Number of the coin.
   */
  val numCoins: Int
  /**
   * Method to collect the coin.
   *
   * @return a new instance of a player whit updated coin number.
   */
  def collect():  Player = {
    self match {
      case alive: CrewmateAlive => CrewmateAlive(alive.color, alive.emergencyCalled, alive.clientId, alive.username,
        alive.numCoins + 1, alive.position)
      case ghost: CrewmateGhost => CrewmateGhost(ghost.color, ghost.clientId, ghost.username, ghost.numCoins + 1,
        ghost.position)
    }
  }
  /**
   * Method to check if a player can collect the coin of the game.
   * @param coins of the game.
   * @return a coin to collect if the player is on top of it, None otherwise.
   */
  def canCollect(coins: Seq[Coin], checkPosition: (Coin, Player) => Boolean): Option[Coin] = {
    coins.find(coin => checkPosition(coin, self))
  }
}