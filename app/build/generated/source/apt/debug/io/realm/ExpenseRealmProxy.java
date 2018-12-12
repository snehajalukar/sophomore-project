package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.sophomoreproject.expensetracker.entities.Category;
import com.sophomoreproject.expensetracker.entities.Expense;
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

public class ExpenseRealmProxy extends Expense
    implements RealmObjectProxy {

    private static long INDEX_ID;
    private static long INDEX_DESCRIPTION;
    private static long INDEX_DATE;
    private static long INDEX_TYPE;
    private static long INDEX_CATEGORY;
    private static long INDEX_TOTAL;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("description");
        fieldNames.add("date");
        fieldNames.add("type");
        fieldNames.add("category");
        fieldNames.add("total");
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
    public String getDescription() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_DESCRIPTION);
    }

    @Override
    public void setDescription(String value) {
        realm.checkIfValid();
        row.setString(INDEX_DESCRIPTION, (String) value);
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
    public int getType() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_TYPE);
    }

    @Override
    public void setType(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_TYPE, (long) value);
    }

    @Override
    public Category getCategory() {
        if (row.isNullLink(INDEX_CATEGORY)) {
            return null;
        }
        return realm.get(com.sophomoreproject.expensetracker.entities.Category.class, row.getLink(INDEX_CATEGORY));
    }

    @Override
    public void setCategory(Category value) {
        if (value == null) {
            row.nullifyLink(INDEX_CATEGORY);
            return;
        }
        row.setLink(INDEX_CATEGORY, value.row.getIndex());
    }

    @Override
    public float getTotal() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_TOTAL);
    }

    @Override
    public void setTotal(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_TOTAL, (float) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Expense")) {
            Table table = transaction.getTable("class_Expense");
            table.addColumn(ColumnType.STRING, "id");
            table.addColumn(ColumnType.STRING, "description");
            table.addColumn(ColumnType.DATE, "date");
            table.addColumn(ColumnType.INTEGER, "type");
            if (!transaction.hasTable("class_Category")) {
                CategoryRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK, "category", transaction.getTable("class_Category"));
            table.addColumn(ColumnType.FLOAT, "total");
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_Expense");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Expense")) {
            Table table = transaction.getTable("class_Expense");
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
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type Expense");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ID = table.getColumnIndex("id");
            INDEX_DESCRIPTION = table.getColumnIndex("description");
            INDEX_DATE = table.getColumnIndex("date");
            INDEX_TYPE = table.getColumnIndex("type");
            INDEX_CATEGORY = table.getColumnIndex("category");
            INDEX_TOTAL = table.getColumnIndex("total");

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
            if (!columnTypes.containsKey("description")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'description'");
            }
            if (columnTypes.get("description") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'description'");
            }
            if (!columnTypes.containsKey("date")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'date'");
            }
            if (columnTypes.get("date") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'date'");
            }
            if (!columnTypes.containsKey("type")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'type'");
            }
            if (columnTypes.get("type") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'type'");
            }
            if (!columnTypes.containsKey("category")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'category'");
            }
            if (columnTypes.get("category") != ColumnType.LINK) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Category' for field 'category'");
            }
            if (!transaction.hasTable("class_Category")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_Category' for field 'category'");
            }
            Table table_4 = transaction.getTable("class_Category");
            if (!table.getLinkTarget(INDEX_CATEGORY).hasSameSchema(table_4)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmObject for field 'category': '" + table.getLinkTarget(INDEX_CATEGORY).getName() + "' expected - was '" + table_4.getName() + "'");
            }
            if (!columnTypes.containsKey("total")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'total'");
            }
            if (columnTypes.get("total") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'total'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Expense class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Expense";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static Expense createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Expense obj = null;
        if (update) {
            Table table = realm.getTable(Expense.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstString(pkColumnIndex, json.getString("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new ExpenseRealmProxy();
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(Expense.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                obj.setId("");
            } else {
                obj.setId((String) json.getString("id"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                obj.setDescription("");
            } else {
                obj.setDescription((String) json.getString("description"));
            }
        }
        if (!json.isNull("date")) {
            Object timestamp = json.get("date");
            if (timestamp instanceof String) {
                obj.setDate(JsonUtils.stringToDate((String) timestamp));
            } else {
                obj.setDate(new Date(json.getLong("date")));
            }
        }
        if (!json.isNull("type")) {
            obj.setType((int) json.getInt("type"));
        }
        if (!json.isNull("category")) {
            com.sophomoreproject.expensetracker.entities.Category categoryObj = CategoryRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("category"), update);
            obj.setCategory(categoryObj);
        }
        if (!json.isNull("total")) {
            obj.setTotal((float) json.getDouble("total"));
        }
        return obj;
    }

    public static Expense createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Expense obj = realm.createObject(Expense.class);
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
            } else if (name.equals("description")) {
                if (reader.peek() == JsonToken.NULL) {
                    obj.setDescription("");
                    reader.skipValue();
                } else {
                    obj.setDescription((String) reader.nextString());
                }
            } else if (name.equals("date")  && reader.peek() != JsonToken.NULL) {
                if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setDate(new Date(timestamp));
                    }
                } else {
                    obj.setDate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("type")  && reader.peek() != JsonToken.NULL) {
                obj.setType((int) reader.nextInt());
            } else if (name.equals("category")  && reader.peek() != JsonToken.NULL) {
                com.sophomoreproject.expensetracker.entities.Category categoryObj = CategoryRealmProxy.createUsingJsonStream(realm, reader);
                obj.setCategory(categoryObj);
            } else if (name.equals("total")  && reader.peek() != JsonToken.NULL) {
                obj.setTotal((float) reader.nextDouble());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Expense copyOrUpdate(Realm realm, Expense object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        Expense realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(Expense.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (object.getId() == null) {
                throw new IllegalArgumentException("Primary key value must not be null.");
            }
            long rowIndex = table.findFirstString(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new ExpenseRealmProxy();
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

    public static Expense copy(Realm realm, Expense newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        Expense realmObject = realm.createObject(Expense.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId() != null ? newObject.getId() : "");
        realmObject.setDescription(newObject.getDescription() != null ? newObject.getDescription() : "");
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        realmObject.setType(newObject.getType());

        com.sophomoreproject.expensetracker.entities.Category categoryObj = newObject.getCategory();
        if (categoryObj != null) {
            com.sophomoreproject.expensetracker.entities.Category cachecategory = (com.sophomoreproject.expensetracker.entities.Category) cache.get(categoryObj);
            if (cachecategory != null) {
                realmObject.setCategory(cachecategory);
            } else {
                realmObject.setCategory(CategoryRealmProxy.copyOrUpdate(realm, categoryObj, update, cache));
            }
        }
        realmObject.setTotal(newObject.getTotal());
        return realmObject;
    }

    static Expense update(Realm realm, Expense realmObject, Expense newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setDescription(newObject.getDescription() != null ? newObject.getDescription() : "");
        realmObject.setDate(newObject.getDate() != null ? newObject.getDate() : new Date(0));
        realmObject.setType(newObject.getType());
        Category categoryObj = newObject.getCategory();
        if (categoryObj != null) {
            Category cachecategory = (Category) cache.get(categoryObj);
            if (cachecategory != null) {
                realmObject.setCategory(cachecategory);
            } else {
                realmObject.setCategory(CategoryRealmProxy.copyOrUpdate(realm, categoryObj, true, cache));
            }
        } else {
            realmObject.setCategory(null);
        }
        realmObject.setTotal(newObject.getTotal());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Expense = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(getDescription());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(getDate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(getType());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category:");
        stringBuilder.append(getCategory() != null ? "Category" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{total:");
        stringBuilder.append(getTotal());
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
        ExpenseRealmProxy aExpense = (ExpenseRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aExpense.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aExpense.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aExpense.row.getIndex()) return false;

        return true;
    }

}
