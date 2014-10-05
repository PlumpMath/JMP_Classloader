package com.epam;

import com.epam.classloader.Interface.ITransport;
import com.epam.classloader.bean.Bus;
import com.epam.classloader.bean.Car;
import com.epam.classloader.classloader.DynamicTransportLoader;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by Yury Zaranok (yury_zaranok@epam.com) on 01.10.14.
 */
public class ClassloaderRunner {
    public static final org.apache.log4j.Logger LOG = Logger.getLogger(ClassloaderRunner.class);

    public static void main(String[] args) {
        if (args.length == 0 || args[0] == null || args[0].length() == 0) {
            throw new IllegalArgumentException("Wrong input args!");
        }
        String jarPath = args[0];

        List<ITransport> transportList = new ArrayList<ITransport>();
        transportList.add(new Car());
        transportList.add(new Bus());
        printTransportInfo(transportList);

        //Class loading (dynamically)
        //Should be added 2 new classes from Jar: Airplane and Train implementations
        List<ITransport> dynamicTransportList = DynamicTransportLoader.loadAllTransport(jarPath);
        LOG.info("--- Dynamically loaded classes: ---");
        printTransportInfo(dynamicTransportList);


    }

    //Printing out information about all transport
    private static void printTransportInfo(List<ITransport> transportList) {
        for (ITransport transport : transportList) {
            LOG.info("Transport type: " + transport.getTransportType());
            LOG.info("Seating capacity: " + transport.getPassengerCount());
        }
    }

}
