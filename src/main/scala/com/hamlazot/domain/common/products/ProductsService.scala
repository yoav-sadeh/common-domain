package com.hamlazot
package domain
package common.products

import com.hamlazot.domain.client.products.ProductsProtocol

/**
 * Created by Owner on 9/30/2016.
 */
trait ProductsService[A <: ProductsAggregate] extends CommonOperations with CommonTerms {

  val productsAggregate: A
  val protocol: ProductsProtocol[A]

  def createProduct: Operation[protocol.CreateProductRequest, protocol.CreateProductResponse]

  def deleteProduct: Operation[protocol.DeleteProductRequest, protocol.DeleteProductResponse]

  def updateProduct: Operation[protocol.UpdateProductRequest, protocol.UpdateProductResponse]

  def getProduct: Operation[protocol.GetProductRequest, protocol.GetProductResponse]
}
