package com.dubbo.test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.dubbo.apps.test.SampleFunction;
import com.dubbo.apps.thrift.Hello;
import com.dubbo.apps.thrift2.InvalidOperationException;
import com.dubbo.apps.thrift2.People;
import com.dubbo.apps.thrift2.SharedService;

@ContextConfiguration(locations = { "classpath:dubbo-services2.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ThriftTest {

	private static final AtomicInteger THRIFT_SEQ_ID = new AtomicInteger( 0 );
	
	@Resource
	private SharedService.Iface sharedService;
	
	@Resource
	private Hello.Iface hello;
	
	@Resource
	private SampleFunction sampleFunction;

	@Test
	public void test() {
		
//		try {
			
			
			for (int i=0; i<6; i++)
			{
//				Man man = sharedService.getStruct(1, new People(2,"名字@"+i,99,86475.387567));
//				System.out.println(man.toString());
				
				sampleFunction.processString("hi");
			}
			
			
			
//		} catch (InvalidOperationException e) {
//			
//			e.printStackTrace();
//			
//		} catch (TException e) {
//			
//			e.printStackTrace();
//			
//		}
//		
		
//		try {
//			hello.helloString("AA");
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//		
	}

	
	public static void main(String args[]) throws InvalidOperationException, TException, UnsupportedEncodingException
	{
		for (int i=0; i<1; i++)
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
						ReferenceConfig<SharedService.Iface> referenceConfig = new ReferenceConfig<SharedService.Iface>();
				        referenceConfig.setInterface(SharedService.Iface.class);
				        StringBuilder url = new StringBuilder();
				        url.append("dubbo://localhost:20002");
				        url.append("/");
				        url.append(SharedService.Iface.class.getName());
				        referenceConfig.setUrl(url.toString());
				        // hardcode 
				        referenceConfig.setConnections(new Integer(1));
				        ApplicationConfig application = new ApplicationConfig();
				        application.setName("dubbo_consumer");
				        referenceConfig.setApplication(application);
				        referenceConfig.setTimeout(new Integer(60000));
				        
				        for (int j=0; j<1; j++)
						{
					        SharedService.Iface ssi = referenceConfig.get();
					        
					        try {
								System.out.println(ssi.getStruct(1, new People(2,String.valueOf( THRIFT_SEQ_ID.getAndIncrement() ),99,86475.387567)));
							} catch (InvalidOperationException e) {
							} catch (TException e) {
							}
						}
					}
					
				
			}).start();
		}
		
		
	}
	
	
}
