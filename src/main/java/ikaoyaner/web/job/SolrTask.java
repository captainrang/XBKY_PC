package ikaoyaner.web.job;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

public class SolrTask {
	
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	/**
	 * 使用solr的过程中，需要定时执行solr的optimize函数来清理磁盘碎片，否则会影响读写效率。对于optimize的参数建议为(false,false,5)
	 */
	public void test(){
		try {
			httpSolrServer.optimize(false, false, 5);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
