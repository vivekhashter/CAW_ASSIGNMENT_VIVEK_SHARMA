package tests;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Test;

import Pages.HerokuPage;


public final class HerokuTests extends BaseTest {
	
    @Test
	public void herokuTableInsert() throws InterruptedException, IOException {
		HerokuPage hp = new HerokuPage();	
		hp.clickonLogin();
		hp.tableMatch();
		
	}
}
