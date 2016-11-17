package com.hamlazot
package domain.tests.scripts


import java.util.UUID

import com.hamlazot.domain.tests.scripts.accounts.ScriptAccountService

//import com.hamlazot.domain.tests.scripts.accounts.ScriptAccountService

import com.hamlazot.domain.tests.scripts.notifications.NotificationsModel.{EntityType, EventType}
import com.hamlazot.domain.tests.scripts.notifications.ScriptNotificationsService
import com.hamlazot.domain.tests.scripts.products.ScriptProductsModel.ProductCategory
import com.hamlazot.domain.tests.scripts.products.ScriptProductsService
import com.hamlazot.domain.tests.scripts.providers.{ConcreteProviderCategory, ScriptProvidersService}
import com.hamlazot.domain.tests.scripts.recommendations.RecommendationModel.RatingOutOfFive.RatingOutOfFive
import com.hamlazot.domain.tests.scripts.recommendations.ScriptRecommendationsService
import com.hamlazot.domain.tests.scripts.users.ScriptUsersModel.AUser
import com.hamlazot.domain.tests.scripts.users.ScriptUsersService

/**
 * @author yoav @since 10/31/16.
 */
trait Scenarios {

  import ScriptProductsService._
  import ScriptRecommendationsService._
  import ScriptUsersService._

  val providerService = ScriptProvidersService()

  import ScriptAccountService._
  import providerService._

  //Scenarios:

  //1. Create account -> Create User  = User UA
  def createAccountAndUser(name: String, mail: String, trustees: List[AUser]) = for {
    account <- ScriptAccountService.signUp(SignUpRequest(name, mail))
    userIdResponse <- ScriptUsersService.createUser(CreateUserRequest(name, trustees))
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

    val subscription = ScriptNotificationsService.subscribe(ScriptNotificationsService.SubscribeRequest(user.userId, entityId, eventType))

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
