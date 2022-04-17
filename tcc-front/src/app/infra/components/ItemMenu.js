/* tcc-java */
export default class ItemMenu {

	titulo;
	url;
	getIcone;
	getRender;

	constructor(titulo, url, getIcone, getRender) {
		this.titulo = titulo;
		this.url = url;
		this.getIcone = getIcone;
		this.getRender = getRender;
	}

}
