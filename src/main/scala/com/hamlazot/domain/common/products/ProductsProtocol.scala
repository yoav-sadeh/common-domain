package com.hamlazot.domain.common.products

import com.hamlazot.domain.scripts.notifications.NotificationsModel.EntityType

/**
 * @author yoav @since 10/31/16.
 */


trait ProductsProtocol extends ProductsAggregate{

  case class DeleteProductRequest(productUser: ProductUser
                                  , productId: ProductId)

  case class DeleteProductResponse()

  case class UpdateProductRequest(productUser: ProductUser
                                  , productId: ProductId
                                  , productName: Option[ProductName]
                                  , productCategory: Option[ProductCategory]
                                  , description: Option[ProductDescription])

  case class UpdateProductResponse()

  case class CreateProductRequest(productUser: ProductUser
                                  , productName: ProductName
                                  , productCategory: ProductCategory
                                  , description: ProductDescription)

  case class CreateProductResponse(productId: ProductId, productName: ProductName)

  case class GetProductResponse(product: Product)

  case class GetProductRequest(productId: ProductId)

  case object ProductEntityType extends EntityType
}
