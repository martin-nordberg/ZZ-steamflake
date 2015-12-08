//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeContainerElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.directives.comments.ISteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.api.directives.logic.ISteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.api.directives.text.ISteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.api.directives.variables.ISteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.api.directives.whitespace.ISteamflakeTmNewLineDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveSequence;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.impl.directives.comments.SteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.impl.directives.logic.SteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.impl.directives.text.SteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.impl.directives.variables.SteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.impl.directives.whitespace.SteamflakeTmNewLineDirective;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of a Steamflake template rule.
 */
@SuppressWarnings( "BooleanParameter" )
public class SteamflakeTmDirectiveSequence
    extends SteamflakeContainerElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmDirectiveSequence {

    public SteamflakeTmDirectiveSequence(
        SteamflakeContainerElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> parent,
        Optional<IFileOrigin> origin,
        Optional<String> description
    ) {
        super( parent, origin, description );

        this.directives = new ArrayList<>();
    }

    @Override
    public ISteamflakeTmCommentDirective addCommentDirective( Optional<IFileOrigin> origin, String text ) {
        return new SteamflakeTmCommentDirective( this, origin, text );
    }

    @Override
    public ISteamflakeTmIfDirective addIfDirective( Optional<IFileOrigin> origin, String boolCondition ) {
        return new SteamflakeTmIfDirective( this, origin, boolCondition );
    }

    @Override
    public ISteamflakeTmNewLineDirective addNewLineDirective(
        Optional<IFileOrigin> origin, boolean isSpaceNeededIfNoNewLine, Optional<String> boolCondition
    ) {
        return new SteamflakeTmNewLineDirective( this, origin, isSpaceNeededIfNoNewLine, boolCondition );
    }

    @Override
    public ISteamflakeTmTextDirective addTextDirective( Optional<IFileOrigin> origin, String text ) {
        return new SteamflakeTmTextDirective( this, origin, text );
    }

    @Override
    public ISteamflakeTmVariableDirective addVariableDirective( Optional<IFileOrigin> origin, String path ) {
        return new SteamflakeTmVariableDirective( this, origin, path );
    }

    @Override
    public IIndexable<ISteamflakeTmAbstractDirective> getDirectives() {
        return new ReadOnlyListAdapter<>( this.directives );
    }

    /** Responds to the event of adding a child directive to this rule. */
    public void onAddChild( ISteamflakeTmAbstractDirective child ) {
        super.onAddChild( child );
        this.directives.add( child );
    }

    private final List<ISteamflakeTmAbstractDirective> directives;

}
