package tv;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import org.apache.commons.io.FileUtils;

public class Show implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static String[] ext = {"avi", "mkv", "mp4"};
    private String showName;
    private String rootDirName;
    private LinkedList<String> showQueue;
    private int timeSlotLength; //Added by Trent: The length of the timeslot needed for the show in multiples of half hours. Eg 2 = one hour timeslot.

    //Added by Trent: Constructor now including 
    public Show(String showName, int timeSlotLength, String directory) throws IOException {
        this.showName = showName;
        this.timeSlotLength = timeSlotLength;
        loadEpisodes(directory);
    }

    //Added by Trent: Default constructor as it is now needed for back-compatibility and will not be auto-added at compile time as there is now a non-default constructor.
    public Show() {
    }

    public String getRootDirName() {
        return rootDirName;
    }

    public void setRootDirName(String rootDirName) {
        this.rootDirName = rootDirName;
    }

    //Added by Trent.
    public int getTimeSlotLength() {
        return timeSlotLength;
    }

    public void setTimeSlotLength(int timeSlotLength) {
        this.timeSlotLength = timeSlotLength;
    }

    public Show setShowName(String name) {
        showName = name;
        return this;
    }

    public String getShowName() {
        return showName;
    }

    public final Show loadEpisodes(String dir) throws IOException {
        rootDirName = dir;
        showQueue = new LinkedList<>();
        for (File f : FileUtils.listFiles(new File(dir), ext, true)) {
            showQueue.add(f.getCanonicalPath());
        }
        return this;
    }

    public String nextEpisode() {
        String nextEpisode = showQueue.removeFirst();
        showQueue.addLast(nextEpisode);
        return nextEpisode;
    }

    public String peekNextEpisode() {
        return showQueue.peekFirst();
    }

    public void setNextEpisode(String nextEpisode) {
        if (showQueue.contains(nextEpisode)) {
            while (!peekNextEpisode().equals(nextEpisode)) {
                nextEpisode();
            }
        } else {
            return;
        }
    }
    
    public void addEpisode(String episodeToAdd, String episodeToFollow) {
        showQueue.addFirst(episodeToAdd);
        moveEpisodeBefore(episodeToAdd, episodeToFollow);
    }
    
    //Adds episode to front of queue.
    public void addEpisodeToStart(String episodeToAdd) {
        addEpisode(episodeToAdd, showQueue.peekFirst());
        setNextEpisode(episodeToAdd);
    }
    
    public void addEpisodeToEnd(String episodeToAdd) {
        addEpisode(episodeToAdd, showQueue.peekFirst());
    }
    
    public void moveEpisodeBefore(String episodeToMove, String episodeToFollow) {
        if (!showQueue.contains(episodeToMove) || !showQueue.contains(episodeToFollow)) {
            return;
        }
        
        String currentEpisode = showQueue.peekFirst();
        showQueue.remove(episodeToMove);
        setNextEpisode(episodeToFollow);
        showQueue.addFirst(episodeToMove);
        setNextEpisode(currentEpisode);
    }
    
    public void deleteEpisode(String episode) {
        if (!showQueue.contains(episode)) {
            return;
        }
        
        String currentEpisode = showQueue.peekFirst();
        if (episode.equals(currentEpisode)) {
            showQueue.removeFirst();
        }
        else {
            setNextEpisode(episode);
            showQueue.removeFirst();
            setNextEpisode(currentEpisode);
        }
    }
    
    public Show printQueue() {
        for (String s : showQueue) {
            System.out.println(s);
        }
        return this;
    }

    //Added by Trent.
    public LinkedList<String> getShowQueue() {
        return showQueue;
    }
}
