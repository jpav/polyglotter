/*
 * Polyglotter (http://polyglotter.org)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors.
 *
 * Polyglotter is free software. Unless otherwise indicated, all code in Polyglotter
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * Polyglotter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.polyglotter.operation;

import org.polyglotter.Polyglotter;
import org.polyglotter.PolyglotterException;
import org.polyglotter.PolyglotterI18n;
import org.polyglotter.transformation.Operation;
import org.polyglotter.transformation.OperationCategory.BuiltInCategory;
import org.polyglotter.transformation.OperationDescriptor;
import org.polyglotter.transformation.Transformation;
import org.polyglotter.transformation.TransformationFactory;
import org.polyglotter.transformation.ValidationProblem;
import org.polyglotter.transformation.Value;
import org.polyglotter.transformation.ValueDescriptor;

/**
 * Subtracts a collection of terms.
 */
public final class Subtract extends AbstractOperation< Number > {

    /**
     * The input term descriptor.
     */
    public static final ValueDescriptor< Number > TERM_DESCRIPTOR =
        TransformationFactory.createValueDescriptor( TransformationFactory.createId( Subtract.class, "input" ),
                                                     PolyglotterI18n.subtractOperationInputDescription.text(),
                                                     PolyglotterI18n.subtractOperationInputName.text(),
                                                     Number.class,
                                                     true,
                                                     2,
                                                     true );

    /**
     * The input descriptors.
     */
    private static final ValueDescriptor< ? >[] INPUT_DESCRIPTORS = { TERM_DESCRIPTOR };

    /**
     * The output descriptor.
     */
    public static final OperationDescriptor< Number > DESCRIPTOR =
        new AbstractOperationDescriptor< Number >( TransformationFactory.createId( Subtract.class ),
                                                   PolyglotterI18n.subtractOperationDescription.text(),
                                                   PolyglotterI18n.subtractOperationName.text(),
                                                   Number.class,
                                                   INPUT_DESCRIPTORS ) {

            /**
             * {@inheritDoc}
             * 
             * @see org.polyglotter.transformation.OperationDescriptor#newInstance(org.polyglotter.transformation.Transformation)
             */
            @Override
            public Operation< Number > newInstance( final Transformation transformation ) {
                return new Subtract( transformation );
            }

        };

    /**
     * @param transformation
     *        the transformation containing this operation (cannot be <code>null</code>)
     * @throws IllegalArgumentException
     *         if the input is <code>null</code>
     */
    Subtract( final Transformation transformation ) {
        super( DESCRIPTOR, transformation );

        try {
            addCategory( BuiltInCategory.ARITHMETIC );
        } catch ( final PolyglotterException e ) {
            Polyglotter.LOGGER.error( e, PolyglotterI18n.errorAddingBuiltInCategory, transformationId() );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.polyglotter.operation.AbstractOperation#calculate()
     */
    @Override
    protected Number calculate() throws PolyglotterException {
        assert !problems().isError();

        Number result = null;
        int i = 0;

        for ( final Value< ? > term : inputs() ) {
            Number value = ( Number ) term.get();

            if ( ( value instanceof Integer ) || ( value instanceof Short ) || ( value instanceof Byte ) ) {
                value = value.longValue();
            } else if ( value instanceof Float ) {
                value = ( ( Float ) value ).doubleValue();
            }

            if ( i == 0 ) {
                if ( value instanceof Long ) {
                    result = new Long( ( Long ) value );
                } else {
                    assert ( result instanceof Double );
                    result = new Double( ( Double ) value );
                }
            } else if ( result != null ) {
                if ( ( result instanceof Double ) || ( value instanceof Double ) ) {
                    result = ( result.doubleValue() - value.doubleValue() );
                } else {
                    result = ( result.longValue() - value.longValue() );
                }
            }

            ++i;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.polyglotter.operation.AbstractOperation#validate()
     */
    @Override
    protected void validate() {
        // make sure there are terms
        if ( inputs().isEmpty() ) {
            final ValidationProblem problem =
                TransformationFactory.createError( transformationId(),
                                                   PolyglotterI18n.subtractOperationHasNoTerms.text( transformationId() ) );
            problems().add( problem );
        } else {
            if ( inputs().size() < INPUT_DESCRIPTORS[ 0 ].requiredValueCount() ) {
                final ValidationProblem problem =
                    TransformationFactory.createError( transformationId(),
                                                       PolyglotterI18n.invalidTermCount.text( name(),
                                                                                              transformationId(),
                                                                                              inputs().size() ) );
                problems().add( problem );
            }

            // make sure all the terms have types of Number
            for ( final Value< ? > term : inputs() ) {
                Object value;

                try {
                    value = term.get();

                    if ( !( value instanceof Number ) ) {
                        final ValidationProblem problem =
                            TransformationFactory.createError( transformationId(),
                                                               PolyglotterI18n.invalidTermType.text( name(),
                                                                                                     transformationId() ) );
                        problems().add( problem );
                    }
                } catch ( final PolyglotterException e ) {
                    final ValidationProblem problem =
                        TransformationFactory.createError( transformationId(),
                                                           PolyglotterI18n.operationValidationError.text( name(),
                                                                                                          transformationId() ) );
                    problems().add( problem );
                    Polyglotter.LOGGER.error( e, PolyglotterI18n.message, problem.message() );
                }
            }
        }
    }

}
