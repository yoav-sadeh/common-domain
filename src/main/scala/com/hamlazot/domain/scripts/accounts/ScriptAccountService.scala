package com.hamlazot
package domain
package scripts
package accounts

import java.util.UUID

import com.hamlazot.domain.common.accounts.{AccountsAggregate, AccountsProtocol, AccountsService}
import com.hamlazot.domain.scripts.accounts.ScriptAccountModel.ScriptAccount
import com.hamlazot.domain.scripts.accounts.ScriptAccountsAggregate

import scala.util.{Success, Try}

/**
 * @author yoav @since 10/31/16.
 */
object ScriptAccountService extends AccountsService[ScriptAccountsAggregate] {
  override type Operation[A, B] = A => M1[B]
  override type AuthenticationToken = UUID

  override def signUp: ((String, String)) => M1[ScriptAccount] = { request =>
    M1(ScriptAccount(UUID.randomUUID(), request._1, request._2))
  }

  override def changeMailAddress: ((UUID, String)) => M1[ScriptAccount] = { request =>
    M1(ScriptAccount(request._1, "DummyName", request._2))
  }

  override def signIn: ((String, String)) => M1[Try[UUID]] = { request =>
    M1(Success(UUID.randomUUID()))
  }

  override def getAccount: (UUID) => M1[ScriptAccount] = { request =>
    M1(ScriptAccount(request, "DummyName", "DummyEmail"))
  }

  override def signOut: (protocol.SignOutRequest) => M1[protocol.SignOutResponse] = { request =>
    M1(protocol.SignOutResponse(true))
  }

  object ScriptAccountsAggregate extends ScriptAccountsAggregate
  object ScriptAccountsProtocol extends AccountsProtocol[ScriptAccountsAggregate] {
    override val aggregate: AccountsAggregate = ScriptAccountsAggregate
  }
  override val aggregate: ScriptAccountsAggregate = ScriptAccountsAggregate
  override val protocol: AccountsProtocol[ScriptAccountsAggregate] = ScriptAccountsProtocol
}

