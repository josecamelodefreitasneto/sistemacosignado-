/* react */
export default class UBinding {

	static allInvisibles(list) {
		return list.filter(o => o.isVisible()).isEmpty();
	}

}
