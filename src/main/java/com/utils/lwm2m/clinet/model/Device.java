/*******************************************************************************
 * Copyright (c) 2015 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *     Achim Kraus (Bosch Software Innovations GmbH) - add reset() for 
 *                                                     REPLACE/UPDATE implementation
 *******************************************************************************/
package com.utils.lwm2m.clinet.model;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A simple {@link LwM2mInstanceEnabler} for the Device (3) object.
 * 3号设备对象
 */
public class Device extends BaseInstanceEnabler {

    private static final List<Integer> supportedResources = Arrays.asList(0, 1, 2, 11, 14, 15, 16);

    private String manufacturer;
    private String modelNumber;
    private String serialNumber;
    private EnumSet<BindingMode> supportedBinding;

    private String timezone = TimeZone.getDefault().getID();
    private String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());

    public Device() {
        // should never be used
    }

    public Device(String manufacturer, String modelNumber, String serialNumber) {
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.supportedBinding = EnumSet.of(BindingMode.U);
    }

    public Device(String manufacturer, String modelNumber, String serialNumber, EnumSet<BindingMode> supportedBinding) {
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.supportedBinding = supportedBinding;
    }

    @Override
    public ReadResponse read(ServerIdentity identity, int resourceid) {

        switch (resourceid) {
        case 0: // manufacturer
            return ReadResponse.success(resourceid, manufacturer);

        case 1: // model number
            return ReadResponse.success(resourceid, modelNumber);

        case 2: // serial number
            return ReadResponse.success(resourceid, serialNumber);

        case 11: // error codes
            return ReadResponse.success(resourceid, new HashMap<Integer, Integer>(), Type.INTEGER);

        case 14: // utc offset
            return ReadResponse.success(resourceid, utcOffset);

        case 15: // timezone
            return ReadResponse.success(resourceid, timezone);

        case 16: // supported binding and modes
            return ReadResponse.success(resourceid, BindingMode.toString(supportedBinding));

        default:
            return super.read(identity, resourceid);
        }
    }

    @Override
    public WriteResponse write(ServerIdentity identity, boolean replace, int resourceid, LwM2mResource value) {

        switch (resourceid) {

        case 14: // utc offset
            utcOffset = (String) value.getValue();
            return WriteResponse.success();

        case 15: // timezone
            timezone = (String) value.getValue();
            return WriteResponse.success();

        default:
            return super.write(identity, replace, resourceid, value);
        }
    }

    @Override
    public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {

        if (resourceid == 4) { // reboot
            return ExecuteResponse.internalServerError("not implemented");
        } else {
            return super.execute(identity, resourceid, params);
        }
    }

    @Override
    public void reset(int resourceid) {
        switch (resourceid) {
        case 14:
            utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());
            break;
        case 15:
            timezone = TimeZone.getDefault().getID();
            break;
        default:
            super.reset(resourceid);
        }
    }

    @Override
    public List<Integer> getAvailableResourceIds(ObjectModel model) {
        return supportedResources;
    }
}
