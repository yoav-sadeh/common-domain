package com.hamlazot
package domain
package common.recommendations

import com.hamlazot.domain.common.recommendations.RecommendationsProtocol


/**
 * @author yoav @since 10/10/16.
 */
trait RecommendationsService extends RecommendationsProtocol with RecommendationsAggregate with CommonTerms with CommonOperations {
  def recommend: Operation[RecommendationRequest, RecommendationResponse]

}
