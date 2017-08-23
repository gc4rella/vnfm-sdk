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
package org.openbaton.common.vnfm_sdk.amqp.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.openbaton.catalogue.nfvo.Action;
import org.openbaton.catalogue.nfvo.messages.Interfaces.NFVMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmErrorMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmGenericMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmGrantLifecycleOperationMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmHealVNFRequestMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmInstantiateMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmLogMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmScalingMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmStartStopMessage;
import org.openbaton.catalogue.nfvo.messages.OrVnfmUpdateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;

/** Created by lto on 10/11/15. */
@Service
public class GsonDeserializerNFVMessage implements JsonDeserializer<NFVMessage> {

  private Gson gson =
      new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(Date.class, new GsonDeserializerDate())
          .registerTypeAdapter(Date.class, new GsonSerializerDate())
          .create();;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public NFVMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    String action = json.getAsJsonObject().get("action").getAsString();
    NFVMessage result;
    switch (action) {
      case "INSTANTIATE":
        result = gson.fromJson(json, OrVnfmInstantiateMessage.class);
        break;
      case "GRANT_OPERATION":
        result = gson.fromJson(json, OrVnfmGrantLifecycleOperationMessage.class);
        break;
      case "SCALING":
        result = gson.fromJson(json, OrVnfmScalingMessage.class);
        break;
      case "SCALE_OUT":
        result = gson.fromJson(json, OrVnfmScalingMessage.class);
        break;
      case "SCALE_IN":
        result = gson.fromJson(json, OrVnfmScalingMessage.class);
        break;
      case "HEAL":
        result = gson.fromJson(json, OrVnfmHealVNFRequestMessage.class);
        break;
      case "UPDATE":
        result = gson.fromJson(json, OrVnfmUpdateMessage.class);
        break;
      case "START":
        result = gson.fromJson(json, OrVnfmStartStopMessage.class);
        break;
      case "STOP":
        result = gson.fromJson(json, OrVnfmStartStopMessage.class);
        break;
      case "ERROR":
        result = gson.fromJson(json, OrVnfmErrorMessage.class);
        break;
      case "LOG_REQUEST":
        result = gson.fromJson(json, OrVnfmLogMessage.class);
        break;
      default:
        result = gson.fromJson(json, OrVnfmGenericMessage.class);
        break;
    }
    result.setAction(Action.valueOf(action));
    log.trace("Deserialized message is " + result);
    return result;
  }
}
