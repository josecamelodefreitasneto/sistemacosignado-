/* react */
export default class Console {

	static log(key, value) {
		console.log(key, value);
	}

	static error(key, value) {
		console.error(key, value);
	}

	static disableYellowBox() {
		console.disableYellowBox = true;
	}

}
