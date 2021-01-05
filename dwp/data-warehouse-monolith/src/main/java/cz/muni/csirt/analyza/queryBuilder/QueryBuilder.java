package cz.muni.csirt.analyza.queryBuilder;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.*;

public class QueryBuilder {

    private final static String SELECT = "SELECT ";
    private final static String FROM = " FROM ";
    private final static String WHERE = " WHERE ";
    private final static String AND = " AND ";
    private final static String GenericObject = "GenericObject ";
    private final static String Links = "Links ";
    private final static String JOIN = " INNER JOIN ";
    private final static String RIGHT_LINKS = ".rightLinks ";
    private final static String LEFT_LINKS = ".leftLinks ";
    private final static String RIGHT = ".right ";
    private final static String LEFT = ".left ";
    private final static String UUID = ".uuid";
    private final static String PROPERTY = ".properties ";

    public static String CreateQuery(String json) throws JSONException {
        String finalQuery = "";
        String selectQuery = "";
        String fromQuery = "";
        String whereQuery = "";
        Map<String, String> map = parseJSON(json);
        fromQuery += FROM + GenericObject;
        String[] tokens = map.get("path").split(":");
        String firstObject = " gobj0";
        fromQuery += firstObject;
        String go = firstObject;
        String l = "l0";
        int i = 1;
        for (String token : tokens) {
            if (token.equals("left")) {
                fromQuery += JOIN + go + RIGHT_LINKS + l + JOIN + l;
                go = "gobj" + String.valueOf(i);
                l = "l" + String.valueOf(i);
                i += 1;
                fromQuery += LEFT + go;
            } else if (token.equals("right")) {
                fromQuery += JOIN + go + LEFT_LINKS + l + JOIN + l;
                go = "gobj" + String.valueOf(i);
                l = "l" + String.valueOf(i);
                i += 1;
                fromQuery += RIGHT + go;
            } else if (token.equals("prop")) {
            fromQuery += JOIN + go + PROPERTY + "prop" + String.valueOf(i-1);
        }

    }
        selectQuery = SELECT + map.get("to");
        whereQuery = WHERE + "gobj0.uuid = '" + map.get("from") + "'";
        if (!map.get("query").equals(""))
                if (checkForSubstrings(map.get("query"))){
                    whereQuery = "";
                } else {
                    whereQuery += AND + map.get("query");
                }
        finalQuery = selectQuery + fromQuery + whereQuery;
        return finalQuery;
    }

    private static Map<String, String> parseJSON(String json) throws JSONException {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject(json);
        map.put("from", jsonObject.getString("from"));
        map.put("path", jsonObject.getString("path"));
        map.put("to", jsonObject.getString("to"));
        map.put("query", jsonObject.getString("query"));
        return map;
    }

    private static boolean checkForSubstrings(String query) {
        String string = query.toLowerCase();
        List<String> substrings = Arrays.asList(new String[]{"select", "update", "delete",
        "union","create","drop","join","alter","database","insert"});
        for(String substring : substrings) {
            if (string.contains(substring)) {
                return true;
            }
        }
        return false;
    }

}
