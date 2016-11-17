package com.hamlazot
package domain.common.accounts

import scala.util.Try


/**
 * @author yoav @since 6/21/16.
 */
trait AccountsService extends AccountsProtocol with AccountsAggregate with CommonOperations {
  
  def signUp: Operation[SignUpRequest, Account]

  def signIn: Operation[SignInRequest, Try[AuthenticationToken]]

  def signOut: Operation[SignOutRequest, SignOutResponse]

  def changeMailAddress: Operation[UpdateMailRequest, Account]

  def getAccount: Operation[GetAccountRequest, Account]
}
