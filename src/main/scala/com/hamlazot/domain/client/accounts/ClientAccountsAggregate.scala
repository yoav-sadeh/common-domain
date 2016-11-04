package com.hamlazot.domain.client.accounts

import com.hamlazot.domain.common.accounts.AccountsAggregate

/**
 * @author yoav @since 10/11/16.
 */
trait ClientAccountsAggregate extends AccountsAggregate {
  type Mail
  override type UpdateMailRequest = (AccountId, Mail)

}
