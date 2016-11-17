package com.hamlazot.domain
package tests.scripts
package notifications

import java.util
import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import com.hamlazot.domain.common.notifications.{NotificationsProtocol, NotificationsService}
import com.hamlazot.domain.tests.scripts.notifications.NotificationsModel.{Event, Subscription}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration

/**
 * @author yoav @since 10/31/16.
 */
trait ScriptNotificationsService extends NotificationsService with NotificationsProtocol with ScriptNotificationsAggregate with LazyLogging {
  override type Operation[A, B] = A => M1[B]


  //  override val aggregate: ScriptNotificationsAggregate = ScriptNotificationsAggregate
  //  override val protocol = new NotificationsProtocol {
  //    override val aggregate = ScriptNotificationsAggregate
  //  }

  private val subscriptions = scala.collection.mutable.MutableList.empty[Subscription]

  protected def processEvent: (Event) => M1[Unit] = { event =>
    subscriptions.find(p => p.entityId == event.entityId).fold(logger.info(s"got event: $event but no subscriptions found...")) {
      case Subscription(_, userId, entityId, eventType) =>
        NotificationBus.queue.put((userId, event))
    }

    M1(())
  }


  override def unsubscribe: (UnsubscribeRequest) => M1[UnsubscribeResponse] = { request =>
    M1(UnsubscribeResponse(true))
  }

  override def subscribe: (SubscribeRequest) => M1[SubscribeResponse] = { request =>
    val subscription = Subscription(UUID.randomUUID(), request.userId, request.entityId, request.eventType)
    subscriptions += subscription
    M1(SubscribeResponse(subscription.subscriptionId))
  }

}

object ScriptNotificationsService extends ScriptNotificationsService {
  def start: Unit = {
    getSystem().scheduler.schedule(FiniteDuration(0, TimeUnit.SECONDS), FiniteDuration(1, TimeUnit.SECONDS)) {
      val events = new util.ArrayList[Event]
      EventBus.queue.drainTo(events)
      events.asScala.toList.foreach(processEvent)
    }
  }
}

object NotificationBus {
  val queue = new ArrayBlockingQueue[(UUID, Event)](10)
}

object EventBus {
  val queue = new ArrayBlockingQueue[Event](10)
}
