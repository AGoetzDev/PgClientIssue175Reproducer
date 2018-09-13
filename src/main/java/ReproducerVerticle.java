
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;



public class ReproducerVerticle extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReproducerVerticle.class);
	
	
	private static final String SELECT_WORKING = "SELECT id, text, \"array\" FROM arrayissue1";
	private static final String SELECT_WORKING_NULLVALUE = "SELECT id, \"array\", text FROM arrayissue2";
	private static final String SELECT_NOTWORKING_EXCEPTION = "SELECT id, \"array\", text FROM arrayissue1";
	
	private PostgreSQLClientWrapper client;
	
	
	@Override
	public void start(Future<Void> startFuture) throws Exception {
		
		
		client = new PostgreSQLClientWrapper(vertx, DataBaseConfig.fromJson(config().getJsonObject("dbConfig")));
		this.testSelectWorking().compose(v->{
			return this.testSelectWorkingNullValue();
		}).compose(v->{
			this.testSelectNotWorking().setHandler(h->{
				startFuture.complete();
			});
		}, startFuture);

	}
	
	//Select from table where text column is before the Long[] column and Long[] column is not null
	//=> works
	private Future<Void> testSelectWorking(){
		Future<Void> result = Future.future();
		client.query(SELECT_WORKING).setHandler(h->{
			if(h.succeeded()){
				LOGGER.info("Result of testSelectWorking():");
				LOGGER.info(h.result().encodePrettily());
				result.complete();
			} else {
				result.fail(h.cause());
			}
		});
		return result;
	}
	
	//Select from table where text column is after the Long[] column and long[] is null
	//=> works
	private Future<Void> testSelectWorkingNullValue(){
		Future<Void> result = Future.future();
		client.query(SELECT_WORKING_NULLVALUE).setHandler(h->{
			if(h.succeeded()){
				LOGGER.info("Result of testSelectWorkingNullValue():");
				LOGGER.info(h.result().encodePrettily());
				result.complete();
			} else {
				result.fail(h.cause());
			}
		});
		return result;
	}
	
	//Select from table where text column is after the Long[] column and Long[] column is not null
	//=> doesn't work, throws index out of bound exception
	private Future<Void> testSelectNotWorking(){
		Future<Void> result = Future.future();
		client.query(SELECT_NOTWORKING_EXCEPTION).setHandler(h->{
			if(h.succeeded()){
				LOGGER.info("Result of testSelectNotWorkingAccessByIndex():");
				LOGGER.info(h.result().encodePrettily());
				result.complete();
			} else {
				result.fail(h.cause());
			}
		});
		return result;
	}

}
