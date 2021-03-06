package com.hamlazot
package domain
package common.users

import com.hamlazot.domain.common.users.UsersProtocol

/**
 * Created by Owner on 9/30/2016.
 */
trait UsersService extends UsersProtocol with UsersAggregate with CommonOperations{

  def createUser: Operation[CreateUserRequest, CreateUserResponse]

  def getUser: Operation[GetUserRequest, GetUserResponse]

  def deleteUser: Operation[DeleteUserRequest, DeleteUserResponse]

  def addTrustees: Operation[AddTrusteesRequest, AddTrusteesResponse]

  def addTrusters: Operation[AddTrustersRequest, AddTrustersResponse]

  def removeTrustees: Operation[RemoveTrusteesRequest, RemoveTrusteesResponse]

  def removeTrusters: Operation[RemoveTrustersRequest, RemoveTrustersResponse]
//AddTrusteesResponse
  //AddTrustersResponse
}



object UsersServiceDescriptionApp extends App{
  val ru = scala.reflect.runtime.universe
  val m = ru.runtimeMirror(getClass.getClassLoader)
  val im = ru.typeOf[UsersService]
  //im.baseClasses(1).asType.info.decls.toList(0)
  //im.baseClasses(1).asType.info.decls.toList(0).asType.typeParams
  im.members.filter(_.info.dealias.resultType.toString.contains("Operation")).foreach(m => println(s"$m ${m.typeSignature}"))
//  {m =>
//    val sig = m.info.dealias.resultType.toString
//    println()
//  }
  println(im.baseClasses(1).asType.info.decls.toList(0))
  println(im.baseClasses(1).asType.info.decls.toList(0).asType.typeParams)
}