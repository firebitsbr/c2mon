/*******************************************************************************
 * This file is part of the Technical Infrastructure Monitoring (TIM) project.
 * See http://ts-project-tim.web.cern.ch
 * 
 * Copyright (C) 2004 - 2011 CERN. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 * 
 * Author: TIM team, tim.support@cern.ch
 ******************************************************************************/
package cern.c2mon.client.core;

import cern.c2mon.client.common.listener.SessionListener;
import cern.tim.shared.common.command.AuthorizationDetails;

/**
 * This interface describes the methods which are provided by
 * the C2MON session manager singleton. The session manager
 * handles the user authentication and allows registering
 * <code>SessionListener</code>.
 *
 * @author Matthias Braeger
 */
public interface C2monSessionManager {
  
  /**
   * Registers a listener for receiving event updates from the
   * <code>SessionManager</code>.
   * 
   * @param pListener The listener instance to register
   */
  void addSessionListener(final SessionListener pListener);

  /**
   * Removes the given listener from the <code>SessionManager</code>.
   * 
   * @param pListener The listener instance to remove
   */
  void removeSessionListener(final SessionListener pListener);

  /**
   * Use this message to authenticate with
   * a given user name and password. The {@link SessionManager} will then 
   * use your (valid) session for all authorization checks.
   * 
   * @param pUserName The user name
   * @param pPassword The password of the user
   * @return <code>true</code>, if the authentication was successful.
   */
  boolean login(final String pUserName, final String pPassword);
  
  /**
   * Use this message to authenticate a user for a given application name.
   * The {@link SessionManager} will then use your (valid) session for
   * all authorization checks.
   * 
   * @param appName The name of the application from which you are performing the
   *                authentication.
   * @param pUserName The user name
   * @param pPassword The password of the user
   * @return <code>true</code>, if the authentication was successful.
   */
  boolean login(final String appName, final String pUserName, final String pPassword);

  /**
   * Closes the current session.
   */
  void logout();
  
  /**
   * @return <code>true</code>, if a user is logged in, otherwise <code>false</code>
   */
  boolean isUserLogged();
  
  /**
   * @return The user name or <code>null</code>, if no user is logged on.
   */
  String getUserName();

  /**
   * Checks whether the logged person has the required authorization rights
   * as specified by the given {@link AuthorizationDetails}. Note that the
   * {@link AuthorizationDetails} are specific to the used authorization
   * method, e.g. RBAC. 
   * @param authorizationDetails The authorization details against which
   *        the user's priviledges shall be checked.
   * @return <code>true</code>, if the user is has the authorized required
   *         prviledges. 
   */
  boolean isAuthorized(AuthorizationDetails authorizationDetails);
}
