package org.example.code.quality;

import org.example.code.quality.terrible_service.horror_factory_service;

public class terrible_application{
    public static void main(String[] args) throws InterruptedException {
        horror_factory_service horrorcontroller = new horror_factory_service();

        for (int i = 0; i<10000;i++) {
            Thread.sleep(2000);
            horrorcontroller.do_creature().toString();
        }
    }
}
