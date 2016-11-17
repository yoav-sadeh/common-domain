package com.hamlazot.domain.common.accounts

/**
 * @author yoav @since 11/4/16.
 */
trait AccountsProtocol extends AccountsAggregate {
  
  case class SignUpRequest(name: String, mail: String)
  case class SignInRequest(token: AuthenticationToken)
  case class SignOutRequest(userId: AccountId)
  case class UpdateMailRequest(accountId: AccountId, mail: String)
  case class GetAccountRequest(accountId: AccountId)

  case class SignUpResponse()
  case class SignInResponse()
  case class SignOutResponse(success: Boolean)
  case class UpdateMailResponse()
  case class GetAccountResponse()
}
