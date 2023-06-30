package skill.issue.symmetricalsystem.net;

import skill.issue.symmetricalsystem.utils.FIX;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public record NatTrack(int[] flightLevels, int tmi, FIX[] route, char id, long validFrom, long validTo, boolean direction) {
    @Override
    public String toString() {
        return String.format("""
NAT Track %s
TMI %d
Valid From %d
Valid To %d
Direction %s
Route %s
Flight Levels %s
""", id, tmi, validFrom, validTo, direction ? "West" : "East", Arrays.toString(route), Arrays.toString(flightLevels)
       );
    }
    public String[] asArray() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return new String[] {
                String.valueOf(id),
                String.valueOf(tmi),
                format.format(validFrom * 1000) + "Z",
                format.format(validTo * 1000) + "Z",
                direction ? "West" : "East",
                Arrays.stream(route).map(FIX::name).reduce((a, b) -> a + " " + b).orElse(""),
                Arrays.stream(flightLevelsToFLNumber()).reduce((a, b) -> a + ", " + b).orElse("")
        };
    }
    private String[] flightLevelsToFLNumber() {
        return Arrays.stream(flightLevels).mapToObj(i -> "" + i/100).toArray(String[]::new);
    }
}
