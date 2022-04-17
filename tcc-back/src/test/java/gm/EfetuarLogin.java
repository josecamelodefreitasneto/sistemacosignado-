package gm;

import java.util.HashMap;
import java.util.Map;

import gm.utils.map.MapSO;
import gm.utils.rest.URest;

public class EfetuarLogin {

	public static void main(String[] args) {
		Map<String, String> headers = null;
		MapSO params = new MapSO().add("login", "gerente@sysconsig.com").add("pass","123456");
		URest post = URest.post("http://localhost:8080/login/efetuar", headers, params);
		MapSO postLogin = post.getData();
		
		
//		MapSO m = ClienteUtils.getNovoPreenchido();
		
		params.clear();
		params.put("id", "teste");
//		params.setObrig("token", postLogin.getString("token"));
		
		headers = new HashMap<String, String>();
		headers.put("token", postLogin.getString("token"));
		
		String url = "http://localhost:8080/cliente/consultar";
		URest.get(url, headers, params).print();
		
	}
	
}
