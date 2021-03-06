/*
 * Chrysalix
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors.
 *
 * Chrysalix is free software. Unless otherwise indicated, all code in Chrysalix
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * Chrysalix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.modelspace.spi;

import javax.jcr.Node;

import org.modelspace.Modelspace;
import org.modelspace.ModelspaceException;

/**
 * Processes dependencies for a specific metamodel.
 */
public interface DependencyProcessor {

    /**
     * A relative path segment for the parent path. Value is {@value} .
     */
    String PARENT_PATH = "..";

    /**
     * A relative path segment for the current path. Value is {@value} .
     */
    String SELF_PATH = ".";

    /**
     * @return the metamodel name (cannot be <code>null</code> or empty)
     */
    String metamodelId();

    /**
     * @param dataPath
     *        the workspace path of the data whose dependencies are being processed
     * @param modelNode
     *        the node of the model whose dependencies are being processed (cannot be <code>null</code>)
     * @param modelspace
     *        the modelspace used to upload dependency data and create models (cannot be <code>null</code>)
     * @param persistData
     *        <code>true</code> if the auto-imported dependency data should be persisted
     * @return the path to the dependencies node or <code>null</code> if no dependencies were processed
     * @throws ModelspaceException
     *         if the specified model is not valid for this processor or if there is an error during processing
     */
    String process( final String dataPath,
                    final Node modelNode,
                    final Modelspace modelspace,
                    final boolean persistData ) throws ModelspaceException;

}
