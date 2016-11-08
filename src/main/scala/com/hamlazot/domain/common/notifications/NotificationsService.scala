package com.hamlazot
package domain
package common.notifications

import com.hamlazot.domain.common.notifications.NotificationsProtocol

/**
 * @author yoav @since 10/31/16.
 */
trait NotificationsService extends NotificationsProtocol with NotificationsAggregate with CommonOperations with CommonTerms {

  def subscribe: Operation[SubscribeRequest, SubscribeResponse]

  def unsubscribe: Operation[UnsubscribeRequest, UnsubscribeResponse]

}
