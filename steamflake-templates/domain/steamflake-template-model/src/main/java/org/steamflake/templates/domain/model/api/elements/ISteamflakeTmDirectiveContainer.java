//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.directives.comments.ISteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.api.directives.logic.ISteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.api.directives.text.ISteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.api.directives.variables.ISteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.api.directives.whitespace.ISteamflakeTmNewLineDirective;

import java.util.Optional;

/**
 * A template rule.
 */
@SuppressWarnings( { "ClassReferencesSubclass", "BooleanParameter" } )
public interface ISteamflakeTmDirectiveContainer
    extends ISteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * Creates a new comment directive within this rule.
     *
     * @param origin the source file location of the new directive.
     * @param text   the text of the directive.
     *
     * @return the newly created directive.
     */
    ISteamflakeTmCommentDirective addCommentDirective( Optional<FileOrigin> origin, String text );

    /**
     * Creates a new if directive within this rule.
     *
     * @param origin        the source file location of the new directive.
     * @param boolCondition the condition to test for the directive.
     *
     * @return the newly created directive.
     */
    ISteamflakeTmIfDirective addIfDirective( Optional<FileOrigin> origin, String boolCondition );

    /**
     * Creates a new new-line directive within this rule.
     *
     * @param origin                   the source file location of the new directive.
     * @param isSpaceNeededIfNoNewLine whether to output a space instead if the boolean condition is false..
     * @param boolCondition            the optional condition to test for the directive.
     *
     * @return the newly created directive.
     */
    ISteamflakeTmNewLineDirective addNewLineDirective(
        Optional<FileOrigin> origin,
        boolean isSpaceNeededIfNoNewLine,
        Optional<String> boolCondition
    );

    /**
     * Creates a new text directive within this rule.
     *
     * @param origin the source file location of the new directive.
     * @param text   the text of the directive.
     *
     * @return the newly created directive.
     */
    ISteamflakeTmTextDirective addTextDirective( Optional<FileOrigin> origin, String text );

    /**
     * Creates a new variable directive within this rule.
     *
     * @param origin the source file location of the new directive.
     * @param path   the path of the variable being referenced.
     *
     * @return te newly created directive.
     */
    ISteamflakeTmVariableDirective addVariableDirective( Optional<FileOrigin> origin, String path );

    /**
     * @return the directives within this rule.
     */
    IIndexable<ISteamflakeTmAbstractDirective> getDirectives();

    /**
     * @return the keyword of this directive container.
     */
    String getKeyword();

}
