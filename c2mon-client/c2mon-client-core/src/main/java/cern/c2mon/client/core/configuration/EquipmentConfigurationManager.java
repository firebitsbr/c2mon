/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 *
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 *
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
package cern.c2mon.client.core.configuration;

import cern.c2mon.shared.client.configuration.ConfigurationReport;
import cern.c2mon.shared.client.configuration.api.equipment.Equipment;
import cern.c2mon.shared.client.configuration.api.process.Process;

import java.util.List;
import java.util.Set;

/**
 * The EquipmentConfigurationManager allows to apply create, update and delete
 * configurations for Equipment.
 *
 * @author Franz Ritter
 */
public interface EquipmentConfigurationManager {

  /**
   * Creates a new 'Equipment' on the server with the given name and handler
   * class. After a successful creation a DAQ which corresponds to the
   * Equipment can be started.
   * <p>
   * The Equipment is created with default parameters including the
   * standard ControlTags.
   *
   * @param processName  The name of the overlying Process.
   * @param name         The name of the Equipment to be created.
   * @param handlerClass The full class path of the Equipment which needs
   *                     to be created.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration,
   * including if it was successful or not.
   * @see EquipmentConfigurationManager#createEquipment(String, Equipment)
   */
  ConfigurationReport createEquipment(String processName, String name, String handlerClass);

  /**
   * Creates a new 'Equipment' on the server with the given name and handler
   * class specified in the {@link Equipment} object. After a successful
   * creation a DAQ which corresponds to the Equipment can be started.
   * <p>
   * Next to the specified parameters the Equipment is created with default
   * parameters including the standard ControlTags.
   * <p>
   * Note: You have to use {@link Equipment#create(String, String)} to
   * instantiate the 'equipment' parameter of this method.
   *
   * @param processName The name of the overlying Process.
   * @param equipment   The {@link Equipment} configuration for the 'create'.
   * @return A {@link ConfigurationReport} containing all details of
   * the Equipment configuration, including if it was successful or not.
   * @see EquipmentConfigurationManager#createEquipment(String, String, String)
   */
  ConfigurationReport createEquipment(String processName, Equipment equipment);

  /**
   * Creates multiple 'Equipment' on the server with the given name and handler
   * class specified in the {@link Equipment} objects. After a successful
   * creation a DAQ which corresponds to the SubEquipments can be started.
   * <p>
   * Next to the specified parameters the Equipment are created with default
   * parameters including the standard ControlTags.
   * <p>
   * Note: You have to use {@link Equipment#create(String, String)} to
   * instantiate the 'subEquipment' parameter of this method.
   *
   * @param processName  The name of the overlying Process.
   * @param equipments The list of {@link Equipment} configurations for the
   *                   'create'.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport createEquipment(String processName, List<Equipment> equipments);

  /**
   * Updates a existing 'Equipment' with the given parameters in the
   * {@link Equipment} object.
   * <p>
   * Note: You have to use {@link Equipment#update(Long)} or
   * {@link Process#update(String)} to instantiate the 'equipment'
   * parameter of this method.
   * </p>
   *
   * @param equipment The {@link Equipment} configuration for the 'update'.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport updateEquipment(Equipment equipment);

  /**
   * Updates a list of existing 'Equipments' with the given parameters in the
   * {@link Equipment} objects.
   * <p>
   * Note: You have to use {@link Equipment#update(Long)} or
   * {@link Process#update(String)} to instantiate the 'equipment' parameter of
   * this method.
   *
   * @param equipments The list of {@link Equipment} configurations for the
   *                   'update'.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport updateEquipment(List<Equipment> equipments);

  /**
   * Removes a existing 'Equipment' with the given id.
   *
   * @param id The id of the Equipment which needs to be removed.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport removeEquipmentById(Long id);

  /**
   * Removes a list of existing 'Equipments' with the given ids.
   *
   * @param ids The set of ids of the Equipments which needs to be removed.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport removeEquipmentById(Set<Long> ids);

  /**
   * Removes a existing 'Equipment' with the given name.
   *
   * @param name The name of the Equipment which needs to be removed.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport removeEquipment(String name);

  /**
   * Removes a list of existing 'Equipments' with the given names.
   *
   * @param names The setof names of the Equipments which needs to be removed.
   * @return A {@link ConfigurationReport} containing all details of the
   * Equipment configuration, including if it was successful or not.
   */
  ConfigurationReport removeEquipment(Set<String> names);

}
