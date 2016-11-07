package com.hamlazot.domain.scripts.providers

import java.util.UUID

import com.hamlazot.domain.common.providers.ProvidersAggregate

/**
 * @author yoav @since 11/7/16.
 */
trait ScriptProvidersAggregate extends ProvidersAggregate {
  override type Provider = ConcreteProvider
  override type ProviderCategory = ConcreteProviderCategory
  override type Product = ConcreteProduct

  override type ProviderCategoryId = UUID
  override type ProviderDescription = String
  override type ProviderName = String
  override type ProviderUser = UUID
  override type ProviderId = UUID
  override type ProviderCategoryName = String
  override type ProductCategory = String
  override type ProductName = String
}

case class ConcreteProvider(providerId: UUID,
                            providerName: String,
                            providerCategory: ConcreteProviderCategory,
                            stock: List[ConcreteProduct],
                            description: String)

case class ConcreteProviderCategory(providerCategoryId: UUID, providerCategoryName: String)

case class ConcreteProduct(productName: String, productCategory: String)