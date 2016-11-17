package com.hamlazot.domain.common.recommendations

/**
 * @author yoav @since 10/31/16.
 */
trait RecommendationsProtocol extends RecommendationsAggregate {


  case class RecommendationRequest(userId: RecommendingUserId, entityId: RecommendedEntityId, recommendedEntityType: RecommendedEntityType, description: String, rating: Rating)

  case class RecommendationResponse(recommendationId: RecommendationId)

}

