package gm.utils.test;

import org.junit.Test;

import gm.utils.comum.UAssert;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;

public class MapSoFromJsonTest {

	@Test
	public void t1() {
		final MapSO o = MapSoFromJson.get("{  \"login\" : \"a@a.com\", \"pass\" : \"123456\"}");
		UAssert.eq(o.getObrig("login"), "a@a.com", "");
		UAssert.eq(o.getObrig("pass"), "123456", "");
	}

	@Test
	public void t2() {
		final MapSO o = MapSoFromJson.get("{\"a\":\"a \n b\"}");
		UAssert.eq(o.getObrig("a"), "a \n b", "");
	}
	
	public static void main(final String[] args) {
		final MapSoFromJsonTest o = new MapSoFromJsonTest();
		o.t1();
		o.t2();
	}
	
}
