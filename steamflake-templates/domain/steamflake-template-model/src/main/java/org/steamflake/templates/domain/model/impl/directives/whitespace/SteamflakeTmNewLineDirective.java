//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives.whitespace;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.directives.whitespace.ISteamflakeTmNewLineDirective;
import org.steamflake.templates.domain.model.impl.directives.SteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.impl.elements.ISteamflakeTmDirectiveContainerMixin;

import java.util.Optional;

/**
 * Implementation of a Steamflake template new line directive.
 */
@SuppressWarnings( "BooleanParameter" )
public class SteamflakeTmNewLineDirective
    extends SteamflakeTmAbstractDirective
    implements ISteamflakeTmNewLineDirective {

    /**
     * Constructs a new Steamflake new line directive.
     *
     * @param parent                   the parent of this directive.
     * @param origin                   the origin of this directive in source code.
     * @param isSpaceNeededIfNoNewLine flag indicating whether to output a space character if a new line is not needed.
     * @param boolConditionPath        path for a condition to test whether the new line is really needed.
     */
    public SteamflakeTmNewLineDirective(
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<FileOrigin> origin,
        boolean isSpaceNeededIfNoNewLine,
        Optional<String> boolConditionPath
    ) {
        super( parent, origin );
        this.isSpaceNeededIfNoNewLine = isSpaceNeededIfNoNewLine;
        this.boolConditionPath = boolConditionPath;
    }

    @Override
    public Optional<String> getBoolConditionPath() {
        return this.boolConditionPath;
    }

    @Override
    public boolean isSpaceNeededIfNoNewLine() {
        return this.isSpaceNeededIfNoNewLine;
    }

    private final Optional<String> boolConditionPath;

    private final boolean isSpaceNeededIfNoNewLine;

}
