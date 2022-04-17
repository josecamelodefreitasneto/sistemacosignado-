/* react */
import BorderStyle from '../consts/enums/BorderStyle';
import Color from '../consts/fixeds/Color';

export default class Border {
	color = Color.cinzaClaro2;
	style = BorderStyle.solid;
	width = 0.5;
	withColor(o){this.color = o; return this;}
	withStyle(o){this.style = o; return this;}
	withWidth(o){this.width = o; return this;}
}
