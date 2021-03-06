package com.hamlazot.domain.tests.scripts


import java.util

import com.hamlazot.domain.tests.scripts.Scenarios
import com.hamlazot.domain.tests.scripts.notifications.ScriptNotificationsService
import com.hamlazot.domain.tests.scripts.Scenarios
import com.hamlazot.domain.tests.scripts.notifications.NotificationsModel.AllEventTypes
import com.hamlazot.domain.tests.scripts.notifications.{NotificationBus, ScriptNotificationsService}
import com.hamlazot.domain.tests.scripts.products.ScriptProductsModel.ProductEntityType
import com.hamlazot.domain.tests.scripts.products.ScriptProductsService
import com.hamlazot.domain.tests.scripts.providers.ProviderEntityType
import com.hamlazot.domain.tests.scripts.recommendations.RecommendationModel.RatingOutOfFive
import com.hamlazot.domain.tests.scripts.recommendations.{RecommendedEventType, ScriptRecommendationsService}

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
    productRecommendation <- createRecommendation(maya.user.userId, product.productId, ProductEntityType, RatingOutOfFive.THREE, "I recommend it!!!")
    _ <- createNotification(yoav.user, product.productId, AllEventTypes)
    ido <- createAccountAndUser("Ido", "Sadeh", List(maya.user, yoav.user))
    provider <- createProvider(ido.user.userId, "Jbabo Sports", "sports", "A terrific sports wear store!!!")
    _ <- createNotification(ido.user, provider.providerId, RecommendedEventType)
    providerRecommendation <- createRecommendation(maya.user.userId, provider.providerId, ProviderEntityType, RatingOutOfFive.TWO, "I recommend it!!!")
  } yield (yoav, maya, product)

  println(prod)
  Thread.sleep(8000)
  val events = new util.ArrayList[Any]
  NotificationBus.queue.drainTo(events)
  events.asScala.toList.foreach(n => println(s"notification: $n"))

}

//TODO: move written scenarios to boot, make sure scenario method reflect scenarios
//TODO: try to segregate domains by packge name to make it difficult to use involuntarily
//TODO: complete README with details abouts constyructing the scripts and running them.
//TODO: ServiceProtocols don't cover clients' bizarre demands which DTOs which are DETACHED from the service do!!!