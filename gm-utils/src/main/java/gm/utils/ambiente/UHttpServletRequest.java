package gm.utils.ambiente;

import javax.servlet.http.HttpServletRequest;

import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UHttpServletRequest {
	
	private HttpServletRequest req;

	public UHttpServletRequest(HttpServletRequest req) {
		this.req = req;
	}
	
	public String getHeader(String s) {
		s = req.getHeader(s);
		if (UString.isEmpty(s) || "unknown".equalsIgnoreCase(s)) {
			return null;
		} else {
			return s;
		}
	}
	private String getPrimeiroHeaderNaoNulo(ListString list) {
		for (String s : list) {
			s = getHeader(s);
			if (UString.notEmpty(s)) {
				return s;
			}
		}
		return null;
	}

	private static final ListString IP_HEADERS = ListString.array(
		"X-Forwarded-For", "ip_client", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
	);
	public String getIp() {
		String s = getPrimeiroHeaderNaoNulo(IP_HEADERS);
		if (UString.isEmpty(s)) {
			return req.getRemoteAddr();
		} else {
			return s;
		}
	}
	
	private static final ListString NAVEGADOR_HEADERS = ListString.array(
		"HTTP-X-OPERAMINI-PHONE-UA", "HTTP-X-SKYFIRE-PHONE", "HTTP-USER-AGENT", "USER-AGENT"
	);
	public String getNavegador() {
		if (getHeader("os-version") != null || getHeader("device-model") != null) {
			return getHeader("device-model") + " " + getHeader("os-version");
		} else {
			return getPrimeiroHeaderNaoNulo(NAVEGADOR_HEADERS);
		}
	}
	
	
}
