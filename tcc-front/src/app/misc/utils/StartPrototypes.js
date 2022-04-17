/* react */
import UArray from './UArray';
import UMap from './UMap';
import UString from './UString';

export default class StartPrototypes {
	static exec () {}
}
const get = (Classe) => {
	return Classe.prototype;
}
get(Array).add = function(o, index) {
	return UArray.add(this, o, index);
}
get(Array).addIfNotContains = function(o) {
	return UArray.addIfNotContains(this, o);
}
get(Array).byId = function(id) {
	return UArray.byId(this, id);
}
get(Array).clear = function() {
	return UArray.clear(this);
}
get(Array).contains = function(value) {
	return UArray.contains(this, value);
}
get(Array).copy = function() {
	return UArray.copy(this);
}
get(Array).distinct = function(func) {
	return UArray.distinct(this, func);
}
get(Array).equals = function(b, comparator) {
	return UArray.equals(this, b, comparator);
}
get(Array).exists = function(func) {
	return UArray.exists(this, func);
}
get(Array).getLast = function() {
	return UArray.getLast(this);
}
get(Array).isEmpty = function() {
	return UArray.isEmpty(this);
}
get(Array).notEmpty = function() {
	return UArray.notEmpty(this);
}
get(Array).pushNotEmpty = function(o) {
	return UArray.pushNotEmpty(this, o);
}
get(Array).remove = function(index) {
	return UArray.remove(this, index);
}
get(Array).removeFirst = function() {
	return UArray.removeFirst(this);
}
get(Array).removeIf = function(predicate) {
	return UArray.removeIf(this, predicate);
}
get(Array).removeLast = function() {
	return UArray.removeLast(this);
}
get(Array).removeObject = function(o) {
	return UArray.removeObject(this, o);
}
get(Array).size = function() {
	return UArray.size(this);
}
get(Array).sortByIdReverse = function() {
	return UArray.sortByIdReverse(this);
}
get(Array).sortByIntegerKey = function(key) {
	return UArray.sortByIntegerKey(this, key);
}
get(Array).sortByIntegerKeyReverse = function(key) {
	return UArray.sortByIntegerKeyReverse(this, key);
}
get(Array).unique = function(predicate) {
	return UArray.unique(this, predicate);
}
get(Array).uniqueObrig = function(predicate) {
	return UArray.uniqueObrig(this, predicate);
}
get(Map).asString = function(separador) {
	return UMap.asString(this, separador);
}
get(Map).containsValue = function(value) {
	return UMap.containsValue(this, value);
}
get(Map).reduce = function(func, initial) {
	return UMap.reduce(this, func, initial);
}
get(String).contains = function(x) {
	return UString.contains(this, x);
}
get(String).equals = function(b) {
	return UString.equals(this, b);
}
get(String).equalsIgnoreCase = function(b) {
	return UString.equalsIgnoreCase(this, b);
}
get(Array).get = function(index) {
	return this[index];
}
try {
	get(TouchList).get = function(index) {
		return this[index];
	}
} catch (error) {}
