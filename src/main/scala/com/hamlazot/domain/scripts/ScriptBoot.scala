package como.scripts


import java.util
import java.util.concurrent.TimeUnit

import com.hamlazot.domain.scripts.Scenarios
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{Event, AllEventTypes}
import com.hamlazot.domain.scripts.notifications.{NotificationBus, ScriptNotificationsService}
import com.hamlazot.domain.scripts.products.ScriptProductsService
import com.hamlazot.domain.scripts.products.ScriptProductsService.ScriptProductsProtocol
import com.hamlazot.domain.scripts.recommendations.RecommendationModel.RatingOutOfFive

import scala.concurrent.ExecutionContext
import scala.collection.JavaConverters.asScalaBufferConverter

/**
 * @author yoav @since 10/30/16.
 *
 */
object ScriptBoot extends App with Scenarios {

  override implicit val ctxt: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  ScriptNotificationsService.start
  val prod = for {
    yoav <- createAccountAndUser("Yoav", "Sadeh", Nil)
    maya <- createAccountAndUserWithTrustees("Jbabo", "Elmekayes", yoav.user)
    product <- createProduct(maya.user, "Soap", "Well, it's a goddam soap!!!", "hygine")
    _ <- createNotification(maya.user, product.productId, AllEventTypes)
    recommendation <- createRecommendation(maya.user.userId, product.productId, ScriptProductsService.protocol.ProductEntityType, RatingOutOfFive.THREE, "I recommend it!!!")
    _ <- createNotification(yoav.user, product.productId, AllEventTypes)
  } yield (yoav, maya, product)

  println(prod)
  Thread.sleep(8000)
  val events = new util.ArrayList[Any]
  NotificationBus.queue.drainTo(events)
  events.asScala.toList.foreach(n => println(s"notification: $n"))

}





