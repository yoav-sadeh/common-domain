package com.hamlazot.domain
package scripts
package products

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.hamlazot.domain.common.products.{ProductsProtocol, ProductsService}
import com.hamlazot.domain.scripts.notifications.EventBus
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{CRUDEvent, Created}
import com.hamlazot.domain.scripts.products.ScriptProductsModel.{Product, ProductCategory}
import como.scripts.ScriptBoot

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

/**
 * @author yoav @since 10/31/16.
 */
private[scripts] trait ScriptProductsService extends ProductsService with ProductsProtocol with ScriptProductsAggregate {
  private var products = mutable.MutableList.empty[Product]
  implicit val ctxt: ExecutionContext

  override type Operation[A, B] = A => M1[B]

  override def getProduct: (GetProductRequest) => M1[GetProductResponse] = { request =>
    products.find(p => p.productId == request.productId) match {
      case Some(product) => M1(GetProductResponse(product))
      case None => M1(GetProductResponse(Product(request.productId, "Dummy", ProductCategory(UUID.randomUUID(), "DummyCategory"), "DummyDescription")))
    }
  }

  override def updateProduct: (UpdateProductRequest) => M1[UpdateProductResponse] = { request =>
    M1(UpdateProductResponse())
  }

  override def deleteProduct: (DeleteProductRequest) => M1[DeleteProductResponse] = { request =>
    M1(DeleteProductResponse())
  }

  override def createProduct: (CreateProductRequest) => M1[CreateProductResponse] = { request =>
    val product = Product(UUID.randomUUID(), request.productName, request.productCategory, request.description)
    products += product
    getSystem().scheduler.scheduleOnce(FiniteDuration(7, TimeUnit.SECONDS)) {
      EventBus.queue.put(CRUDEvent(ProductEntityType, product.productId, product.productName, Created))
    }
    M1(CreateProductResponse(product.productId, request.productName))
  }
}

object ScriptProductsService extends ScriptProductsService {
  override implicit val ctxt: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

}