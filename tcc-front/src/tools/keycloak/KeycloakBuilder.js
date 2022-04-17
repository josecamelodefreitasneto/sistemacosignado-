/* react */
import Keycloak from 'keycloak-js';
import ReactDOM from 'react-dom';
import UCommons from '../../app/misc/utils/UCommons';
import UNative from '../../app/misc/utils/UNative';

export default class KeycloakBuilder {

	realm_ = "cooperforte";
	authServerUrl_ = "cooperforte";
	sslRequired_ = "none";
	resource_;
	publicClient_ = true;
	confidentialPort_ = 0;
	clientId_;

	realm(value) {
		this.realm_ = value;
		return this;
	}
	authServerUrl(value) {
		this.authServerUrl_ = value;
		return this;
	}
	sslRequired(value) {
		this.sslRequired_ = value;
		return this;
	}
	resource(value) {
		this.resource_ = value;
		return this;
	}
	publicClient(value) {
		this.publicClient_ = value;
		return this;
	}
	confidentialPort(value) {
		this.confidentialPort_ = value;
		return this;
	}
	clientId(value) {
		this.clientId_ = value;
		return this;
	}
	build(func) {

		if (UCommons.isEmpty(UNative.getAtributo(window, "Keycloak"))) {
			window.location.href = "/";
		}

		let o = {};
		o["realm"] = this.realm_;
		o["auth-server-url"] = this.authServerUrl_;
		o["ssl-required"] = this.sslRequired_;
		o["resource"] = this.resource_;
		o["public-client"] = this.publicClient_;
		o["confidential-port"] = this.confidentialPort_;
		o["clientId"] = this.clientId_;
		let obj = new Keycloak(o);
		obj.init({onLoad: "login-required", promiseType: "native"}).then(authenticated => {
			if (authenticated) {
				ReactDOM.render(func(), document.getElementById("root"));
			}
		});
		return obj;
	}
}
