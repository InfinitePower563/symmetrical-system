package skill.issue.symmetricalsystem.display;

import skill.issue.symmetricalsystem.net.NatTrack;
import skill.issue.symmetricalsystem.net.NatTrackFactory;
import skill.issue.symmetricalsystem.utils.FIX;

import javax.swing.*;
import java.util.Arrays;

public class Map extends JFrame {
    public Map() {
        super("World Map");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        String path = Map.class.getClassLoader().getResource("map.png").getPath();
        ImageIcon image = new ImageIcon(path);
        JLabel label1 = new JLabel(image);
        add(label1);
        pack();

        NatTrack[] tracks = NatTrackFactory.getNatTracks();
        FIX[][] route = Arrays.stream(tracks).map(NatTrack::route).toArray(FIX[][]::new);

        for (FIX[] fixes : route) {
            for (FIX fix : fixes) {
                //draw a line
            }
        }

        setVisible(true);
    }
    public static void main(String[] args) {
        new Map();
    }
}
