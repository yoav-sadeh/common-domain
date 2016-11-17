package com.hamlazot
package domain
package common.recommendations


/**
 * @author yoav @since 10/10/16.
 */
trait RecommendationsService extends RecommendationsProtocol with RecommendationsAggregate with CommonOperations {
  def recommend: Operation[RecommendationRequest, RecommendationResponse]

}
