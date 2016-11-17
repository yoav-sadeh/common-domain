package com.hamlazot.domain.tests

import akka.actor.ActorSystem

/**
 * @author yoav @since 10/30/16.
 */
package object scripts {

  case class M1[T](value: T) {
    def map[S](f: T => S): M1[S] = {
      M1(f(value))
    }

    def flatMap[S](f: T => M1[S]): M1[S] = {
      f(value)
    }



  }
  private val system = ActorSystem("scripts")

  def getSystem() = system

}
