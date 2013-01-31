/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Trent
 */
public class FileIO {
    private static String[] ext = {"avi", "mkv", "mp4"};
    private static String tvIDString = "tv";

    public static void saveSchedule(Schedule schedule, File file) throws IOException {
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(schedule);
        out.close();
    }

    public static Schedule loadSchedule(File file) throws ClassNotFoundException, IOException {
        ObjectInput in = new ObjectInputStream(new FileInputStream(file));
        Schedule load = (Schedule) in.readObject();
        in.close();
        return load;
    }
    
    public static void scrapeDirectory(String directory, Schedule schedule) throws IOException {
        if (!(new File(directory)).isDirectory()) {
            return;
        }
        for (File f : FileUtils.listFiles(new File(directory), ext, true)) {
            ArrayList<String> matches = new ArrayList<>();
            for (String s : schedule.allShows.keySet()) {
                if (((f.getName().replaceAll(" ", ".")).toLowerCase()).contains((s.replaceAll(" ", ".")).toLowerCase()) && (f.getName().toLowerCase()).contains(tvIDString)) {
                    matches.add(s);
                }
            }
            if (matches.size() == 1) { //can only process shows that match names with one show.
                Show show = schedule.allShows.get(matches.get(0));
                f.renameTo(new File(show.getRootDirName() + f.getName()));
                show.addEpisodeToEnd(f.getCanonicalPath());
            }
        }
    }
}
