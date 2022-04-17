/* react-web-antd */
export default class BotaoShape {
	s;
	constructor(s) {this.s = s;}
}
BotaoShape.circle = new BotaoShape("circle");
BotaoShape.round = new BotaoShape("round");
BotaoShape.def = new BotaoShape(null);
