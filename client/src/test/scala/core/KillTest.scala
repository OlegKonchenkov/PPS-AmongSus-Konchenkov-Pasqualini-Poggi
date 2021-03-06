package core

import it.amongsus.core.entities.map.{DeadBody, Tile}
import it.amongsus.core.entities.player.{CrewmateAlive, ImpostorAlive, Player}
import it.amongsus.core.entities.util.Point2D
import it.amongsus.model.actor.ModelActorInfo
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class KillTest extends AnyWordSpecLike with BeforeAndAfterAll {

  private final val positionDefault35 = 35

  private val crewmateAlive: Player = CrewmateAlive("green", emergencyCalled = true, "asdasdasd",
    "imCrewmate", 3, Point2D(positionDefault35, positionDefault35))
  private val deadCrewmate: DeadBody = DeadBody("green", Point2D(positionDefault35, positionDefault35))
  private val impostorAlive: Player = ImpostorAlive("green", emergencyCalled = true,
    "qwerty", "imImpostor", Point2D(positionDefault35, positionDefault35))
  private val modelActor: ModelActorInfo = ModelActorInfo()
  private val map: Array[Array[Tile]] = modelActor.generateMap(loadMap())
  modelActor.generateCollectionables(map)

  "An Impostor Alive" should {
    "Can Kill Crewmate" in {
      this.impostorAlive match {
        case impostor: ImpostorAlive => assert(impostor.canKill(Point2D(positionDefault35, positionDefault35),
          Seq(crewmateAlive)))
      }
    }

    "Can Report Kill" in {
      this.impostorAlive match {
        case impostor: ImpostorAlive => assert(impostor.canReport(Point2D(positionDefault35, positionDefault35),
          Seq(deadCrewmate)))
      }
    }
  }

  "A Crewmate Alive" should {
    "Can Report Kill" in {
      this.crewmateAlive match {
        case crewmate: CrewmateAlive => assert(crewmate.canReport(Point2D(positionDefault35, positionDefault35),
          Seq(deadCrewmate)))
      }
    }
  }

  def loadMap(): Iterator[String] = {
    val bufferedSource = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/images/gameMap.csv"))
    bufferedSource.getLines
  }
}