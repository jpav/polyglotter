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
package org.modelspace;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.chrysalix.common.Logger;
import org.modelspace.internal.ModelspaceImpl;

/**
 * 
 */
public interface Modelspace extends AutoCloseable {

    /**
     * 
     */
    Logger LOGGER = Logger.logger( ModelspaceI18n.class );

    /**
     * @return the path to the configuration for the embedded repository supplied when this Modelspace was instantiated.
     */
    String configurationPath();

    /**
     * 
     * @param path
     *        the path of the model being deleted (cannot be <code>null</code> or empty)
     * @return <code>true</code> if deleted or <code>false</code> if node at that path does not exist
     * @throws ModelspaceException
     *         if node is not a model at that path
     */
    boolean deleteModel( String path ) throws ModelspaceException;

    /**
     * @param model
     *        a workspace model
     * @param file
     *        a file to which the supplied model should be exported
     * @throws ModelspaceException
     *         if any problem occurs
     */
    void export( Model model,
                 File file ) throws ModelspaceException;

    /**
     * @param model
     *        a workspace model
     * @param stream
     *        an output stream to which the supplied model should be exported
     * @throws ModelspaceException
     *         if any problem occurs
     */
    void export( Model model,
                 OutputStream stream ) throws ModelspaceException;

    /**
     * @param model
     *        a workspace model
     * @param url
     *        a URL to which the supplied model should be exported
     * @throws ModelspaceException
     *         if any problem occurs
     */
    void export( Model model,
                 URL url ) throws ModelspaceException;

    /**
     * @param dataPath
     *        the workspace path to the data; must not be empty.
     * @param modelPath
     *        the workspace path where the model should be created
     * @return a new model using a metamodel determined by the data's content, and if the data is a file, its file extension; never
     *         <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model generateModel( final String dataPath,
                         final String modelPath ) throws ModelspaceException;

    /**
     * @param dataPath
     *        the workspace path to the data; must not be empty.
     * @param modelPath
     *        the workspace path where the model should be created
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model generateModel( final String dataPath,
                         final String modelPath,
                         final Metamodel metamodel ) throws ModelspaceException;

    /**
     * @param file
     *        the file to be imported. Must not be <code>null</code>.
     * @param workspaceFolder
     *        the parent path where the file should be imported
     * @return the workspace path the to imported data
     * @throws ModelspaceException
     *         if any problem occurs
     */
    String importData( final File file,
                       final String workspaceFolder ) throws ModelspaceException;

    /**
     * @param file
     *        the file to be imported. Must not be <code>null</code>.
     * @param workspaceFolder
     *        the parent path where the file should be imported
     * @param workspaceName
     *        the name of the file in the workspace. If <code>null</code> or empty, the name of the supplied file will be used.
     * @return the workspace path the to imported data
     * @throws ModelspaceException
     *         if any problem occurs
     */
    String importData( final File file,
                       final String workspaceFolder,
                       final String workspaceName ) throws ModelspaceException;

    /**
     * @param stream
     *        the data's content to be imported. Must not be <code>null</code>.
     * @param workspacePath
     *        the path where the data should be imported
     * @return the workspace path the to imported data
     * @throws ModelspaceException
     *         if any problem occurs
     */
    String importData( final InputStream stream,
                       final String workspacePath ) throws ModelspaceException;

    /**
     * @param url
     *        the name of the data as it should be stored in the workspace. Must not be empty.
     * @param workspaceFolder
     *        the parent path where the data should be imported
     * @return the workspace path the to imported data
     * @throws ModelspaceException
     *         if any problem occurs
     */
    String importData( final URL url,
                       final String workspaceFolder ) throws ModelspaceException;

    /**
     * @param url
     *        the name of the data as it should be stored in the workspace. Must not be empty.
     * @param workspaceFolder
     *        the parent path where the data should be imported
     * @param workspaceName
     *        the name of the data in the workspace. If <code>null</code> or empty, the last segment of the supplied URL will be
     *        used.
     * @return the workspace path the to imported data
     * @throws ModelspaceException
     *         if any problem occurs
     */
    String importData( final URL url,
                       final String workspaceFolder,
                       final String workspaceName ) throws ModelspaceException;

    /**
     * Creates a model with the name of the supplied file.
     * 
     * @param file
     *        the file to be imported. Must not be <code>null</code>.
     * @param modelFolder
     *        the parent path where the model should be created
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model importModel( final File file,
                       final String modelFolder,
                       final Metamodel metamodel ) throws ModelspaceException;

    /**
     * Creates a model with the name of the supplied file.
     * 
     * @param file
     *        the file to be imported. Must not be <code>null</code>.
     * @param modelFolder
     *        the parent path where the model should be created
     * @param modelName
     *        the name of the model. If <code>null</code> or empty, the name of the supplied file will be used.
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model importModel( final File file,
                       final String modelFolder,
                       final String modelName,
                       final Metamodel metamodel ) throws ModelspaceException;

    /**
     * @param stream
     *        the data's content to be imported. Must not be <code>null</code>.
     * @param modelPath
     *        the path where the model should be created
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model importModel( final InputStream stream,
                       final String modelPath,
                       final Metamodel metamodel ) throws ModelspaceException;

    /**
     * @param dataUrl
     *        the URL of an data; must not be <code>null</code>.
     * @param modelFolder
     *        the parent path where the model should be created
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model importModel( final URL dataUrl,
                       final String modelFolder,
                       final Metamodel metamodel ) throws ModelspaceException;

    /**
     * @param dataUrl
     *        the URL of an data; must not be <code>null</code>.
     * @param modelFolder
     *        the parent path where the model should be created
     * @param modelName
     *        the name of the model. If <code>null</code> or empty, the name of the supplied file will be used.
     * @param metamodel
     *        the metamodel of model to be created for the supplied data; may be <code>null</code>.
     * @return a new model of the supplied metamodel; never <code>null</code>
     * @throws ModelspaceException
     *         if any problem occurs
     */
    Model importModel( final URL dataUrl,
                       final String modelFolder,
                       final String modelName,
                       final Metamodel metamodel ) throws ModelspaceException;

    /**
     * @return the metamodel manager
     * @throws ModelspaceException
     *         if any error occurs
     */
    MetamodelManager metamodelManager() throws ModelspaceException;

    /**
     * @param path
     *        a workspace path for a model
     * @return the model at the supplied path, or <code>null</code> if not found
     * @throws ModelspaceException
     *         if any error occurs
     */
    Model model( final String path ) throws ModelspaceException;

    /**
     * Creates a new model or returns an existing one.
     * 
     * @param modelPath
     *        the workspace path where the model should be created (cannot be <code>null</code> or empty)
     * @param metamodelId
     *        the metamodel identifier (cannot be <code>null</code> or empty)
     * @return the new model or the existing one (never <code>null</code>)
     * @throws ModelspaceException
     *         if the path already exists and the node is not a model with the specified metamodel ID
     */
    Model newModel( final String modelPath,
                    final String metamodelId ) throws ModelspaceException;

    /**
     * Creates a new model or returns an existing one.
     * 
     * @param modelPath
     *        the workspace path where the model should be created (cannot be <code>null</code> or empty)
     * @param metamodelId
     *        the metamodel identifier (cannot be <code>null</code> or empty)
     * @param override
     *        <code>true</code> if an existing model is found and should be replaced
     * @return the new model or the existing one (never <code>null</code>)
     * @throws ModelspaceException
     *         when not in override mode, if the path already exists and the node is not a model with the specified metamodel ID
     */
    Model newModel( final String modelPath,
                    final String metamodelId,
                    boolean override ) throws ModelspaceException;

    /**
     * @return the path to the folder that should contain the repository store
     */
    String repositoryStoreParentPath();

    /**
     * 
     */
    class Factory {

        /**
         * Uses a default configuration.
         * 
         * @param repositoryStoreParentPath
         *        the path to the folder that should contain the repository store
         * @return a new Modelspace with a default configuration
         */
        public static Modelspace instance( final String repositoryStoreParentPath ) {
            return instance( repositoryStoreParentPath );
        }

        /**
         * @param repositoryStoreParentPath
         *        the path to the folder that should contain the repository store
         * @param configurationPath
         *        the path to a configuration file
         * @return a new Modelspace
         */
        public static Modelspace instance( final String repositoryStoreParentPath,
                                           final String configurationPath ) {
            return new ModelspaceImpl( repositoryStoreParentPath, configurationPath );
        }
    }
}
