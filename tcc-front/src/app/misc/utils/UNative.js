/* react */
const UNative = {};
UNative.asyncPromise = async (promise, then) => {
	const o = await promise;
	then(o);
}
UNative.getAtributo = (o, key) => o[key];
UNative.setAtributo = (o, key, value) => o[key] = value;
UNative.inJava = false;
UNative.asArray = o => o;
export default UNative;
