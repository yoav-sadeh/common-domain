package com.hamlazot.domain.common.providers

import com.hamlazot.domain.scripts.notifications.NotificationsModel.EntityType

/**
 * @author yoav @since 11/7/16.
 */
trait ProvidersProtocol extends ProvidersAggregate{
  case class CreateProviderRequest(providerUser: ProviderUser,
                                   providerName: ProviderName,
                                    providerCategory: ProviderCategory,
                                    providerDescription: ProviderDescription)
  case class CreateProviderResponse(providerId: ProviderId)
  case class DeleteProviderRequest(providerUser: ProviderUser, providerId: ProviderId)
  case class DeleteProviderResponse()
  case class UpdateProviderRequest(providerUser: ProviderUser,
                                   providerId: ProviderId,
                                    providerName: Option[ProviderName],
                                    providerCategory: Option[ProviderCategory],
                                    description: Option[ProviderDescription])
  case class UpdateProviderResponse()
  case class GetProviderRequest(providerId: ProviderId)
  case class GetProviderResponse(provider: Provider)

  case object ProviderEntityType extends EntityType
}
