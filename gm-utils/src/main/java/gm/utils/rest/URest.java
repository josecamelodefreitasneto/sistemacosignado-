package gm.utils.rest;

import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import gm.utils.exception.UException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromJson;
import gm.utils.map.MapSoToJson;
import gm.utils.outros.Box;
import gm.utils.string.StringBox;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class URest {

    private MapSO data;
    private int code;
    private String message;
    private String cookies;
    
    public static void main(final String[] args) {
//		get("http://localhost:8080/mobile/service/gerenciamento/controle/disponibilidade", null).print();
//		get("http://localhost:8080/mobile/service/convenios/conveniosEducacionais", null).print();
//		post("http://localhost:8080/mobile/service/logon96", null).print();
		final URest o = URest.post("http://localhost:8080/mobile/service/logon2019", (Map<String, String>) null, new MapSO().add("username", "=96").add("password","123456"));
		o.data.getSub("associado").print();
	}
    
    private static Map<String, String> getMapStringString(final GetMapStringString o) {
    	return o == null ? null : o.getMapStringString();
    }
    
	public static URest post(final String url, final GetMapStringString headers, final MapSO dados) {
    	return URest.post(url, URest.getMapStringString(headers), dados);
    }
	public static URest get(final String url, final GetMapStringString headers, final MapSO dados) {
    	return URest.get(url, URest.getMapStringString(headers), dados);
    }
	public static URest post(final String url, final Map<String, String> headers, final String dados) {
		return post(url, headers, dados, null);
	}
	public static URest post(final String url, final Map<String, String> headers, final String dados, FTT<String, String> tratarReader) {
		return new URest(url, headers, dados, true, tratarReader);
	}
	public static URest post(final String url, final Map<String, String> headers, final MapSO dados) {
    	return post(url, headers, dados, null);
    }
	public static URest post(final String url, final Map<String, String> headers, final MapSO dados, FTT<String, String> tratarReader) {
		return new URest(url, headers, dados, true, tratarReader);
	}
    public static URest get(final String url, final Map<String, String> headers, final MapSO dados) {
    	return get(url, headers, dados, null);
    }
    public static URest get(final String url, final Map<String, String> headers, final MapSO dados, FTT<String, String> tratarReader) {
    	return new URest(url, headers, dados, false, tratarReader);
    }
    public static URest get(final String url, final Map<String, String> headers, final String dados) {
    	return get(url, headers, dados, null);
    }
    public static URest get(final String url, final Map<String, String> headers, final String dados, FTT<String, String> tratarReader) {
    	return new URest(url, headers, dados, false, tratarReader);
    }
    

    private static String convertDados(Map<String, String> headers, final MapSO dados, final boolean post) {
    	if (dados == null || dados.isEmpty()) {
			return null;
		}
    	
		boolean form = headers != null && headers.get("Content-Type") != null && "application/x-www-form-urlencoded".contentEquals(headers.get("Content-Type"));
    	
    	if (post && !form) {
    		
    		try {
    			return new String(MapSoToJson.get(dados).getBytes("UTF-8"), "UTF-8");
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
    		
		} else {
			
			final StringBox box = new StringBox();
			
			dados.forEach((key, value) -> {
				box.add("&" + key + "=" + value);
			});
			
			String s = box.get().substring(1);
			
			if (post) {
				byte[] postData = s.getBytes( StandardCharsets.UTF_8 );
				int postDataLength = postData.length;
				headers.put("charset", "utf-8");
				headers.put("Content-Length", Integer.toString(postDataLength));
				return s;
			} else {
				return "?" + s;
			}
			
		}
    	
    }
    
    private URest(final String url, final Map<String, String> headers, final MapSO dados, final boolean post, FTT<String, String> tratarReader) {
    	this(url, headers, URest.convertDados(headers, dados, post), post, tratarReader);
    }
	private URest(String url, final Map<String, String> headers, final String dados, final boolean post, FTT<String, String> tratarReader) {
		
		final CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		
		if (!post && dados != null) {
			url += "?" + dados;
		}
		
		final Box<HttpURLConnection> box = new Box<>();

		try {
			
			MapSO finalHeaders = new MapSO();
			finalHeaders.put("Content-Type", "application/json; charset=UTF-8");
			finalHeaders.put("Accept", "*/*");

			if (headers != null) {
				headers.forEach((key, value) -> finalHeaders.put(key, value));
			}
			
			final URL obj = new URL(url);
			box.set((HttpURLConnection) obj.openConnection());
			box.get().setDoOutput(true);
			box.get().setRequestMethod(post ? "POST" : "GET");
			finalHeaders.forEach((k,v) -> box.get().setRequestProperty(k, v.toString()));

			if (post && dados != null) {
				final OutputStreamWriter out = new OutputStreamWriter(box.get().getOutputStream());
				out.write(dados);
				out.close();
			}
			
			String reader;
			
			try {
				reader = IOUtils.toString(box.get().getInputStream(), "UTF-8");
			} catch (final Exception e) {
				reader = IOUtils.toString(box.get().getErrorStream(), "UTF-8");
			}
			
			if (tratarReader != null) {
				reader = tratarReader.call(reader);
			}
			
			if (UString.isEmpty(reader)) {
				this.data = null;				
			} else {

				if (!reader.contains("{")) {
					reader = "{result:"+reader+"}";
				}
				
				this.data = MapSoFromJson.get(reader);
				
			}
			
			this.code = box.get().getResponseCode();
			this.message = box.get().getResponseMessage();
			
			final List<HttpCookie> cookies2 = cookieManager.getCookieStore().getCookies();
			
			if (cookies2.isEmpty()) {
				this.cookies = null;	
			} else {
				this.cookies = "";
				cookies2.forEach(o -> this.cookies += ";"+o);
				this.cookies = this.cookies.substring(1);
			}
			
			if (this.code < 200 || this.code > 299) {
				throw new ResponseException(this.message, this.code, this.data);
			}
			
		} catch (final ResponseException e) {
			throw e;
		} catch (final Exception e) {
			throw UException.runtime(e);
		} finally {
			if (box.isNotNull()) {
				box.get().disconnect();
			}
		}
	}

	public void print() {
		System.out.println("---------------------");
		System.out.println("statusCode: " + this.code);
		this.getData().print();
	}

}
