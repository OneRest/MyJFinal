package wjs.Config;

import java.net.UnknownHostException;


import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import com.mongodb.MongoClient;

/**
 * Mongodb连接池
 * @author weijiashang
 *@date 2015年11月9日下午5:47:26
 */
public class MongodbPlugin implements IPlugin  {

	private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAUL_PORT = 27017;

    protected final Logger logger = Logger.getLogger(getClass());

    private MongoClient client;
    private String host;
    private int port;
    private String database;

    /**
     * 初始化 mongo连接池
     * @param database
     */
    public MongodbPlugin(String database) {
        this.host = DEFAULT_HOST;
        this.port = DEFAUL_PORT;
        this.database = database;
    }

    public MongodbPlugin(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }
	
	@Override
	public boolean start() {
		/* try {
	            client = new MongoClient(host, port);
	        } catch (UnknownHostException e) {
	        	
	            throw new RuntimeException("can't connect mongodb, please check the host and port:" + host + "," + port, e);

	        }*/
		client = new MongoClient(host, port);

	        MongoKit.init(client, database);
	        return true;
	}

	@Override
	public boolean stop() {
		 if (client != null) {
	            client.close();
	        }
	        return true;
	    }
	}


