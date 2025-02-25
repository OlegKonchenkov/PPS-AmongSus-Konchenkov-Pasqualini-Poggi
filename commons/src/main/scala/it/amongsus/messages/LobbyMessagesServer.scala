package it.amongsus.messages

import akka.actor.ActorRef

object LobbyMessagesServer {
  /**
   * Request of client to connect
   *
   * @param clientRef the reference to the player
   */
  case class ConnectServer(clientRef: ActorRef)
  /**
   * Message sent by the client to join a public lobby for a match with the given number of players
   *
   * @param clientId of the player
   * @param username the username chosen by the user
   * @param numberOfPlayers the number of players required to start a match
   */
  case class JoinPublicLobbyServer(clientId: String, username: String, numberOfPlayers: Int)
  /**
   * Message sent by the client to join a private lobby
   *
   * @param clientId of the player
   * @param username username chosen by the user
   * @param privateLobbyCode required to start a match
   */
  case class JoinPrivateLobbyServer(clientId: String, username: String, privateLobbyCode: String)
  /**
   * Message sent by the client to request a creation of a new private lobby and join
   *
   * @param clientId The ID of the user (the one retrieved by the server after the first connection)
   * @param username the username chosen by the user
   * @param numberOfPlayers the number of players required to start a match
   */
  case class CreatePrivateLobbyServer(clientId: String, username: String, numberOfPlayers: Int)
  /**
   * Message sent by the client to leave the current lobby
   *
   * @param clientId The ID of the user (the one retrieved by the server after the first connection)
   */
  case class LeaveLobbyServer(clientId: String)
  /**
   * Notify an error during the lobby phase
   *
   * @param error the error occurred
   */
  case class LobbyErrorOccurred(error: LobbyError)
  /**
   * Message sent by the server on match found
   *
   * @param gameRoom the references to the actor of the game room to join
   */
  case class MatchFound(gameRoom: ActorRef)
  /**
   * Error occurred in the lobby phase
   */
  sealed class LobbyError

  object LobbyError {
    case object PrivateLobbyIdNotValid extends LobbyError
    case object InvalidUserId extends LobbyError
  }
}