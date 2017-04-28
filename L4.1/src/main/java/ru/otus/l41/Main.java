package ru.otus.l41;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * Created by tully.
 */
/*
 -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 -Xms512m
 -Xmx512m
 -XX:MaxMetaspaceSize=256m
 -XX:+UseConcMarkSweepGC
 -XX:+CMSParallelRemarkEnabled
 -XX:+UseCMSInitiatingOccupancyOnly
 -XX:CMSInitiatingOccupancyFraction=70
 -XX:+ScavengeBeforeFullGC
 -XX:+CMSScavengeBeforeRemark
 -XX:+UseParNewGC
 -verbose:gc
 -Xloggc:./logs/gc_pid_%p.log
 -XX:+PrintGCDateStamps
 -XX:+PrintGCDetails
 -XX:+UseGCLogFileRotation
 -XX:NumberOfGCLogFiles=10
 -XX:GCLogFileSize=1M
 -Dcom.sun.management.jmxremote.port=15000
 -Dcom.sun.management.jmxremote.authenticate=false
 -Dcom.sun.management.jmxremote.ssl=false
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dumps/
 -XX:OnOutOfMemoryError="kill -3 %p"


 jinfo -- list VM parameters
 jhat / jvisualvm -- analyze heap dump

 */

// У меня использовались параметры:
// -Xms512m
// -Xmx512m

public class Main {
    public static void main(String... args) throws Exception {
        System.out.println(new Date() + "\n" +
                "Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        // Создаём объект, который будет следить за сборками мусора
        new GCMonitor();

        // Создаём и запускаем объект, который создаёт мусор
        new GarbageCreator().run();
    }

}
