package com.hamlazot
package domain
package scripts
package providers

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.hamlazot.domain.common.providers.{ProvidersProtocol, ProvidersService}
import com.hamlazot.domain.scripts.notifications.EventBus
import com.hamlazot.domain.scripts.notifications.NotificationsModel.{CRUDEvent, Created}

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

/**
 * @author yoav @since 11/7/16.
 */
trait ScriptProvidersService extends ProvidersService with ProvidersProtocol with ScriptProvidersAggregate {

  private var providers = mutable.MutableList.empty[ConcreteProvider]

  val system: ActorSystem = getSystem()
  import system.dispatcher

  override type Operation[A, B] = A => M1[B]

  override def createProvider: (CreateProviderRequest) => M1[CreateProviderResponse] = { request =>
    val provider = ConcreteProvider(UUID.randomUUID(), request.providerName, request.providerCategory, List(), request.providerDescription)
    providers += provider

    getSystem().scheduler.scheduleOnce(FiniteDuration(7, TimeUnit.SECONDS)) {
      val event = CRUDEvent(ProviderEntityType, provider.providerId, provider.providerName, Created)
      EventBus.queue.put(event)
    }
    M1(CreateProviderResponse(provider.providerId))
  }

  override def updateProvider: (UpdateProviderRequest) => M1[UpdateProviderResponse] = { request =>
    var changed = false
    providers.transform(p => {
      if (p.providerId == request.providerId) {
        val transformed = p.copy(providerName = request.providerName.getOrElse(p.providerName),
          providerCategory = request.providerCategory.getOrElse(p.providerCategory),
          stock = p.stock ::: request.stock.getOrElse(Nil),
          description = request.description.getOrElse(p.description))
        changed = true
        transformed
      } else
        p
    })
    M1(UpdateProviderResponse(changed))
  }

  override def getProvider: (GetProviderRequest) => M1[GetProviderResponse] = { request =>
    providers.find(p => p.providerId == request.providerId) match {
      case Some(provider) => M1(GetProviderResponse(Some(provider)))
      case None => M1(GetProviderResponse(None))
    }
  }

  override def deleteProvider: (DeleteProviderRequest) => M1[DeleteProviderResponse] = { request =>
    M1(DeleteProviderResponse())
  }

  override def queryStock: (StockQuery) => M1[StockResponse] = { request =>
    val result = getProvider(GetProviderRequest(request.providerId)) map { p =>
      StockResponse(p.provider.map(_.stock.find { pp =>
        pp.productCategory == request.category && pp.productName == request.productName
      }).flatMap(identity))

    }

    result
  }
}

object ScriptProvidersService {
  private lazy val service = new ScriptProvidersService {}

  def apply(): ScriptProvidersService = {
    service
  }
}