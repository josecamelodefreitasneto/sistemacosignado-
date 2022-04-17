/* react-web-antd */
export default class BotaoType {
	s;
	constructor(s) {this.s = s;}
}
BotaoType.normal = new BotaoType(null);
BotaoType.dashed = new BotaoType("dashed");
BotaoType.primary = new BotaoType("primary");
BotaoType.danger = new BotaoType("danger");
BotaoType.link = new BotaoType("link");
