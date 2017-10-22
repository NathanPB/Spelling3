package cf.nathanpb.Spelling3;

import org.bukkit.ChatColor;

/**
 * Created by nathanpb on 10/20/17.
 */
public class Phase {
    private String message;
    private Runnable run;
    private boolean wip = false;

    public Phase(String s, Runnable run){
        this.message = s;
        this.run = run;
    }
    public Phase setWip(boolean flag){
        this.wip = flag;
        return this;
    }


    public void run(){
        run.run();
    }

    public String getMessage() {
        if(wip)
            return message+ ChatColor.DARK_RED+" [WIP]";
        else
            return message;
    }
}
