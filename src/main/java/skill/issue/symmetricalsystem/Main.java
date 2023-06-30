package skill.issue.symmetricalsystem;

import skill.issue.symmetricalsystem.display.Frame;
import skill.issue.symmetricalsystem.net.NatTrack;
import skill.issue.symmetricalsystem.net.NatTrackFactory;

//scratch.txt idk how to start
public class Main {
    public static void main(String[] args) {
        NatTrack[] tracks = NatTrackFactory.getNatTracks();
        String[][] tracksAsTable = new String[tracks.length][7];
        for (int i = 0; i < tracks.length; i++) {
            tracksAsTable[i] = tracks[i].asArray();
        }
        String[] columns = {"ID", "TMI", "Valid From", "Valid To", "Direction", "Route", "Flight Levels"};

        new Frame(tracksAsTable, columns);
    }
}
