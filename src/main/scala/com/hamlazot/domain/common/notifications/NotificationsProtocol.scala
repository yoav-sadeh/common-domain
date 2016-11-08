package com.hamlazot.domain.common.notifications

/**
 * @author yoav @since 10/31/16.
 */
trait NotificationsProtocol extends NotificationsAggregate{
  
  case class SubscribeRequest(
                               userId: UserId
                               , entityId: EntityId
                               , eventType: EventType
                               )

  case class SubscribeResponse(
                                subcriptionId: SubscriptionId
                                )

  case class UnsubscribeRequest(
                                 subcriptionId: SubscriptionId
                                 )

  case class UnsubscribeResponse(result: Boolean)

}
