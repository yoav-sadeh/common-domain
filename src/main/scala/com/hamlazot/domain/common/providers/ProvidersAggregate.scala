package com.hamlazot.domain.common.providers

/**
 * @author yoav @since 11/7/16.
 */
trait ProvidersAggregate {
  type Provider <: {
    val providerId: ProviderId
    val providerName: ProviderName
    val providerCategory: ProviderCategory
    val description: ProviderDescription
  }
  type ProviderId
  type ProviderDescription
  type ProviderName

  type ProviderCategory <: {
    val providerCategoryId: ProviderCategoryId
    val providerCategoryName: ProviderCategoryName
  }
  type ProviderCategoryId
  type ProviderCategoryName
  type ProviderUser

}
