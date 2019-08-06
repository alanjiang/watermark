package com.hanniu.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
@Configuration
@PropertySource(value = "classpath:application.properties")
public class CommonConfig {
	@Value("${http.pool.connection.max}")
	 private int http_pool_conn_max;
	 @Value("${http.route.per.max}")
	 private int http_route_per_max;
	 @Value("${http.keep.alive.time}")
	 private int http_keep_alive_time;
     @Value("${http.request.timeout}")
	 private int http_request_timeout;
     @Value("${http.connection.timeout}")
	 private int http_conn_timeout;
     @Value("${http.socket.timeout}")
	 private int http_socket_timeout;
     @Value("${http.error.retry.times}")
     private int http_error_retry_times;
     //end of http pool
     
      
      
      @Value("${libreoffice.tool.install}")
      private String libreOfficeToolInstall;
    		 
      @Value("${libreoffice.scrip.path}")
      private String libreOfficeScript;
     
       @Bean
	   PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		    PoolingHttpClientConnectionManager poolHttpManager=new PoolingHttpClientConnectionManager();
			poolHttpManager.setMaxTotal(http_pool_conn_max);
			poolHttpManager.setDefaultMaxPerRoute(http_route_per_max);
			return poolHttpManager;
	   }
	   
	   @Bean 
	   ConnectionKeepAliveStrategy keepAliveStrategy() {
		    
		    	return (HttpResponse response, HttpContext context)->{
		               HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
		           while (it.hasNext()) {
		                HeaderElement he = it.nextElement();
		                String param = he.getName();
		                String value = he.getValue();
		                if (value != null && param.equalsIgnoreCase("timeout")){
		                    return Long.parseLong(value) * 1000;
		                }
		            }
		                return http_keep_alive_time;
		    	  };   
	   }
	   
	   @Bean
	   RequestConfig requestConfig() {
		   return RequestConfig.custom()  
		            .setConnectionRequestTimeout(http_request_timeout)  
		            .setConnectTimeout(http_conn_timeout)  
		            .setSocketTimeout(http_socket_timeout)
		            .build();  
	   }
	   
	   
	  //支持HTTP、HTTPS
	   @Bean
	   public CloseableHttpClient httpClient(RequestConfig requestConfig,PoolingHttpClientConnectionManager connectionManager)
	   throws Exception
	   {
	      
		   Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
		           .register("http", PlainConnectionSocketFactory.getSocketFactory())
		           .register("https", SSLConnectionSocketFactory.getSocketFactory())
		           .build();
		    
		   
		   
		   CloseableHttpClient httpclient=HttpClientBuilder.create()
				   
				   .setDefaultRequestConfig(requestConfig)
			       .setConnectionManager(connectionManager)
			       .setRetryHandler(new DefaultHttpRequestRetryHandler(http_error_retry_times, false)).
				   build();
		   return httpclient;
		   
	   }

	   @Bean
	   HttpComponentsClientHttpRequestFactory clientHttpRequestFactory ( CloseableHttpClient httpClient) {
	       return new HttpComponentsClientHttpRequestFactory(httpClient);
	   }
	  
	    
	   @Bean
	   public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory clientHttpRequestFactory) {
	       RestTemplate restTemplate = new RestTemplate();
	          
	      
	       restTemplate.setRequestFactory(clientHttpRequestFactory);
	       restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
	       restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	       return restTemplate;
	   }
	   /****end Http Client *****/
	 

	   //libreoffice
	   @Value("${libreoffice.file.temp.world.input}")
	   private String libreFileInput;
	   
	   @Value("${libreoffice.file.temp.pdf.output}")
	   private String libreFileOutput;
	   
	  
	  
	   @Bean
	   @Qualifier("libreFileInput")
	   public String libreFileInput() {
		   return libreFileInput;
	   }
	   
	   @Bean
	   @Qualifier("libreFileOutput")
	   public String libreFileOutput() {
		   return libreFileOutput;
	   }
	   
	   //libreoffice工具所在路径
	   @Bean
	   @Qualifier("libreOfficeToolInstall")
	   public String libreOfficeToolInstall() {
		   return libreOfficeToolInstall;
	   }
	   
	   //转换脚本路径
	   @Bean
	   @Qualifier("libreOfficeScript")
	   public String libreOfficeScript() {
		   return libreOfficeScript;
	   }
	   
	   @Value("${libreoffice.pdf.trans.timeout}")
	   private Integer libreofficeTransTimeout;
	 
	   @Bean
	   @Qualifier("libreofficeTransTimeout")
	   public Integer libreofficeTransTimeout() {
		   return libreofficeTransTimeout;
	   }
}
