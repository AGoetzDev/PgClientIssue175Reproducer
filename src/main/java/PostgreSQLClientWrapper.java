


import java.time.LocalDateTime;


import io.reactiverse.pgclient.PgClient;
import io.reactiverse.pgclient.PgConnection;
import io.reactiverse.pgclient.PgPool;
import io.reactiverse.pgclient.PgPoolOptions;
import io.reactiverse.pgclient.PgRowSet;
import io.reactiverse.pgclient.Row;
import io.reactiverse.pgclient.data.Json;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class PostgreSQLClientWrapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLClientWrapper.class);
	private DataBaseConfig dbConfig;
	private Vertx vertx;
	private PgPool client;

	public PostgreSQLClientWrapper(Vertx vertx, DataBaseConfig dbConfig) {
		this.vertx = vertx;
		this.dbConfig = dbConfig;
		this.setupClient();
	}
	
	private void setupClient(){
		if(this.client != null){
			this.client.close();
		}
		PgPoolOptions options = new PgPoolOptions()
				.setPort(dbConfig.getPort())
				.setHost(dbConfig.getHost())
				.setDatabase(dbConfig.getDatabase())
				.setUser(dbConfig.getUsername())
				.setPassword(dbConfig.getPassword())
				.setMaxSize(dbConfig.getMaxSize())
				.setTrustAll(true)
				.setCachePreparedStatements(false);

		this.client = PgClient.pool(vertx, options);
	}

	public Future<JsonArray> query(String sql) {
		Future<JsonArray> future = Future.future();
		getConnection().compose(connection -> {

			connection.exceptionHandler(h -> {
				LOGGER.error(h, h);
			});
			connection.query(sql, r -> {
				if (r.succeeded()) {
					try {
						PgRowSet rows = r.result();
						future.complete(convertRowSet(rows));
					} catch (Exception e) {
						future.fail(e);
					}
				} else {
					future.fail(r.cause());
				}
				connection.close();
			});
		}, future);
		
		return future;

	}
	
	
	
	private JsonArray convertRowSet(PgRowSet rows){
		JsonArray list = new JsonArray();
		for(Row row: rows){
			JsonObject json = new JsonObject();
			for(int i = 0; i<row.size(); i++){
				String columnName = row.getColumnName(i);
				Object value = row.getValue(i);
				
				if(value != null){
					LOGGER.info("ColumnName: "+columnName+" Value: "+value.toString());
					if(value instanceof Json){
						Json j = (Json) value;
						json.put(columnName, new JsonObject(j.toString()));
					} else if(value instanceof LocalDateTime){
						json.put(columnName,value.toString());
					} else if(value.getClass().isArray()){
						Object[] v = (Object[]) value;
						JsonArray array = new JsonArray();
						for(Object o: v){
							array.add(o);
						}
						json.put(columnName, array);
					} else {
						json.put(columnName, value);
					}
				}
				
			}
			list.add(json);
		}
		return list;
	}
	
	private Future<PgConnection> getConnection() {
		Future<PgConnection> future = Future.future();
		client.getConnection(h->{
			if(h.succeeded()){
				future.complete(h.result());
			} else {
				future.fail(h.cause());
			}
		});
		
		return future;
	}

}
