package com.hamlazot
package domain.tests.scripts

package recommendations

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.hamlazot.domain.common.recommendations.{RecommendationsProtocol, RecommendationsService}
import com.hamlazot.domain.tests.scripts.notifications.EventBus
import com.hamlazot.domain.tests.scripts.notifications.NotificationsModel.{Created, CRUDEvent}
import com.hamlazot.domain.tests.scripts.recommendations.RecommendationModel.Recommendation

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration

/**
 * @author yoav @since 10/31/16.
 */
trait ScriptRecommendationsService extends RecommendationsService with RecommendationsProtocol with ScriptRecommendationsAggregate {
  override type Operation[A, B] = A => M1[B]
  private val recommendations = mutable.HashMap.empty[UUID, List[Recommendation]]
//  override val aggregate = ScriptRecommendationsAggregate
//
//  object ScriptRecommendationsProtocol extends RecommendationsProtocol[ScriptRecommendationsAggregate] {
//    override val aggregate = ScriptRecommendationsAggregate
//  }
//
//  override val protocol: RecommendationsProtocol[ScriptRecommendationsAggregate] = ScriptRecommendationsProtocol

  override def recommend: (RecommendationRequest) => M1[RecommendationResponse] = { request =>
    val recommendation = Recommendation(UUID.randomUUID(), request.userId, request.entityId, request.recommendedEntityType, request.rating, request.description)

    if (recommendations.contains(request.entityId)) {
      recommendations.update(request.entityId, recommendations(request.entityId) ::: List(recommendation))
    }
    import scala.concurrent.ExecutionContext.Implicits.global
    ActorSystem().scheduler.scheduleOnce(FiniteDuration(7, TimeUnit.SECONDS)) {
      val recommendationMsg = s"${request.recommendedEntityType} with id ${request.entityId} was recommended by user" +
        s"${request.userId}, with rating of ${request.rating}"
      EventBus.queue.put(CRUDEvent(RecommendationEntityType, recommendation.recommendedEntityId, s"CRUD event: ${RecommendationEntityType} with id ${recommendation.recommendedEntityId}", Created))
      EventBus.queue.put(EntityRecommendedEvent(RecommendationEntityType, recommendation.recommendedEntityId, recommendationMsg))
    }

    M1(RecommendationResponse(recommendation.recommendationId))
  }


}
object ScriptRecommendationsService extends ScriptRecommendationsService
