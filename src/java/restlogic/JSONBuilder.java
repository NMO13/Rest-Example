/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restlogic;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Martin
 */
class JSONBuilder {

    static String build(Transaction ta) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", ta.amount);
        jsonObject.put("type", ta.type);
        jsonObject.put("parent_id", ta.parentId);
        return jsonObject.toString();
    }

    static String ok() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "ok");
        return jsonObject.toString();
    }

    static String failure() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "failure");
        return jsonObject.toString();
    }

    static String getIds(Object[] transactions, String type) {
        JSONArray jsonArray = new JSONArray();
        for (Object o : transactions)
        {
            Transaction t = (Transaction) o;
            if(t.getType().equals(type))
                jsonArray.put(t.transactionId);
        }
        return jsonArray.toString();
    }

    static String getSum(double sum) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sum", sum);
        return jsonObject.toString();
    }
}
