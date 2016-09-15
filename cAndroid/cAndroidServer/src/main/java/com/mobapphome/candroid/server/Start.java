/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobapphome.candroid.server;

import com.mobapphome.candroid.server.Server;

/**
 *
 * @author SETTAR
 */
public class Start {
   // static Thread thread;

    public static void main(String argv[]) {
        int port = 6000;
        if(argv.length > 0){
           try{
               port = Integer.parseInt(argv[0]);
           }catch(NumberFormatException nfe){
               System.out.println("Portu duzgun yazin. Default port 6000");
           }
        }
        Server server = new Server(port);
        server.startServer();

//        thread = new Thread(new Runnable() {
//
//            public void run() {
//                while (true) {
//                    System.out.println("---------Request Time = " + requestTime);
//                    if (requestTime > 0) {
//                        long diff = new Date().getTime() - requestTime;
//                        System.out.println("---------Diff = " + diff);
//                        if (diff > 10000) {
//                            System.out.println("---------Trye to close client = " + diff);
//                            closeClient();
//                            setRequestTime(0);
//                        }
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException i) {
//                        System.out.println("Error: on Thread, " + i.getMessage());
//                    }
//                }
//            }
//        });
//        thread.setPriority(Thread.MAX_PRIORITY);
//        thread.start();
//
    }

}
