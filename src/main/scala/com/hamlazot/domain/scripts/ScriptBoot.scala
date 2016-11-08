package como.scripts


import java.util

import com.hamlazot.domain.scripts.Scenarios
import com.hamlazot.domain.scripts.notifications.NotificationsModel.AllEventTypes
import com.hamlazot.domain.scripts.notifications.{NotificationBus, ScriptNotificationsService}
import com.hamlazot.domain.scripts.products.ScriptProductsService
import com.hamlazot.domain.scripts.recommendations.RecommendationModel.RatingOutOfFive
import com.hamlazot.domain.scripts.recommendations.ScriptRecommendationsService

import scala.collection.JavaConverters.asScalaBufferConverter

/**
 * @author yoav @since 10/30/16.
 *
 */
object ScriptBoot extends App with Scenarios {


  ScriptNotificationsService.start

  val prod = for {
    yoav <- createAccountAndUser("Yoav", "Sadeh", Nil)
    maya <- createAccountAndUserWithTrustees("Maya", "Mamluk", yoav.user)
    product <- createProduct(maya.user, "Soap", "Well, it's a goddam soap!!!", "hygine")
    _ <- createNotification(maya.user, product.productId, AllEventTypes)
    productRecommendation <- createRecommendation(maya.user.userId, product.productId, ScriptProductsService.ProductEntityType, RatingOutOfFive.THREE, "I recommend it!!!")
    _ <- createNotification(yoav.user, product.productId, AllEventTypes)
    ido <- createAccountAndUser("Ido", "Sadeh", List(maya.user, yoav.user))
    provider <- createProvider(ido.user.userId, "Jbabo Sports", "sports", "A terrific sports wear store!!!")
    _ <- createNotification(ido.user, provider.providerId, ScriptRecommendationsService.RecommendedEventType)
    providerRecommendation <- createRecommendation(maya.user.userId, provider.providerId, providerService.ProviderEntityType, RatingOutOfFive.TWO, "I recommend it!!!")
  } yield (yoav, maya, product)

  println(prod)
  Thread.sleep(8000)
  val events = new util.ArrayList[Any]
  NotificationBus.queue.drainTo(events)
  events.asScala.toList.foreach(n => println(s"notification: $n"))

}





