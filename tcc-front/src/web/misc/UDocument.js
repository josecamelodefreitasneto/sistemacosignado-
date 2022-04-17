/* react-web */
export default class UDocument {
	static querySelectorAll(s) {
		const list = document.querySelectorAll(s);
		const elems = [];
		list.forEach(o => elems.push(o));
		return elems;
	}
}
