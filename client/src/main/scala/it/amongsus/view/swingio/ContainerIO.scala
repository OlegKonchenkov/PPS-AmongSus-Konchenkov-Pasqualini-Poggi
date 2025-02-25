package it.amongsus.view.swingio

import cats.effect.IO
import java.awt.{Component, Container, LayoutManager}

/**
 * A class that provides a monadic description of the operations supplied by awt's [[Container]] in the form
 * of IO monad in a purely functional style. This package provided some ad-hoc factory utilities for the most
 * popular Swing's containers.
 *
 * @param component the container that this class wraps.
 * @tparam T the type of the component to be wrapped. and whose methods are to be enhanced with IO description.
 */
class ContainerIO[T<:Container](override val component: T) extends ComponentIO(component) {
  /**
   * Monadic description of Swing's add method
   * @param componentToBeAdded
   */
  def add(componentToBeAdded: ComponentIO[_<:Component]): IO[Component] =
    IO {component.add(componentToBeAdded.component)}


  /**
   * Monadic description of Swing's add method
   * @param name
   * @param componentToBeAdded
   */
  def add(name: String, componentToBeAdded: ComponentIO[_<:Component]): IO[Component] =
    IO {component.add(name, componentToBeAdded.component)}


  /**
   * Monadic description of Swing's add method
   * @param componentToBeAdded
   * @param constraints
   */
  def add(componentToBeAdded: ComponentIO[_<:Component], constraints : Object): IO[Unit] =
    IO {component.add(componentToBeAdded.component, constraints)}

  /**
   * Monadic description of Swing's remove method
   * @param componentToBeAdded
   * @return
   */
  def remove(componentToBeRemoved: ComponentIO[_<:Component]): IO[Unit] =
    IO {component.remove(componentToBeRemoved.component)}

  /**
   * Monadic description of Swing's removeAll method
   */
  def removeAll(): IO[Unit] = IO {component.removeAll()}

  /**
   * Monadic description of Swing's setLayout method
   * @param manager
   */
  def setLayout(manager : LayoutManager): IO[Unit] = IO {component.setLayout(manager)}

  /**
   * Monadic description using InvokeAndWaiting of Swing's add method
   * @param componentToBeAdded
   */
  def addInvokingAndWaiting(componentToBeAdded: ComponentIO[_<:Component]): IO[Unit] =
    invokeAndWaitIO(component.add(componentToBeAdded.component))

  /**
   * Monadic description using InvokeAndWaiting of Swing's add method
   * @param name
   * @param componentToBeAdded
   */
  def addInvokingAndWaiting(name: String, componentToBeAdded: ComponentIO[_<:Component]): IO[Unit] =
    invokeAndWaitIO(component.add(name, componentToBeAdded.component))

  /**
   * Monadic description using InvokeAndWaiting of Swing's add method
   * @param componentToBeAdded
   * @param constraints
   */
  def addInvokingAndWaiting(componentToBeAdded: ComponentIO[_<:Component], constraints : Object): IO[Unit] =
    invokeAndWaitIO(component.add(componentToBeAdded.component, constraints))

  /**
   * Monadic description using InvokeAndWaiting of Swing's remove method
   * @param componentToBeRemoved
   */
  def removeInvokingAndWaiting(componentToBeRemoved: ComponentIO[_<:Component]): IO[Unit] =
    invokeAndWaitIO(component.remove(componentToBeRemoved.component))

  /**
   * Monadic description using InvokeAndWaiting of Swing's removeAll method
   */
  def removeAllInvokingAndWaiting(): IO[Unit] = invokeAndWaitIO(component.removeAll())

  /**
   * Monadic description using InvokeAndWaiting of Swing's setLayout method
   * @param manager
   */
  def setLayoutInvokingAndWaiting(manager : LayoutManager): IO[Unit] = invokeAndWaitIO(component.setLayout(manager))
}