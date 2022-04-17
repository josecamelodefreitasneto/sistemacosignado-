/* front-constructor */
export default class Coluna {

	id;
	grupo = false;
	sorted = false;
	width = 0;
	cols = 0;
	title;
	get;
	textAlign;
	itensFiltrados = [];
	sort;
	renderItem;
	noClick = false;

	onEdit(o) {}
	constructor(widthP, titleP,getP, textAlign) {
		this.width = widthP;
		this.title = titleP;
		this.get = getP;
		this.textAlign = textAlign;
	}
	setCols(value) {
		this.cols = value;
		return this;
	}
	setSort(value) {
		this.sort = value;
		return this;
	}
	setGrupo(value) {
		this.grupo = value;
		return this;
	}
	setId(value) {
		this.id = value;
		return this;
	}
	setRenderItem(value) {
		this.renderItem = value;
		return this;
	}
}
