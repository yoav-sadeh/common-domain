package com.hamlazot.domain.scripts.providers

import java.util.UUID

import com.hamlazot.domain.common.providers.ProvidersAggregate

/**
 * @author yoav @since 11/7/16.
 */
trait ScriptProvidersAggregate extends ProvidersAggregate {
  override type Provider = ConcreteProvider
  override type ProviderCategory = ConcreteProviderCategory

  override type ProviderCategoryId = UUID
  override type ProviderDescription = String
  override type ProviderName = String
  override type ProviderUser = UUID
  override type ProviderId = UUID
  override type ProviderCategoryName = String
}

case class ConcreteProvider(providerId: UUID, 
                             providerName: String, 
                             providerCategory: ConcreteProviderCategory,
                             description: String)

case class ConcreteProviderCategory(providerCategoryId: UUID, providerCategoryName: String)