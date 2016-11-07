package com.hamlazot
package domain.scripts
package providers

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.hamlazot.domain.common.providers.{ProvidersService, ProvidersProtocol}
import com.hamlazot.domain.scripts.notifications.EventBus
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{Created, CRUDEvent}
import com.hamlazot.domain.scripts.products.ScriptProductsModel.Product

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

/**
 * @author yoav @since 11/7/16.
 */
trait ScriptProvidersService extends ProvidersService with ProvidersProtocol with ScriptProvidersAggregate{

  private var providers = mutable.MutableList.empty[ConcreteProvider]
  implicit val ec: ExecutionContext

  override type Operation[A,B] = A => M1[B]

  override def createProvider: (CreateProviderRequest) => M1[CreateProviderResponse] = { request =>
    val provider = ConcreteProvider(UUID.randomUUID(), request.providerName, request.providerCategory, request.providerDescription)
    providers += provider
    ActorSystem().scheduler.scheduleOnce(FiniteDuration(7, TimeUnit.SECONDS)) {
      EventBus.queue.put(CRUDEvent(ProviderEntityType, provider.providerId, provider.providerName, Created))
    }
    M1(CreateProviderResponse(provider.providerId))
  }

  override def updateProvider: (UpdateProviderRequest) => M1[UpdateProviderResponse] = { request =>
    M1(UpdateProviderResponse())
  }

  override def getProvider: (GetProviderRequest) => M1[GetProviderResponse] = { request =>
    providers.find(p => p.providerId == request.providerId) match {
      case Some(provider) => M1(GetProviderResponse(provider))
      case None => M1(GetProviderResponse(ConcreteProvider(request.providerId, "Dummy", ConcreteProviderCategory(UUID.randomUUID(), "DummyCategory"), "DummyDescription")))
    }
  }

  override def deleteProvider: (DeleteProviderRequest) => M1[DeleteProviderResponse] = { request =>
    M1(DeleteProviderResponse())
  }
}
