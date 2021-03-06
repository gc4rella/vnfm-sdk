/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.openbaton.common.vnfm_sdk.interfaces;

import java.util.Collection;
import java.util.Map;
import org.openbaton.catalogue.mano.descriptor.VNFComponent;
import org.openbaton.catalogue.mano.record.VNFCInstance;
import org.openbaton.catalogue.mano.record.VNFRecordDependency;
import org.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.openbaton.catalogue.nfvo.Action;
import org.openbaton.catalogue.nfvo.Script;
import org.openbaton.catalogue.nfvo.viminstances.BaseVimInstance;

/** Created by mpa on 05/05/15. */
public interface VNFLifecycleManagement {

  /**
   * This operation allows creating a VNF instance and run the instantiate action.
   *
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to instantiate
   * @param scripts the scripts to exectute, could be a link or a list of {@link Script}
   * @param vimInstances the mapping between the {@link BaseVimInstance} and te VDU ids
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord instantiate(
      VirtualNetworkFunctionRecord virtualNetworkFunctionRecord,
      Object scripts,
      Map<String, Collection<BaseVimInstance>> vimInstances)
      throws Exception;

  /** This operation allows retrieving VNF instance state and attributes. */
  void query();

  /**
   * This operation allows scaling (out/in, up/down) a VNF instance.
   *
   * @param scaleOut the {@link Action} to execute
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to scale
   * @param component the {@link VNFComponent} or {@link VNFCInstance} to add/remove
   * @param scripts the scripts to exectute, could be a link or a list of {@link Script}
   * @param dependency the {@link VNFRecordDependency} regarding the scale action
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord scale(
      Action scaleOut,
      VirtualNetworkFunctionRecord virtualNetworkFunctionRecord,
      VNFComponent component,
      Object scripts,
      VNFRecordDependency dependency)
      throws Exception;

  /** This operation allows verifying if the VNF instantiation is possible. */
  void checkInstantiationFeasibility();

  /**
   * This operation allows verifying if the VNF instantiation is possible.
   *
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to heal
   * @param component the {@link VNFCInstance} to heal
   * @param cause the cause of the problem
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord heal(
      VirtualNetworkFunctionRecord virtualNetworkFunctionRecord,
      VNFCInstance component,
      String cause)
      throws Exception;

  /**
   * This operation allows applying a minor/limited software update (e.g. patch) to a VNF instance.
   *
   * @param script the scripts to exectute, could be a link or a list of {@link Script}
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to update
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord updateSoftware(
      Script script, VirtualNetworkFunctionRecord virtualNetworkFunctionRecord) throws Exception;

  /**
   * This operation allows making structural changes (e.g. configuration, topology, behavior,
   * redundancy model) to a VNF instance.
   *
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to which invoke
   *     the modify
   * @param dependency the the {@link VNFRecordDependency} regarding the this VNFR
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord modify(
      VirtualNetworkFunctionRecord virtualNetworkFunctionRecord, VNFRecordDependency dependency)
      throws Exception;

  /** This operation allows deploying a new software release to a VNF instance. */
  void upgradeSoftware();

  /**
   * This operation allows terminating gracefully or forcefully a previously created VNF instance.
   *
   * @param virtualNetworkFunctionRecord the {@link VirtualNetworkFunctionRecord} to terminate
   * @return the {@link VirtualNetworkFunctionRecord} updated
   * @throws Exception in case of an exception
   */
  VirtualNetworkFunctionRecord terminate(VirtualNetworkFunctionRecord virtualNetworkFunctionRecord)
      throws Exception;
}
