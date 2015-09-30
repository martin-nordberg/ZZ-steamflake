//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.infrastructure.utilities.functions;

/**
 * Interface to a simple action to be performed.
 */
@SuppressWarnings( "InterfaceNamingConvention" )
@FunctionalInterface
public interface IAction {

    /** Performs the action. */
    void perform();

}
