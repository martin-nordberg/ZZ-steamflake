//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.infrastructure.utilities.collections;

/**
 * Sample derived class for testing the MappedRecord capability.
 */
public class SampleMappedRecord
    extends MappedRecord {

    public SampleMappedRecord( int i, boolean b, String s, float f ) {
        this.i = i;
        this.b = b;
        this.s = s;
        this.f = f;
    }

    public final boolean b;

    public final float f;

    public final int i;

    public final String s;

}
