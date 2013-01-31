package tv;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

public class Schedule implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    HashMap<String, Show> allShows;
    HashMap<Integer, Show[]> sched;

    public Schedule() {
        allShows = new HashMap<String, Show>();
        sched = new HashMap<Integer, Show[]>();
        initSched(); //Trent changed.
    }

    public void addShow(String name, Show sh) {
        allShows.put(name, sh);
    }
    
    //Trent added.
    public void removeShow(String name) {
        removeFromSched(allShows.get(name));
        allShows.remove(name);
    }

    public void addToSched(String show_name, int day, int hour, int min) {
        sched.get(day)[hour + min] = allShows.get(show_name);
    }
    
    //Trent added.
    public void removeFromSched(Show show) {
        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < sched.get(i).length; j++) {
                if (sched.get(i)[j] == null) continue;
                if (sched.get(i)[j].equals(show)) {
                    sched.get(i)[j] = null;
                }
            }
        }
    }

    //Trent Added;
    public void clearSched() {
        sched.clear();
        initSched();
    }

    //Trent Added;
    private void initSched() {
        for (int i = 1; i < 8; i++) {
            //time index array of 30 min blocks, hour + 00/30 mins, 00:00 = index 0, 23:30 = index 53
            sched.put((Integer) i, new Show[54]);
        }
    }

    public void checkSched() throws IOException, InterruptedException {
        Calendar cal = Calendar.getInstance();
        int min = cal.get(Calendar.MINUTE);
        if (min == 30 || min == 0) {
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            Show check = sched.get(cal.get(Calendar.DAY_OF_WEEK))[hour + min];
            if (check != null) {
                String[] temp = new String[] { "vlc", "--rc-fake-tty", "-f",check.nextEpisode(),"vlc://quit"};
                Runtime.getRuntime().exec(temp).waitFor();
            }
        }
    }

    public void printSchedDay(int i) {
        Show[] day = sched.get(i);

        for (int h = 0; h < 24; h++) {
            int a = h, b = h + 30;
            if (day[a] != null) {
                System.out.println(h + "00\t" + day[a].getShowName());
            } else {
                System.out.println(h + "00\t" + "###");
            }

            if (day[b] != null) {
                System.out.println(h + "30\t" + day[b].getShowName());
            } else {
                System.out.println(h + "30\t" + "###");
            }
        }
    }
}
