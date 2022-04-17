/* front-constructor */
import Post from '../../projeto/Post';

export default class SearchService {

	url;
	getOutrosParams;

	constructor(url, getOutrosParams) {
		this.url = url;
		this.getOutrosParams = getOutrosParams;
	}

	exec(filtro, callback) {
		const params = {};
		params.text = filtro;
		if (this.getOutrosParams !== null) {
			params.outrosParams = this.getOutrosParams();
		}
		new Post(this.url, params, res => {
			let result = res.body;
			res.body = result.dados;
			callback(res);
		}).run();
	}

}
