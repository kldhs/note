package com.utils.lwm2m.clinet.model;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mMultipleResource;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.LwM2mResourceInstance;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jackmiao on 2021/4/16.
 */
public class MyDevice extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(MyDevice.class);
    private static final Random RANDOM = new Random();
    private static final List<Integer> supportedResources = Arrays.asList(5900);


    public MyDevice() {
        // notify new date each 5 second
        Timer timer = new Timer("Device-Current Time");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fireResourcesChange(5900);
            }
        }, 1000, 1000);
    }

    @Override
    public ObserveResponse observe(ServerIdentity identity, int resourceid) {
        //ObserveResponse aa=  ObserveResponse.success(resourceid,
        //        "7D0103520990017614810000612F43686E3C0005000008002BE104040100466EE60204036A3C5DE606020130E" +
        //                "6070403393432E6081817535230335F423330305F41544C5F312E302E392E303233");
        //return aa;
        return super.observe(identity, resourceid);
    }

    @Override
    public ReadResponse read(ServerIdentity identity, int resourceid) {
        LOG.info("Read on Device resource /{}/{}/{}", getModel().id, getId(), resourceid);
        switch (resourceid) {
            case 0:
                return ReadResponse.success(resourceid, getManufacturer());
            case 1:
                return ReadResponse.success(resourceid, getModelNumber());
            case 2:
                return ReadResponse.success(resourceid, getSerialNumber());
            case 3:
                return ReadResponse.success(resourceid, getFirmwareVersion());
            case 9:
                return ReadResponse.success(resourceid, getBatteryLevel());
            case 10:
                return ReadResponse.success(resourceid, getMemoryFree());
            case 11:
                Map<Integer, Long> errorCodes = new HashMap<>();
                errorCodes.put(0, getErrorCode());
                return ReadResponse.success(resourceid, errorCodes, ResourceModel.Type.INTEGER);
            case 13:
                return ReadResponse.success(resourceid, getCurrentTime());
            case 14:
                return ReadResponse.success(resourceid, getUtcOffset());
            case 15:
                return ReadResponse.success(resourceid, getTimezone());
            case 16:
                return ReadResponse.success(resourceid, getSupportedBinding());
            case 17:
                return ReadResponse.success(resourceid, getDeviceType());
            case 18:
                return ReadResponse.success(resourceid, getHardwareVersion());
            case 19:
                return ReadResponse.success(resourceid, getSoftwareVersion());
            case 5706:
                ReadResponse aa=  ReadResponse.success(resourceid,
                        "7D0103520990017614810000612F43686E3C0005000008002BE104040100466EE60204036A3C5DE606020130E" +
                                "6070403393432E6081817535230335F423330305F41544C5F312E302E392E303233");
                return aa;
            case 21:
                return ReadResponse.success(resourceid, getMemoryTotal());
            default:
                return super.read(identity, resourceid);
        }
    }

    @Override
    public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
        String withParams = null;
        if (params != null && params.length() != 0)
            withParams = " with params " + params;
        LOG.info("Execute on Device resource /{}/{}/{} {}", getModel().id, getId(), resourceid,
                withParams != null ? withParams : "");

        if (resourceid == 4) {
            new Timer("Reboot Lwm2mClient").schedule(new TimerTask() {
                @Override
                public void run() {
                    getLwM2mClient().stop(true);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    getLwM2mClient().start();
                }
            }, 500);
        }
        return ExecuteResponse.success();
    }

    @Override
    public WriteResponse write(ServerIdentity identity, boolean replace, int resourceid, LwM2mResource value) {
        LOG.info("write On Device value :{}",value.getValue());
        LOG.info("Write on Device resource /{}/{}/{}", getModel().id, getId(), resourceid);
        switch (resourceid) {
            case 13:
                return WriteResponse.notFound();
            case 14:
                setUtcOffset((String) value.getValue());
                fireResourcesChange(resourceid);
                return WriteResponse.success();
            case 15:
                setTimezone((String) value.getValue());
                fireResourcesChange(resourceid);
                return WriteResponse.success();
            case 200:
                setTimezone(String.valueOf(value.getValue()));
                fireResourcesChange(resourceid);
                return WriteResponse.success();
            case 5706:
                setUtcOffset(String.valueOf(value.getValue()));
                fireResourcesChange(resourceid);
                return WriteResponse.success();
            default:
                return super.write(identity, replace, resourceid, value);
        }
    }

    private String getManufacturer() {
        return "Leshan Demo Device";
    }

    private String getModelNumber() {
        return "Model 500";
    }

    private String getSerialNumber() {
        return "LT-500-000-0001";
    }

    private String getFirmwareVersion() {
        return "1.0.0";
    }

    private long getErrorCode() {
        return 0;
    }

    private int getBatteryLevel() {
        return RANDOM.nextInt(101);
    }

    private long getMemoryFree() {
        return Runtime.getRuntime().freeMemory() / 1024;
    }

    private Date getCurrentTime() {
        return new Date();
    }

    private String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());

    private String getUtcOffset() {
        return utcOffset;
    }

    private void setUtcOffset(String t) {
        utcOffset = t;
    }

    private String timeZone = TimeZone.getDefault().getID();

    private String getTimezone() {
        return timeZone;
    }

    private void setTimezone(String t) {
        timeZone = t;
    }

    private String getSupportedBinding() {
        return "U";
    }

    private String getDeviceType() {
        return "Demo";
    }

    private String getHardwareVersion() {
        return "1.0.1";
    }

    private String getSoftwareVersion() {
        return "1.0.2";
    }

    private int getBatteryStatus() {
        return RANDOM.nextInt(7);
    }

    private long getMemoryTotal() {
        return Runtime.getRuntime().totalMemory() / 1024;
    }

    @Override
    public List<Integer> getAvailableResourceIds(ObjectModel model) {
        ArrayList<Integer> resourceIds = new ArrayList<>(model.resources.keySet());
        Collections.sort(resourceIds);
        return resourceIds;
    }
}
