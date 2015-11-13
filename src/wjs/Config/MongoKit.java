package wjs.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.Document;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Mongodb的操作
 * @author weijiashang
 *@date 2015年11月9日下午5:42:43
 */
public class MongoKit {
	protected static Logger logger = Logger.getLogger(MongoKit.class);

	private static MongoClient client;
	private static DB defaultDb;
	private static MongoDatabase db;

	/**
	 * 初始化数据库
	 * @param client
	 * @param database
	 */
	public static void init(MongoClient client, String database) {
		MongoKit.client = client;
		MongoKit.defaultDb = client.getDB(database);
		

	}


	/**
	 * 更新
	 * @param collectionName
	 * @param q 条件
	 * @param o 修改的数据
	 */
	public static void updateFirst(String collectionName, Map<String, Object> q, Map<String, Object> o) {
		MongoKit.getCollection(collectionName).findAndModify(toDBObject(q), toDBObject(o));
	}

	/*public static int update(String collectionName, Map<String, Object> q, Map<String, Object> o) {
		 return MongoKit.getCollection(collectionName).findAndModify(toDBObject(q), toDBObject(o));
	}*/
	
	/**
	 * 删除表中所有记录
	 * @param collectionName
	 * @return
	 */
	public static int removeAll(String collectionName) {
		return MongoKit.getCollection(collectionName).remove(new BasicDBObject()).getN();
	}

	/**
	 * 删除一条记录
	 * @param collectionName
	 * @param filter
	 * @return
	 */
	public static int remove(String collectionName, Map<String, Object> filter) {
		return MongoKit.getCollection(collectionName).remove(toDBObject(filter)).getN();
	}

	/**
	 * 保存（多条记录）
	 * @param collectionName
	 * @param records
	 * @return
	 */
	public static int save(String collectionName, List<Record> records) {
		List<DBObject> objs = new ArrayList<DBObject>();
		for (Record record : records) {
			objs.add(toDbObject(record));
		}
		return MongoKit.getCollection(collectionName).insert(objs).getN();
	}

	/**
	 * 保存（一条记录）
	 * @param collectionName
	 * @param record
	 * @return
	 */
	public static int save(String collectionName, Record record) {
		return MongoKit.getCollection(collectionName).save(toDbObject(record)).getN();
	}

	//-------------------------------------
	//2015-11-10
	  public static Record find(String collectionName, Map<String, Object> filter){
	    	BasicDBObject f = new BasicDBObject();
	    	f.putAll(filter);
	    	Document doc = db.getCollection(collectionName).find(f).first();
	    	if(doc != null){
	    		return toRecord(doc);
	    	}
	    	return null;
	    }
	  
	  
	  /**
	   * 根据条件获取一条记录（2015-11-11 韦家尚添加）
	   * @param collectionName
	   * @param filter
	   * @return
	   */
	  public static Record findOne(String collectionName, Map<String, Object> filter){
	 	return toRecord(MongoKit.getCollection(collectionName).findOne((toDBObject(filter))));
	  }
	  
/*	  public static Record findByConditons(String collectionName, Map<String, Object> filter){
		 	return toRecord(MongoKit.getCollection(collectionName).find((toDBObject(filter))));
		  }*/
	  //---------------------------
	  
	/**
	 * 查询第一条记录
	 * @param collectionName 表名
	 * @return
	 */
	public static Record findFirst(String collectionName) {
		return toRecord(MongoKit.getCollection(collectionName).findOne());
	}

	/**
	 * 分页查询
	 * @param collection 表名
	 * @param pageNumber 当前页
	 * @param pageSize 每页总条数
	 * @return
	 */
	public static Page<Record> paginate(String collection, int pageNumber, int pageSize) {
		return paginate(collection, pageNumber, pageSize, null, null, null);
	}

	/**
	 * 过滤的分页查询
	 * @param collection
	 * @param pageNumber
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	public static Page<Record> paginate(String collection, int pageNumber, int pageSize, Map<String, Object> filter) {
		return paginate(collection, pageNumber, pageSize, filter, null, null);
	}

	//过滤和模糊的分页查询
	public static Page<Record> paginate(String collection, int pageNumber, int pageSize, Map<String, Object> filter,
			Map<String, Object> like) {
		return paginate(collection, pageNumber, pageSize, filter, like, null);
	}
	

	public static Page<Record> paginate(String collection, int pageNumber, int pageSize, Map<String, Object> filter,
			Map<String, Object> like, Map<String, Object> sort) {
		DBCollection logs = MongoKit.getCollection(collection);
		BasicDBObject conditons = new BasicDBObject();
		buildFilter(filter, conditons);
		buildLike(like, conditons);
		DBCursor dbCursor = logs.find(conditons);
		page(pageNumber, pageSize, dbCursor);
		sort(sort, dbCursor);
		List<Record> records = new ArrayList<Record>();
		while (dbCursor.hasNext()) {
			records.add(toRecord(dbCursor.next()));
		}
		int totalRow = dbCursor.count();
		if (totalRow <= 0) {
			
			return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, 0, 0);
		}
		int totalPage = totalRow / pageSize;
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		Page<Record> page = new Page<Record>(records, pageNumber, pageSize, totalPage, totalRow);
		return page;
	}

	private static void page(int pageNumber, int pageSize, DBCursor dbCursor) {
		dbCursor = dbCursor.skip((pageNumber - 1) * pageSize).limit(pageSize);
	}

	private static void sort(Map<String, Object> sort, DBCursor dbCursor) {
		if (sort != null) {
			DBObject dbo = new BasicDBObject();
			Set<Entry<String, Object>> entrySet = sort.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object val = entry.getValue();
				dbo.put(key, "asc".equalsIgnoreCase(val + "") ? 1 : -1);
			}
			dbCursor = dbCursor.sort(dbo);
		}
	}

	private static void buildLike(Map<String, Object> like, BasicDBObject conditons) {
		if (like != null) {
			Set<Entry<String, Object>> entrySet = like.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object val = entry.getValue();
				conditons.put(key, MongoKit.getLikeStr(val));
			}
		}
	}

	private static void buildFilter(Map<String, Object> filter, BasicDBObject conditons) {
		if (filter != null) {
			Set<Entry<String, Object>> entrySet = filter.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object val = entry.getValue();
				conditons.put(key, val);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public static Record toRecord(DBObject dbObject) {
		Record record = new Record();
		record.setColumns(dbObject.toMap());
		return record;
	}
	
	public static Record toRecord(Document doc) {
        Record record = new Record();
        record.setColumns(doc);
        return record;
    }

	public static BasicDBObject getLikeStr(Object findStr) {
		Pattern pattern = Pattern.compile("^.*" + findStr + ".*$", Pattern.CASE_INSENSITIVE);
		return new BasicDBObject("$regex", pattern);
	}

	public static DB getDB() {
		return defaultDb;
	}

	public static DB getDB(String dbName) {
		return client.getDB(dbName);
	}

	public static DBCollection getCollection(String name) {
		return defaultDb.getCollection(name);
	}

	public static DBCollection getDBCollection(String dbName, String collectionName) {
		return getDB(dbName).getCollection(collectionName);
	}

	public static MongoClient getClient() {
		return client;
	}

	public static void setMongoClient(MongoClient client) {
		MongoKit.client = client;
	}

	private static BasicDBObject toDBObject(Map<String, Object> map) {
		BasicDBObject dbObject = new BasicDBObject();
		Set<Entry<String, Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object val = entry.getValue();
			dbObject.append(key, val);
		}
		return dbObject;
	}

	private static BasicDBObject toDbObject(Record record) {
		BasicDBObject object = new BasicDBObject();
		for (Entry<String, Object> e : record.getColumns().entrySet()) {
			object.append(e.getKey(), e.getValue());
		}
		return object;
	}
}
