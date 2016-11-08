package com.hamlazot
package domain
package common.products

import com.hamlazot.domain.common.products.ProductsProtocol

/**
 * Created by Owner on 9/30/2016.
 */
trait ProductsService extends ProductsProtocol with ProductsAggregate with CommonOperations with CommonTerms {
  
  def createProduct: Operation[CreateProductRequest, CreateProductResponse]

  def deleteProduct: Operation[DeleteProductRequest, DeleteProductResponse]

  def updateProduct: Operation[UpdateProductRequest, UpdateProductResponse]

  def getProduct: Operation[GetProductRequest, GetProductResponse]
}
