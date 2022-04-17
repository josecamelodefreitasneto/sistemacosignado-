/* react */
import UNative from './UNative';

export default class UFile {

	static getBase64FromEvent(e, then) {
		UFile.getBase64FromFile(e.target.files[0], then);
	}

	static getBase64FromFile(file, then) {

		const promise =
		new Promise((resolve, reject) => {
			const reader = new FileReader();
		 reader.onloadend = () => {
		 	resolve(reader.result);
		 };
		 reader.onerror = reject;
		 reader.readAsDataURL(file);
		});

		UNative.asyncPromise(promise, then);

	}

}
