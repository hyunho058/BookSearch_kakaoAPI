package com.example.booksearchrecyclerviewkakaoapi.model;


import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonDataScan {

//    protected JSONObject mObject;
//
//    public JsonDataScan(JSONObject object) {
//        mObject = object;
//    }
//
//    public JsonDataScan() {
//        mObject = new JSONObject();
//    }
//
//    public JSONObject getObject() {
//        return mObject;
//    }
//
//    public boolean setJSON(String data) {
//        try {
//
//            JsonElement element = new JsonPrimitive(data);
//            mObject = element.getAsJsonObject();
//
//            return true;
//
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public boolean isJsonNull(String field) {
//        JsonElement element = mObject.get(field);
//        return element instanceof JsonNull;
//    }
//
//    public String getString(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsString();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }
//
//    // default value
//    public String getString(String field, String defaultValue) {
//        String value = getString(field);
//        if (value.equals("")) return defaultValue;
//
//        return value;
//    }
//
//    public int getInt(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsInt();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//    public long getLong(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsLong();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//    public float getFloat(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsFloat();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return 0.0f;
//    }
//
//    public double getDouble(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsDouble();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return 0.0f;
//    }
//
//    public boolean getBoolean(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.get(field) != null) {
//                    return mObject.get(field).getAsBoolean();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//
//    public JSONArray getJsonArray(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.getAsJsonArray (field) != null) {
//                    return mObject.get(field).getAsJsonArray();
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return new JSONArray();
//    }
//
//    public JSONObject getJsonObject(String field) {
//        try {
//            if (!isJsonNull(field)) {
//                if (mObject.getAsJsonObject(field) != null) {
//                    return mObject.getAsJsonObject(field);
//                }
//            }
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return new JSONObject();
//    }
//
//
//    public boolean setLong(String field, long value) {
//        try {
//            mObject.addProperty(field, value);
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//    public boolean setBoolean(String field, boolean value) {
//        try {
//            mObject.addProperty(field, value);
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//
//    public void setString(String field, String value) {
//        try {
//            mObject.addProperty(field, value);
//        } catch (JsonIOException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    public void setJsonObject(String field, JSONObject value) {
//        try {
//            mObject.add(field, value);
//        } catch (JsonIOException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    public void setJSONArray(String field, JsonArray value) {
//        try {
//            mObject.add(field, value);
//
//        } catch (JsonIOException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    public String getJSON() {
//        return mObject.toString();
//    }

}
