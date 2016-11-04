package com.hamlazot
package domain.common.accounts

import scala.util.Try


/**
 * @author yoav @since 6/21/16.
 */
trait AccountsService[A <: AccountsAggregate] extends CommonOperations with CommonTerms {

  val aggregate: A
  val protocol: AccountsProtocol[A]

  def signUp: Operation[aggregate.SignUpRequest, aggregate.Account]

  def signIn: Operation[aggregate.SignInRequest, Try[AuthenticationToken]]

  def signOut: Operation[protocol.SignOutRequest, protocol.SignOutResponse]

  def changeMailAddress: Operation[aggregate.UpdateMailRequest, aggregate.Account]

  def getAccount: Operation[aggregate.GetAccountRequest, aggregate.Account]
}
