//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.infrastructure.utilities.collections;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract base class implements the Map interface (read-only), returning the value of a derived class field with name
 * matching the key requested in a get().
 */
@SuppressWarnings( "NullableProblems" )
public abstract class MappedRecord
    implements Map<String, Object> {

    protected MappedRecord() {
        this.fieldsByName = new HashMap<>();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException( "Unexpected use of 'clear' method." );
    }

    @Override
    public boolean containsKey( Object key ) {
        if ( !( key instanceof String ) ) {
            throw new IllegalArgumentException( "Non-string key." );
        }
        return this.cacheAndGetFieldsByName().keySet().contains( key );
    }

    @Override
    public boolean containsValue( Object value ) {
        throw new UnsupportedOperationException( "Unexpected use of 'containsValue' method." );
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException( "Unexpected use of 'entrySet' method." );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public Object get( Object key ) {

        if ( !( key instanceof String ) ) {
            throw new IllegalArgumentException( "Non-string key." );
        }

        Field field = this.cacheAndGetFieldsByName().get( key );
        if ( field == null ) {
            // TODO: look in inner map

            throw new IllegalArgumentException( "Invalid field name/key: " + key + "." );
        }

        try {
            return field.get( this );
        }
        catch ( IllegalAccessException e ) {
            throw new IllegalArgumentException( "Illegal access to field: " + key + ".", e );
        }

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Set<String> keySet() {
        return this.cacheAndGetFieldsByName().keySet();
    }

    @Override
    public Object put( String key, Object value ) {
        throw new UnsupportedOperationException( "Unexpected use of 'put' method." );
    }

    @Override
    public void putAll( Map<? extends String, ?> m ) {
        throw new UnsupportedOperationException( "Unexpected use of 'putAll' method." );
    }

    @Override
    public Object remove( Object key ) {
        throw new UnsupportedOperationException( "Unexpected use of 'remove' method." );
    }

    @Override
    public int size() {
        return this.cacheAndGetFieldsByName().keySet().size();
    }

    @Override
    public Collection<Object> values() {
        throw new UnsupportedOperationException( "Unexpected use of 'values' method." );
    }

    private synchronized Map<String, Field> cacheAndGetFieldsByName() {

        // Cache fields the first time used.
        if ( this.fieldsByName.isEmpty() ) {

            Class<?> cls = this.getClass();

            while ( cls != MappedRecord.class ) {
                Field[] fields = this.getClass().getFields();
                for ( Field field : fields ) {
                    // TODO: check for inner map

                    this.fieldsByName.put( field.getName(), field );
                }

                cls = cls.getSuperclass();
            }

        }

        return this.fieldsByName;

    }

    private final Map<String, Field> fieldsByName;

}
