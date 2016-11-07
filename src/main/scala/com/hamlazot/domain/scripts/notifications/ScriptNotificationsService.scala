package com.hamlazot.domain
package scripts
package notifications

import java.util
import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import akka.actor.ActorSystem
import com.hamlazot.domain.client.notifications.NotificationsProtocol
import com.hamlazot.domain.common.notifications.NotificationsService
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{Event, Subscription}
import com.typesafe.scalalogging.LazyLogging
import como.scripts.ScriptBoot

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration
/**
 * @author yoav @since 10/31/16.
 */
trait ScriptNotificationsService extends NotificationsService[ScriptNotificationsAggregate] with LazyLogging{
  override type Operation[A, B] = A => M1[B]


  override val aggregate: ScriptNotificationsAggregate = ScriptNotificationsAggregate
  override val protocol = new NotificationsProtocol {
    override val aggregate = ScriptNotificationsAggregate
  }

  private val subscriptions = scala.collection.mutable.MutableList.empty[aggregate.Subscription]

  protected def processEvent: (Event) => M1[Unit] = { event =>
    subscriptions.find(p => p.entityId == event.entityId).fold(logger.info(s"got event: $event but no subscriptions found...")){
      case Subscription(_, userId, entityId, eventType) =>
        NotificationBus.queue.put((userId, event))
    }

    M1(())
  }


  override def unsubscribe: (protocol.UnsubscribeRequest) => M1[protocol.UnsubscribeResponse] = { request =>
    M1(protocol.UnsubscribeResponse(true))
  }

  override def subscribe: (protocol.SubscribeRequest) => M1[protocol.SubscribeResponse] = { request =>
    val subscription = Subscription(UUID.randomUUID(), request.userId, request.entityId, request.eventType)
    subscriptions += subscription
    M1(protocol.SubscribeResponse(subscription.subscriptionId))
  }

}

object ScriptNotificationsService extends ScriptNotificationsService{
  def start: Unit ={
    getSystem().scheduler.schedule(FiniteDuration(0, TimeUnit.SECONDS), FiniteDuration(1, TimeUnit.SECONDS)){
      val events = new util.ArrayList[Event]
      EventBus.queue.drainTo(events)
      events.asScala.toList.foreach(processEvent)
    }
  }
}

object NotificationBus{
  val queue = new ArrayBlockingQueue[(UUID, Event)](10)
}

object EventBus{
  val queue = new ArrayBlockingQueue[Event](10)
}
