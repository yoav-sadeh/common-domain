package com.hamlazot.domain.common.recommendations

import java.util.UUID

import com.hamlazot.domain.scripts.notifications.NotificationsModel.{EntityType, Event, EventType}

/**
 * @author yoav @since 10/31/16.
 */
trait RecommendationsProtocol extends RecommendationsAggregate {

  case object RecommendationEntityType extends EntityType
  case object RecommendedEventType extends EventType

  case class EntityRecommendedEvent(entityType: EntityType, entityId: UUID, entityKnownName: String) extends Event {
    override val eventType: EventType = RecommendedEventType
  }
  case class RecommendationRequest(userId: RecommendingUserId, entityId: RecommendedEntityId, recommendedEntityType: RecommendedEntityType, description: String, rating: Rating)
  case class RecommendationResponse(recommendationId: RecommendationId)
}

