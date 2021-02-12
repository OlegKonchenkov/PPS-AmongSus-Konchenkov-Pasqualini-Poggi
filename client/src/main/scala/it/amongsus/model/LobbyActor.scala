package it.amongsus.model

import akka.actor.{Actor, ActorLogging, Props}
import it.amongsus.messages.GameMessageClient._
import it.amongsus.messages.GameMessageServer._
import it.amongsus.messages.LobbyMessagesClient._
import it.amongsus.messages.LobbyMessagesServer._
import it.amongsus.view.actor.UiActorGameMessages._
import it.amongsus.view.actor.UiActorLobbyMessages._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object LobbyActor {
  def props(state: LobbyActorInfo): Props =
    Props(new LobbyActor(state))
}

/**
 * Actor responsible to receiving the message of the lobby server
 *
 * @param state state of the lobbyActorInfo that represents the function that notify the user about the received event
 */
class LobbyActor(private val state: LobbyActorInfo) extends Actor  with ActorLogging {

  override def receive: Receive = defaultBehaviour(state)

  private def defaultBehaviour(state: LobbyActorInfo): Receive = {
    case ConnectClient(address, port) =>
      state.guiRef.get ! Init()
      state.resolveRemoteActorPath(state.generateServerActorPath(address, port)) onComplete {
        case Success(ref) =>
          ref ! ConnectServer(context.self)
        case Failure(t) =>
          println(LobbyJoinErrorEvent(ErrorEvent.ServerNotFound))
      }
    case Connected(id) => context become defaultBehaviour(LobbyActorInfoData(Option(sender), state.guiRef, id))

    case JoinPublicLobbyClient(username: String, numberOfPlayers: Int) =>
      state.serverRef.get ! JoinPublicLobbyServer(state.clientId, username, numberOfPlayers)

    case CreatePrivateLobbyClient(username: String, numberOfPlayers: Int) =>
      state.serverRef.get ! CreatePrivateLobbyServer(state.clientId, username, numberOfPlayers)

    case JoinPrivateLobbyClient(username: String, privateLobbyCode: String) =>
      state.serverRef.get ! JoinPrivateLobbyServer(state.clientId, username, privateLobbyCode)

    case LeaveLobbyClient() =>
      state.serverRef.get ! LeaveLobbyServer(state.clientId)

    case UserAddedToLobbyClient(numPlayers) => state.guiRef.get ! UserAddedToLobbyUi(numPlayers)

    case UpdateLobbyClient(numPlayers) => println("prova ")
      state.guiRef.get ! UpdateLobbyClient(numPlayers)

    case PrivateLobbyCreatedClient(lobbyCode) => state.guiRef.get ! PrivateLobbyCreatedUi(lobbyCode)

    case MatchFound(gameRoom) =>
      state.guiRef.get ! GameFoundUi()
      context become gameBehaviour(GameActorInfo(Option(gameRoom), state.guiRef, state.clientId))

    case LobbyErrorOccurred(error) => error match {
      case LobbyError.PrivateLobbyIdNotValid => ???
      case _ =>
    }

    case m: String => log.debug(m)
  }

  private def gameBehaviour(state: GameActorInfo): Receive = {
    case PlayerReadyClient() => state.gameServerRef.get ! PlayerReadyServer(state.clientId, self)

    case LeaveGameClient() => state.gameServerRef.get ! LeaveGameServer(state.clientId)

    case GameWonClient() => state.guiRef.get ! GameWonUi()

    case GameLostClient() => state.guiRef.get ! GameLostUi()

    case PlayerLeftClient() => state.guiRef.get ! PlayerLeftUi()

    case InvalidPlayerActionClient() => state.guiRef.get ! InvalidPlayerActionUi()

    case GameStateUpdatedClient() => GameStateUpdatedUi()

    case _ => println("error")
  }
}