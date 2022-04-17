/* tcc-java */
import config_properties from './config.properties';
import icon_png from './icon.png';
import login_jpg from './login.jpg';
import loginescuro_png from './loginescuro.png';
import money_png from './money.png';
import percent_png from './percent.png';
import user_png from './user.png';

export default class Resources {}
Resources.config = config_properties;
Resources.icon = icon_png;
Resources.login = login_jpg;
Resources.loginescuro = loginescuro_png;
Resources.money = money_png;
Resources.percent = percent_png;
Resources.user = user_png;
Resources.map =
		new Map()
		.set("config", Resources.config)
		.set("icon", Resources.icon)
		.set("login", Resources.login)
		.set("loginescuro", Resources.loginescuro)
		.set("money", Resources.money)
		.set("percent", Resources.percent)
		.set("user", Resources.user)
		;
Resources.list = [
	Resources.config
	, Resources.icon
	, Resources.login
	, Resources.loginescuro
	, Resources.money
	, Resources.percent
	, Resources.user
];
