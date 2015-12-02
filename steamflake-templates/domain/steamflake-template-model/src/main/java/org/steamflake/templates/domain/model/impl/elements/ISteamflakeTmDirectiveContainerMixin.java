//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.directives.comments.ISteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.api.directives.logic.ISteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.api.directives.text.ISteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.api.directives.variables.ISteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.api.directives.whitespace.ISteamflakeTmNewLineDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveContainer;
import org.steamflake.templates.domain.model.impl.directives.comments.SteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.impl.directives.logic.SteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.impl.directives.text.SteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.impl.directives.variables.SteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.impl.directives.whitespace.SteamflakeTmNewLineDirective;

import java.util.Optional;

/**
 * Implementation of a Steamflake template rule.
 */
@SuppressWarnings( "BooleanParameter" )
public interface ISteamflakeTmDirectiveContainerMixin
    extends ISteamflakeTmDirectiveContainer {

    @Override
    default ISteamflakeTmCommentDirective addCommentDirective( Optional<FileOrigin> origin, String text ) {
        return new SteamflakeTmCommentDirective( this, origin, text );
    }

    @Override
    default ISteamflakeTmIfDirective addIfDirective( Optional<FileOrigin> origin, String boolCondition ) {
        return new SteamflakeTmIfDirective( this, origin, boolCondition );
    }

    @Override
    default ISteamflakeTmNewLineDirective addNewLineDirective(
        Optional<FileOrigin> origin, boolean isSpaceNeededIfNoNewLine, Optional<String> boolCondition
    ) {
        return new SteamflakeTmNewLineDirective( this, origin, isSpaceNeededIfNoNewLine, boolCondition );
    }

    @Override
    default ISteamflakeTmTextDirective addTextDirective( Optional<FileOrigin> origin, String text ) {
        return new SteamflakeTmTextDirective( this, origin, text );
    }

    @Override
    default ISteamflakeTmVariableDirective addVariableDirective( Optional<FileOrigin> origin, String path ) {
        return new SteamflakeTmVariableDirective( this, origin, path );
    }

}
