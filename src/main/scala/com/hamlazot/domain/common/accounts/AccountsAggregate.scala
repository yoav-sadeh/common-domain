package com.hamlazot.domain.common.accounts

/**
 * @author yoav @since 10/11/16.
 */
trait AccountsAggregate {
  type Account
  type AccountId
  type SignUpRequest
  type SignInRequest
  //type SignOutRequest
  type UpdateMailRequest
  type GetAccountRequest
}
