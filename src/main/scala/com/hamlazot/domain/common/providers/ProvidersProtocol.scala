package com.hamlazot.domain.common.providers

/**
 * @author yoav @since 11/7/16.
 */
trait ProvidersProtocol extends ProvidersAggregate {

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
                                   stock: Option[Stock],
                                   description: Option[ProviderDescription])

  case class UpdateProviderResponse(success: Boolean)

  case class GetProviderRequest(providerId: ProviderId)

  case class GetProviderResponse(provider: Option[Provider])

  case class StockQuery(providerId: ProviderId, productName: ProductName, category: ProductCategory)

  case class StockResponse(product: Option[Product])

}
