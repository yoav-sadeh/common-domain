package com.hamlazot
package domain
package scripts

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.hamlazot.domain.scripts.accounts.ScriptAccountService
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{EntityType, EventType, Created}
import com.hamlazot.domain.scripts.notifications.{NotificationBus, ScriptNotificationsService}
import com.hamlazot.domain.scripts.products.ScriptProductsModel.ProductCategory
import com.hamlazot.domain.scripts.products.ScriptProductsService
import com.hamlazot.domain.scripts.providers.{ScriptProvidersService, ConcreteProviderCategory}
import com.hamlazot.domain.scripts.recommendations.RecommendationModel.RatingOutOfFive.RatingOutOfFive
import com.hamlazot.domain.scripts.users.ScriptUsersModel.AUser
import com.hamlazot.domain.scripts.users.ScriptUsersService
import com.hamlazot.domain.scripts.recommendations.ScriptRecommendationsService

import scala.concurrent.ExecutionContext

/**
 * @author yoav @since 10/31/16.
 */
trait Scenarios {

  import ScriptProductsService.protocol._
  import ScriptUsersService.protocol._
  import ScriptRecommendationsService.protocol._
  val providerService = ScriptProvidersService()
  import providerService._

  //Scenarios:

  //1. Create account -> Create User  = User UA
  def createAccountAndUser(name: String, mail: String, trustees: List[AUser]) = for {
    account <- ScriptAccountService.signUp(name, mail)
    userIdResponse <- ScriptUsersService.createUser(CreateUserRequest(account.name, trustees))
    user <- ScriptUsersService.getUser(GetUserRequest(userIdResponse.userId))
  } yield user

  //2. Create account -> Create User -> Add Trustees(UA)(Add Trusters to UA) = User UB
  def createAccountAndUserWithTrustees(name: String, mail: String, user: AUser) = for {
    userResponse <- createAccountAndUser(name, mail, List(user))
    result <- ScriptUsersService.addTrusters(AddTrustersRequest(user.userId, List(userResponse.user)))
  } yield userResponse

  //3. UB - Create Product = Product PA
  def createProduct(user: AUser, productName: String, productDescription: String, productCategoryName: String) = {
    val product = ScriptProductsService.createProduct(CreateProductRequest(user.userId, productName, ProductCategory(UUID.randomUUID(), productCategoryName), productDescription))

    product
  }

  //4. UB - Create Product Notification on PA
  def createNotification(user: AUser, entityId: UUID, eventType: EventType) = {

    val subscription = ScriptNotificationsService.subscribe(ScriptNotificationsService.protocol.SubscribeRequest(user.userId, entityId, eventType))

    subscription
  }

  //5. UA - Create Recommendation on PA
  def createRecommendation(userId: UUID, entityId: UUID, entityType: EntityType, rating: RatingOutOfFive, description: String) = {
    val request = RecommendationRequest(userId, entityId, entityType, description, rating)
    ScriptRecommendationsService.recommend(request)
  }

  //6. create user UC

  //7. UC - create provider
  def createProvider(providerUser: UUID,
                     providerName: String,
                     providerCategory: String,
                     providerDescription: String) = {

    providerService.createProvider(CreateProviderRequest(providerUser, providerName, ConcreteProviderCategory(UUID.randomUUID(), providerCategory), providerDescription))
  }
  //8. UC - recommend provider
}
