package com.hamlazot
package domain.tests
package scripts
package accounts

import java.util.UUID

import com.hamlazot.domain.common.accounts.{AccountsProtocol, AccountsService}
import com.hamlazot.domain.tests.scripts.accounts.ScriptAccountModel.ScriptAccount

import scala.util.{Success, Try}

/**
 * @author yoav @since 10/31/16.
 */
object ScriptAccountService extends AccountsService with AccountsProtocol with ScriptAccountsAggregate {
  override type Operation[A, B] = A => M1[B]
  override type AuthenticationToken = UUID

  override def signUp: (SignUpRequest) => M1[ScriptAccount] = { request =>
    M1(ScriptAccount(UUID.randomUUID(), request.name, request.mail))
  }

  override def changeMailAddress: (UpdateMailRequest) => M1[ScriptAccount] ={ request =>
    M1(ScriptAccount(request.accountId, "DummyName", request.mail))
  }

  override def signIn: (SignInRequest) => M1[Try[UUID]] = { request =>
    M1(Success(UUID.randomUUID()))
  }

  override def getAccount: (GetAccountRequest) => M1[ScriptAccount] =  { request =>
    M1(ScriptAccount(request.accountId, "DummyName", "DummyEmail"))
  }

  override def signOut: (SignOutRequest) => M1[SignOutResponse] = { request =>
    M1(SignOutResponse(true))
  }
}

