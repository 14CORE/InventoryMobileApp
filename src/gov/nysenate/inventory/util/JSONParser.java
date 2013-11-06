package gov.nysenate.inventory.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import gov.nysenate.inventory.model.InvItem;
import gov.nysenate.inventory.model.Location;
import gov.nysenate.inventory.model.Pickup;

public class JSONParser {

    public static Pickup parsePickup(String json) throws JSONException {
        Log.i("JSONParser single: ", json);
        Pickup pickup = new Pickup();
        JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();
        ArrayList<InvItem> pickupItems = parsePickupItems(obj.getJSONArray("pickupItems"));
        Location origin = parseLocation(obj.getJSONObject("origin"));
        Location destination = parseLocation(obj.getJSONObject("destination"));

        pickup.setNapickupby(obj.getString("napickupby"));
        pickup.setNareleaseby(obj.getString("nareleaseby"));
        pickup.setNuxrrelsign(obj.getString("nuxrrelsign"));
        pickup.setDate(obj.getString("date"));
        pickup.setNuxrpd(Integer.valueOf(obj.getString("nuxrpd")));
        pickup.setCount(Integer.valueOf(obj.getString("count")));
        pickup.setPickupItems(pickupItems);
        pickup.setOrigin(origin);
        pickup.setDestination(destination);

        return pickup;
    }

    private static ArrayList<InvItem> parsePickupItems(JSONArray json) throws JSONException {
        ArrayList<InvItem> items = new ArrayList<InvItem>();
        for (int i = 0; i < json.length(); i++) {
            InvItem aItem = new InvItem();
            JSONObject obj = json.getJSONObject(i);
            aItem.setDecommodityf(obj.getString("decommodityf"));
            aItem.setType(obj.getString("type"));
            aItem.setNusenate(obj.getString("nusenate"));
            aItem.setCdcategory(obj.getString("cdcategory"));
            aItem.setCdlocat(obj.getString("cdcategory"));
            aItem.setCdintransit(obj.getString("cdintransit"));
            aItem.setDecomments(obj.getString("decomments"));
            aItem.setCdcommodity(obj.getString("cdcommodity"));
            aItem.setSelected(Boolean.parseBoolean(obj.getString("selected")));
            items.add(aItem);
        }
        return items;
    }

    private static Location parseLocation(JSONObject json) throws JSONException {
        Location loc = new Location();
        loc.setCdlocat(json.getString("cdlocat"));
        loc.setCdloctype(json.getString("cdloctype"));
        loc.setAdstreet1(json.getString("adstreet1"));
        loc.setAdcity(json.getString("adcity"));
        loc.setAdzipcode(json.getString("adzipcode"));

        return loc;
    }

    public static List<Pickup> parseMultiplePickups(String json) throws JSONException {
        Log.i("JSONParser multi: ", json);
        List<Pickup> pickups = new ArrayList<Pickup>();
        JSONTokener token = new JSONTokener(json);
        JSONArray obj = (JSONArray) token.nextValue();
        for (int i = 0; i < obj.length(); i++) {
            pickups.add(parsePickup(obj.getJSONObject(i).toString()));
        }
        return pickups;
    }
}
