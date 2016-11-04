package com.hamlazot.domain.common.accounts

/**
 * @author yoav @since 11/4/16.
 */
trait AccountsProtocol[A <: AccountsAggregate] {

  val aggregate: AccountsAggregate
  
  case class SignUpRequest()
  case class SignInRequest()
  case class SignOutRequest(userId: aggregate.AccountId)
  case class UpdateMailRequest()
  case class GetAccountRequest()

  case class SignUpResponse()
  case class SignInResponse()
  case class SignOutResponse(success: Boolean)
  case class UpdateMailResponse()
  case class GetAccountResponse()
}
