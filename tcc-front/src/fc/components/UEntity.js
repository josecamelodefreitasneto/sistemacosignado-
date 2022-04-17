/* front-constructor */
import UCommons from '../../app/misc/utils/UCommons';
import UInteger from '../../app/misc/utils/UInteger';

export default class UEntity {

	static compareId(a, b) {
		if (UCommons.isEmpty(a)) {
			if (UCommons.isEmpty(b)) {
				return 0;
			} else {
				return -1;
			}
		} else if (UCommons.isEmpty(b)) {
			return 1;
		} else {
			return UInteger.compare(a.id, b.id);
		}
	}

	static equalsId(a, b) {
		return UEntity.compareId(a, b) === 0;
	}

}
