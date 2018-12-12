package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.sophomoreproject.expensetracker.entities.Reminder;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReminderRealmProxy extends Reminder
    implements RealmObjectProxy {

    private static long INDEX_ID;
    private static long INDEX_NAME;
    private static long INDEX_STATE;
    private static long INDEX_DAY;
    private static long INDEX_DATE;
    private static long INDEX_CREATEDAT;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("state");
        fieldNames.add("day");
        fieldNames.add("date");
        fieldNames.add("createdAt");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public String getId() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_ID);
    }

    @Override
    public void setId(String value) {
        realm.checkIfValid();
        row.setString(INDEX_ID, (String) value);
    }

    @Override
    public String getName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_NAME);
    }

    @Override
    public void setName(String value) {
        realm.checkIfValid();
        row.setString(INDEX_NAME, (String) value);
    }

    @Override
    public boolean isState() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_STATE);
    }

    @Override
    public void setState(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_STATE, (boolean) value);
    }

    @Override
    public int getDay() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_DAY);
    }

    @Override
    public void setDay(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_DAY, (long) value);
    }

    @Override
    public Date getDate() {
        realm.checkIfValid();
        return (java.util.Date) row.getDate(INDEX_DATE);
    }

    @Override
    public void setDate(Date value) {
        realm.checkIfValid();
        row.setDate(INDEX_DATE, (Date) value);
    }

    @Override
    public Date getCreatedAt() {
        realm.checkIfValid();
        return (java.util.Date) row.getDate(INDEX_CREATEDAT);
    }

    @Override
    public void setCreatedAt(Date value) {
        realm.checkIfValid();
        row.setDate(INDEX_CREATEDAT, (Date) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Reminder")) {
            Table table = transaction.getTable("class_Reminder");
            table.addColumn(ColumnType.STRING, "id");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.BOOLEAN, "state");
            table.addColumn(ColumnType.INTEGER, "day");
            table.addColumn(ColumnType.DATE, "date");
            table.addColumn(ColumnType.DATE, "createdAt");
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_Reminder");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Reminder")) {
            Table table = transaction.getTable("class_Reminder");
            if (table.getColumnCount() != 6) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 6 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 6; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type Reminder");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ID = table.getColumnIndex("id");
            INDEX_NAME = table.getColumnIndex("name");
            INDEX_STATE = table.getColumnIndex("state");
            INDEX_DAY = table.getColumnIndex("day");
            INDEX_DATE = table.getColumnIndex("date");
            INDEX_CREATEDAT = table.getColumnIndex("createdAt");

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id'");
            }
            if (columnTypes.get("id") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'id'");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'id'");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'id'");
            }
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name'");
            }
            if (!columnTypes.containsKey("state")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'state'");
            }
            if (columnTypes.get("state") != ColumnType.BOOLEAN) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'boolean' for field 'state'");
            }
            if (!columnTypes.containsKey("day")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'day'");
            }
            if (columnTypes.get("day") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'day'");
            }
            if (!columnTypes.containsKey("date")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'date'");
            }
            if (columnTypes.get("date") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'date'");
            }
            if (!columnTypes.containsKey("createdAt")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'createdAt'");
            }
            if (columnTypes.get("createdAt") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'createdAt'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Reminder class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Reminder";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static Reminder createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Reminder obj = null;
        if (update) {
            Table table = realm.getTable(Reminder.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstString(pkColumnIndex, json.getString("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new ReminderRealmProxy();
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(Reminder.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                obj.setId("");
            } else {
                obj.setId((String) json.getString("id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                obj.setName("");
            } else {
                obj.setName((String) json.getString("name"));
            }
        }
        if (!json.isNull("state")) {
            obj.setState((boolean) json.getBoolean("state"));
        }
        if (!json.isNull("day")) {
            obj.setDay((int) json.getInt("day"));
        }
        if (!json.isNull("date")) {
            Object timestamp = json.get("date");
            if (timestamp instanceof String) {
                obj.setDate(JsonUtils.stringToDate((String) timestamp));
            } else {
                obj.setDate(new Date(json.getLong("date")));
            }
        }
        if (!json.isNull("createdAt")) {
            Object timestamp = json.get("createdAt");
            if (timestamp instanceof String) {
                obj.setCreatedAt(JsonUtils.stringToDate((String) timestamp));
            } else {
                obj.setCreatedAt(new Date(json.getLong("createdAt")));
            }
        }
        return obj;
    }

    public static Reminder createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Reminder obj = realm.createObject(Reminder.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setId("");
                    reader.skipValue();
                } else {
                    obj.setId((String) reader.nextString());
                }
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setName("");
                    reader.skipValue();
                } else {
                    obj.setName((String) reader.nextString());
                }
            } else if (name.equals("state")  && reader.peek() != JsonToken.NULL) {
                obj.setState((boolean) reader.nextBoolean());
            } else if (name.equals("day")  && reader.peek() != JsonToken.NULL) {
                obj.setDay((int) reader.nextInt());
            } else if (name.equals("date")  && reader.peek() != JsonToken.NULL) {
                if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setDate(new Date(timestamp));
                    }
                } else {
                    obj.setDate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("createdAt")  && reader.peek() != JsonToken.NULL) {
                if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setCreatedAt(new Date(timestamp));
                    }
                } else {
                    obj.setCreatedAt(JsonUtils.stringToDate(reader.nextString()));
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Reminder copyOrUpdate(Realm realm, Reminder object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        Reminder realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(Reminder.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (object.getId() == null) {
                throw new IllegalArgumentException("Primary key value must not be null.");
            }
            long rowIndex = table.findFirstString(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new ReminderRealmProxy();
                realmObject.realm = realm;
                realmObject.row = table.getUncheckedRow(rowIndex);
                cache.put(object, (RealmObjectProxy) realmObject);
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static Reminder copy(Realm realm, Reminder newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        Reminder realmObject = realm.createObject(Reminder.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId() != null ? newObject.getId() : "");
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setState(newObject.isState());
        realmObject.setDay(newObject.getDay());
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        realmObject.setCreatedAt(newObject.getCreatedAt() != null ? newObject.getCreatedAt() : new Date(0));
        return realmObject;
    }

    static Reminder update(Realm realm, Reminder realmObject, Reminder newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setState(newObject.isState());
        realmObject.setDay(newObject.getDay());
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        realmObject.setCreatedAt(newObject.getCreatedAt() != null ? newObject.getCreatedAt() : new Date(0));
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Reminder = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{state:");
        stringBuilder.append(isState());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{day:");
        stringBuilder.append(getDay());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(getDate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{createdAt:");
        stringBuilder.append(getCreatedAt());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReminderRealmProxy aReminder = (ReminderRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aReminder.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aReminder.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aReminder.row.getIndex()) return false;

        return true;
    }

}
