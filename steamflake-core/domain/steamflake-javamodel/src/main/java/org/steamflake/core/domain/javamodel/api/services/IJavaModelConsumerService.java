package org.steamflake.core.domain.javamodel.api.services;

import org.steamflake.core.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to an output supplier extension service.
 */
@FunctionalInterface
public interface IJavaModelConsumerService<E extends IJavaModelElement, T> {

    void consume( E element, T value );

}
