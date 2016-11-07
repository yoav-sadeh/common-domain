package com.hamlazot
package domain.common.providers

/**
 * @author yoav @since 11/7/16.
 */
trait ProvidersService extends ProvidersProtocol with CommonOperations{

  def createProvider: Operation[CreateProviderRequest, CreateProviderResponse]

  def deleteProvider: Operation[DeleteProviderRequest, DeleteProviderResponse]

  def updateProvider: Operation[UpdateProviderRequest, UpdateProviderResponse]

  def getProvider: Operation[GetProviderRequest, GetProviderResponse]
}