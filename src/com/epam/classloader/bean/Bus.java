package com.epam.classloader.bean;

import com.epam.classloader.Interface.ITransport;

/**
 * Created by Yury Zaranok (yury_zaranok@epam.com) on 01.10.14.
 */
public class Bus implements ITransport {
    @Override
    public String getTransportType() {
        return "Bus";
    }

    @Override
    public int getPassengerCount() {
        return 40;
    }
}
