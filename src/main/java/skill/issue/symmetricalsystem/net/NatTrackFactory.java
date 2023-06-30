package skill.issue.symmetricalsystem.net;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import skill.issue.symmetricalsystem.utils.FIX;
import skill.issue.symmetricalsystem.utils.LaunchOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.slf4j.Logger;

public class NatTrackFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(NatTrackFactory.class);
    public static NatTrack[] getNatTracks(/*LaunchOptions */) {
        //http request
        try {
            URL endpoint = new URL("https://tracks.ganderoceanic.ca/data");
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");

            InputStream responseStream = connection.getInputStream();
            byte[] responseBytes = responseStream.readAllBytes();
            String response = new String(responseBytes);
            //convert the response to a json object
            JSONArray responseJson = (JSONArray) new JSONParser().parse(response);

            NatTrack[] tracks = new NatTrack[responseJson.size()];
            for (int i = 0; i < responseJson.size(); i++) {
                Object obj = responseJson.get(i);
                JSONObject object = (JSONObject) obj;
                //get the flight levels
                JSONArray flightLevelsJson = (JSONArray) object.get("flightLevels");
                int[] flightLevels = new int[flightLevelsJson.size()];
                for (int j = 0; j < flightLevelsJson.size(); j++) {
                    flightLevels[j] = ((Long) flightLevelsJson.get(j)).intValue();
                }
                //get the tmi
                int tmi = Integer.parseInt(object.get("tmi").toString());
                //get the route
                JSONArray routeJson = (JSONArray) object.get("route");
                FIX[] route = new FIX[routeJson.size()];
                for (int j = 0; j < routeJson.size(); j++) {
                    JSONObject fixJson = (JSONObject) routeJson.get(j);
                    String name = (String) fixJson.get("name");
                    double lat = (double) fixJson.get("latitude");
                    double longitude = (double) fixJson.get("longitude");
                    route[j] = new FIX(name, lat, longitude);
                }
                //get the id
                char id = ((String) object.get("id")).charAt(0);

                long validFrom = Long.parseLong(object.get("validFrom").toString());
                long validTo = Long.parseLong(object.get("validTo").toString());
                //get the direction
                int direction = ((Long) object.get("direction")).intValue();
                boolean dir = direction == 2;
                //create the nat track
                NatTrack track = new NatTrack(flightLevels, tmi, route, id, validFrom, validTo, dir);
                tracks[i] = track;
            }
            LOGGER.info("Loaded {} NAT Tracks", tracks.length);
            return tracks;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
