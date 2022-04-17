/* react */
import AlignItens from '../consts/enums/AlignItens';
import BorderStyle from '../consts/enums/BorderStyle';
import Color from '../consts/fixeds/Color';
import Display from '../consts/enums/Display';
import FlexDirection from '../consts/enums/FlexDirection';
import JustifyContent from '../consts/enums/JustifyContent';
import Style from '../utils/Style';

export default class CommonStyles {}
CommonStyles.POINTER = Style.create().cursor("pointer");
CommonStyles.EMPTY = Style.create();
CommonStyles.BOLD = Style.create().bold(true).lock();
CommonStyles.W100P = Style.create().widthPercent(100).lock();
CommonStyles.H100P = Style.create().heightPercent(100).lock();
CommonStyles.W50P = Style.create().widthPercent(50).lock();
CommonStyles.W47P = Style.create().widthPercent(47).lock();
CommonStyles.DEFAULT_INPUT_STYLE = CommonStyles.W100P.copy().marginTop(20).lock();
CommonStyles.DRAG_AND_DROP = CommonStyles.W100P.copy().alignItems(AlignItens.center).lock();
CommonStyles.BOX
= Style.create()
.borderWidth(2)
.borderBottomWidth(4)
.borderRightWidth(4)
    .borderRadius(5)
    .borderColor(Color.cinzaClaro)
    .padding(10)
    .borderStyle(BorderStyle.solid)
    ;
CommonStyles.FLEX_COL
= Style.create()
.flexDirection(FlexDirection.column)
.justifyContent(JustifyContent.center)
.alignItems(AlignItens.center)
.heightPercent(100)
.widthPercent(100)
.display(Display.flex)
	;
CommonStyles.FLEX_ROW
= Style.create()
.flexDirection(FlexDirection.row)
.justifyContent(JustifyContent.center)
.alignItems(AlignItens.center)
.heightPercent(100)
.widthPercent(100)
.display(Display.flex)
	;
CommonStyles.MODAL =
	Style.create()
	.padding(15)
	.backgroundColor(Color.transparentCinza)
	.heightPercent(100)
	;
CommonStyles.CONTEUDO_MODAL =
	Style.create()
	.padding(10)
	.backgroundColor(Color.brancoCooper)
	.borderRadius(10)
	.widthPercent(100)
	.borderWidth(0.5)
	;
CommonStyles.CONTAINER_GRID =
	CommonStyles.W100P.copy()
	.flexDirection(FlexDirection.row)
	.justifyContent(JustifyContent.spaceBetween)
	.lock()
	;
CommonStyles.POPUP = Style.create().backgroundColor(Color.brancoCooper).margin(20).padding(20).borderRadius(10);
