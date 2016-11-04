package com.hamlazot.domain.client.accounts

import com.hamlazot.domain.common.accounts.AccountsService

/**
 * @author yoav @since 7/6/16.
 */
private[domain] trait ClientAccountService extends AccountsService[ClientAccountsAggregate] {

  val dataStore: AccountLocalRepository[aggregate.Account]
}

