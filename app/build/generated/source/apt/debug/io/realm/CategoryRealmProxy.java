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

public class CategoryRealmProxy extends Category
    implements RealmObjectProxy {

    private static long INDEX_ID;
    private static long INDEX_NAME;
    private static long INDEX_TYPE;
    private static long INDEX_EXPENSES;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("type");
        fieldNames.add("expenses");
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
    public RealmList<Expense> getExpenses() {
        return new RealmList<Expense>(Expense.class, row.getLinkList(INDEX_EXPENSES), realm);
    }

    @Override
    public void setExpenses(RealmList<Expense> value) {
        LinkView links = row.getLinkList(INDEX_EXPENSES);
        if (value == null) {
            return;
        }
        links.clear();
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Category")) {
            Table table = transaction.getTable("class_Category");
            table.addColumn(ColumnType.STRING, "id");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.INTEGER, "type");
            if (!transaction.hasTable("class_Expense")) {
                ExpenseRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "expenses", transaction.getTable("class_Expense"));
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_Category");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Category")) {
            Table table = transaction.getTable("class_Category");
            if (table.getColumnCount() != 4) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 4 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 4; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type Category");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ID = table.getColumnIndex("id");
            INDEX_NAME = table.getColumnIndex("name");
            INDEX_TYPE = table.getColumnIndex("type");
            INDEX_EXPENSES = table.getColumnIndex("expenses");

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
            if (!columnTypes.containsKey("type")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'type'");
            }
            if (columnTypes.get("type") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'type'");
            }
            if (!columnTypes.containsKey("expenses")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'expenses'");
            }
            if (columnTypes.get("expenses") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Expense' for field 'expenses'");
            }
            if (!transaction.hasTable("class_Expense")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_Expense' for field 'expenses'");
            }
            Table table_3 = transaction.getTable("class_Expense");
            if (!table.getLinkTarget(INDEX_EXPENSES).hasSameSchema(table_3)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'expenses': '" + table.getLinkTarget(INDEX_EXPENSES).getName() + "' expected - was '" + table_3.getName() + "'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Category class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Category";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static Category createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Category obj = null;
        if (update) {
            Table table = realm.getTable(Category.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstString(pkColumnIndex, json.getString("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new CategoryRealmProxy();
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(Category.class);
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
        if (!json.isNull("type")) {
            obj.setType((int) json.getInt("type"));
        }
        if (!json.isNull("expenses")) {
            obj.getExpenses().clear();
            JSONArray array = json.getJSONArray("expenses");
            for (int i = 0; i < array.length(); i++) {
                com.sophomoreproject.expensetracker.entities.Expense item = ExpenseRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                obj.getExpenses().add(item);
            }
        }
        return obj;
    }

    public static Category createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Category obj = realm.createObject(Category.class);
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
            } else if (name.equals("type")  && reader.peek() != JsonToken.NULL) {
                obj.setType((int) reader.nextInt());
            } else if (name.equals("expenses")  && reader.peek() != JsonToken.NULL) {
                reader.beginArray();
                while (reader.hasNext()) {
                    com.sophomoreproject.expensetracker.entities.Expense item = ExpenseRealmProxy.createUsingJsonStream(realm, reader);
                    obj.getExpenses().add(item);
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Category copyOrUpdate(Realm realm, Category object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        Category realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(Category.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (object.getId() == null) {
                throw new IllegalArgumentException("Primary key value must not be null.");
            }
            long rowIndex = table.findFirstString(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new CategoryRealmProxy();
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

    public static Category copy(Realm realm, Category newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        Category realmObject = realm.createObject(Category.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId() != null ? newObject.getId() : "");
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setType(newObject.getType());

        RealmList<Expense> expensesList = newObject.getExpenses();
        if (expensesList != null) {
            RealmList<Expense> expensesRealmList = realmObject.getExpenses();
            for (int i = 0; i < expensesList.size(); i++) {
                Expense expensesItem = expensesList.get(i);
                Expense cacheexpenses = (Expense) cache.get(expensesItem);
                if (cacheexpenses != null) {
                    expensesRealmList.add(cacheexpenses);
                } else {
                    expensesRealmList.add(ExpenseRealmProxy.copyOrUpdate(realm, expensesList.get(i), update, cache));
                }
            }
        }

        return realmObject;
    }

    static Category update(Realm realm, Category realmObject, Category newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName() != null ? newObject.getName() : "");
        realmObject.setType(newObject.getType());
        RealmList<Expense> expensesList = newObject.getExpenses();
        RealmList<Expense> expensesRealmList = realmObject.getExpenses();
        expensesRealmList.clear();
        if (expensesList != null) {
            for (int i = 0; i < expensesList.size(); i++) {
                Expense expensesItem = expensesList.get(i);
                Expense cacheexpenses = (Expense) cache.get(expensesItem);
                if (cacheexpenses != null) {
                    expensesRealmList.add(cacheexpenses);
                } else {
                    expensesRealmList.add(ExpenseRealmProxy.copyOrUpdate(realm, expensesList.get(i), true, cache));
                }
            }
        }
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Category = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(getType());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{expenses:");
        stringBuilder.append("RealmList<Expense>[").append(getExpenses().size()).append("]");
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
        CategoryRealmProxy aCategory = (CategoryRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aCategory.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aCategory.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aCategory.row.getIndex()) return false;

        return true;
    }

}
