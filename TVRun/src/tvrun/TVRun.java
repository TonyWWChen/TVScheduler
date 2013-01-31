package tvrun;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import tv.*;

public class TVRun {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        if (args == null) {
            System.out.println("Usage: TVRun [schedule file]");
            System.out.println("Example: TVRun schedule /media/tv/Data/Downloads/Deluge/Complete/");
            return;
        }
        
        File file = new File(args[0]);
        boolean enableScraping = false;
        String scrapeDirectory = null;
        if (args.length >= 2) {
            scrapeDirectory = args[1];
            if (new File(scrapeDirectory).isDirectory()) {
                enableScraping = true;
            }
            else {
                System.out.println("The directory to scrape does not exist, or is not a directory.");
            }
        }

        while (true) {
            Schedule sched = FileIO.loadSchedule(file);
            if (enableScraping) {
                FileIO.scrapeDirectory(scrapeDirectory, sched);
            }
            sched.checkSched();
            FileIO.saveSchedule(sched, file);
            Thread.sleep(30000);
        }
    }
}
