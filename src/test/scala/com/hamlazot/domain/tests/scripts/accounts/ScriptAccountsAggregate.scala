package com.hamlazot.domain.tests.scripts.accounts

import java.util.UUID

import com.hamlazot.domain.common.accounts.AccountsAggregate

/**
 * @author yoav @since 10/31/16.
 */
trait ScriptAccountsAggregate extends AccountsAggregate{
  import ScriptAccountModel._
  override type Account = ScriptAccount
  //override type SignInRequest = (String, String)
  //override type GetAccountRequest = UUID
  //override type SignUpRequest = (String, String)
  //override type UpdateMailRequest = (UUID, String)
  //override type SignOutRequest = UUID
  override type AccountId = UUID
}

object ScriptAccountModel{
  case class ScriptAccount(id: UUID, name: String, mail: String)
}